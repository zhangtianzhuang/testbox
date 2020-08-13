package com.bjtu.testbox.controller;

import com.bjtu.testbox.config.api.Code;
import com.bjtu.testbox.config.api.R;
import com.bjtu.testbox.config.constant.Status;
import com.bjtu.testbox.entity.Approver;
import com.bjtu.testbox.entity.Examine;
import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.service.ApproverService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/approvers", produces = "application/json;charset=UTF-8")
public class ApproverController {

    private static final String TAG = "ApproverController";
    private Logger logger = LoggerFactory.getLogger(TAG);

    @Autowired
    private ApproverService approverService;

    /**
     * 审批者查询要审批的任务
     * @param map
     * @return
     */
    @PostMapping("/taskList")
    public R queryTaskList( @RequestBody Map<String, Object> map){
        String taskCity = (String) map.get("taskCity");
        String taskPoint = (String) map.get("taskPoint");
        Long startDate = (Long) map.get("startDate");
        Long endDate = (Long) map.get("endDate");
        // 测试，待动态车间审批
        Integer taskStatus = Status.TASK_WAIT_WORKSHOP;
        List<Task> tasks = approverService.showTaskListByStatus(null, taskCity, taskStatus,
                taskPoint, startDate, endDate);
        return R.success().data(tasks).code(Code.OK).msg(R.SUCCESS);
    }

    /**
     * 审批者查询任务的详情
     * @param map
     * @return
     */
    @PostMapping("/taskDetail")
    public R queryTaskDetail(@RequestBody Map<String, Integer> map){
        int taskId = map.get("taskId");
        Task task = approverService.showTaskDetail(taskId);
        return R.success().code(Code.OK).msg("success").data(task);
    }

    /**
     * 审批者查询个人信息
     * @return
     */
    @GetMapping("/approverInfo")
    public R approverInfo(){
        Integer approverId = 1;
        Approver approver = approverService.showApproverInfo(approverId);
        return R.success().msg(R.SUCCESS).code(Code.OK).data(approver);
    }

    /**
     * 审批者审批
     * @return
     */
    @PostMapping("/examine")
    public R workshopExamineTask(@RequestBody Examine examine){
         Examine examineTask = approverService.examineTask(examine);
         String info = "taskID:"+examine.getTaskId()+", Result:" +
                 examine.getExamineResult() + ", Reason:"+examine.getExamineReason();
         logger.info(info);
         if (examineTask != null)
             return R.success().data(examineTask).msg(R.SUCCESS).code(Code.OK);
         else
             return R.fail().code(Code.SERVER_ERROR).msg(R.FAILURE);
    }

    /**
     * 审批者查看历史审批记录，已同意或者已拒绝
     * @param examineResult
     * @return
     */
    @GetMapping("/historyRecord")
    public R queryHistoryTask(@RequestParam Integer examineResult){
        int approverId = 1;
        List<Task> tasks = approverService.showHistoryTask(approverId, examineResult);
        return R.success().data(tasks).code(Code.OK).msg(R.SUCCESS);
    }
}
