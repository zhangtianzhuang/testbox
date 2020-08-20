package com.bjtu.testbox;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.bjtu.testbox.config.constant.Status;
import com.bjtu.testbox.entity.*;
import com.bjtu.testbox.mapper.*;
import com.bjtu.testbox.tools.TestboxTool;
import com.bjtu.testbox.tools.model.BoxOption;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    private static final Logger logger = LoggerFactory.getLogger(MapperLayerTest.class);
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
        List<Task> tasks = taskMapper.queryTask(null, 1, null,
                null, null, null);
        for (Task task : tasks) {
            System.out.println(task);
        }
    }
    @Test
    public void taskSelectTest2(){
        Task task = taskMapper.queryTaskDetail(13);
        System.out.println(task);
    }
    @Test
    public void taskUpdateTest1(){
        taskMapper.updateTaskStatus(1, 2);
    }

    @Test
    public  void taskStatusNumTest(){
        List<Map<String,Object>> statusCount = taskMapper.queryTaskStatusNum(-1);
        System.out.println(statusCount.get(0).get("num").getClass().getName());
    }

    @Test
    public void taskInfoTest(){
        List<Task> tasks = taskMapper.queryAllTasks();
        for(Task t: tasks){
            System.out.println(t);
        }
    }

    @Test
    public void test_task_selectMaxId(){
        Integer integer = taskMapper.selectMaxId();
        System.out.println(integer);
    }

    @Test
    public void test_task_insertTaskBox(){
        taskMapper.insertTaskBox(1, 2);
    }

    @Test
    public void test_task_queryTaskByApprover(){
        List<Task> tasks = taskMapper.queryTaskByApprover(1, 1);
        for (Task task : tasks) {
            System.out.println("taskId：" + task.getTaskId()+", taskNumber:"+task.getTaskNumber());
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

    @Test
    public void boxTest_selectBoxNumber(){
        List<Box> boxes = boxMapper.selectBoxNumberMul(null, null, null);
        for (Box box : boxes) {
//            System.out.println("编号:"+box.getBoxNumber()+", 类型:"+Status.BOX_TYPE.get(box.getBoxType())
//                    +", 区域:"+box.getBoxArea()+", ID:"+box.getBoxId());
            System.out.println(box);
        }
    }

    @Test
    public void boxTest_selectBoxNumber2(){
        BoxOption boxOption = boxMapper.selectBoxNumberMul2(null, null, null);
        System.out.println(boxOption);
    }

    @Test
    public void boxTest_selectBoxNumber1(){
        List<Box> boxes = boxMapper.selectBoxNumberMul(0, "成都", 1);
        System.out.println(boxes);
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

    @Test
    public void test_hasExamined(){
        Examine examine = examineMapper.hasExamined(1, 13);
        String info = "examine是否为空?" + examine;
        logger.info(info);
    }

    @Test
    public void test_queryTaskExamineRecord(){
        List<Examine> examines = examineMapper.queryTaskExamineRecord(3);
        logger.info("test_queryTaskExamineRecord" + " >>> " + examines);
    }


    //////////////////  Examine   //////////////////////////

    //////////////////  Shiro   //////////////////////////

    @Autowired
    ShiroMapper shiroMapper;
    @Test
    public void getRole(){
        User user = shiroMapper.getRoleAndPerm("");
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

    @Test
    public void test_queryTaskStatusNum(){
        List<Map<String, Object>> maps = taskMapper.queryTaskStatusNum(1);
        Map<String, String> m = new HashMap<>();
        for (Map<String, Object> map : maps) {
            String num = String.valueOf(map.get("num"));
            String status = String.valueOf(map.get("status"));
            m.put(status, num);
        }
        System.out.println(m);
    }

    //////////////////// USER ///////////////////////////
    @Autowired
    UserMapper userMapper;
    @Test
    public void test_user_getArray(){
    }
    //////////////////// USER ///////////////////////////


    ////////////// 添加试验箱和线缆 /////////////////
    @Test
    public void add_boxes(){
        //直流岔道型
        String name1 = Status.BOX_TYPE.get(Status.BOX_TYPE_DC_CROSSROAD);
        //交流岔道型
        String name2 = Status.BOX_TYPE.get(Status.BOX_TYPE_AC_CROSSROAD);
        //轨道电路型
        String name3 = Status.BOX_TYPE.get(Status.BOX_TYPE_ELECTRICITY);
        String cities[] = {"成都", "重庆", "贵阳"};
        String tag[] = {"DZ", "DJ", "GW"};
        String names[] = {name1, name2, name3};
        int type[] = {0, 1, 2};

//        int i = 0; // 类型
//        int j = 0; // 城市
//        int n = 1; // 箱子数
        int k = 10;
        // 先存一个箱子
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int n = 1; n <= k; n++) {
                    Box box = new Box();
                    box.setBoxNumber(tag[i]+"-"+((j*k)+n));
                    box.setBoxType(type[i]);
                    box.setBoxName(names[i]);
                    box.setBoxTagId(TestboxTool.randomString(8));
                    box.setBoxArea(cities[j]);
                    box.setBoxStatus(Status.BOX_STATUS_IN_REPOSITORY);
                    logger.info(">>> " + box.getBoxNumber()+"\t"+box.getBoxType()+"\t"
                    +box.getBoxName()+"\t"+box.getBoxArea());
                    boxMapper.insertBox(box);
                }
            }
        }

    }

    @Test
    public void addCableforBox(){
        List<Box> boxes = boxMapper.selectBoxNumberMul(null, null, null);
        for (Box box : boxes) {
            int type = box.getBoxType();
            String number = box.getBoxNumber();
            Cable cable = new Cable();
            int boxId = box.getBoxId();
            String cableArea = box.getBoxArea();
            cable.setCableBoxId(boxId);
            cable.setCableArea(cableArea);
            cable.setCableTagId(TestboxTool.randomString(10));
            if (type == Status.BOX_TYPE_DC_CROSSROAD || type == Status.BOX_TYPE_AC_CROSSROAD){ //直流或者交流
                for (int i = 1; i <= 4; i++) {  // 2.2m
                    cable.setCableNumber(number+"-"+i);
                    cable.setCableType(Status.CABLE_TYPE_2);
                    logger.info(">>> 试验箱编号:"+number+"\t线缆编号:"+cable.getCableNumber()+"\t");
                    cableMapper.insertCable(cable);
                }
                for (int i = 5; i <= 8; i++) {  // 1.5m
                    cable.setCableNumber(number+"-"+i);
                    cable.setCableType(Status.CABLE_TYPE_1);
                    logger.info(">>> 试验箱编号:"+number+"\t线缆编号:"+cable.getCableNumber()+"\t");
                    cableMapper.insertCable(cable);
                }
                for (int i = 0; i < 8; i++) {
                    cable.setCableNumber("ADAPTER-CABLE");
                    cable.setCableType(Status.CABLE_TYPE_3);
                    logger.info(">>> 试验箱编号:"+number+"\t线缆编号:"+cable.getCableNumber()+"\t");
                    cableMapper.insertCable(cable);
                }
            }else{  //轨道电路
                for (int i = 1; i <= 12; i++) {  // 2.2m
                    cable.setCableNumber(number+"-"+i);
                    cable.setCableType(Status.CABLE_TYPE_2);
                    logger.info(">>> 试验箱编号:"+number+"\t线缆编号:"+cable.getCableNumber()+"\t");
                    cableMapper.insertCable(cable);
                }
                for (int i = 13; i <= 24; i++) {  // 1.5m
                    cable.setCableNumber(number+"-"+i);
                    cable.setCableType(Status.CABLE_TYPE_1);
                    logger.info(">>> 试验箱编号:"+number+"\t线缆编号:"+cable.getCableNumber()+"\t");
                    cableMapper.insertCable(cable);
                }
            }
        }
    }
    ////////////// 添加试验箱和线缆 /////////////////

    @Test
    public void test1(){

    }
}