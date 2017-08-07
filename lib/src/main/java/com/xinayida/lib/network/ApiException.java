package com.xinayida.lib.network;

/**
 * Created by stephan on 16/7/21.
 */
public class ApiException extends Exception {
    public int code;
    public String message;
    public ErrorBean errorBean;

    public ApiException(String msg, int code) {
        this(msg, code, null);
    }

    public ApiException(String msg, int code, ErrorBean errorBean) {
        this.message = msg;
        this.code = code;
        this.errorBean = errorBean;
    }

    public ApiException(Throwable e) {
        super(e);
        message = e.toString();
    }

    public String getErrorMsg() {
        if (errorBean != null) {
            return errorBean.desc == null ? errorBean.detail : errorBean.desc;
        }
        return message;
    }
}