package com.bjtu.testbox.service.impl;

import com.bjtu.testbox.entity.Approver;
import com.bjtu.testbox.entity.User;
import com.bjtu.testbox.entity.Worker;
import com.bjtu.testbox.mapper.ApproverMapper;
import com.bjtu.testbox.mapper.UserMapper;
import com.bjtu.testbox.mapper.WorkerMapper;
import com.bjtu.testbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ApproverMapper approverMapper;

    @Autowired
    WorkerMapper workerMapper;

    public User Sel(int id){
        return userMapper.Sel(id);
    }

    public Approver select(int id){
        return approverMapper.sel(id);
    }

    @Override
    public Worker selectWorker(int id) {
        return null;
    }

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
}
