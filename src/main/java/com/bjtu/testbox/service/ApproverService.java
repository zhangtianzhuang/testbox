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
    Integer examineTask(Examine examine);

    /**
     * show approver's basic info
     * @param approverId
     * @return
     */
    Approver showApproverInfo(Integer approverId);

    /**
     * @param approverId
     * @param examineResult
     * @return
     */
    List<Task> showHistoryTask(Integer approverId, Integer examineResult);

    /**
     *
     */
    Examine queryExamine(Integer approverId, Integer taskId);
}
