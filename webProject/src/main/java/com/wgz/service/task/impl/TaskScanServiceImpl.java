package com.wgz.service.task.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wgz.base.Constants;
import com.wgz.base.ResultCode;
import com.wgz.base.TaskRunException;
import com.wgz.dao.TaskMapper;
import com.wgz.dao.TaskRunMapper;
import com.wgz.dao.TaskSubMapper;
import com.wgz.pojo.TaskInfo;
import com.wgz.pojo.TaskRunInfo;
import com.wgz.pojo.TaskSubInfo;
import com.wgz.service.task.TaskBaseService;
import com.wgz.service.task.TaskScanService;
import com.wgz.utils.CronUtil;
import com.wgz.utils.DateUtils;
import com.wgz.utils.IdGenerator;
import com.wgz.utils.SpringContextUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("taskScanService")
public class TaskScanServiceImpl implements TaskScanService{
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private TaskSubMapper taskSubMapper;
	@Autowired
	private TaskRunMapper taskRunMapper;
	
	ScheduledExecutorService ses = new ScheduledThreadPoolExecutor(10);
	
	@Override
	public void doScan() {
		log.info("查询任务主表...");
		String currentTime = DateUtils.formatLocalDateTimeToString(LocalDateTime.now(),"yyyy-MM-dd HH:mm:ss");
		/**	
		 *查询出需要执行的任务
		 *条件：enable_flag 任务启用，start_time 在当前执行时间内，task_status 任务没有在执行中
		 */
		List<TaskInfo> taskInfos = taskMapper.selectList(
		        new QueryWrapper<TaskInfo>().eq("enable_flag", Constants.ENABLE_FLAG_1).lt("start_time", currentTime).ne("task_status", Constants.TASK_STATUS_PROCESS));
		
		taskInfos.forEach(taskInfo -> {
			try {
				//将此任务的状态置为执行中:PROCESS
				taskInfo.setTaskStatus(Constants.TASK_STATUS_PROCESS);
				log.info("更新主任务表状态");
				taskMapper.updateById(taskInfo);
				
				//调度执行主任务
				ExecuteTask executeTask = new ExecuteTask();
				executeTask.setModel(taskInfo);
				ses.execute(executeTask);
			} catch(Exception e) {
				log.error("执行任务task_id为"+taskInfo.getTaskId()+"异常:"+e.toString());
			}
		});
	}
	
	
	/**
	 * @Description:执行主任务对应的子任务
	 */
	public class ExecuteTask implements Runnable{
		private TaskInfo model;
		public void setModel(TaskInfo model) {
			this.model = model;
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void run() {
			String currentTime = DateUtils.formatLocalDateTimeToString(LocalDateTime.now(),"yyyy-MM-dd HH:mm:ss");
			try {
				log.info("开始执行"+model.getTaskId()+"的子任务");
				
				/**
				 *根据主任务id查询子任务
				 *条件：task_id 主任务id，enable_flag 任务启用
				 */
				List<TaskSubInfo> taskSubInfos = taskSubMapper.selectList(new QueryWrapper<TaskSubInfo>().eq("task_id", model.getTaskId()).eq("enable_flag", Constants.ENABLE_FLAG_1));
				
				//记录任务执行进度 task_run_info
				recordTaskRunInfo(taskSubInfos, model.getStartTime());
				
				//调度执行顺序为0的子任务，执行顺序为0的子任务和其他子任务无业务关联
				Iterator<TaskSubInfo> it = taskSubInfos.iterator();
				while(it.hasNext()){
					TaskSubInfo taskSubInfo = it.next();
					if(taskSubInfo.getExeSeq() == 0){
						ExecuteSbuTask eSubTask = new ExecuteSbuTask();
						eSubTask.setModel(taskSubInfo);
						eSubTask.setStartTime(model.getStartTime());
						ses.execute(eSubTask);
						it.remove();
					}
				}
				
				log.info("根据执行顺序排序子任务");
				Collections.sort(taskSubInfos,new Comparator () {
					@Override
					public int compare(Object o1, Object o2) {
						if(o1 instanceof TaskSubInfo && o2 instanceof TaskSubInfo){
							TaskSubInfo t1 = (TaskSubInfo) o1;
							TaskSubInfo t2 = (TaskSubInfo) o2;
							return t1.getExeSeq().compareTo(t2.getExeSeq());
						}
						throw new ClassCastException("不能转换为taskSubInfo类型");
					}
				});
				
				//按照顺序执行子任务
				executeTaskSub(taskSubInfos, model.getStartTime());
				
			} catch (Exception e) {
				log.error("执行主任务id为"+model.getTaskId()+"异常:"+e.toString());
			} finally {
				//更新主任务状态为INIT和下次执行的start_time
				log.info("更新主任务状态和start_time");
				TaskInfo taskInfo = taskMapper.selectById(model.getId());
				taskInfo.setTaskStatus(Constants.TASK_STATUS_INIT);
				taskInfo.setUpdatedTime(DateUtils.formatLocalDateTimeToString(LocalDateTime.now(),"yyyy-MM-dd HH:mm:ss"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					String fristTime = CronUtil.getNextTimePoint(taskInfo.getTaskCron(),taskInfo.getStartTime());
					String secendTime = CronUtil.getNextTimePoint(taskInfo.getTaskCron(),fristTime);
					Long corn = sdf.parse(secendTime).getTime() - sdf.parse(fristTime).getTime();
					Long run = sdf.parse(taskInfo.getUpdatedTime()).getTime() - sdf.parse(currentTime).getTime();
					if(run > corn) {
						//任务运行时间比一个任务周期长，设置超长时间标识
						taskInfo.setOutTimeFlag(1);
					}
					taskInfo.setStartTime(CronUtil.getNextTimePoint(taskInfo.getTaskCron()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				taskMapper.updateById(taskInfo);
			}
		}
	}
	
	/**    
	 * @Description: 初始化记录任务执行进度
	 */ 
	private void recordTaskRunInfo(List<TaskSubInfo> taskSubInfos,String startTime) {
		log.info("初始化记录任务执行进度...");
		for(TaskSubInfo taskSubInfo : taskSubInfos) {
			TaskRunInfo taskRunInfo = getTaskRunInfo(taskSubInfo,startTime);
			taskRunMapper.insert(taskRunInfo);
		}
	}
	
	/**
	 * @Description:子任务调度执行
	 * @author: wenguozhang 
	 * @date:   2019年5月29日 下午5:45:56  
	 */
	public class ExecuteSbuTask implements Runnable{
		private TaskSubInfo model;
		private String startTmie;
		
		public void setModel(TaskSubInfo model) {
			this.model = model;
		}
		public void setStartTime(String startTmie) {
			this.startTmie = startTmie;
		}
		@Override
		public void run() {
			List<TaskSubInfo> taskSubInfos = new ArrayList<TaskSubInfo>();
			taskSubInfos.add(model);
			try {
				executeTaskSub(taskSubInfos, startTmie);
			} catch (Exception e) {
				log.error("执行子任务t("+model.getTaskSubName()+")异常:"+e.toString());
			}
		}
	}
	
	/**    
	 * @throws Exception 
	 * @Description: 顺序执行子任务
	 */ 
	private void executeTaskSub(List<TaskSubInfo> taskSubInfos, String start_time) throws Exception {
		for(TaskSubInfo taskSubInfo : taskSubInfos) {
			//将此任务的状态置为处理中
			TaskRunInfo taskRunInfo = new TaskRunInfo();				
			Map<String, Object> params = new HashMap<String, Object>(16);
			params.put("task_id", taskSubInfo.getTaskId());
			params.put("task_sub_id", taskSubInfo.getTaskSubId());
			params.put("start_time", start_time);
			taskRunInfo = taskRunMapper.selectOne(new QueryWrapper<TaskRunInfo>().allEq(params).last("limit 1"));
			taskRunInfo.setTaskStatus(Constants.TASK_STATUS_PROCESS);
			taskRunInfo.setResultCode(ResultCode.任务处理中.getCode());
			taskRunInfo.setResultMessage(ResultCode.任务处理中.getMessage());
			taskRunMapper.updateById(taskRunInfo);
			
			log.info("执行子任务对应的实现类...");
			try {
				log.info("开始执行子任务:"+taskSubInfo.getTaskSubName());
				executeClass(taskSubInfo,taskRunInfo.getBatchNo());
				
				log.info("更新子任务的最后一次执行时间(least_time)以及执行状态");
				taskSubInfo.setLastTime(start_time);
				taskSubInfo.setUpdatedTime(DateUtils.formatLocalDateTimeToString(LocalDateTime.now(),"yyyy-MM-dd HH:mm:ss"));
				taskSubMapper.updateById(taskSubInfo);
				taskRunInfo.setTaskStatus(Constants.TASK_STATUS_SUCCESS);
				taskRunInfo.setResultCode(ResultCode.任务处理完成.getCode());
				taskRunInfo.setResultMessage(ResultCode.任务处理完成.getMessage());
				taskRunMapper.updateById(taskRunInfo);
			} catch (TaskRunException eTask) {
				taskRunInfo.setTaskStatus(Constants.TASK_STATUS_FAIL);
				taskRunInfo.setResultCode(eTask.getErrorCode().getCode());
				taskRunInfo.setResultMessage(eTask.getErrorCode().getMessage()+":"+eTask.getMessage());
				taskRunMapper.updateById(taskRunInfo);
				throw eTask;
			} catch (Exception e) {
				taskRunInfo.setTaskStatus(Constants.TASK_STATUS_FAIL);
				taskRunInfo.setResultCode(ResultCode.系统错误.getCode());
				taskRunInfo.setResultMessage(ResultCode.系统错误.getMessage()+":"+e.getClass().getName());
				taskRunMapper.updateById(taskRunInfo);
				throw e;
			}
		}
	}
	
	/**    
	 * @Description: 执行子任务的实现类
	 */ 
	public void executeClass(TaskSubInfo taskSubInfo, Long batchNo) throws Exception {
		String className = taskSubInfo.getImplClass();
		Object obj = null;
		if(className.contains(".")) {
			Class<?> clazz = Class.forName(className);
			obj = clazz.newInstance();
		} else {
			obj = SpringContextUtil.getBean(className);
		}
		if(!TaskBaseService.class.isAssignableFrom(obj.getClass())){
			throw new TaskRunException(ResultCode.无效配置, "子任务（"+taskSubInfo.getTaskSubName()+"）实现类无效");
		}
		TaskBaseService impl = (TaskBaseService) obj;
		impl.execute(taskSubInfo, batchNo);
	}
	
	/**    
	 * @Description: 实体类转换 TaskSubInfo->TaskRunInfo
	 */ 
	private TaskRunInfo getTaskRunInfo(TaskSubInfo taskSubInfo, String startTime){
		String currentTime = DateUtils.formatLocalDateTimeToString(LocalDateTime.now(),"yyyy-MM-dd HH:mm:ss");
		TaskRunInfo taskRunInfo = new TaskRunInfo();
		taskRunInfo.setTaskId(taskSubInfo.getTaskId());
		taskRunInfo.setTaskSubId(taskSubInfo.getTaskSubId());
		taskRunInfo.setBatchNo(IdGenerator.generatorId());
		taskRunInfo.setStartTime(startTime);
		taskRunInfo.setExeSeq(taskSubInfo.getExeSeq());
		taskRunInfo.setLastTime(taskSubInfo.getLastTime());
		taskRunInfo.setTaskStatus(Constants.TASK_STATUS_INIT);
		taskRunInfo.setResultCode(ResultCode.任务初始化.getCode());
		taskRunInfo.setResultMessage(ResultCode.任务初始化.getMessage());
		taskRunInfo.setCreatedTime(currentTime);
		taskRunInfo.setUpdatedTime(currentTime);
		return taskRunInfo;
	}
	
	/**    
	 * @Description: 系统重启时重跑任务
	 */ 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void reload() {
		log.info("系统重启,重跑任务开始...");
		String currentTime = DateUtils.formatLocalDateTimeToString(LocalDateTime.now(),"yyyy-MM-dd HH:mm:ss");
		//系统重启后，查询正在执行中的主任务
		List<TaskInfo> taskInfos = taskMapper.selectList(
		        new QueryWrapper<TaskInfo>().eq("enable_flag", Constants.ENABLE_FLAG_1).eq("task_status", Constants.TASK_STATUS_PROCESS));
		
		for(TaskInfo taskInfo : taskInfos) {
			try {
				//根据主任务id查询子任务的执行状态
				List<TaskRunInfo> taskRunInfos = taskRunMapper.selectList(new QueryWrapper<TaskRunInfo>().eq("task_id", taskInfo.getTaskId()).eq("start_time", taskInfo.getStartTime()));
				if(taskRunInfos == null || taskRunInfos.isEmpty()) {
					//如果查询不到子任务的执行状态，查询子任务
					List<TaskSubInfo> taskSubInfos = taskSubMapper.selectList(new QueryWrapper<TaskSubInfo>().eq("task_id", taskInfo.getTaskId()));
					if(taskSubInfos == null || taskSubInfos.isEmpty())
						return;
					recordTaskRunInfo(taskSubInfos, taskInfo.getStartTime());
					taskRunInfos = taskRunMapper.selectList(new QueryWrapper<TaskRunInfo>().eq("task_id", taskInfo.getTaskId()).eq("start_time", taskInfo.getStartTime()));
				}
				log.info("根据执行顺序排序子任务");
				Collections.sort(taskRunInfos,new Comparator () {
					@Override
					public int compare(Object o1, Object o2) {
						if(o1 instanceof TaskRunInfo && o2 instanceof TaskRunInfo){
							TaskRunInfo t1 = (TaskRunInfo) o1;
							TaskRunInfo t2 = (TaskRunInfo) o2;
							return t1.getExeSeq().compareTo(t2.getExeSeq());
						}
						throw new ClassCastException("不能转换为taskSubInfo类型");
					}
				});
				for(TaskRunInfo run : taskRunInfos) {
					//重跑非成功的任务
					if(!Constants.TASK_STATUS_SUCCESS.equals(run.getTaskStatus()))
							restartTask(run.getId(), "true");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//更新主任务状态为INIT和下次执行的start_time
				log.info("更新主任务状态和start_time");
				taskInfo.setTaskStatus(Constants.TASK_STATUS_INIT);
				taskInfo.setUpdatedTime(DateUtils.formatLocalDateTimeToString(LocalDateTime.now(),"yyyy-MM-dd HH:mm:ss"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					String fristTime = CronUtil.getNextTimePoint(taskInfo.getTaskCron(),taskInfo.getStartTime());
					String secendTime = CronUtil.getNextTimePoint(taskInfo.getTaskCron(),fristTime);
					Long corn = sdf.parse(secendTime).getTime() - sdf.parse(fristTime).getTime();
					Long run = sdf.parse(taskInfo.getUpdatedTime()).getTime() - sdf.parse(currentTime).getTime();
					if(run > corn) {
						//任务运行时间比一个任务周期长，设置超长时间标识
						taskInfo.setOutTimeFlag(1);
					}
					taskInfo.setStartTime(CronUtil.getNextTimePoint(taskInfo.getTaskCron()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				taskMapper.updateById(taskInfo);
				log.info("系统重启,重跑任务结束...");
			}
		}
	}
	
	/**    
	 * @Description: 人工重跑子任务
	 */ 
	public Map<String,String> restartTask(Integer id, String... strings ) {
		Map<String,String> map = new HashMap<String,String>();
		TaskRunInfo taskRunInfo = taskRunMapper.selectById(id);
		log.info("开始重新执行子任务(task_sub_id="+taskRunInfo.getTaskSubId()+")");
		if(strings.length>0) {
			//系统重启不执行执行状态为成功的任务
			if(Constants.TASK_STATUS_SUCCESS.equals(taskRunInfo.getTaskStatus())) {	//如果此任务的状态为成功，则直接返回
				map.put("resultCode", ResultCode.任务处理完成.getCode());
				map.put("resultMessage", ResultCode.任务处理完成.getMessage()+",不能再次调起!");
				log.info("子任务(task_sub_id="+taskRunInfo.getTaskSubId()+")执行成功,不能再次调起!");
				return map;
			}
		} else {
			//人工重跑不能重跑处理中的任务
			if(Constants.TASK_STATUS_PROCESS.equals(taskRunInfo.getTaskStatus())) {	//如果此任务的状态为成功，则直接返回
				map.put("resultCode", ResultCode.任务处理中.getCode());
				map.put("resultMessage", ResultCode.任务处理中.getMessage()+",不能再次调起!");
				log.info("子任务(task_sub_id="+taskRunInfo.getTaskSubId()+")正在执行中,不能再次调起!");
				return map;
			}
		}
		if(taskRunInfo.getExeSeq() > 1) {  //如果此任务的执行顺序大于1，需要判断此任务之前的关联任务是否执行成功
			List<TaskRunInfo> taskRunInfos = taskRunMapper.selectList(new QueryWrapper<TaskRunInfo>().eq("task_id", taskRunInfo.getTaskId())
					.eq("start_time", taskRunInfo.getStartTime()).lt("exe_seq", taskRunInfo.getExeSeq()).gt("exe_seq", 0));
			for(TaskRunInfo info : taskRunInfos) {
				if(!Constants.TASK_STATUS_SUCCESS.equals(info.getTaskStatus())) {
					map.put("resultCode", ResultCode.任务处理失败.getCode());
					map.put("resultMessage", ResultCode.任务处理失败.getMessage()+",有关联任务没有执行成功，此任务无法执行!");
					log.info("子任务(task_sub_id="+taskRunInfo.getTaskSubId()+")执行失败，有关联任务没有执行成功，此任务无法执行!");
					return map;
				}
			}
		}
		//将此任务的状态置为处理中
		TaskSubInfo taskSubInfo = taskSubMapper.selectById(taskRunInfo.getId());
		taskRunInfo.setTaskStatus(Constants.TASK_STATUS_PROCESS);
		taskRunInfo.setResultCode(ResultCode.任务处理中.getCode());
		taskRunInfo.setResultMessage(ResultCode.任务处理中.getMessage());
		taskRunMapper.updateById(taskRunInfo);
		try {
			executeClass(taskSubInfo,taskRunInfo.getBatchNo());
			
			log.info("更新子任务的最后一次执行时间(least_time)以及执行状态");
			taskSubInfo.setLastTime(taskRunInfo.getStartTime());
			taskSubInfo.setUpdatedTime(DateUtils.formatLocalDateTimeToString(LocalDateTime.now(),"yyyy-MM-dd HH:mm:ss"));
			taskSubMapper.updateById(taskSubInfo);
			taskRunInfo.setTaskStatus(Constants.TASK_STATUS_SUCCESS);
			taskRunInfo.setResultCode(ResultCode.任务处理完成.getCode());
			taskRunInfo.setResultMessage(ResultCode.任务处理完成.getMessage());
			taskRunInfo.setLastTime(taskRunInfo.getStartTime());
			taskRunMapper.updateById(taskRunInfo);
		} catch (TaskRunException eTask) {
			taskRunInfo.setTaskStatus(Constants.TASK_STATUS_FAIL);
			taskRunInfo.setResultCode(eTask.getErrorCode().getCode());
			taskRunInfo.setResultMessage(eTask.getErrorCode().getMessage());
			taskRunMapper.updateById(taskRunInfo);
			log.error("重跑子任务task_sub_id为"+taskRunInfo.getTaskSubId()+"异常:"+eTask.toString());
		} catch (Exception e) {
			taskRunInfo.setTaskStatus(Constants.TASK_STATUS_FAIL);
			taskRunInfo.setResultCode(ResultCode.系统错误.getCode());
			taskRunInfo.setResultMessage(ResultCode.系统错误.getMessage()+":"+e.getClass().getName());
			taskRunMapper.updateById(taskRunInfo);
			log.error("重跑子任务task_sub_id为"+taskRunInfo.getTaskSubId()+"异常:"+e.toString());
		}
		return null;
	}
}
