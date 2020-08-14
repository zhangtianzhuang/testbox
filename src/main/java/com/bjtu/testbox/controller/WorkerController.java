package com.bjtu.testbox.controller;

import com.bjtu.testbox.config.api.ResultMap;
import com.bjtu.testbox.config.shiro.AppSecurityUtils;
import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.entity.Worker;
import com.bjtu.testbox.service.WorkerService;
import com.bjtu.testbox.tools.model.BoxOption;
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
    private AppSecurityUtils appSecurity;
    @Autowired
    private ResultMap resultMap;

    /**
     * 工人申请任务
     * @param task
     * @return
     */
    @ApiOperation("工人提交一个任务申请")
    @PostMapping(value = "/task")
    public ResultMap applyTask(@RequestBody Task task) {
        logger.info(task.toString());
        Integer workerId = appSecurity.getBindId();
        task.setTaskWorkerId(workerId);
        Task returnTask = workerService.applyTask(task);
        if (returnTask != null) {
            return resultMap.success().msg("任务提交成功").code(ResultMap.OK).data(returnTask);
        } else {
            return resultMap.fail().msg("任务提交失败").code(ResultMap.SERVER_ERROR);
        }
    }

    /**
     * 工人查询个人信息
     * @return
     */
    @ApiOperation("工人查看个人信息")
    @GetMapping(value = "/workerInfo")
    public ResultMap queryWorkerPersonInfo() {
        Integer workerId = appSecurity.getBindId();
        Worker worker = workerService.showWorkerInfo(workerId);
        if (worker != null){
            return resultMap.success().code(ResultMap.OK).data(worker);
        }
        return resultMap.fail().code(ResultMap.SERVER_ERROR);
    }

    /**
     * 工人查询个人申请的任务列表
     * @return
     */
    @ApiOperation("工人查看任务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskStatus", value = "任务状态", paramType = "query"),
            @ApiImplicitParam(name = "taskPoint", value = "任务地点", paramType = "query"),
            @ApiImplicitParam(name = "taskCity", value = "任务城市", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "查找起始日期", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "查找终止日期", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功")
    })
    @GetMapping("/taskList")
    public ResultMap queryTaskList(
            @RequestParam(value = "taskStatus", required = false) Integer taskStatus,
            @RequestParam(value = "taskPoint", required = false) String taskPoint,
            @RequestParam(value = "taskCity", required = false) String taskCity,
            @RequestParam(value = "startDate", required = false) Long startDate,
            @RequestParam(value = "endDate", required = false) Long endDate
            ) {
        logger.info("taskStatus:"+taskStatus+", taskPoint:"+taskPoint+", taskCity:"+taskCity+
                ", startDate:"+startDate+", endDate:"+endDate);
        Integer workerId = appSecurity.getBindId();
        List<Task> tasks = workerService.showWorkerTask(workerId, taskCity, taskStatus,
                taskPoint, startDate, endDate);
        return resultMap.success().data(tasks).code(ResultMap.OK).msg("查询任务成功");
    }

    @ApiOperation("工人查看可用的试验箱，按类型分类")
    @GetMapping("/boxes")
    public BoxOption queryUsableBox() {
        BoxOption boxOption = workerService.selectUsableBox();
        return boxOption;
    }


    @ApiOperation("工人查看某个任务的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务ID", required = true, paramType = "query")
    })
    @GetMapping("/taskDetail")
    public ResultMap queryTaskDetail(@RequestParam("taskId") Integer taskId) {
        logger.debug(">>>>> 工人查看任务详细信息 <<<<<<<<");
        Task task = workerService.showTaskDetail(taskId);
        logger.debug(task.toString());
        return resultMap.success().data(task).code(ResultMap.OK);
    }

    @ApiOperation("工人按任务状态统计每个状态的任务数量")
    @GetMapping("/taskStatusNumber")
    public ResultMap taskStatusNumber() {
        int workerId = appSecurity.getBindId();
        Map<String, Integer> map = workerService.selectTaskStatusNumber(workerId);
        return resultMap.success().code(ResultMap.OK).data(map);
    }
}