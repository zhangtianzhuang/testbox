package com.bjtu.testbox;

import com.bjtu.testbox.entity.*;
import com.bjtu.testbox.mapper.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperLayerTest {

    @Autowired
    WorkerMapper workerMapper;
    @Autowired
    BoxMapper boxMapper;
    @Autowired
    CableMapper cableMapper;
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    ExamineMapper examineMapper;

    //////////////////  Worker   //////////////////////////

    @Test
    public void workerInsertTest1(){
        Worker w = new Worker("Tom", "abc", "第一工区",
                "第一班组","北京", "18888888888");
        workerMapper.insertWorker(w);
    }

    @Test
    public void workerSelectTest1(){
        Worker select = workerMapper.selectByNumber(456);
        assert select != null;
    }


    @Test
    public void workerUpdateTest1(){
        workerMapper.updateWorkerInfo(1, "newName", "newArea",
                "newGroup", "newCity", "newPhone");
    }

    //////////////////  Worker   //////////////////////////

    //////////////////  Task   //////////////////////////
    @Test
    public void taskInsertTest1(){
        Task task = new Task();
        task.setTaskName("工程1");
        task.setBorrowDate(System.currentTimeMillis());
        task.setTaskDate(System.currentTimeMillis());
        task.setReturnDate(System.currentTimeMillis());
        task.setTaskNumber("0001");
        task.setTaskPoint("北京");
        task.setTaskWorkerId(1);
        task.setTaskWorkerName("ztz");
        task.setTaskStatus(0);
        task.setTaskDesc("没有描述");
        taskMapper.insertTask(task);
    }
    @Test
    public void taskSelectTest1(){
        String taskCity = null;
        int workerId = 2;
        int taskStatus = -1;
        String taskPoint = null;
        long startDate = System.currentTimeMillis()-1000*60*60*24;
        long endDate = System.currentTimeMillis();

        List<Task> tasks = taskMapper.queryTask(taskCity, workerId, taskStatus, taskPoint, startDate, endDate);
        for (Task task : tasks) {
            System.out.println(task);
        }
    }
    @Test
    public void taskSelectTest2(){
        Task task = taskMapper.queryTaskDetail(1);
        System.out.println(task);
    }
    @Test
    public void taskUpdateTest1(){
        taskMapper.updateTaskStatus(1, 2);
    }

    @Test
    public  void taskStatusNumTest(){
        List<Map<String,Object>> statusCount = taskMapper.queryTaskStatusNum();
        System.out.println(statusCount.get(0).get("num").getClass().getName());
    }

    @Test
    public void taskInfoTest(){
        List<Task> tasks = taskMapper.queryAllTasks();
        for(Task t: tasks){
            System.out.println(t);
        }
    }
    //////////////////  Task   //////////////////////////



    //////////////////  Box   //////////////////////////
    @Test
    public void boxInsertTest1(){
        Box box = new Box();
    }

    @Test
    public void boxSelectTest1(){
        String s = boxMapper.selectBoxNumber("3");
        System.out.println(s==null);
    }

    @Test
    public void boxSelectTest2(){
        System.out.println(boxMapper.selectBasicInfo(1));
    }

    @Test
    public void boxSelectTest3(){
        System.out.println(boxMapper.selectBoxId(0));
    }

    @Test
    public void boxSelectTest4(){
        System.out.println(boxMapper.selectBoxAndCable(1));
    }


    @Test
    public void boxUpdateTest1(){
        boxMapper.updateBoxStatus(1, 3);
    }

    @Test
    public void boxUpdateTest2(){
        boxMapper.updateBoxBasicInfo("杭州", "ABCDEFG", 1);
    }

    //////////////////  Box   //////////////////////////


    //////////////////  Cable   //////////////////////////

    @Test
    public void cableInsertTest1(){
        Cable c = new Cable("2222", 1, "EFDFD", "北京",
                1);
        cableMapper.insertCable(c);
    }

    @Test
    public void cableSelectTest1(){
        System.out.println(cableMapper.findCableInBox(1, "00001"));
    }

    @Test
    public void cableSelectTest2(){
        System.out.println(cableMapper.findCable(1));
    }


    @Test
    public void cableUpdateTest1(){
        cableMapper.updateCableInfo(1, null, null, 2);
    }

    @Test
    public void cableDeleteTest1(){
        cableMapper.deleteCableById(8);
    }

    //////////////////  Cable   //////////////////////////

    @Autowired
    ApproverMapper approverMapper;
    //////////////////  Approver   //////////////////////////
    @Test
    public void approverSelectTest1(){
        System.out.println(approverMapper.queryApproverById(2));
    }

    @Test
    public void approverInsertTest1(){
        Approver approver = new Approver();
        approver.setApproverName("审批者1");
        approver.setApproverNumber("APPROVER-001");
        approver.setApproverLevel(1);
        approver.setApproverPhone("18888888999");
        approverMapper.insertApprover(approver);
    }

    @Test
    public void approverUpdateTest1() {
        approverMapper.updateApproverPhone("11111", 1);
    }

    //////////////////  Approver   //////////////////////////


    //////////////////  Examine   //////////////////////////

    @Test
    public void examineInsertTest1(){
        Examine ex = new Examine();
        ex.setApproverId(1);
        ex.setTaskId(1);
        ex.setExamineLevel(1);
        ex.setExamineResult(1);
        ex.setExamineReason("棒!");
        examineMapper.insertExamine(ex);
    }
    //////////////////  Examine   //////////////////////////

    //////////////////  Shiro   //////////////////////////

    @Autowired
    ShiroMapper shiroMapper;
    @Test
    public void getRole(){
        User user = shiroMapper.getRoleAndPerm(1);
        System.out.println(user.getUid());
        List<SysRole> roles =  user.getRoleList();
        for (SysRole role : roles) {
            System.out.println(role.getRole());
            List<SysPermission> perms = role.getPermissions();
            for (SysPermission perm : perms) {
                System.out.println(perm.getPermission());
            }
        }
    }
    //////////////////  Shiro   //////////////////////////

}
