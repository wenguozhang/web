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
 * 子任务表
 * </p>
 *
 * @author wenguozhang
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TaskSubInfo implements Serializable {

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
     * 子任务id
     */
    @TableField("task_sub_id")
    private Integer taskSubId;

    /**
     * 子任务名称
     */
    @TableField("task_sub_name")
    private String taskSubName;

    /**
     * 启动标识，1启用，0禁用
     */
    @TableField("enable_flag")
    private Integer enableFlag;

    /**
     * 执行顺序
     */
    @TableField("exe_seq")
    private Integer exeSeq;

    /**
     * 最大执行时间
     */
    @TableField("max_exe_times")
    private Integer maxExeTimes;

    /**
     * 实现类
     */
    @TableField("impl_class")
    private String implClass;

    /**
     * 获取数据方式：sql 数据库，url http接口
     */
    @TableField("source_type")
    private String sourceType;

    /**
     * 执行sql
     */
    @TableField("exe_sql")
    private String exeSql;

    /**
     * 执行url
     */
    @TableField("exe_url")
    private String exeUrl;

    /**
     * 全量标识，1全量，0增量
     */
    @TableField("full_flag")
    private Integer fullFlag;

    /**
     * 增量限制sql
     */
    @TableField("inc_sql")
    private String incSql;

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
     * 上次执行时间
     */
    @TableField("last_time")
    private LocalDateTime lastTime;

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
