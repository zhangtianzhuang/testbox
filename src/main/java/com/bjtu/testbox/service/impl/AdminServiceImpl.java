package com.bjtu.testbox.service.impl;

import com.bjtu.testbox.config.constant.Status;
import com.bjtu.testbox.entity.Admin;
import com.bjtu.testbox.entity.Box;
import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.mapper.AdminMapper;
import com.bjtu.testbox.mapper.BoxMapper;
import com.bjtu.testbox.mapper.CableMapper;
import com.bjtu.testbox.mapper.TaskMapper;
import com.bjtu.testbox.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 与作业管理业务有关的接口类实现
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BoxMapper boxMapper;

    @Autowired
    private CableMapper cableMapper;

    /**
     * 查询所有任务处于不同状态的数目
     * @return
     */
    @Override
    public List<Map<String, Object>> getTaskStatusNum() {
        /**
         * 从taskMapper中查询出来的状态类别是状态码
         * 将不同状态码对具体状态名做映射
         * 对于不存在的状态设置为0
         */
        return taskMapper.queryTaskStatusNum(-1);
    }

    /**
     * 查询所有任务的简单信息：任务ID、申请人姓名、申请日期、工作地点、任务状态
     * @return
     */
    @Override
    public List<Task> showSimpleTasks() {
        return taskMapper.queryAllTasks();
    }

    /**
     * 根据状态码查询任务的简单信息
     * @param taskStatus
     * @return
     */
    @Override
    public List<Task> showSimpleTasksByStatus(int taskStatus){
        return  taskMapper.queryTaskByStatus(taskStatus);
    }

    @Override
    public Task queryTaskDetial(int taskId){
        return taskMapper.queryTaskDetail(taskId);
    }

    @Override
    public Admin showPersonInfo(Integer adminId) {
        // 测试
        return adminMapper.showPersonInfo(adminId);
    }

    @Override
    public Box showBoxInfo(Integer boxId) {
        // 查询试验箱基本信息
        Box box = boxMapper.selectBoxAndCable(boxId);
        // 查询不同类型线缆数量
        List<Map<String, Object>> maps = cableMapper.cableNumberByType(boxId);
        for (Map<String, Object> map : maps) {
            Integer type = Integer.parseInt(String.valueOf(map.get("type")));
            Integer number = Integer.parseInt(String.valueOf(map.get("number")));
            if (type==1){  // 1.5米线缆
                box.setLen15(number);
            }else if (type==2){ // 2.2米线缆
                box.setLen22(number);
            }else { // 其他线缆
                box.setLenOther(number);
            }
        }
        return box;
    }

    @Override
    public List<Box> showBoxes(Integer boxStatus, Integer boxType, String boxArea) {
        return boxMapper.selectBoxNumberMul(boxStatus, boxArea, boxType);
    }

    @Override
    public Map<Integer, Integer> boxNubmerByTypeAndStatus(Integer boxType) {
        List<Map<String, Object>> maps = boxMapper.boxByTypeAndStatus(boxType);
        Map<Integer, Integer> result = new HashMap<>();
        // 初始化
        for (int i = 0; i < 4; i++)
            result.put(i, 0);
        for (Map<String, Object> map : maps) {
            Integer status = Integer.parseInt(String.valueOf(map.get("status")));
            Integer number = Integer.parseInt(String.valueOf(map.get("number")));
            result.put(status, number);
//            if (status==Status.BOX_STATUS_IN_REPOSITORY){  // 在库中
//            }else if (status==Status.BOX_STATUS_BUG){  // 故障
//                box.setLen15(number);
//            }else if (status==Status.BOX_STATUS_OUT_REPOSITORY){ // 出库
//                box.setLen22(number);
//            }else if(status==Status.BOX_STATUS_LOST){ // 丢失
//                box.setLenOther(number);
//            }
        }
        return result;
    }
}
