package com.bjtu.testbox;


import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.entity.Worker;
import com.bjtu.testbox.service.ApproverService;
import com.bjtu.testbox.service.WorkerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceLayerTest {

    @Autowired
    WorkerService workerService;

    @Autowired
    ApproverService approverService;

    /////////////////////// Worker ///////////////////////////
    @Test
    public void applyTaskTest(){
        Task task = new Task();
        task.setTaskName("工程6");
        task.setTaskWorkerId(1);
        task.setTaskPoint("北京东站");
        task.setTaskDate(System.currentTimeMillis());
        task.setBorrowDate(System.currentTimeMillis());
        task.setReturnDate(System.currentTimeMillis());
        task.setTaskStatus(1);
        task.setTaskDesc("没有描述");
        workerService.applyTask(task);
    }

    @Test
    public void queryWorkerInfo(){
        Worker worker = workerService.showWorkerInfo(1);
        System.out.println(worker);
    }

    @Test
    public void queryAppliedTask(){
        List<Task> tasks = workerService.showWorkerTask(1, null, -1, null, -1, -1);
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    @Test
    public void queryTaskDetail(){
        Task task = workerService.showTaskDetail(5);
        System.out.println(task);
    }

    /////////////////////// Worker ///////////////////////////


    /////////////////////// Approver ///////////////////////////
    @Test
    public void queryAllTask(){
        List<Task> tasks = approverService.showTaskListByStatus(-1, null, -1,
                null, -1, -1);
        for (Task task : tasks) {
            System.out.println(task);
        }
    }
    /////////////////////// Approver ///////////////////////////

}
