package com.bjtu.testbox.controller;

import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.LoggingMXBean;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;


    @RequestMapping("/jobadmin")
    public String taskAdmin(Model model){
        // 查询所有任务的状态数目
        Map<String, Integer> statusCount = taskService.getTaskStatusNum();
        model.addAllAttributes(statusCount);
        int sumTask = 0;
        for(String key:statusCount.keySet()){
            sumTask += statusCount.get(key);
        }
        model.addAttribute("sumTask",sumTask);

        // 显示所有的任务信息
        List<Task> allTaskInfo = taskService.showSimpleTasks();
        // 整理，只要任务ID、申请人姓名、申请日期、工作地点、任务状态
        List<Map> taskList = new ArrayList<>();
        for(Task task: allTaskInfo){
            Map<String,String> taskMap = new HashMap<>();
            taskMap.put("taskID",task.getTaskNumber().toString());
            taskMap.put("approverName",task.getTaskWorkerName());
            Date date = new Date(task.getTaskDate());
            taskMap.put("approveDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            taskMap.put("taskPoint",task.getTaskPoint());
            String taskStatus = String.valueOf(task.getTaskStatus());
            taskMap.put("taskStatus",taskStatus);
            taskList.add(taskMap);
        }
        System.out.println("taskInfo:");
        System.out.println(allTaskInfo);
        model.addAttribute("taskList",taskList);

        return "adminUI/jobadmin";
    }
}
