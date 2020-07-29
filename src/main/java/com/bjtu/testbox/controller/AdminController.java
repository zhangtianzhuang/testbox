package com.bjtu.testbox.controller;

import com.bjtu.testbox.entity.Box;
import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AdminController {
    // 查询所有任务的状态数目
    static final Map<Integer,String> mapStatusCode;
    static {
        mapStatusCode = new HashMap<Integer, String>();
        mapStatusCode.put(1,"checkpending1");    // 待一级审核
        mapStatusCode.put(2,"checkpending2");    // 待二级审核
        mapStatusCode.put(3,"standingby");       // 待领用
        mapStatusCode.put(4,"notreturn");        // 待归还
        mapStatusCode.put(5,"completed");        // 已完成
        mapStatusCode.put(6,"rejected");         // 被拒绝
    }

    static final Map<Integer,String> mapStatusShow;
    static {
        mapStatusShow = new HashMap<>();
        mapStatusShow.put(1,"待一级审核");
        mapStatusShow.put(2,"待二级审核");
        mapStatusShow.put(3,"待领用");
        mapStatusShow.put(4,"待归还");
        mapStatusShow.put(5,"已完成");
        mapStatusShow.put(6,"被拒绝");
    }
    @Autowired
    private AdminService adminService;


    @RequestMapping("/jobadmin")
    public String taskAdmin(Model model){
        System.out.println("管理员界面");
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
        System.out.println("状态数查询完毕");

        // 根据状态码显示的任务信息
        List<Task> taskInfo = adminService.showSimpleTasks();

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
        System.out.println("查询结果完毕");
        return "adminUI/jobadmin";
    }

    @RequestMapping("/jobadmin/status")
    public String showJobByStatus(Model model,@RequestParam(value = "statusCode") int statusCode){
        System.out.println("ajax请求:statusCode="+statusCode);
        List<Task> taskInfo = new ArrayList<>();
        if (statusCode > 0 && statusCode < 7){
            taskInfo = adminService.showSimpleTasksByStatus(statusCode);
        }else {
            taskInfo = adminService.showSimpleTasks();
        }
        // 整理，只要任务ID、申请人姓名、申请日期、工作地点、任务状态
        List<Map> taskList = new ArrayList<>();
        for(Task task: taskInfo){
            Map<String,String> taskMap = new HashMap<>();
            taskMap.put("taskKeyID",String.valueOf(task.getTaskId()));
            taskMap.put("taskID",task.getTaskNumber().toString());
            taskMap.put("approverName",task.getTaskWorkerName());
            Date date = new Date(task.getTaskDate());
            taskMap.put("approveDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
//            taskMap.put("approveDate",String.valueOf(task.getTaskDate()));
            taskMap.put("taskPoint",task.getTaskPoint());
            String taskStatus = mapStatusShow.get(task.getTaskStatus());
            taskMap.put("taskStatus",taskStatus);
            taskList.add(taskMap);
        }
        model.addAttribute("taskList",taskList);
        return "adminUI/jobadmin::resultbystatus";
    }

    @RequestMapping("/jobinfo")
    public String jobInfo(@RequestParam(value="taskId")String taskId, Model model){
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
