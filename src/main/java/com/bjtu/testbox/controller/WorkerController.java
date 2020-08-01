package com.bjtu.testbox.controller;

import com.bjtu.testbox.config.api.Code;
import com.bjtu.testbox.config.api.R;
import com.bjtu.testbox.config.api.ResultBean;
import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.entity.User;
import com.bjtu.testbox.service.UserService;
import com.bjtu.testbox.service.WorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
//@RequestMapping("/workers")
public class WorkerController {

    private static Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @Autowired
    private WorkerService workerService;

    @Autowired
    private UserService userService;

    /**
     * 工人UI的入口
     */
    @RequestMapping("/workers")
    public String workersUI(){
        return "workerUI/taskapply";
    }

    /**
     * 测试前端发送测试表单
     * @return
     */
    public String workerApplyTest(){

        return "workerUI/apply_success";
    };
    /**
     * 工人申请任务
     *
     * @param task
     * @return
     */
    @PostMapping(value = "/workers/task")
    public String applyTask(@RequestBody Task task) {
        // 判断参数是否合法
        if (task.getTaskName() != null && task.getTaskWorkerName() != null
                && task.getTaskArea() != null && task.getTaskPoint() != null) {
            logger.info(task.toString());
            int i = workerService.applyTask(task);
        }
        return "workerUI/apply_success";
    }

    /**
     * 工人查询个人信息
     *
     * @return
     */
    @GetMapping(value = "/workers/personInfo")
    public String queryWorkerPersonInfo(@Param("workerId") int workerId, Model model) {
        User user = userService.obtainUserDetailInfo();
        model.addAttribute("user", user);
        return "index::div1";
    }

    /**
     * 工人查询个人申请的任务列表
     *
     * @param taskStatus
     * @param taskPoint
     * @param taskCity
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/workers/taskList")
    public String queryTaskList(
            @RequestParam(value = "taskStatus", defaultValue = "0") Integer taskStatus,
            @RequestParam(value = "taskPoint", defaultValue = "null") String taskPoint,
            @RequestParam(value = "taskCity", defaultValue = "null") String taskCity,
            @RequestParam(value = "startDate", defaultValue = "-1") long startDate,
            @RequestParam(value = "endDate", defaultValue = "-1") long endDate,
            @RequestParam(value = "workerId", defaultValue = "-1") int workerId,
            Model model) {
//        User user = userService.obtainUserDetailInfo();
//        int id = user.getWorker().getWorkerId();
        List<Task> tasks = workerService.showWorkerTask(1, taskCity, taskStatus,
                taskPoint, startDate, endDate);
        model.addAttribute("taskList", tasks);
        return "index::div2";
    }

    @GetMapping("/workers/taskDetail")
    public String queryTaskDetail(@RequestParam(value = "taskId", required = true) int taskId,
                             Model model) {
        Task task = workerService.showTaskDetail(taskId);
        model.addAttribute("task", task);
        return null;
    }

    @GetMapping("/workers/taskStatusNumber")
    public String taskStatusNumber(@Param("workerId")int workerId, Model model) {
        Map<String, Integer> map = workerService.selectTaskStatusNumber(workerId);
        model.addAllAttributes(map);
        return null;
    }
}
