package com.bjtu.testbox.service;


import com.bjtu.testbox.entity.Box;

import java.util.List;

public interface BoxService {
    // 查询某个Box下的所有线缆
    public Box selectCableById(int id);

    // 根据类型查询所有试验箱
    public List<Box> queryBoxByType(int typeCode);
}
