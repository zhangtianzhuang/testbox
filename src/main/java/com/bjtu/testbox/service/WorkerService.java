package com.bjtu.testbox.service;

import com.bjtu.testbox.entity.Box;
import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.entity.Worker;
import com.bjtu.testbox.tools.model.BoxOption;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface WorkerService {

    /**
     * submit task form
     * @param task
     * @return succes 1, failure -1
     */
    Integer applyTask(Task task);

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
    List<Task> showWorkerTask(Integer workerId, String taskCity, Integer taskStatus,
                              String taskPoint, Long startDate, Long endDate);

    /**
     * show the detail info of the task.
     * @param taskId
     * @return
     */
    Task showTaskDetail(int taskId);

    /**
     * show the number of tasks by different status.
     * @param workerId
     * @return
     */
    //Map<String, Integer> selectTaskStatusNumber(int workerId);

    /**
     * query usable box when a worker apply for a task.
     * @return
     */
    BoxOption selectUsableBox();

    Task taskDetailWithCables(Integer taskId);
}
