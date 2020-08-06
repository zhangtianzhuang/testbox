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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WorkerServiceImpl implements WorkerService {

    private Logger logger = LoggerFactory.getLogger(WorkerServiceImpl.class);

    @Autowired
    private WorkerMapper workerMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private BoxMapper boxMapper;

    @Override
    public Task applyTask(Task task) {
        // 获取当前登录的工人信息
//        Worker worker = workerMapper.selectById(AppSecurityUtils.obtainLoginedUser().getBindUser());
        // 判断workerId是否合法
        Worker worker = workerMapper.selectById(task.getTaskWorkerId());
        // 系统自动设置字段
        if (worker != null) {
            // 城市
            task.setTaskCity(worker.getWorkerCity());
            // 申请人的工区
            if (task.getTaskArea() == null)
                task.setTaskArea(worker.getWorkerArea());
            // 申请人的名字
            if (task.getTaskWorkerName() == null)
                task.setTaskWorkerName(worker.getWorkerName());
            // 申请编号
            task.setTaskNumber(TestboxTool.randomTaskNum());
            // 申请日期
            task.setTaskDate(System.currentTimeMillis());
            // 待动态车间审核的状态
            task.setTaskStatus(Status.TASK_WAIT_WORKSHOP);
            task.setTaskWorkerId(worker.getWorkerId());
        }
        try {
            // 查询任务表中ID最大值
            Integer taskId = taskMapper.selectMaxId();
            if (taskId == null) {
                taskId = 1;
            } else {
                taskId++;
            }
            task.setTaskId(taskId);
            taskMapper.insertTask(task);
            // 记录该任务使用的所有试验箱
            for (Box box : task.getBoxes()) {
                int boxId = box.getBoxId();
                logger.info("为任务添加试验箱:" + boxId);
                taskMapper.insertTaskBox(taskId, boxId);
            }
        } catch (Exception e) {
            logger.info("发生异常:" + e.toString());
            return null;
        }
        logger.info("当前任务对象是否为空?" + task == null ? "是的" : "不是");
        return task;
    }

    @Override
    public Worker showWorkerInfo(int workId) {
        return workerMapper.selectById(workId);
    }

    @Override
    public List<Task> showWorkerTask(Integer workerId, String taskCity, Integer taskStatus, String taskPoint,
                                     Long startDate, Long endDate) {
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
