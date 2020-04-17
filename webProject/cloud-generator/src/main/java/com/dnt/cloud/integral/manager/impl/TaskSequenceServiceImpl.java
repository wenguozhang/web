package com.dnt.cloud.integral.manager.impl;

import com.dnt.cloud.integral.pojo.po.TaskSequence;
import com.dnt.cloud.integral.mapper.TaskSequenceMapper;
import com.dnt.cloud.integral.manager.ITaskSequenceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 任务批次序号表 服务实现类
 * </p>
 *
 * @author wenguozhang
 * @since 2019-12-26
 */
@Service
public class TaskSequenceServiceImpl extends ServiceImpl<TaskSequenceMapper, TaskSequence> implements ITaskSequenceService {

}
