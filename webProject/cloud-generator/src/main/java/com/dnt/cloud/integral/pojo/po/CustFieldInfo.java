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
 * 数据转换字段映射表
 * </p>
 *
 * @author wenguozhang
 * @since 2019-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CustFieldInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 子任务id
     */
    @TableField("task_sub_id")
    private Integer taskSubId;

    /**
     * 源字段名称
     */
    @TableField("source_field_name")
    private String sourceFieldName;

    /**
     * 目标字段名称
     */
    @TableField("target_field_name")
    private String targetFieldName;

    /**
     * 字段转换方式
     */
    @TableField("exchange_type")
    private String exchangeType;

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
     * 备用1
     */
    @TableField("extend1")
    private String extend1;

    /**
     * 备用2
     */
    @TableField("extend2")
    private String extend2;


}
