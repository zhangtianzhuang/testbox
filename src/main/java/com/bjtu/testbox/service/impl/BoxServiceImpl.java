package com.bjtu.testbox.service.impl;

import com.bjtu.testbox.entity.Box;
import com.bjtu.testbox.mapper.BoxMapper;
import com.bjtu.testbox.mapper.TaskMapper;
import com.bjtu.testbox.service.BoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoxServiceImpl implements BoxService {
    @Autowired
    BoxMapper boxMapper;

    @Override
    public Box selectCableById(int id) {

        return null;
    }

    @Override
    public List<Box> queryBoxByType(int typeCode) {
        return boxMapper.selectBoxByType(typeCode);
    }
}
