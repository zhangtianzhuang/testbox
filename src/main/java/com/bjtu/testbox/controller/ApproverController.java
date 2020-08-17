package com.bjtu.testbox.controller;

import com.bjtu.testbox.config.api.R;
import com.bjtu.testbox.config.api.ResultMap;
import com.bjtu.testbox.config.constant.Status;
import com.bjtu.testbox.config.shiro.AppSecurityUtils;
import com.bjtu.testbox.entity.Approver;
import com.bjtu.testbox.entity.Examine;
import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.service.ApproverService;
import com.bjtu.testbox.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "审批者API接口")
@RequiresRoles(logical = Logical.OR, value = {"dynamic_workshop", "segment"})
@RestController
@RequestMapping(value = "/approvers", produces = "application/json;charset=UTF-8")
public class ApproverController {

    private static final Logger logger = LoggerFactory.getLogger(ApproverController.class);

    @Autowired
    private ApproverService approverService;
    @Autowired
    private AppSecurityUtils appSecurity;
    @Autowired
    private UserService userService;
    @Autowired
    private ResultMap resultMap;

    /**
     * 审批者查询要审批的任务
     * @return
     */
    @ApiOperation("审批者查看待审批的任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskCity", value = "任务城市", required = false, paramType = "query"),
            @ApiImplicitParam(name = "taskPoint", value = "任务地点", required = false, paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "查找起始日期", required = false, paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "查找终止日期", required = false, paramType = "query"),
    })
    @GetMapping("/taskList")
    public ResultMap queryTaskList(
            @RequestParam(value = "taskCity", required = false) String taskCity,
            @RequestParam(value = "taskPoint", required = false) String taskPoint,
            @RequestParam(value = "startDate", required = false) Long startDate,
            @RequestParam(value = "endDate", required = false) Long endDate
    ) {
        // 测试，待动态车间审批
        Integer taskStatus = -1;
        int type = appSecurity.getUser().getType();
        if (type == Status.USER_TYPE_WORKSHOP_APPROVER) {
            taskStatus = Status.TASK_WAIT_WORKSHOP;
        } else if (type == Status.USER_TYPE_SEGMENT_APPROVER) {
            taskStatus = Status.TASK_WAIT_SEGMENT;
        }
        // 查找任务
        List<Task> tasks = userService.showTaskList(null, taskCity, taskStatus,
                taskPoint, startDate, endDate);
        // 没有查找到任务
        if (tasks == null || tasks.size() == 0) {
            return resultMap.fail()
                    .code(ResultMap.FAIL)
                    .msg(ResultMap.NO_CONTENT_QUERY);
        }
        if (tasks.size()==0) {
            return resultMap.success()
                    .code(ResultMap.OK_NO_DATA)
                    .msg(ResultMap.NO_CONTENT_QUERY);
        }
        return resultMap.success().data(tasks)
                .code(ResultMap.OK)
                .msg(ResultMap.SUCCESS_QUERY);
    }

    /**
     * 审批者查询任务的详情
     * @return
     */
    @ApiOperation("审批者查看某个任务的详情")
    @ApiImplicitParam(name = "taskId", value = "任务ID", required = true, paramType = "query")
    @GetMapping("/taskDetail")
    public ResultMap queryTaskDetail(@RequestParam("taskId") Integer taskId) {
        Task task = userService.showTaskDetail(null, taskId);
        if (task != null) {
            return resultMap.success().data(task)
                    .code(ResultMap.OK)
                    .msg(ResultMap.SUCCESS_QUERY);
        }
        return resultMap.fail().code(ResultMap.FAIL)
                .msg(ResultMap.INTERNET_ERROR);
    }

    /**
     * 审批者查询个人信息
     *
     * @return
     */
    @ApiOperation("审批者查看个人信息")
    @GetMapping("/approverInfo")
    public ResultMap approverInfo() {
        Integer approverId = appSecurity.getBindId();
        logger.info("approverInfo" + ": 当前登录审批者ID：" + approverId);
        Approver approver = approverService.showApproverInfo(approverId);
        logger.info("approverInfo" + ":" + approver);
        if (approver != null) {
            return resultMap.success().data(approver)
                    .code(ResultMap.OK)
                    .msg(ResultMap.SUCCESS_QUERY);
        }
        return resultMap.fail().code(ResultMap.FAIL)
                .msg(ResultMap.INTERNET_ERROR);
    }

    /**
     * 审批者审批
     *
     * @return
     */
    @PostMapping("/examine")
    @ApiOperation("审批者审批任务")
    public ResultMap workshopExamineTask(@RequestBody Examine examine) {
        // 审批人ID
        Integer approverId = appSecurity.getBindId();
        Integer res = approverService.examineTask(examine);
        String info = "taskID:" + examine.getTaskId() + ", Result:" +
                examine.getExamineResult() + ", Reason:" + examine.getExamineReason();
        logger.info(info);
        // 查询审批结果，根据{approverId, taskId}
        Examine examine1 = approverService.queryExamine(approverId, examine.getTaskId());
        if (res == -1) {
            return resultMap.fail().code(res).msg("您已审批过该任务");
        } else if (res == -2) {
            return resultMap.fail().code(res).msg("任务可能已被其他审批员审批");
        } else if (res == -3) {
            return resultMap.fail().code(res).msg("系统异常");
        } else if (res == -4) {
            return resultMap.fail().code(res).msg("任务已经结束，不可审批");
        } else {
            return resultMap.success().code(ResultMap.OK).msg("审批成功").data(examine1);
        }
    }

    /**
     * 审批者查看历史审批记录，已同意或者已拒绝
     *
     * @param examineResult
     * @return
     */
    @ApiOperation("查看历史审批记录")
    @GetMapping("/historyRecord")
    public ResultMap queryHistoryTask(@RequestParam Integer examineResult) {
        int approverId = appSecurity.getBindId();
        List<Task> tasks = approverService.showHistoryTask(approverId, examineResult);
        if (tasks == null || tasks.size() == 0) {
            return resultMap.fail().code(ResultMap.FAIL)
                    .msg(ResultMap.NO_CONTENT_QUERY);
        }
        return resultMap.success().data(tasks)
                .code(ResultMap.OK)
                .msg(ResultMap.SUCCESS_QUERY);
    }
}
