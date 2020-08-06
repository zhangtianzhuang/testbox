package com.bjtu.testbox.controller;

import com.bjtu.testbox.config.api.Code;
import com.bjtu.testbox.config.api.R;
import com.bjtu.testbox.config.constant.Status;
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

@Controller
@RequestMapping("/approvers")
public class ApproverController {

    private static final String TAG = "ApproverController";
    private Logger logger = LoggerFactory.getLogger(TAG);

    @Autowired
    private ApproverService approverService;

    /**
     * 审批者主界面
     * @return
     */
    @RequestMapping("/approver")
    public String approverUITest(){
        return "approverUI/re-check";
    }

    /**
     * 测试审批表单的ajax请求
     * @return
     */
    @GetMapping("/approver/taskListTest")
    @ResponseBody
    public String approverTaskListTest(){
        String datastr = "{ \"data\":[" +
                "{ \"taskId\" : 25543254345325, \"approverName\" : \"张三\", \"approveDate\" : 12288087, \"projectArea\" : false, \"taskStatus\" : 0 }, " +
                "{ \"taskId\" : 43242354325432, \"approverName\" : \"张三\", \"approveDate\" : 37601280, \"projectArea\" : false, \"taskStatus\" : 1 }, " +
                "{ \"taskId\" : 35432543254325, \"approverName\" : \"张三\", \"approveDate\" : 40783872, \"projectArea\" : true, \"taskStatus\" : 2 }, " +
                "{ \"taskId\" : 45432543253253, \"approverName\" : \"张三\", \"approveDate\" : 29380608, \"projectArea\" : true, \"taskStatus\" : 3 }]}";
        return datastr;
    }

    /**
     * 审批者查询要审批的任务
     * @param taskCity
     * @param taskPoint
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/taskList")
    @ResponseBody
    public R queryTaskList(
            // 参数接收测试没有问题
            @RequestParam(value = "taskCity" , required = false) String taskCity,
            @RequestParam(value = "taskPoint", required = false) String taskPoint,
            @RequestParam(value = "startDate", required = false) Long startDate,
            @RequestParam(value = "endDate"  , required = false) Long endDate
        ){
        // 测试，待动态车间审批
        Integer taskStatus = Status.TASK_WAIT_WORKSHOP;
        List<Task> tasks = approverService.showTaskListByStatus(null, taskCity, taskStatus,
                taskPoint, startDate, endDate);
        return R.success().data(tasks).code(Code.OK);
    }

    /**
     * 审批者查询任务的详情
     * @param taskId
     * @return
     */
    @GetMapping("/taskDetail")
    @ResponseBody
    public R queryTaskDetail(@RequestParam("taskId") int taskId){
        Task task = approverService.showTaskDetail(taskId);
        return R.success().code(Code.OK).msg("success").data(task);
    }

    /**
     * 审批者审批
     * @return
     */
    @PostMapping("/examine")
    @ResponseBody
    public R workshopExamineTask(
            @RequestParam Integer taskId,
            @RequestParam Integer examineResult,
            @RequestParam String examineReason
            ){
         Examine examine = new Examine();
         examine.setTaskId(taskId);
         examine.setExamineResult(examineResult);
         examine.setExamineReason(examineReason);

         Examine examineTask = approverService.examineTask(examine);
         String info = "taskID:"+taskId+", Result:" +
                 examineResult + ", Reason:"+examineReason;
         logger.info(info);
         if (examineTask != null)
             return R.success().data(examineTask).msg(R.SUCCESS).code(Code.OK);
         else
             return R.fail().code(Code.SERVER_ERROR).msg(R.FAILURE);
    }
}
