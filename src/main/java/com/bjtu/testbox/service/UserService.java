package com.bjtu.testbox.service;


import com.bjtu.testbox.entity.*;

import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 根据用户名查询是否存在该用户
     * @param username
     * @return
     */
    User selectByUsername(String username);

    /**
     * show the list of the task that one worked has applied or has been applying.
     * @param workerId
     * @param taskCity
     * @param taskStatus
     * @param taskPoint
     * @param startDate
     * @param endDate
     * @return
     */
    List<Task> showTaskList(Integer workerId, String taskCity, Integer taskStatus,
                              String taskPoint, Long startDate, Long endDate);

    /**
     * show the detail info of the task.
     * @param taskId
     * @return
     */
    Task showTaskDetail(Integer workerId, int taskId);

    /**
     * show the number of tasks by different status.
     * @param workerId
     * @return
     */
    Map<String, Integer> selectTaskStatusNumber(Integer workerId);

    /**
     * 根据条件查询试验箱列表
     * @param boxStatus
     * @param boxType
     * @param boxArea
     * @return
     */
    List<Box> showBoxes(Integer boxStatus, Integer boxType, String boxArea);

    /**
     * 查看试验箱详细信息
     * @param boxId
     * @return
     */
    Box showBoxInfo(Integer boxId);

    /**
     * 查询手机端线缆使用记录
     * @param taskId
     * @return
     */
    List<CableRecord> showCableRecordList(Integer taskId);
}