package com.bjtu.testbox.service;

import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.entity.Worker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface WorkerService {

    /**
     * submit task form
     * @param task
     * @return succes 1, failure -1
     */
    int applyTask(Task task);

    /**
     * show the worker's basic info
     * @param workerId
     * @return
     */
    Worker showWorkerInfo(int workerId);

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
    List<Task> showWorkerTask(int workerId, String taskCity, int taskStatus,
                              String taskPoint, long startDate, long endDate);

    /**
     * show the detail info of the task.
     * @param taskId
     * @return
     */
    Task showTaskDetail(int taskId);

    public Map<String, Integer> selectTaskStatusNumber(int workerId);
}
