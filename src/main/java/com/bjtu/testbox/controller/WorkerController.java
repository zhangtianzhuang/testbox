package com.bjtu.testbox.controller;

import com.bjtu.testbox.config.api.Code;
import com.bjtu.testbox.config.api.R;
import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.entity.Worker;
import com.bjtu.testbox.service.UserService;
import com.bjtu.testbox.service.WorkerService;
import com.bjtu.testbox.tools.model.BoxOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    @RequestMapping(value = {"/workers"})
    public String workersUI() {
        return "workerUI/taskapply";
    }

    @RequestMapping(value = {"/workers/taskshow"})
    public String taskshow() {
        return "workerUI/taskshow";
    }

    @RequestMapping(value = {"/workers/taskapply"})
    public String taskapply() {
        return "workerUI/taskapply";
    }

    /**
     * 工人申请任务
     *
     * @param task
     * @return
     */
    @PostMapping(value = "/workers/task")
    @ResponseBody
    public R applyTask(@RequestBody Task task) {
        logger.info(task.toString());
        // 测试
        Integer workerId = 1;
        task.setTaskWorkerId(workerId);
        Task returnTask = workerService.applyTask(task);
        if (returnTask != null) {
            return R.success().msg("success").code(Code.OK).data(returnTask);
        } else {
            return R.fail().code(Code.SERVER_ERROR).msg("failure");
        }
    }

    /**
     * 工人查询个人信息
     *
     * @return
     */
    // 注意要加上produces="application/json;charset=UTF-8"，编码为UTF-8，不设置编码默认是ISO-8859-1字符集
    @GetMapping(value = "/workers/personInfo", produces = "application/json;charset=UTF-8")
    @ResponseBody
    // 如果workerId 没有接收到值，则会自动置为空，所以不用int类型，而是用Integer类型
    public R queryWorkerPersonInfo() {
        // User user = userService.obtainUserDetailInfo();
        // model.addAttribute("user", user);
        // 测试
        int workerId = 1;
        Worker worker = workerService.showWorkerInfo(workerId);
        worker.setWorkerId(-1);
        if (worker != null){
            return R.success().msg("success").code(Code.OK).data(worker);
        }
        return R.fail().msg("failure").code(Code.SERVER_ERROR);
    }

    /**
     * 工人查询个人申请的任务列表
     *
     * @param map
     * @return
     */
    @PostMapping("/workers/taskList")
    @ResponseBody
    public R queryTaskList(@RequestBody Map<String, Object> map) {
        Integer taskStatus = (Integer) map.get("taskStatus");
        String taskPoint = (String) map.get("taskPoint");
        String taskCity = (String) map.get("taskCity");
        // Map只能获取Integer，不能转化为Long
//        Long startDate =  Long.valueOf(((Integer) map.get("startDate")).toString());
//        Long endDate = Long.valueOf(((Integer) map.get("endDate")).toString());
        Long startDate = (Long) map.get("startDate");
        Long endDate = (Long) map.get("endDate");
        logger.info("taskStatus:"+taskStatus+", taskPoint:"+taskPoint+", taskCity:"+taskCity+
                ", startDate:"+startDate+", endDate:"+endDate);
//        User user = userService.obtainUserDetailInfo();
        Integer taskWorkerId = 1;
        List<Task> tasks = workerService.showWorkerTask(taskWorkerId, taskCity, taskStatus,
                taskPoint, startDate, endDate);
        return R.success().data(tasks).code(Code.OK).msg("success");
    }


    @GetMapping("/workers/boxes")
    @ResponseBody
    public BoxOption queryUsableBox() {
        BoxOption boxOption = workerService.selectUsableBox();
        return boxOption;
    }

    @RequestMapping("/workers/taskDetail")
    @ResponseBody
    public R queryTaskDetail(@RequestBody Map<String, Integer> map) {
        Integer taskId = map.get("taskId");
        Task task = workerService.showTaskDetail(taskId);
        return R.success().data(task).msg(R.SUCCESS).code(Code.OK);
    }

    @PostMapping("/workers/taskStatusNumber")
    @ResponseBody
    public R taskStatusNumber() {
        int workerId = 1;
        Map<String, Integer> map = workerService.selectTaskStatusNumber(workerId);
        return R.success().msg(R.SUCCESS).code(Code.OK).data(map);
    }
}
