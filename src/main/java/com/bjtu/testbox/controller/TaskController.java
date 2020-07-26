package com.bjtu.testbox.controller;

import com.bjtu.testbox.service.TaskService;
import com.bjtu.testbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.Map;
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
        return "adminUI/jobadmin";
    }
}
