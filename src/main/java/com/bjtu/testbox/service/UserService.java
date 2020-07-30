package com.bjtu.testbox.service;


import com.bjtu.testbox.entity.Approver;
import com.bjtu.testbox.entity.User;
import com.bjtu.testbox.entity.Worker;

public interface UserService {

    /**
     * 根据用户名查询是否存在该用户
     * @param username
     * @return
     */
    User selectByUsername(String username);

    /**
     * 必须在登录后才有权执行
     * 获取当前用户以及绑定的工人或者审批者的信息
     * @return
     */
    User obtainUserDetailInfo();
}