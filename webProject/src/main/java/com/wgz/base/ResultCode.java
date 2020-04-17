package com.wgz.base;


/**
 * @Description:错误码
 * @author: wenguozhang 
 * @date:   2019年5月24日 上午10:59:59  
 */
public class ResultCode {

	private String code;
	private String message;

	public ResultCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public static final ResultCode 任务处理完成 = new ResultCode("0000", "任务处理完成");
	public static final ResultCode 任务处理失败 = new ResultCode("0001", "任务处理失败");
	public static final ResultCode 任务初始化 = new ResultCode("0002", "任务初始化");
	public static final ResultCode 任务处理中 = new ResultCode("0003", "任务处理中");
	public static final ResultCode 系统错误 = new ResultCode("9999", "系统错误");
	
	public static final ResultCode 缺失配置 = new ResultCode("1001", "缺失配置");
	public static final ResultCode 无效配置 = new ResultCode("1002", "无效配置");
	public static final ResultCode 唯一索引未知 = new ResultCode("1003", "无效唯一索引未知");
	public static final ResultCode 比较方法未知 = new ResultCode("1004", "无效的比较方法");
	public static final ResultCode 转换器参数异常 = new ResultCode("1005", "转换器参数异常");
	public static final ResultCode 转换器转换异常 = new ResultCode("1006", "转换器转换异常");
	
	public static final ResultCode 异动表中没有标准数据 = new ResultCode("2001", "异动表中没有标准数据");
	public static final ResultCode 未查到住宿周期编码 = new ResultCode("2002", "未查到住宿周期编码");
	public static final ResultCode 查询住宿周期编码异常 = new ResultCode("2003", "查询住宿周期编码异常");
	public static final ResultCode 非法日期字符串 = new ResultCode("2004", "非法日期字符串");
	
	public static final ResultCode 操作文件IO异常 = new ResultCode("0101", "操作文件IO异常");
	public static final ResultCode 解析文件异常 = new ResultCode("0102", "解析文件异常");
	public static final ResultCode 文件入库异常 = new ResultCode("0103", "文件入库异常");
	public static final ResultCode ftp连接异常 = new ResultCode("0104", "ftp连接异常");
	public static final ResultCode 获取文件异常 = new ResultCode("0105", "获取文件异常");
	public static final ResultCode 参数异常 = new ResultCode("0106", "参数异常");
}
