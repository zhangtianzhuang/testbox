package com.bjtu.testbox.service.impl;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.bjtu.testbox.config.constant.Status;
import com.bjtu.testbox.config.shiro.AppSecurityUtils;
import com.bjtu.testbox.entity.*;
import com.bjtu.testbox.mapper.*;
import com.bjtu.testbox.service.UserService;
import com.bjtu.testbox.tools.TestboxTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    private BoxMapper boxMapper;
    @Autowired
    private CableMapper cableMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private ExamineMapper examineMapper;

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public List<Task> showTaskList(Integer workerId, String taskCity,
                                   Integer taskStatus, String taskPoint,
                                   Long startDate, Long endDate) {
        return taskMapper.queryTask(taskCity, workerId, taskStatus,
                taskPoint, startDate, endDate);
    }

    /**
     * 查看任务详情
     * @param workerId
     * @param taskId
     * @return
     */
    @Override
    public Task showTaskDetail(Integer workerId, int taskId) {
        // 查询任务基本信息
        Task task = taskMapper.queryTaskDetail(taskId);
        // 查询审批情况
        List<Examine> examines = examineMapper.queryTaskExamineRecord(taskId);
        task.setExamines(examines);
        return task;
    }

    @Override
    public Map<String, Integer> selectTaskStatusNumber(Integer workerId) {
        List<Map<String, Object>> maps = taskMapper.queryTaskStatusNum(workerId);
        Map<String, Integer> hashMap = new HashMap<>();
        // 初始值
        for (int i = 1; i <= 6; i++) {
            hashMap.put(TestboxTool.mapStatusCode.get(i), 0);
        }
        for (Map<String, Object> map : maps) {
            Integer num = Integer.valueOf(String.valueOf(map.get("num")));
            Integer status = Integer.valueOf(String.valueOf(map.get("status")));
            hashMap.put(TestboxTool.mapStatusCode.get(status), num);
        }
        return hashMap;
    }

    @Override
    public List<Box> showBoxes(Integer boxStatus, Integer boxType,
                               String boxArea) {
        return boxMapper.selectBoxNumberMul(boxStatus, boxArea, boxType);
    }

    @Override
    public Box showBoxInfo(Integer boxId) {
        // 查询试验箱基本信息
        Box box = boxMapper.selectBoxAndCable(boxId);
        // 查询不同类型线缆数量
        List<Map<String, Object>> maps = cableMapper.cableNumberByType(boxId);
        for (Map<String, Object> map : maps) {
            Integer type = Integer.parseInt(String.valueOf(map.get("type")));
            Integer number = Integer.parseInt(String.valueOf(map.get("number")));
            if (type==Status.CABLE_TYPE_1){  // 1.5米线缆
                box.setLen15(number);
            }else if (type==Status.CABLE_TYPE_2){ // 2.2米线缆
                box.setLen22(number);
            }else { // 其他线缆
                box.setLenOther(number);
            }
        }
        return box;
    }
}
