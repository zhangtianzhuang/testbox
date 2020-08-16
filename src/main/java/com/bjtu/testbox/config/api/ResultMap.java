package com.bjtu.testbox.config.api;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ResultMap extends HashMap<String, Object> {

    public static final String INTERNET_ERROR = "网络错误，查询失败";
    public static final String SUCCESS_QUERY = "查询成功";
    public static final String NO_CONTENT_QUERY = "未查询到符合条件的任务";


    public static final int OK = 1;
    public static final int FAIL = 0;

    // 服务器返回数据成功 -- GET
    public static final int GET_OK = 200;
    // 用户新建或修改数据成功
    public static final int CREATED_OK = 201;
    // 用户删除数据成功
    public static final int NO_CONTENT = 204;

    // 用户发出的请求有错误
    public static final int INVALID_REQUEST = 400;
    // 没有权限
    public static final int UNAUTHORIZED = 401;
    // 表示用户得到授权
    public static final int FORBIDDEN  = 403;
    // 服务器发生错误
    public static final int SERVER_ERROR = 500;

    public ResultMap() {}

    public ResultMap success() {
        this.clear();
        this.put("result", "success");
        return this;
    }

    public ResultMap fail() {
        this.clear();
        this.put("result", "failure");
        return this;
    }

    public ResultMap code(int code) {
        this.put("code", code);
        return this;
    }

    public ResultMap msg(Object message) {
        this.put("msg", message);
        return this;
    }

    public ResultMap data(Object data){
        this.put("data", data);
        return this;
    }

    public ResultMap link(Object data){
        this.put("link", data);
        return this;
    }

    public ResultMap putData(String key, Object value){
        this.put(key, value);
        return this;
    }

    private void reset(){
        this.clear();
    }
}

