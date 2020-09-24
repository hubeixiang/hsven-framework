package org.framework.hsven.quartz.core.entity;

import java.io.Serializable;

public class ResponseScheduler<T> implements Serializable {
    //处理正常
    public final static int CODE_OK = 0;
    //参数异常
    public final static int CODE_PARAM_ERROR = 1;
    //程序异常
    public final static int CODE_INTERNAL_ERROR = 2;
    //业务异常
    public final static int CODE_BUSINESS_ERROR = 3;

    private int code = 0;
    private String error;
    private T data;

    public ResponseScheduler() {
    }

    public ResponseScheduler(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
