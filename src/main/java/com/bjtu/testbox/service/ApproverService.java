package com.bjtu.testbox.service;

import com.bjtu.testbox.entity.Examine;
import com.bjtu.testbox.entity.Task;

import java.util.List;

public interface ApproverService {

    /**
     * show tasks
     * @param workId
     * @param taskCity
     * @param taskStatus
     * @param taskPoint
     * @param startDate
     * @param endDate
     * @return
     */
    List<Task> showTaskListByStatus(int workId, String taskCity, int taskStatus,
                                    String taskPoint, long startDate, long endDate);

    /**
     * show task detail
     * @param taskId
     * @return
     */
    Task showTaskDetail(int taskId);

    /**
     *
     * @param examine
     * @return
     */
    int examineTask(Examine examine);
}
