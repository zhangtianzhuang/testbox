package com.bjtu.testbox.service;


import com.bjtu.testbox.entity.User;

public interface UserService {
    User Sel(int id);
    User selectByUsername(String username);
}