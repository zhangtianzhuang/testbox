package com.bjtu.testbox.controller;

import com.bjtu.testbox.service.TaskService;
import com.bjtu.testbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;
import java.util.logging.LoggingMXBean;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("/hello")
    public String hello(){
        return "index";
    }


    @RequestMapping("/jobadmin")
    public String taskAdmin(Model model){
        // 查询所有任务的状态数目
        Map<String, Integer> statusCount = taskService.getTaskStatusNum();
        model.addAllAttributes(statusCount);
        return "adminUI/jobadmin";
    }
}
