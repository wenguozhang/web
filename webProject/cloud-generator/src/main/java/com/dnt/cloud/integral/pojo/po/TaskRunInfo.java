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
 * 任务执行记录表
 * </p>
 *
 * @author wenguozhang
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TaskRunInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商户id
     */
    @TableField("mer_id")
    private String merId;

    /**
     * 主任务id
     */
    @TableField("task_id")
    private Integer taskId;

    /**
     * 子任务id
     */
    @TableField("task_sub_id")
    private Integer taskSubId;

    /**
     * 任务批次号
     */
    @TableField("batch_no")
    private Long batchNo;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 上次执行时间
     */
    @TableField("last_time")
    private LocalDateTime lastTime;

    /**
     * 任务状态:INIT,PROCESS,SUCCESS,FAIL
     */
    @TableField("task_status")
    private String taskStatus;

    /**
     * 执行顺序
     */
    @TableField("exe_seq")
    private Integer exeSeq;

    /**
     * 结果编码
     */
    @TableField("result_code")
    private String resultCode;

    /**
     * 结果信息
     */
    @TableField("result_message")
    private String resultMessage;

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


}
