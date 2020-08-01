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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("/workers")
//@RequiresRoles("worker")
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
     * @param boxes
     * @return
     */
    @RequestMapping(value = "/workers/task", method = RequestMethod.POST)
    public String applyTask(Task task, String boxes) {
        System.out.println(task);
        return "workerUI/apply_success";
        // 判断参数是否合法
//        if (task.getTaskName() != null && task.getTaskWorkerName() != null
//                && task.getTaskArea() != null && task.getTaskPoint() != null) {
//            logger.info(task.toString());
//            int i = workerService.applyTask(task);
//            if (i == 1)
//                return "apply_success";
//        }
//        return "apply_failure";
    }

    /**
     * 工人查询个人信息
     *
     * @return
     */
    @ApiOperation(value = "工人查询个人信息", notes = "说明notes", produces = "application/json")
    @RequestMapping(value = "/personInfo")
    public String queryWorkerPersonInfo(Model model) {
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
    @RequestMapping("/taskList")
    public String queryTaskList(
            @RequestParam(value = "taskStatus", required = false, defaultValue = "0") Integer taskStatus,
            @RequestParam(value = "taskPoint", required = false, defaultValue = "null") String taskPoint,
            @RequestParam(value = "taskCity", required = false, defaultValue = "null") String taskCity,
            @RequestParam(value = "startDate", required = false, defaultValue = "-1") long startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "-1") long endDate,
            Model model) {
//        User user = userService.obtainUserDetailInfo();
//        int id = user.getWorker().getWorkerId();
        List<Task> tasks = workerService.showWorkerTask(1, taskCity, taskStatus,
                taskPoint, startDate, endDate);
        model.addAttribute("taskList", tasks);
        return "index::div2";
    }

    @GetMapping("/taskDetail")
    public String queryTaskDetail(@RequestParam(value = "taskId", required = true) int taskId,
                             Model model) {
        Task task = workerService.showTaskDetail(taskId);
        return null;
    }

    @RequestMapping("/taskStatusNumber")
    public String taskStatusNumber() {

        return null;
    }
}
