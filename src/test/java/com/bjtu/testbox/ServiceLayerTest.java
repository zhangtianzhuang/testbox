package com.bjtu.testbox;


import com.bjtu.testbox.config.constant.Status;
import com.bjtu.testbox.entity.Box;
import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.entity.Worker;
import com.bjtu.testbox.service.AdminService;
import com.bjtu.testbox.service.ApproverService;
import com.bjtu.testbox.service.UserService;
import com.bjtu.testbox.service.WorkerService;
import com.bjtu.testbox.tools.TestboxTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceLayerTest {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLayerTest.class);
    @Autowired
    WorkerService workerService;
    @Autowired
    ApproverService approverService;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    /////////////////////// Worker ///////////////////////////
    @Test
    public void applyTaskTest(){
        Task task = new Task();
        task.setTaskName("测试1");
        task.setTaskWorkerId(1);
        task.setTaskPoint("北京东站");
        task.setTaskWorkerName("李磊");
        task.setTaskDate(System.currentTimeMillis());
        task.setBorrowDate(System.currentTimeMillis());
        task.setReturnDate(System.currentTimeMillis());
        task.setTaskStatus(Status.TASK_WAIT_WORKSHOP);
        task.setTaskDesc("没有描述");

        task.setTaskCity("北京");
        task.setTaskArea("第一工区");
        task.setTaskNumber(TestboxTool.randomTaskNum());
        task.setTaskStatus(Status.TASK_WAIT_WORKSHOP);
        task.setTaskWorkerId(1);

        List<Box> boxes = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Box box = new Box();
            box.setBoxId(i);
            boxes.add(box);
        }
        task.setBoxes(boxes);
        workerService.applyTask(task);
    }

    @Test
    public void queryWorkerInfo(){
        Worker worker = workerService.showWorkerInfo(1);
        System.out.println(worker);
    }

    @Test
    public void queryAppliedTask(){
        List<Task> tasks = workerService.showWorkerTask(1, null, null,
                null, null, null);
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    @Test
    public void queryTaskDetail(){
        Task task = workerService.showTaskDetail(1);
        System.out.println(task);
    }

    @Test
    public void queryTaskStatusNumber(){
//        Map<String, Integer> stringIntegerMap = workerService.selectTaskStatusNumber(-1);
//        System.out.println(stringIntegerMap);
    }

    @Test
    public void test_worker_selectUsableBox(){
        workerService.selectUsableBox();
    }

    /////////////////////// Worker ///////////////////////////


    /////////////////////// Approver ///////////////////////////
    @Test
    public void queryAllTask(){
        List<Task> tasks = approverService.showTaskListByStatus(null, null, null,
                null, null, null);
        for (Task task : tasks) {
            System.out.println(task);
        }
    }
    /////////////////////// Approver ///////////////////////////


    /////////////////////// USER ///////////////////////////

    @Test
    public void user_showTaskDetail(){
        Task task = userService.showTaskDetail(null, 26);
        logger.info("user_showTaskDetail" + " >>> " + task);
    }

    @Test
    public void test_selectTaskStatusNumber(){
        Map<String, Integer> map = userService.selectTaskStatusNumber(null);
        logger.info("test_selectTaskStatusNumber" + " >>> " + map);
    }



    /////////////////////// USER ///////////////////////////

}
