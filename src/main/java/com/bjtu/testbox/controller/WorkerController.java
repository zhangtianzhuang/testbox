package com.bjtu.testbox.controller;

import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.service.WorkerService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/worker")
@RequiresRoles("worker")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    /**
     * 工人申请任务
     * @param task
     * @param boxes
     * @return
     */
    @RequestMapping("/applyTask")
    public String applyTask(Task task, String boxes) {
        // 判断参数是否合法
        if (task.getTaskName() != null && task.getTaskWorkerName() != null &&
                task.getTaskArea() != null && task.getTaskPoint() != null) {

        }
        return null;
    }

    /**
     * 工人查询个人信息
     * @return
     */
    @RequestMapping("/personInfo")
    public String queryWorkerPersonInfo(){

        return null;
    }

    /**
     * 工人查询个人申请的任务列表
     * @param taskStatus
     * @param taskPoint
     * @param taskCity
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping("/taskList")
    public String queryTaskList(
            @RequestParam(value = "taskStatus", required = false, defaultValue = "0") Integer taskStatus,
            @RequestParam(value = "taskPoint", required = false, defaultValue = "null") String taskPoint,
            @RequestParam(value = "taskCity", required = false, defaultValue = "null") String taskCity,
            @RequestParam(value = "startDate", required = false, defaultValue = "-1") long startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "-1") long endDate){

        return null;
    }

    @RequestMapping("/taskDetail")
    public String queryTaskDetail(@RequestParam(value = "taskId", required = true) int taskId){

        return null;
    }

    @RequestMapping("/taskStatusNumber")
    public String taskStatusNumber(){
        return null;
    }

}
