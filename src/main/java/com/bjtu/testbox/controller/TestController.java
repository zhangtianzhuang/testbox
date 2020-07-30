package com.bjtu.testbox.controller;

import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.service.AdminService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.omg.IOP.TAG_ALTERNATE_IIOP_ADDRESS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class TestController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/hello.json", method = RequestMethod.GET)
    public String getListTag(HttpServletRequest request,
                             @RequestParam(value = "name", required = false, defaultValue = "0") String name) {
        try {
            return "hello :" + name;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "hello everyone !";
    }

    @RequestMapping(value = "/save.json", method = RequestMethod.POST)
    public String saveTag(HttpServletRequest request,
                          @RequestParam(value = "name", required = true) String name,
                          @RequestParam(value = "level", required = true) Integer level) {
        try {
            return "recive your param " + "name: " + name + " level: " + level;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用户查询.
     * @return
     */
    @RequestMapping("/userList")
    @RequiresPermissions("userInfo:view") //权限管理;
    public String userInfo(){
        return "userInfo";
    }

    /**
     * 用户添加;
     * @return
     */
    @RequestMapping("/userAdd")
    @RequiresPermissions("userInfo:add")  //权限管理;
//    @RequiresRoles("vip")
    public String userInfoAdd(){
        return "userInfoAdd";
    }

    /**
     * 用户删除;
     * @return
     */
    @RequestMapping("/userDel")
    @RequiresPermissions("userInfo:del")  //权限管理;
    public String userDel(){
        return "userInfoDel";
    }

    @RequestMapping("/task")
    public String showTask(){
        return "task";
    }

    /**
     * 测试前后端数据交互
     * @param model
     * @param statusCode
     * @return
     */
    @RequestMapping("/task/status")
    public String showTaskByStatus(Model model,@RequestParam(value = "statusCode")int statusCode){
        System.out.println("请求状态");
        Map<Integer,String> mapStatusShow = new HashMap<>();
        mapStatusShow.put(0,"待审核");
        mapStatusShow.put(1,"被拒绝");
        mapStatusShow.put(2,"待领用");
        mapStatusShow.put(3,"待归还");
        mapStatusShow.put(4,"已完成");

        // 根据状态码显示的任务信息
        List<Task> taskInfo = new ArrayList<>();
        if(statusCode >=0 && statusCode <= 4) {
            taskInfo = adminService.showSimpleTasksByStatus(statusCode);
        }else
            taskInfo = adminService.showSimpleTasks();

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
        return "task::task-list";
    }
}
