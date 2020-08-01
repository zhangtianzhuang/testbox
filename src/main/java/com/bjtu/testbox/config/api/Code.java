package com.bjtu.testbox.config.api;

public class Code {
    // 服务器返回数据成功 -- GET
    public static final int OK = 200;
    // 用户新建或修改数据成功
    public static final int CREATED = 201;
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
}
