package com.wgz.pojo;

import lombok.Data;

/**
 * @Description: 非学生应收收款实体类
 * @author: wenguozhang 
 * @date:   2019年12月10日 上午11:06:51  
 */
@Data
public class NccSlShys{
	/*--- biz field start ---*/
	//年份
	private String year;
	//月份
	private String month;
	//日期
	private String day;
	//单位编码
	private String dwbm;
	//单位名称
	private String dwmc;
	//部门编码
	private String bmbm;
	//部门名称
	private String bmmc;
	//结算方式编码
	private String jsfsbm;
	//结算方式名称
	private String jsfsmc;
	//收支项目编码
	private String szxmbm;
	//收支项目名称
	private String szxmmc;
	//客户编码
	private String khbm;
	//客户名称
	private String khmc;
	//中间表主键
	private String slid;
	//NC单据主键
	private String ncid;
	//推送时间
	private String tssj;
	//修改时间
	private String xgsj;
	//修改人
	private String sgr;
	//单据日期
	private String jdrq;
	//项目编码
	private String xmbm;
	//项目名称
	private String xmmc;
	//收入周期
	private String srzq;
	//应收或收款
	private String ywlx;
	//是否预收
	private String sfys;
	//操作人：指定用户
	private String czy;
	//附件张数
	private String fjzs;
	//无税金额
	private String je;
	//税率
	private String sl;
	//税金
	private String sj;
	//含税金额
	private String hsje;
	//币种
	private String bz;
	//本币汇率
	private String bbhl;
	//收款账户名称
	private String skzhmc;
	//收款账户
	private String skzhbm;
	//摘要
	private String zy;
	//计划项目
	private String jhxm;
	//错误信息
	private String errmsg;
	//数据状态
	private String status;
	//单据号
	private String billno;
	/*--- biz field end ---*/
	
}
