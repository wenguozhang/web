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
 * 三方机构信息表
 * </p>
 *
 * @author wenguozhang
 * @since 2019-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SourceOrg implements Serializable {

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
    private String merId;

    /**
     * 三方数据id
     */
    @TableField("source_id")
    private String sourceId;

    /**
     * 任务批次号
     */
    @TableField("batch_no")
    private Long batchNo;

    /**
     * 层级编码
     */
    @TableField("level_code")
    private Integer levelCode;

    /**
     * 机构类别编码
     */
    @TableField("type_id")
    private String typeId;

    /**
     * 机构编码
     */
    @TableField("org_no")
    private String orgNo;

    /**
     * 机构名称
     */
    @TableField("org_name")
    private String orgName;

    /**
     * 上级机构编码
     */
    @TableField("parent_org_no")
    private String parentOrgNo;

    /**
     * 排序码
     */
    @TableField("sort_code")
    private Integer sortCode;

    /**
     * 是否分支机构,0:否,1:是
     */
    @TableField("branch_org_flag")
    private Integer branchOrgFlag;

    /**
     * 删除状态 0:正常,1:删除
     */
    @TableField("del_flag")
    private Integer delFlag;

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
     * 推送标识
     */
    @TableField("push_flag")
    private String pushFlag;


}
