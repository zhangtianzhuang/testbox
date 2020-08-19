package com.bjtu.testbox.controller;

import com.bjtu.testbox.config.api.R;
import com.bjtu.testbox.config.api.ResultMap;
import com.bjtu.testbox.config.shiro.AppSecurityUtils;
import com.bjtu.testbox.entity.User;
import com.bjtu.testbox.tools.TestboxTool;
import com.bjtu.testbox.tools.json.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"测试接口"}, description = "测试接口")
@RestController
@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private ResultMap resultMap;
    @Autowired
    private AppSecurityUtils appSecurityUtils;
    @Autowired
    private HttpServletRequest request;
    /**
     * 拥有 user, admin 角色的用户可以访问下面的页面
     */
    @CrossOrigin
    @ApiOperation(value = "获取消息", notes = "测试登录")
    @GetMapping(value = "/getMessage", produces = "application/json;charset=UTF-8")
//    @RequiresRoles(logical = Logical.OR, value = {"worker", "dynamic_workshop", "segment", "admin"})
    @JSON(type = User.class, include = "username,type,bindUser")
    public R getMessage() {
        String header = request.getHeader("user-agent");
        logger.info("getMessage" + " >>> 当前登录设备Header.USER-AGENT信息:" + header);
        boolean mobileDevice = TestboxTool.isMobileDevice(header);
        logger.info("getMessage" + " >>> 当前是否为手机设备登录?"
                + (mobileDevice == true ? "是的" : "不是"));
        // 测试能不能获取当前登录用户信息
        User user = appSecurityUtils.getUser();
        if (user != null){
            logger.info("用户名:" + user.getUsername() + ", 类型:" + (user.getType() == 1 ? "工人" : "审批者")
                    + ", 绑定ID：" + user.getBindUser());
        }
        return R.success().code(200).msg("成功获得信息！").data(user);
    }

    // 测试屏蔽接口
    @ApiIgnore
    @CrossOrigin
    @GetMapping("/testCoverApi")
    public ResultMap testCoverApi() {
        return resultMap.success().data("hello").msg("success");
    }
}
