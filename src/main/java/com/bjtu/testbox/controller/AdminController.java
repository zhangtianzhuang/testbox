package com.bjtu.testbox.controller;

import com.bjtu.testbox.config.api.Code;
import com.bjtu.testbox.config.api.R;
import com.bjtu.testbox.entity.Box;
import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.service.AdminService;
import com.bjtu.testbox.service.BoxService;
import com.bjtu.testbox.tools.TestboxTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/admins", produces = "application/json;charset=UTF-8")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private BoxService boxService;

    /**
     * 获取个人信息
     * @return
     */
    @GetMapping("/adminInfo")
    public R getPersonInfo(){
        return R.success().msg(R.SUCCESS).data(adminService.showPersonInfo()).code(Code.OK);
    }

    /**
     * 查看试验箱列表
     * @param boxStatus
     * @param boxType
     * @param boxArea
     * @return
     */
    @GetMapping("/boxes")
    public R getBoxes(
            @RequestParam(value = "boxStatus", required = false) Integer boxStatus,
            @RequestParam(value = "boxType", required = false) Integer boxType,
            @RequestParam(value = "boxArea", required = false) String boxArea
        ){
        List<Box> boxes = adminService.showBoxes(boxStatus, boxType, boxArea);
        return R.success().data(boxes).msg(R.SUCCESS).code(Code.OK);
    }

    /**
     * 查询单个试验箱
     * @return
     */
    @GetMapping("/box")
    public R getBox(@RequestParam("boxId") Integer boxId){
        Box box = adminService.showBoxInfo(boxId);
        return R.success().data(box).msg(R.SUCCESS).code(Code.OK);
    }

    @GetMapping("/boxCount")
    public R getBoxNumber(@RequestParam(value = "boxType", required = false) Integer boxType){
        Map<Integer, Integer> map = adminService.boxNubmerByTypeAndStatus(boxType);
        return R.success().data(map).code(Code.OK).msg(R.SUCCESS);
    }

    @RequestMapping("/jobadmin")
    public String taskAdmin(Model model){

        List<Map<String, Object>> statusNum = adminService.getTaskStatusNum();
        Map<String,Integer> statusNumStand = new HashMap<String, Integer>();
        // 初始化为计数为0
        for(String cate: TestboxTool.mapStatusCode.values()){
            statusNumStand.put(cate,0);
        }

        // 根据查询结果修正类别数
        for(Map<String, Object> itea: statusNum){
            int statusCode = Integer.parseInt(String.valueOf(itea.get("status")));
            int statusCount = Integer.parseInt(String.valueOf(itea.get("num")));
            statusNumStand.put(TestboxTool.mapStatusCode.get(statusCode),statusCount);
        }

        model.addAllAttributes(statusNumStand);
        System.out.println("dddddddddddddd  " +statusNumStand);
        int sumTask = 0;
        for(String key:statusNumStand.keySet()){
            sumTask += statusNumStand.get(key);
        }
        model.addAttribute("sumTask",sumTask);
        System.out.println(sumTask);

        // 显示所有任务
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
            String taskStatus = TestboxTool.mapStatusShow.get(task.getTaskStatus());
            taskMap.put("taskStatus",taskStatus);
            taskList.add(taskMap);
        }
        model.addAttribute("taskList",taskList);
        System.out.println(taskList);
        return "adminUI/jobadmin";
    }

    @RequestMapping("/jobadmin/status")
    public String showJobByStatus(Model model,@RequestParam(value = "statusCode") int statusCode){
        System.out.println("ajax请求:statusCode="+statusCode);
        List<Task> taskInfo;
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
            String taskStatus = TestboxTool.mapStatusShow.get(task.getTaskStatus());
            taskMap.put("taskStatus",taskStatus);
            taskList.add(taskMap);
        }
        model.addAttribute("taskList",taskList);
        return "adminUI/jobadmin::queryresult";
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

    @RequestMapping("/boxadmin")
    public String boxAdmin(Model model){
        // 查询所有试验箱的信息，将试验箱的编号、状态列表加入到model中
        class BoxInfo{
            public String boxID;
            public int boxStatus;
            public BoxInfo(String id,int status){
                this.boxID=id;
                this.boxStatus=status;
            }
        }
        // 交流道岔型 1
        List<BoxInfo> DJList = new ArrayList<>();
        for(Box box: boxService.queryBoxByType(1)){
            BoxInfo boxInfo = new BoxInfo(box.getBoxNumber(),box.getBoxStatus());
            DJList.add(boxInfo);
        }

        // 直流道岔型 0
        List<BoxInfo> DZList = new ArrayList<>();
        for(Box box: boxService.queryBoxByType(0)){
            BoxInfo boxInfo = new BoxInfo(box.getBoxNumber(),box.getBoxStatus());
            DZList.add(boxInfo);
        }

        // 轨道电路型 2
        List<BoxInfo> GWList = new ArrayList<>() ;
        for(Box box: boxService.queryBoxByType(2)){
            BoxInfo boxInfo = new BoxInfo(box.getBoxNumber(),box.getBoxStatus());
            GWList.add(boxInfo);
        }

        model.addAttribute("DJList",DJList);
        model.addAttribute("DZList",DZList);
        model.addAttribute("GWList",GWList);
        return "adminUI/boxadmin";
    }
}
