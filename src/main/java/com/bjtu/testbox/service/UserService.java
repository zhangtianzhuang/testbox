package com.bjtu.testbox.service;


import com.bjtu.testbox.entity.Approver;
import com.bjtu.testbox.entity.User;
import com.bjtu.testbox.entity.Worker;

public interface UserService {
    User Sel(int id);
    User selectByUsername(String username);
    Approver select(int id);
    Worker selectWorker(int id);
}