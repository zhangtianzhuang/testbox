package com.bjtu.testbox.service.impl;

import com.bjtu.testbox.mapper.TaskMapper;
import com.bjtu.testbox.service.TaskService;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 与作业管理业务有关的接口类实现
 */
@Service
public class TaskServiceImpl implements TaskService {
    /* 状态码到状态名的映射 */
    private static Map<Integer,String> mapStatusCode = new HashMap<Integer, String>();
    static {
        mapStatusCode.put(0,"checkpending");    // 待审核
        mapStatusCode.put(1,"rejected");        // 被拒绝
        mapStatusCode.put(2,"standingby");      // 待领用
        mapStatusCode.put(3,"notreturn");       // 待归还
        mapStatusCode.put(4,"completed");       // 已完成
    }


    @Autowired
    TaskMapper taskMapper;

    /**
     * 查询所有任务处于不同状态的数目
     * @return
     */
    @Override
    public Map<String,Integer> getTaskStatusNum() {
        /**
         * 从taskMapper中查询出来的状态类别是状态码
         * 将不同状态码对具体状态名做映射
         * 对于不存在的状态设置为0
         */
        List<Map<String, Integer>> statusNum = taskMapper.queryTaskStatusNum();
        Map<String,Integer> statusNumStand = new HashMap<String, Integer>();
        // 初始化为计数为0
        for(String cate: mapStatusCode.values()){
            statusNumStand.put(cate,0);
        }

        // 根据查询结果修正类别数
        for(Map<String, Integer> itea: statusNum){
            int statusCode = itea.get("status");
            int statusCount = itea.get("num");
            statusNumStand.put(mapStatusCode.get(statusCode),statusCount);
        }
        return statusNumStand;
    }
}
