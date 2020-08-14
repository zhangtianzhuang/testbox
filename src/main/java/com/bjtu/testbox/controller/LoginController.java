package com.bjtu.testbox.controller;

import com.bjtu.testbox.config.api.R;
import com.bjtu.testbox.config.api.ResultMap;
import com.bjtu.testbox.config.constant.Status;
import com.bjtu.testbox.config.shiro.jwt.JWTUtil;
import com.bjtu.testbox.entity.User;
import com.bjtu.testbox.mapper.UserMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Api(description = "登录API接口")
@RestController
@RequestMapping(value = "/", produces = "application/json;charset=UTF-8")
public class LoginController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResultMap resultMap;

    @PostMapping("/login")
    public ResultMap login(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
        String realPassword = userMapper.selectPassword(username);
        if (realPassword == null) {
            return resultMap.fail().code(401).msg("用户名错误");
        } else if (!realPassword.equals(password)) {
            return resultMap.fail().code(401).msg("密码错误");
        } else {
            User user = userMapper.selectByUsername(username);
            String type = null;
            if (user.getType()==Status.USER_TYPE_WORKER) {
                type = "worker";
            }else if(user.getType()==Status.USER_TYPE_WORKSHOP_APPROVER ||
                    user.getType()==Status.USER_TYPE_SEGMENT_APPROVER) {
                type = "approver";
            }else if(user.getType()==Status.USER_TYPE_ADMIN) {
                type = "admin";
            }
            return resultMap.success().code(ResultMap.OK).msg(JWTUtil.createToken(username))
                    .putData("userType", type)
                    .putData("userId", user.getBindUser());
        }
    }

    @RequestMapping(path = "/unauthorized/{message}")
    public R unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        return R.success().code(ResultMap.UNAUTHORIZED).msg(message);
    }
}