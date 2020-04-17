package com.wgz.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wgz.base.Constants;
import com.wgz.base.ResultCode;
import com.wgz.base.TaskRunException;
import com.wgz.pojo.NccSlShys;
import com.wgz.pojo.NccTaskRunInfo;
import com.wgz.service.NccSLShysService;
import com.wgz.utils.DateUtils;
import com.wgz.utils.FTPUtil;
import com.wgz.utils.FunUtils;
import com.wgz.utils.IdGenerator;
import com.wgz.utils.RequestUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/Test")
public class TestController {
	@Autowired
	private NccSLShysService sLShysService;
//	@Autowired
//	private NccTaskRunInfoMapper nccRunMapper;
	
	@RequestMapping("/reCall")
	public Map<String,String> reCall(HttpServletRequest request){
		
		Map<String, String> resultMap = new HashMap<String,String>();
		Map<String, Object> requestMap = RequestUtil.getParams(request);
		log.info("ncc任务重发：params("+requestMap.toString()+")");
		
		NccTaskRunInfo ncc = getNccTaskRunInfo(requestMap);
			
		try {
			getFile(ncc);
			List<NccSlShys> lists = analyzeFile(ncc);
			sLShysService.insertOrUpdate(ncc, lists);
			
			resultMap.put("resultCode","S");
			resultMap.put("resultMessage","处理成功");
			resultMap.put("status",ncc.getStatus());
			resultMap.put("errData",ncc.getErrData());
			resultMap.put("errInfo",ncc.getErrInfo());
//			nccRunMapper.updateById(ncc);
			return resultMap;
		} catch (TaskRunException e) {
			resultMap.put("resultCode","F");
			resultMap.put("resultMessage","处理失败");
			resultMap.put("status",ncc.getStatus());
			resultMap.put("errData",ncc.getErrData());
			resultMap.put("errInfo",ncc.getErrInfo());
//			nccRunMapper.updateById(ncc);
			return resultMap;
		}
	}
	
	public NccTaskRunInfo getNccTaskRunInfo(Map<String, Object> requestMap) {
		String merId = (String)requestMap.get("merId");
		String ywlx = (String)requestMap.get("ywlx");
		String personType = (String)requestMap.get("personType");
		String date = (String)requestMap.get("date");
		//111261_FXS_SS_20191211.txt
		String FileName  = merId+"_"+personType+"_"+ywlx+"_"+date+".txt";
		String currentTime = DateUtils.formatLocalDateTimeToString(LocalDateTime.now(),"yyyy-MM-dd HH:mm:ss");
		NccTaskRunInfo ncc = new NccTaskRunInfo();
		ncc.setMerId(merId);
		ncc.setFileName(FileName);
		ncc.setId(IdGenerator.generatorId());
		ncc.setErrData(requestMap.toString());
		ncc.setRunTime(currentTime);
		ncc.setStatus(Constants.TASK_STATUS_INIT);
		return ncc;
	}
	
	public void getFile(NccTaskRunInfo nccRun) throws TaskRunException {
		String server = "39.106.254.228";
	    int port = 50021;
	    String userName = "xgjncc_ro";
	    String userPassword = "KFJH239hjkhf3HksO3H";
	    String localPath = "D:\\slzx\\业财一体化\\新高教\\NC\\测试数据\\ftp\\";
	    String merId = nccRun.getMerId();
		String fileName = nccRun.getFileName();
		FTPUtil ftp = new FTPUtil(server, port, userName, userPassword);
		if (!ftp.open()) {
            log.info("====链接ftp报错====server:"+server+"port:"+port+"userName:"+userName);
            nccRun.setStatus(Constants.TASK_STATUS_FAIL);
			nccRun.setErrData("server:"+server+"port:"+port+"userName:"+userName);
			nccRun.setErrInfo(ResultCode.ftp连接异常.getMessage());
//			nccRunMapper.updateById(nccRun);
            throw new TaskRunException(ResultCode.ftp连接异常, "server:"+server+"port:"+port+"userName:"+userName);
        }
		boolean b = ftp.get("/"+merId+"/"+fileName, localPath+fileName);
		if(!b) {
			 log.info("====ftp文件下载失败====(/"+merId+"/"+fileName + "------>" + localPath+fileName);
	            nccRun.setStatus(Constants.TASK_STATUS_FAIL);
				nccRun.setErrData("====ftp文件下载失败====(/"+merId+"/"+fileName + "------>" + localPath+fileName);
				nccRun.setErrInfo(ResultCode.获取文件异常.getMessage());
//				nccRunMapper.updateById(nccRun);
				ftp.close();
	            throw new TaskRunException(ResultCode.获取文件异常, "====ftp文件下载失败====(/"+merId+"/"+fileName + "------>" + localPath+fileName);
		}
		nccRun.setFileName(localPath+fileName);
		ftp.close();
	}
	
