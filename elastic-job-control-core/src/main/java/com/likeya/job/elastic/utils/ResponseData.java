package com.likeya.job.elastic.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 封装返回结果
 * @param <T>
 */
public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code = 200;

    private String msg = "success";

    private T data;

    public ResponseData() {
    }

    public ResponseData(Throwable e) {
        this.msg = e.getMessage();
        this.code = 500;
    }

    public static <T> ResponseData<T> succeed() {
        return ResponseData(null, null, null);
    }

    public static <T> ResponseData<T> succeed(T data) {
        return ResponseData(data, null, null);
    }

    public static <T> ResponseData<T> succeed(T data, String msg) {
        return ResponseData(data, null, msg);
    }

    public static <T> ResponseData<T> fail(String msg) {
        return ResponseData(null, 500, msg);
    }

    public static <T> ResponseData<T> fail(int code, String msg) {
        return ResponseData(null, code, msg);
    }

    public static <T> ResponseData<T> fail(int code, String msg, T data) {
        ResponseData<T> r = new ResponseData<>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    private static <T> ResponseData<T> ResponseData(T data, Integer code, String msg) {
        ResponseData<T> ResponseData = new ResponseData<>();
        if (null != code) {
            ResponseData.setCode(code);
        }
        if (null != msg) {
            ResponseData.setMsg(msg);
        }
        if (null != data) {
            ResponseData.setData(data);
        }
        return ResponseData;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return 200 == this.code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
