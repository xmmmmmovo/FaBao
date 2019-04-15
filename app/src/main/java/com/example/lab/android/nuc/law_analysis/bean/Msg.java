package com.example.lab.android.nuc.law_analysis.bean;

/**
 * 返回信息类
 * */
public class Msg<T> {
    private Integer reCode; // 返回码
    private String msg; // 返回信息
    private T data; // 内容

    public Integer getReCode() {
        return reCode;
    }

    public void setReCode(Integer reCode) {
        this.reCode = reCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
