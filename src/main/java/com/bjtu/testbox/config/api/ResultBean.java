package com.bjtu.testbox.config.api;

public class ResultBean {
    public static R success(Integer code, String msg, Object data){
        R result = new R();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static R success(Integer code, Object data){
        return success(code, "success", data);
    }

    public static R success(Object data){
        return success(200, "success", data);
    }
}
