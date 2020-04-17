package com.dnt.cloud.integral.manager.impl;

import com.dnt.cloud.integral.pojo.po.TaskRunInfo;
import com.dnt.cloud.integral.mapper.TaskRunInfoMapper;
import com.dnt.cloud.integral.manager.ITaskRunInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 任务执行记录表 服务实现类
 * </p>
 *
 * @author wenguozhang
 * @since 2019-12-23
 */
@Service
public class TaskRunInfoServiceImpl extends ServiceImpl<TaskRunInfoMapper, TaskRunInfo> implements ITaskRunInfoService {

}
