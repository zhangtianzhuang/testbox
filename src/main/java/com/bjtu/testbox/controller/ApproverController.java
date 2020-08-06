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
import java.util.Map;

@Controller
//@RequestMapping("/approvers")
public class ApproverController {

    private static final String TAG = "ApproverController";
    private Logger logger = LoggerFactory.getLogger(TAG);

    @Autowired
    private ApproverService approverService;

    /**
     * 审批者主界面
     * @return
     */
    @RequestMapping("/approvers")
    public String approverUITest(){
        return "approverUI/re-check";
    }

    /**
     * 测试审批表单的ajax请求
     * @return
     */
    @GetMapping("/approvers/taskListTest")
    @ResponseBody
    public String approverTaskListTest(){
        String datastr = "{ \"data\":[" +
                "{ \"taskNumber\" : 25543254345325, \"approverName\" : \"张三\", \"approveDate\" : 12288087, \"projectArea\" : false, \"taskStatus\" : 0, \"taskId\" : 1}, " +
                "{ \"taskNumber\" : 43242354325432, \"approverName\" : \"张三\", \"approveDate\" : 37601280, \"projectArea\" : false, \"taskStatus\" : 1, \"taskId\" : 2}, " +
                "{ \"taskNumber\" : 35432543254325, \"approverName\" : \"张三\", \"approveDate\" : 40783872, \"projectArea\" : true, \"taskStatus\" : 2, \"taskId\" : 3}, " +
                "{ \"taskNumber\" : 45432543253253, \"approverName\" : \"张三\", \"approveDate\" : 29380608, \"projectArea\" : true, \"taskStatus\" : 3, \"taskId\" : 4}]}";
        return datastr;
    }

    /**
     * 审批者查询要审批的任务
     * @param map
     * @return
     */
    @GetMapping("/taskList")
    @ResponseBody
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
    @GetMapping("/approvers/taskDetail")
    @ResponseBody
    public R queryTaskDetail(@RequestBody Map<String, Integer> map){
        int taskId = map.get("taskId");
        Task task = approverService.showTaskDetail(taskId);
        return R.success().code(Code.OK).msg("success").data(task);
    }

    /**
     * 审批者审批
     * @return
     */
    @PostMapping("/examine")
    @ResponseBody
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
}
