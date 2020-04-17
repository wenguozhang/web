package com.dnt.cloud.integral.manager.impl;

import com.dnt.cloud.integral.pojo.po.TaskInfo;
import com.dnt.cloud.integral.mapper.TaskInfoMapper;
import com.dnt.cloud.integral.manager.ITaskInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 主任务表 服务实现类
 * </p>
 *
 * @author wenguozhang
 * @since 2019-12-23
 */
@Service
public class TaskInfoServiceImpl extends ServiceImpl<TaskInfoMapper, TaskInfo> implements ITaskInfoService {

}
