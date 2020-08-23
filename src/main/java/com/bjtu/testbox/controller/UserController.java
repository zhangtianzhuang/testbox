package com.bjtu.testbox.controller;

import com.bjtu.testbox.config.api.ResultMap;
import com.bjtu.testbox.entity.CableRecord;
import com.bjtu.testbox.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: testbox
 * @description: 用户访问接口，包括工人，审批者，管理员
 * @author: Mr.Bruin
 * @create: 2020-08-23 13:29
 **/
@Api(description = "用户访问API接口")
@RequiresRoles(logical = Logical.OR, value = {"worker", "approver", "admin"})
@RestController
@RequestMapping(value = "/users", produces = "application/json;charset=UTF-8")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ResultMap resultMap;


    @ApiOperation("根据taskId查询所使用的线缆记录日志")
    @GetMapping("/cableUsingRecord")
    public ResultMap cableUsingRecord(@RequestParam("taskId") Integer taskId){
        List<CableRecord> cableRecords = userService.showCableRecordList(taskId);
        if (cableRecords != null && cableRecords.size()!=0){
            return resultMap.success().msg(ResultMap.SUCCESS_QUERY).code(ResultMap.OK).data(cableRecords);
        }
        if (cableRecords.size()==0){
            return resultMap.success().msg(ResultMap.SUCCESS_QUERY).code(ResultMap.OK_NO_DATA).data(cableRecords);
        }
        return resultMap.fail().msg(ResultMap.INTERNET_ERROR).code(ResultMap.FAIL);
    }
}
