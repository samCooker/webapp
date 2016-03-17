package cn.com.cookie.common.bean;

import cn.com.cookie.common.utils.JsonMapper;

public class ErrorInfo {

    private String errorUrl;
    private String errorMsg;

    public ErrorInfo() {
        super();
    }

    public ErrorInfo(String errorUrl, String errorMsg) {
        super();
        this.errorUrl = errorUrl;
        this.errorMsg = errorMsg;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String toJson() {
        JsonMapper mapper = JsonMapper.getInstance();
        return mapper.writeValueAsString(this);
    }

}
