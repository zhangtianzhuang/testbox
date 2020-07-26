package com.bjtu.testbox.service.impl;

import com.bjtu.testbox.entity.Examine;
import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.mapper.ApproverMapper;
import com.bjtu.testbox.mapper.ExamineMapper;
import com.bjtu.testbox.mapper.TaskMapper;
import com.bjtu.testbox.service.ApproverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApproverServiceImpl implements ApproverService {

    @Autowired
    private TaskMapper taskMapper;
//    @Autowired
//    private ApproverMapper approverMapper;
    @Autowired
    private ExamineMapper examineMapper;

    @Override
    public List<Task> showTaskListByStatus(int workerId, String taskCity, int taskStatus,
                                           String taskPoint, long startDate, long endDate) {
        return taskMapper.queryTask(taskCity, workerId, taskStatus, taskPoint, startDate, endDate);
    }

    @Override
    public Task showTaskDetail(int taskId) {
        return taskMapper.queryTaskDetail(taskId);
    }

    @Override
    public int examineTask(Examine examine) {
        try {
            examineMapper.insertExamine(examine);
        } catch (Exception e) {
            return -1;
        }
        return 1;
    }
}
