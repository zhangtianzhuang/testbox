package com.bjtu.testbox.controller;

import com.bjtu.testbox.config.api.Code;
import com.bjtu.testbox.config.api.R;
import com.bjtu.testbox.config.api.ResultBean;
import com.bjtu.testbox.entity.Box;
import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.entity.User;
import com.bjtu.testbox.entity.Worker;
import com.bjtu.testbox.service.UserService;
import com.bjtu.testbox.service.WorkerService;
import com.bjtu.testbox.tools.model.BoxOption;
import com.bjtu.testbox.tools.model.BoxOption;
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
    public String workersUI() {
        return "workerUI/taskapply";
    }

    @RequestMapping("/workers/boxes")
    public String workersboxes() {
        return "index";
    }

    /**
     * 测试前端发送测试表单
     *
     * @return
     */
    @PostMapping(value = "/workers/taskTest", produces = "application/json;charset=UTF-8")
    public String workerApplyTest(@RequestBody Task task) {
        System.out.println(task);

        return null;
    }

    ;

    /**
     * 工人申请任务
     *
     * @param task
     * @return
     */
    @PostMapping(value = "/workers/task")
    @ResponseBody
    public R applyTask(@RequestBody Task task) {
        // 判断参数是否合法
        Task returnTask = workerService.applyTask(task);
        if (returnTask != null) {
            return R.success().msg("success").code(200).data(returnTask);
        } else {
            return R.fail().code(500).msg("failure");
        }
    }

    /**
     * 工人查询个人信息
     *
     * @return
     */
    @GetMapping(value = "/workers/personInfo")
    @ResponseBody
    // 如果workerId 没有接收到值，则会自动置为空，所以不用int类型，而是用Integer类型
    public R queryWorkerPersonInfo(@Param("workerId") Integer workerId, Model model) {
        // User user = userService.obtainUserDetailInfo();
        // model.addAttribute("user", user);
        // 测试
        Worker worker = workerService.showWorkerInfo(1);
        worker.setWorkerId(-1);
        if (worker != null){
            return R.success().msg("success").code(200).data(worker);
        }
        return R.fail().msg("failure").code(500);
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
        return "index";
    }


    @GetMapping("/workers/boxes")
    @ResponseBody
    public BoxOption queryUsableBox(Model model) {
        BoxOption boxOption = workerService.selectUsableBox();
        // model.addAttribute("usable_boxes", boxes);
        return boxOption;
    }

    @GetMapping("/workers/taskDetail")
    public String queryTaskDetail(@RequestParam(value = "taskId", required = true) int taskId,
                                  Model model) {
        Task task = workerService.showTaskDetail(taskId);
        model.addAttribute("task_detail", task);
        return null;
    }

    @GetMapping("/workers/taskStatusNumber")
    public String taskStatusNumber(@Param("workerId") int workerId, Model model) {
        Map<String, Integer> map = workerService.selectTaskStatusNumber(workerId);
        model.addAllAttributes(map);
        return null;
    }
}
