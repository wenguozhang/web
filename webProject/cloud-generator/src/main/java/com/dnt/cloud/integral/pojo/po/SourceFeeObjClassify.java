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
 * 计费对象特征值分类表
 * </p>
 *
 * @author wenguozhang
 * @since 2019-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SourceFeeObjClassify implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商户号
     */
    @TableId("mer_id")
    private String merId;

    /**
     * 计费特征分类代码
     */
    @TableField("standard_type_no")
    private String standardTypeNo;

    /**
     * 计费特征分类名称
     */
    @TableField("standard_type_name")
    private String standardTypeName;

    /**
     * 分类计费性质
     */
    @TableField("fees_nature")
    private String feesNature;

    /**
     * 人员信息呈现
     */
    @TableField("personnel_information_present")
    private String personnelInformationPresent;

    /**
     * 周期类别
     */
    @TableField("period_type")
    private String periodType;

    /**
     * 计费特征值编码,参照计费特征值对照表
     */
    @TableField("standard_no")
    private String standardNo;

    /**
     * 计费特征值名称
     */
    @TableField("standard_name")
    private String standardName;

    /**
     * 周期编码
     */
    @TableField("period_code")
    private String periodCode;

    /**
     * 编码可用标识，0不可用1可用
     */
    @TableField("code_flag")
    private Integer codeFlag;

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


}
