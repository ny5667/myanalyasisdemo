package com.supcon.orchid.SESECD.model.vo.common;

public class BaseInfoVO {
	
	private String errMsg;	    //错误信息
	private Object result;	    //结果对象
	private boolean success;	//成功结果
	
	
	public BaseInfoVO() {
		super();
	}
	
	public BaseInfoVO(boolean success) {
		this(success, null);
	}
	
	public BaseInfoVO(String errMsg) {
		super();
		this.errMsg = errMsg;
		this.result = null;
		this.success = false;
	}
	
	public BaseInfoVO(boolean success, Object result) {
		this(success, result, "");
	}

	public BaseInfoVO(boolean success, Object result, String errMsg) {
		super();
		this.errMsg = errMsg;
		this.result = result;
		this.success = success;
	}

	public BaseInfoVO build(String errMsg, Object result, boolean success) {
		return new BaseInfoVO();
	}
	
	public static BaseInfoVO ok(Object result) {
		return new BaseInfoVO(true, result, "");
	}
	
	public static BaseInfoVO fail(String errMsg) {
		return new BaseInfoVO(false, null, errMsg);
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "BaseInfoVo [errMsg=" + errMsg + ", result=" + result
				+ ", success=" + success + "]";
	}
}
