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
 * 任务批次表
 * </p>
 *
 * @author wenguozhang
 * @since 2019-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TaskList implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商户号
     */
    @TableField("mer_id")
    private Integer merId;

    /**
     * 任务批次号
     */
    @TableField("batch_no")
    private String batchNo;

    /**
     * 任务编码(任务的intId)
     */
    @TableField("task_code")
    private String taskCode;

    /**
     * 任务状态:N:任务初始,S:受理成功,F:受理成功,R:等待重发
     */
    @TableField("status_code")
    private String statusCode;

    /**
     * 任务状态说明
     */
    @TableField("status_info")
    private String statusInfo;

    /**
     * 任务重发次数
     */
    @TableField("resend_time")
    private Integer resendTime;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("modify_time")
    private LocalDateTime modifyTime;

    /**
     * 最后更新时间
     */
    @TableField("finish_time")
    private LocalDateTime finishTime;


}
