package com.bjtu.testbox.controller;

import com.bjtu.testbox.entity.Examine;
import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.service.ApproverService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/approvers")
public class ApproverController {

    private static final String TAG = "ApproverController";
    private Logger logger = LoggerFactory.getLogger(TAG);

    @Autowired
    private ApproverService approverService;

    /**
     * 审批者查询要审批的任务
     * @param taskCity
     * @param taskPoint
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/taskList")
    public String queryTaskList(
            // 参数接收测试没有问题
            @RequestParam(value = "taskStatus", defaultValue = "0") int taskStatus,
            @RequestParam(value = "taskCity", defaultValue = "null") String taskCity,
            @RequestParam(value = "taskPoint", defaultValue = "null") String taskPoint,
            @RequestParam(value = "startDate", defaultValue = "-1") long startDate,
            @RequestParam(value = "endDate", defaultValue = "-1") long endDate,
            Model model
        ){
        List<Task> tasks = approverService.showTaskListByStatus(-1, taskCity, taskStatus,
                taskPoint, startDate, endDate);
        model.addAttribute("tasks", tasks);
        return null;
    }

    /**
     * 审批者查询任务的详情
     * @param taskId
     * @return
     */
    @GetMapping("/taskDetail")
    public String queryTaskDetail(@RequestParam("taskId") int taskId, Model model){
        Task task = approverService.showTaskDetail(taskId);
        model.addAttribute("task", task);
        return null;
    }

    /**
     * 审批者审批
     * @param approverId
     * @param taskId
     * @param examineResult
     * @param examineReason
     * @return
     */
    @PostMapping("/examine")
    public String workshopExamineTask(@Param("approverId") int approverId,
                                      @Param("taskId") int taskId,
                                      @Param("examineResult") int examineResult,
                                      @Param("examineReason") String examineReason,
                                      @Param("examineLevel") int examineLevel){
        Examine examine = new Examine();
        examine.setApproverId(approverId);
        examine.setTaskId(taskId);
        examine.setExamineResult(examineResult);
        examine.setExamineReason(examineReason);
        examine.setExamineLevel(examineLevel);
        int i = approverService.examineTask(examine);
        return null;
    }
}
