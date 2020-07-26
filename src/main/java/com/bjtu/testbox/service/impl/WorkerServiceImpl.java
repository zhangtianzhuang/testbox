package com.bjtu.testbox.service.impl;

import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.entity.Worker;
import com.bjtu.testbox.mapper.TaskMapper;
import com.bjtu.testbox.mapper.WorkerMapper;
import com.bjtu.testbox.service.WorkerService;
import com.bjtu.testbox.tools.TestboxTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    private WorkerMapper workerMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public int applyTask(Task task) {
        // other operation
        int workerId = task.getTaskWorkerId();
        Worker worker = workerMapper.selectById(workerId);
        task.setTaskCity(worker.getWorkerCity());
        task.setTaskArea(worker.getWorkerArea());
        task.setTaskWorkerName(worker.getWorkerName());
        task.setTaskNumber(TestboxTool.randomTaskNum());
        try{
            taskMapper.insertTask(task);
        }catch (Exception e){
            return -1;
        }
        return 1;
    }

    @Override
    public Worker showWorkerInfo(int workId) {
        return workerMapper.selectById(workId);
    }

    @Override
    public List<Task> showWorkerTask(int workerId, String taskCity, int taskStatus, String taskPoint, long startDate, long endDate) {
        // if workId
        return taskMapper.queryTask(taskCity, workerId, taskStatus, taskPoint, startDate, endDate);
    }

    /**
     * 展示任务的详细信息，包含该任务申请的试验箱以及试验箱中的线缆信息
     * 如果该任务没有申请任何试验箱，则返回为null
     * @param taskId
     * @return 如果该任务申请了大于等于1个试验箱，则返回所有的信息
     *         如果该任务没有申请试验箱，则返回为空。
     */
    @Override
    public Task showTaskDetail(int taskId) {
        return taskMapper.queryTaskDetail(taskId);
    }
}
