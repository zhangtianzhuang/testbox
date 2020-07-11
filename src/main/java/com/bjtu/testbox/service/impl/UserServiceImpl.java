package com.bjtu.testbox.service.impl;

import com.bjtu.testbox.entity.User;
import com.bjtu.testbox.mapper.UserMapper;
import com.bjtu.testbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    public User Sel(int id){
        return userMapper.Sel(id);
    }

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
}
