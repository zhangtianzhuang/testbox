package com.bjtu.testbox.service;

import com.bjtu.testbox.entity.Approver;
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
    List<Task> showTaskListByStatus(Integer workId, String taskCity, Integer taskStatus,
                                    String taskPoint, Long startDate, Long endDate);

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
    Examine examineTask(Examine examine);


    Approver showApproverInfo(Integer approverId);
}
