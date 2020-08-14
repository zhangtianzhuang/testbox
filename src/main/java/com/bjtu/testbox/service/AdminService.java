package com.bjtu.testbox.service;

import com.bjtu.testbox.entity.Admin;
import com.bjtu.testbox.entity.Box;
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

    /**
     * 根据任务的ID号查询任务的详细信息
     * @param taskId
     * @return
     */
    public Task queryTaskDetial(int taskId);

    /**
     * 查询个人信息
     * @return
     */
    Admin showPersonInfo(Integer adminId);

    /**
     * 查询boxId试验箱的详细信息
     * @param boxId
     * @return
     */
    Box showBoxInfo(Integer boxId);

    /**
     * 根据条件查询试验箱列表
     * @param boxStatus
     * @param boxType
     * @param boxArea
     * @return
     */
    List<Box> showBoxes(Integer boxStatus, Integer boxType, String boxArea);

    Map<Integer, Integer> boxNubmerByTypeAndStatus(Integer boxType);
}
