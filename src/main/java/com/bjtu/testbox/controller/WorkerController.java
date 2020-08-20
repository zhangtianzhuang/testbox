package com.bjtu.testbox.controller;

import com.bjtu.testbox.config.api.ResultMap;
import com.bjtu.testbox.config.shiro.AppSecurityUtils;
import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.entity.Worker;
import com.bjtu.testbox.service.UserService;
import com.bjtu.testbox.service.WorkerService;
import com.bjtu.testbox.tools.model.BoxOption;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// 注意要加上produces="application/json;charset=UTF-8"，编码为UTF-8，不设置编码默认是ISO-8859-1字符集
@Api(description = "工人API接口")
@RequiresRoles("worker")
@RestController
@RequestMapping(value = "/workers", produces = "application/json;charset=UTF-8")
public class WorkerController {

    private static Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @Autowired
    private WorkerService workerService;
    @Autowired
    private UserService userService;
    @Autowired
    private AppSecurityUtils appSecurity;
    @Autowired
    private ResultMap resultMap;

    /**
     * 工人申请任务
     *
     * @param task
     * @return
     */
    @CrossOrigin
    @ApiOperation("工人提交一个任务申请")
    @PostMapping(value = "/task")
    public ResultMap applyTask(@RequestBody Task task) {
        logger.info(task.toString());
        Integer workerId = appSecurity.getBindId();
        task.setTaskWorkerId(workerId);
        Integer taskId = workerService.applyTask(task);
        if (taskId > 0) {
            // 这里再查询出来任务的详细情况
            Task task1 = userService.showTaskDetail(null, taskId);
            return resultMap.success().msg("任务申请成功，等待审批").code(ResultMap.OK).data(task1);
        } else if (taskId == -1) {
            return resultMap.fail().msg("当前登录账户异常").code(taskId);
        } else {
            return resultMap.fail().msg("服务器异常").code(taskId);
        }
    }

    /**
     * 工人查询个人信息
     *
     * @return
     */
    @CrossOrigin
    @ApiOperation("工人查看个人信息")
    @GetMapping(value = "/workerInfo")
    public ResultMap queryWorkerPersonInfo() {
        Integer workerId = appSecurity.getBindId();
        Worker worker = workerService.showWorkerInfo(workerId);
        if (worker != null) {
            return resultMap.success().code(ResultMap.OK)
                    .data(worker)
                    .msg(ResultMap.SUCCESS_QUERY);
        }
        return resultMap.fail().code(ResultMap.FAIL).
                msg(ResultMap.INTERNET_ERROR);
    }

    /**
     * 工人查询个人申请的任务列表
     *
     * @return
     */
    @CrossOrigin
    @ApiOperation("工人查看任务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskStatus", value = "任务状态", paramType = "query"),
            @ApiImplicitParam(name = "taskPoint", value = "任务地点", paramType = "query"),
            @ApiImplicitParam(name = "taskCity", value = "任务城市", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "查找起始日期", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "查找终止日期", paramType = "query")
    })
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "请求成功")
//    })
    @GetMapping("/taskList")
    public ResultMap queryTaskList(
            @RequestParam(value = "taskStatus", required = false) Integer taskStatus,
            @RequestParam(value = "taskPoint", required = false) String taskPoint,
            @RequestParam(value = "taskCity", required = false) String taskCity,
            @RequestParam(value = "startDate", required = false) Long startDate,
            @RequestParam(value = "endDate", required = false) Long endDate
    ) {
        logger.info("taskStatus:" + taskStatus + ", taskPoint:" + taskPoint + ", taskCity:" + taskCity +
                ", startDate:" + startDate + ", endDate:" + endDate);
        Integer workerId = appSecurity.getBindId();
        List<Task> tasks = userService.showTaskList(workerId, taskCity, taskStatus,
                taskPoint, startDate, endDate);
        // 查询到数据
        if (tasks != null && tasks.size() != 0) {
            return resultMap.success().data(tasks)
                    .code(ResultMap.OK)
                    .msg(ResultMap.SUCCESS_QUERY);
        }
        if (tasks.size() == 0){
            return resultMap.success().data(tasks)
                    .code(ResultMap.OK_NO_DATA)
                    .msg(ResultMap.SUCCESS_QUERY);
        }
        return resultMap.fail().code(ResultMap.FAIL)
                .msg(ResultMap.NO_CONTENT_QUERY);
    }

    @CrossOrigin
    @ApiOperation("工人查看可用的试验箱，按类型分类")
    @GetMapping("/boxes")
    public BoxOption queryUsableBox() {
        BoxOption boxOption = workerService.selectUsableBox();
        return boxOption;
    }

    @CrossOrigin
    @ApiOperation("工人查看某个任务的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务ID", required = true, paramType = "query")
    })
    @GetMapping("/taskDetail")
    public ResultMap queryTaskDetail(@RequestParam("taskId") Integer taskId) {
        logger.debug(">>>>> 工人查看任务详细信息 <<<<<<<<");
        Task task = userService.showTaskDetail(null, taskId);
        logger.debug(task.toString());
        if (task != null) {
            return resultMap.success().data(task)
                    .code(ResultMap.OK)
                    .msg(ResultMap.SUCCESS_QUERY);
        }
        return resultMap.fail().code(ResultMap.FAIL)
                .msg(ResultMap.INTERNET_ERROR);
    }

    @CrossOrigin
    @ApiOperation("工人按任务状态统计每个状态的任务数量")
    @GetMapping("/taskStatusNumber")
    public ResultMap taskStatusNumber() {
        int workerId = appSecurity.getBindId();
        Map<String, Integer> map = userService.selectTaskStatusNumber(workerId);
        if (map != null) {
            return resultMap.success().code(ResultMap.OK).data(map);
        }
        return resultMap.fail().code(ResultMap.FAIL)
                .msg(ResultMap.INTERNET_ERROR);
    }

    //////////////////// 以下为手机版 /////////////////////////////////

    @CrossOrigin
    @ApiOperation("工人查看某个任务的详细信息-手机版，包括线缆信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务ID", required = true, paramType = "query")
    })
    @GetMapping("/taskDetailByPhone")
    public ResultMap queryTaskDetailByPhone(@RequestHeader("taskId") Integer taskId) {
        logger.debug(">>>>> 工人查看任务详细信息 <<<<<<<<");
        Task task = workerService.taskDetailWithCables(taskId);
        logger.debug(task.toString());
        if (task != null) {
            return resultMap.success().data(task)
                    .code(ResultMap.OK)
                    .msg(ResultMap.SUCCESS_QUERY);
        }
        return resultMap.fail().code(ResultMap.FAIL)
                .msg(ResultMap.INTERNET_ERROR);
    }
}