package com.bean;

import com.alibaba.fastjson.JSON;

public class Result {
    private Object data;
    private boolean success;
    private String message;

    public Result() {
    }
    public Result(boolean success){
        this.success=success;
    }
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
