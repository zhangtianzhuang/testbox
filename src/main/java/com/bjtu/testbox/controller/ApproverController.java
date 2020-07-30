package com.bjtu.testbox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/approver")
public class ApproverController {

    /**
     * 审批者查询要审批的任务
     * @param taskCity
     * @param taskPoint
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping("/queryTaskList")
    public String queryTaskList(
            @RequestParam(value = "taskCity", defaultValue = "null", required = false) String taskCity,
            @RequestParam(value = "taskPoint", defaultValue = "null", required = false) String taskPoint,
            @RequestParam(value = "startDate", defaultValue = "-1", required = false) long startDate,
            @RequestParam(value = "endDate", defaultValue = "-1", required = false) long endDate
        ){

        return null;
    }

    /**
     * 审批者查询任务的详情
     * @param taskId
     * @return
     */
    @RequestMapping("/queryTaskDetail")
    public String queryTaskDetail(@RequestParam("taskId") int taskId){

        return null;
    }

    /**
     * 动态车间审批
     * @param approverId
     * @param taskId
     * @param examineResult
     * @param examineReason
     * @return
     */
    @RequestMapping("/workshopExamineTask")
    public String workshopExamineTask(int approverId, int taskId, int examineResult, String examineReason){

        return null;
    }

    /**
     * 段生产调度室审批
     * @param approverId
     * @param taskId
     * @param examineResult
     * @param examineReason
     * @return
     */
    @RequestMapping("/segmentExamineTask")
    public String segmentExamineTask(int approverId, int taskId, int examineResult, String examineReason){

        return null;
    }
}
