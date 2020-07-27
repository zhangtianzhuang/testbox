package com.bjtu.testbox.controller;

import com.bjtu.testbox.entity.Box;
import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;


    @RequestMapping("/jobadmin")
    public String taskAdmin(HttpServletRequest request, @RequestParam(value = "taskStatusCode",required = false)int taskStatusCode, Model model){
        // 查询所有任务的状态数目
        Map<Integer,String> mapStatusCode = new HashMap<Integer, String>();
        mapStatusCode.put(0,"checkpending");    // 待审核
        mapStatusCode.put(1,"rejected");        // 被拒绝
        mapStatusCode.put(2,"standingby");      // 待领用
        mapStatusCode.put(3,"notreturn");       // 待归还
        mapStatusCode.put(4,"completed");       // 已完成

        Map<Integer,String> mapStatusShow = new HashMap<>();
        mapStatusShow.put(0,"待审核");
        mapStatusShow.put(1,"被拒绝");
        mapStatusShow.put(2,"待领用");
        mapStatusShow.put(3,"待归还");
        mapStatusShow.put(4,"已完成");

        List<Map<String, Object>> statusNum = adminService.getTaskStatusNum();
        Map<String,Integer> statusNumStand = new HashMap<String, Integer>();
        // 初始化为计数为0
        for(String cate: mapStatusCode.values()){
            statusNumStand.put(cate,0);
        }

        // 根据查询结果修正类别数
        for(Map<String, Object> itea: statusNum){
            int statusCode = Integer.parseInt(String.valueOf(itea.get("status")));
            int statusCount = Integer.parseInt(String.valueOf(itea.get("num")));
            statusNumStand.put(mapStatusCode.get(statusCode),statusCount);
        }

        model.addAllAttributes(statusNumStand);
        int sumTask = 0;
        for(String key:statusNumStand.keySet()){
            sumTask += statusNumStand.get(key);
        }
        model.addAttribute("sumTask",sumTask);

        // 根据状态码显示的任务信息
        List<Task> taskInfo = null;
        if(taskStatusCode < 0 || taskStatusCode > 4)
            taskInfo = adminService.showSimpleTasks();
        else
            taskInfo = adminService.showSimpleTasksByStatus(taskStatusCode);

        // 整理，只要任务ID、申请人姓名、申请日期、工作地点、任务状态
        List<Map> taskList = new ArrayList<>();
        for(Task task: taskInfo){
            Map<String,String> taskMap = new HashMap<>();
            taskMap.put("taskKeyID",String.valueOf(task.getTaskId()));
            taskMap.put("taskID",task.getTaskNumber().toString());
            taskMap.put("approverName",task.getTaskWorkerName());
            Date date = new Date(task.getTaskDate());
            taskMap.put("approveDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            taskMap.put("taskPoint",task.getTaskPoint());
            String taskStatus = mapStatusShow.get(task.getTaskStatus());
            taskMap.put("taskStatus",taskStatus);
            taskList.add(taskMap);
        }
        model.addAttribute("taskList",taskList);
        return "adminUI/jobadmin";
    }

    @RequestMapping("/jobinfo")
    public String jobInfo(HttpServletRequest request, @RequestParam(value="taskId")String taskId, Model model){
        System.out.println("查看详情："+taskId);
        // 根据taskId填充信息表
        Task task = adminService.queryTaskDetial(Integer.parseInt(taskId));

        // 前端展示信息
        class DetialInfo{
            public String taskID;
            public String proposerName;
            public String area;
            public String projectName;
            public String projectPoint;
            public String timeRange;
            public String applyTime;
            public String boxes;
            public String desc;
        }
        DetialInfo taskInfo = new DetialInfo();
        taskInfo.taskID = task.getTaskNumber();
        taskInfo.proposerName = task.getTaskWorkerName();
        taskInfo.area = task.getTaskArea();
        taskInfo.projectName = task.getTaskName();
        taskInfo.projectPoint = task.getTaskPoint();
        taskInfo.timeRange = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(task.getBorrowDate()))
                + " - " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(task.getReturnDate()));
        taskInfo.applyTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(task.getTaskDate()));
        StringBuilder strboxes = new StringBuilder("");
        for(Box box:task.getBoxes()){
            strboxes.append(box.getBoxName());
            strboxes.append(" ");
        }
        taskInfo.boxes = strboxes.toString();
        taskInfo.desc = task.getTaskDesc();
        model.addAttribute("taskinfo",taskInfo);
        return "adminUI/jobinfo";
    }
}
