package com.bjtu.testbox.config.api;

import java.io.Serializable;

public class R implements Serializable {
    // msg属性的一般取值
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    // 结果-代码
    private Integer code;
    // 简单消息说明
    private String msg;
    // 实际返回对象
    private Object data;
    // 跳转链接
    private String link;

    public R() {
    }

    public R(Integer code, String msg, Object data, String link) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.link = link;
    }

    public static R success(Integer code, String msg, Object data, String link){
        return new R(code, msg, data, link);
    }

    public static R success(Integer code, String msg, String link){
        return success(code, msg, null, link);
    }

    public static R success(){
        return new R();
    }

    public static R fail(){
        return new R();
    }

    public R msg(String msg){
        this.setMsg(msg);
        return this;
    }

    public R link(String link){
        setLink(link);
        return this;
    }

    public R code(int code){
        setCode(code);
        return this;
    }

    public R data(Object data){
        setData(data);
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "R{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
