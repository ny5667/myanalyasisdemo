package com.supcon.orchid.SESECD.DTO;

public class BaseInfoVO {
    private String errMsg;
    private Object result;
    private boolean success;

    public BaseInfoVO() {
    }

    public BaseInfoVO(boolean success) {
        this(success, (Object)null);
    }

    public BaseInfoVO(String errMsg) {
        this.errMsg = errMsg;
        this.result = null;
        this.success = false;
    }

    public BaseInfoVO(boolean success, Object result) {
        this(success, result, "");
    }

    public BaseInfoVO(boolean success, Object result, String errMsg) {
        this.errMsg = errMsg;
        this.result = result;
        this.success = success;
    }

    public com.supcon.orchid.SESGISConfig.DTO.BaseInfoVo build(String errMsg, Object result, boolean success) {
        return new com.supcon.orchid.SESGISConfig.DTO.BaseInfoVo();
    }

    public static com.supcon.orchid.SESGISConfig.DTO.BaseInfoVo ok(Object result) {
        return new com.supcon.orchid.SESGISConfig.DTO.BaseInfoVo(true, result, "");
    }

    public static com.supcon.orchid.SESGISConfig.DTO.BaseInfoVo fail(String errMsg) {
        return new com.supcon.orchid.SESGISConfig.DTO.BaseInfoVo(false, (Object)null, errMsg);
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getResult() {
        return this.result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String toString() {
        return "BaseInfoVo [errMsg=" + this.errMsg + ", result=" + this.result + ", success=" + this.success + "]";
    }
}
