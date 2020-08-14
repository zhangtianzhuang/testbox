package com.bjtu.testbox.controller;

import com.bjtu.testbox.config.api.R;
import com.bjtu.testbox.config.shiro.AppSecurityUtils;
import com.bjtu.testbox.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"测试接口"}, description = "测试接口")
@RestController
@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private AppSecurityUtils appSecurityUtils;

    /**
     * 拥有 user, admin 角色的用户可以访问下面的页面
     */
    @ApiOperation(value="获取消息", notes="测试登录")
    @GetMapping("/getMessage")
    @RequiresRoles(logical = Logical.OR, value = {"worker", "dynamic_workshop", "segment"})
    public R getMessage() {
        // 测试能不能获取当前登录用户信息
        User user = appSecurityUtils.getUser();
        logger.info("用户名:"+user.getUsername()+", 类型:" + (user.getType()==1?"工人":"审批者") + ", 绑定ID："+user.getBindUser());
        return R.success().code(200).msg("成功获得信息！");
    }

    // 测试屏蔽接口
    @ApiIgnore
    @GetMapping("/testCoverApi")
    public R testCoverApi(){
        return R.success();
    }
}
