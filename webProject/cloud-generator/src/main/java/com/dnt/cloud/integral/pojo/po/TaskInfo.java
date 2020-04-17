package com.dnt.cloud.integral.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 主任务表
 * </p>
 *
 * @author wenguozhang
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TaskInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 主任务id
     */
    @TableField("task_id")
    private Integer taskId;

    /**
     * 主任务名称
     */
    @TableField("task_name")
    private String taskName;

    /**
     * 商户id
     */
    @TableField("mer_id")
    private String merId;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 执行规律
     */
    @TableField("task_cron")
    private String taskCron;

    /**
     * 启动标识，1启用，0禁用
     */
    @TableField("enable_flag")
    private Integer enableFlag;

    /**
     * 任务状态:INIT,PROCESS
     */
    @TableField("task_status")
    private String taskStatus;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    @TableField("updated_time")
    private LocalDateTime updatedTime;

    /**
     * 任务执行超时标识，1 超时，0 未超时
     */
    @TableField("out_time_flag")
    private Integer outTimeFlag;

    /**
     * 平台编号
     */
    @TableField("plan_no")
    private String planNo;


}
