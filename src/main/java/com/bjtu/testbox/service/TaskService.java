package com.bjtu.testbox.service;

import java.util.List;
import java.util.Map;

/**
 * 与作业管理业务有关的接口类
 */
public interface TaskService {
    /**
     * 查询所有任务中各种状态的任务数目
     * @return
     */
    public  Map<String,Integer> getTaskStatusNum();
}
