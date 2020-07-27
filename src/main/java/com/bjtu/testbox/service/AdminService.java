package com.bjtu.testbox.service;

import com.bjtu.testbox.entity.Task;

import java.util.List;
import java.util.Map;

/**
 * 与作业管理业务有关的接口类
 */
public interface AdminService {
    /**
     * 查询所有任务中各种状态的任务数目
     * @return
     */
    public List<Map<String, Object>> getTaskStatusNum();

    /**
     * 查询所有任务的简单信息
     */
    public List<Task> showSimpleTasks();

    /**
     * 根据状态查询所有任务的简单信息
     */
    public List<Task> showSimpleTasksByStatus(int taskStatus);


    public Task queryTaskDetial(int taskId);
}