package com.bjtu.testbox.service.impl;

import com.bjtu.testbox.config.constant.Status;
import com.bjtu.testbox.config.shiro.AppSecurityUtils;
import com.bjtu.testbox.entity.Approver;
import com.bjtu.testbox.entity.User;
import com.bjtu.testbox.entity.Worker;
import com.bjtu.testbox.mapper.ApproverMapper;
import com.bjtu.testbox.mapper.UserMapper;
import com.bjtu.testbox.mapper.WorkerMapper;
import com.bjtu.testbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ApproverMapper approverMapper;

    @Autowired
    WorkerMapper workerMapper;

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 获取已经登录的用户详细信息，包括绑定的工人或者审批者的信息
     * @return
     */
    @Override
    public User obtainUserDetailInfo() {
        User user = AppSecurityUtils.obtainLoginedUser();
        // 如果用户是工人
        if (user.getType() == Status.USER_TYPE_WORKER) {
            Worker worker = workerMapper.selectById(user.getBindUser());
            user.setWorker(worker);
            return user;
        }
        // 如果用户是审批者
        else if (user.getType() == Status.USER_TYPE_WORKSHOP_APPROVER ||
                user.getType() == Status.USER_TYPE_SEGMENT_APPROVER){
            Approver approver = approverMapper.queryApproverById(user.getBindUser());
            user.setApprover(approver);
            return user;
        }
        return user;
    }
}
