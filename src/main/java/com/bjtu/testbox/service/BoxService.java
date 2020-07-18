package com.bjtu.testbox.service;


import com.bjtu.testbox.entity.Box;

public interface BoxService {
    // 查询某个Box下的所有线缆
    public Box selectCableById(int id);
}
