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
 * 数据错误信息表
 * </p>
 *
 * @author gin
 * @since 2019-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DataErrInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
     * 错误时间
     */
    @TableField("err_time")
    private LocalDateTime errTime;


}
