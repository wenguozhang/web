package com.dnt.cloud.integral.manager.impl;

import com.dnt.cloud.integral.pojo.po.TaskList;
import com.dnt.cloud.integral.mapper.TaskListMapper;
import com.dnt.cloud.integral.manager.ITaskListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 任务批次表 服务实现类
 * </p>
 *
 * @author wenguozhang
 * @since 2019-12-26
 */
@Service
public class TaskListServiceImpl extends ServiceImpl<TaskListMapper, TaskList> implements ITaskListService {

}
