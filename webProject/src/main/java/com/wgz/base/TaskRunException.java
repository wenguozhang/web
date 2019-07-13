package com.wgz.base;


/**
 * @Description:任务运行异常类
 * @author: wenguozhang 
 * @date:   2019年5月24日 上午11:06:09  
 */
public class TaskRunException extends Exception {
	private static final long serialVersionUID = 1L;

	private ResultCode code;

	public TaskRunException(ResultCode code) {
		super(code.getMessage());
		this.code = code;
	}

	public TaskRunException(ResultCode code, Throwable cause) {
		super(code.getMessage(), cause);
		this.code = code;
	}

	public TaskRunException(ResultCode code, String message) {
		super(code.getMessage() + (message == null ? "" : (": " + message)));
		this.code = code;
	}

	public TaskRunException(ResultCode code, String message, Throwable cause) {
		super(code.getMessage() + (message == null ? "" : (": " + message)), cause);
		this.code = code;
	}

	public ResultCode getErrorCode() {
		return code;
	}
}
