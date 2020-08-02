package com.bjtu.testbox.service.impl;

import com.bjtu.testbox.config.constant.Status;
import com.bjtu.testbox.config.shiro.AppSecurityUtils;
import com.bjtu.testbox.entity.Box;
import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.entity.Worker;
import com.bjtu.testbox.mapper.BoxMapper;
import com.bjtu.testbox.mapper.TaskMapper;
import com.bjtu.testbox.mapper.WorkerMapper;
import com.bjtu.testbox.service.WorkerService;
import com.bjtu.testbox.tools.TestboxTool;
import com.bjtu.testbox.tools.model.BoxOption;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    private WorkerMapper workerMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private BoxMapper boxMapper;

    @Override
    public int applyTask(Task task) {
        // 获取当前登录的工人信息
        Worker worker = workerMapper.selectById(AppSecurityUtils.obtainLoginedUser().getBindUser());
        // 系统自动设置字段
        if (worker != null){
            task.setTaskCity(worker.getWorkerCity());
            task.setTaskArea(worker.getWorkerArea());
            task.setTaskWorkerName(worker.getWorkerName());
            task.setTaskNumber(TestboxTool.randomTaskNum());
            task.setTaskStatus(Status.TASK_WAIT_WORKSHOP);
            task.setTaskWorkerId(worker.getWorkerId());
        }
        try {

            taskMapper.insertTask(task);
        } catch (Exception e) {
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
     *
     * @param taskId
     * @return 如果该任务申请了大于等于1个试验箱，则返回所有的信息
     * 如果该任务没有申请试验箱，则返回为空。
     */
    @Override
    public Task showTaskDetail(int taskId) {
        return taskMapper.queryTaskDetail(taskId);
    }


    /**
     * 查询ID为@workerId的工人所申请所有任务状态以及数量
     *
     * @param workerId
     * @return 以Map数据结构返回{状态名,数量}，状态名在TestboxTool类中定义
     */
    public Map<String, Integer> selectTaskStatusNumber(int workerId) {
        List<Map<String, Object>> maps = taskMapper.queryTaskStatusNum(workerId);
        Map<String, Integer> hashMap = new HashMap<>();
        for (Map<String, Object> map : maps) {
            Integer num = Integer.valueOf(String.valueOf(map.get("num")));
            Integer status = Integer.valueOf(String.valueOf(map.get("status")));
            hashMap.put(TestboxTool.mapStatusCode.get(status), num);
        }
        return hashMap;
    }

    @Override
    public BoxOption selectUsableBox() {
        List<Box> boxes = boxMapper.selectBoxNumberMul(0, null, null);
        BoxOption convert = new BoxOption().convert(boxes);
        return convert;
    }
}
