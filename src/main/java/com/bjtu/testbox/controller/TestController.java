package com.bjtu.testbox.controller;

import com.bjtu.testbox.config.api.R;
import com.bjtu.testbox.config.shiro.AppSecurityUtils;
import com.bjtu.testbox.entity.User;
import com.bjtu.testbox.mapper.UserMapper;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8")
public class TestController {
    @Autowired
    private UserMapper userMapper;

    /**
     * 拥有 user, admin 角色的用户可以访问下面的页面
     */
    @GetMapping("/getMessage")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    public R getMessage() {
        // 测试能不能获取当前登录用户信息
        String username = AppSecurityUtils.getUsername();
        User user = userMapper.selectByUsername(username);
        System.out.println("用户名:"+username+", 类型:" + (user.getType()==1?"工人":"审批者") + ", 绑定ID："+user.getBindUser());
        return R.success().code(200).msg("成功获得信息！");
    }
}