	private List<NccSlShys> analyzeFile(NccTaskRunInfo nccRun) throws TaskRunException {
		String fileName = nccRun.getFileName();
		log.info("analyze file{"+fileName+"} start...");
		File file = new File(fileName);
		List<NccSlShys> ysLists = new ArrayList<NccSlShys>();
		BufferedReader bufferedReader = null;
		try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            NccSlShys nccData = new NccSlShys();
            String lineTxt ;
            int row = 1;
            while ((lineTxt = bufferedReader.readLine()) != null) {
            	try {
					nccData = getNccYs(lineTxt);
				} catch (Exception e) {
					nccRun.setStatus(Constants.TASK_STATUS_FAIL);
					nccRun.setErrData(FunUtils.subStr("第"+row+"行："+lineTxt, 999));
					nccRun.setErrInfo(FunUtils.subStr(e.toString(), 999));
//					nccRunMapper.updateById(nccRun);
					throw new TaskRunException(ResultCode.解析文件异常,"第"+row+"行："+lineTxt+",异常"+e);
				}
            	ysLists.add(nccData);
            	row++;
            }
            log.info("analyze file{"+fileName+"} end,oprated rows:"+(row-1));
            return ysLists;
        } catch (IOException e) {
        	nccRun.setStatus(Constants.TASK_STATUS_FAIL);
			nccRun.setErrData(nccRun.getFileName());
			nccRun.setErrInfo(FunUtils.subStr(e.toString(), 999));
//			nccRunMapper.updateById(nccRun);
            throw new TaskRunException(ResultCode.操作文件IO异常,e);
        } finally {
        	if(bufferedReader != null) {
        		try {
        			bufferedReader.close();
        		} catch (IOException e) {
                    e.printStackTrace();
                }
        	}
        }
	}
		
	private NccSlShys getNccYs(String lineTxt) throws Exception {
		NccSlShys nccData = new NccSlShys();
		String[] arr = lineTxt.split("\\|");
		nccData.setSlid(arr[0]);//中间表主键（推送流水号）
		nccData.setYear(arr[1]);//年
		nccData.setMonth(arr[2]);//月
		nccData.setDay(arr[3]);//日
		nccData.setDwbm(arr[4]);//单位编码
		nccData.setDwmc(arr[5]);//单位名称
		nccData.setBmbm(arr[6]);//部门编码
		nccData.setBmmc(arr[7]);//部门名称
		nccData.setJsfsbm(arr[8]);//结算方式编码（支付渠道编码）
		nccData.setJsfsmc(arr[9]);//结算方式名称
		nccData.setSzxmbm(arr[10]);//收支项目编码（收费项目编码）
		nccData.setSzxmmc(arr[11]);//收支项目名称（收费项目名称）
		nccData.setKhbm(arr[12]);//客户编码
		nccData.setKhmc(arr[13]);//客户名称
		nccData.setTssj(arr[14]);//推送时间
		nccData.setJdrq(arr[15]);//单据日期
		nccData.setXmbm(arr[16]);//项目编码
		nccData.setXmmc(arr[17]);//项目名称
		nccData.setSrzq(arr[18]);//收入周期
		nccData.setYwlx(arr[19]);//应收或收款
		nccData.setSfys(arr[20]);//是否预收
		nccData.setCzy(arr[21]);//操作人：指定用户
		nccData.setFjzs(arr[22]);//附件张数
		nccData.setJe(arr[23]);//无税金额
		nccData.setSl(arr[24]);//税率
		nccData.setSj(arr[25]);//税金
		nccData.setHsje(arr[26]);//含税金额
		nccData.setBz(arr[27]);//币种
		nccData.setBbhl(arr[28]);//本币汇率
		nccData.setSkzhmc(arr[29]);//收款账户名称
		nccData.setSkzhbm(arr[30]);//收款账户
		nccData.setZy(arr[31]);//摘要
		nccData.setStatus(arr[32]);//数据状态 
		return nccData;
	}
}