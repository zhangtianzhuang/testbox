package com.bjtu.testbox.controller;

import com.bjtu.testbox.config.api.R;
import com.bjtu.testbox.config.shiro.jwt.JWTUtil;
import com.bjtu.testbox.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
public class LoginController {

    @Autowired
    private UserMapper userMapper;


    @PostMapping("/login")
    public R login(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
        String realPassword = userMapper.selectPassword(username);
        if (realPassword == null) {
            return R.fail().code(401).msg("用户名错误");
        } else if (!realPassword.equals(password)) {
            return R.fail().code(401).msg("密码错误");
        } else {
            return R.success().code(200).msg(JWTUtil.createToken(username));
        }
    }

    @RequestMapping(path = "/unauthorized/{message}")
    public R unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        return R.success().code(401).msg(message);
    }
}