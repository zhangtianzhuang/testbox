package com.bjtu.testbox.service.impl;

import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.mapper.TaskMapper;
import com.bjtu.testbox.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 与作业管理业务有关的接口类实现
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    TaskMapper taskMapper;

    /**
     * 查询所有任务处于不同状态的数目
     * @return
     */
    @Override
    public List<Map<String, Object>> getTaskStatusNum() {
        /**
         * 从taskMapper中查询出来的状态类别是状态码
         * 将不同状态码对具体状态名做映射
         * 对于不存在的状态设置为0
         */
        return taskMapper.queryTaskStatusNum(-1);
    }



    /**
     * 查询所有任务的简单信息：任务ID、申请人姓名、申请日期、工作地点、任务状态
     * @return
     */
    @Override
    public List<Task> showSimpleTasks() {
        return taskMapper.queryAllTasks();
    }

    /**
     * 根据状态码查询任务的简单信息
     * @param taskStatus
     * @return
     */
    @Override
    public List<Task> showSimpleTasksByStatus(int taskStatus){
        return  taskMapper.queryTaskByStatus(taskStatus);
    }

    @Override
    public Task queryTaskDetial(int taskId){
        return taskMapper.queryTaskDetail(taskId);
    }
}
