package com.dnt.cloud.integral.pojo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * Ncc任务运行表
 * </p>
 *
 * @author wenguozhang
 * @since 2019-12-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NccTaskRunInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据id
     */
    @TableId("id")
    private Long id;

    /**
     * 子任务id
     */
    @TableField("task_sub_id")
    private Integer taskSubId;

    /**
     * 商户id
     */
    @TableField("mer_id")
    private String merId;

    /**
     * 任务批次号
     */
    @TableField("batch_no")
    private Long batchNo;

    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 错误数据
     */
    @TableField("err_data")
    private String errData;

    /**
     * 错误信息
     */
    @TableField("err_info")
    private String errInfo;

    /**
     * 执行时间
     */
    @TableField("run_time")
    private LocalDateTime runTime;


}
