package com.bjtu.testbox.mapper;

import com.bjtu.testbox.entity.Task;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TaskMapper {
    /**
     * 创建一个任务
     * @param task 任务实体
     * @return
     */
    void insertTask(Task task);

    /**
     * 查询所申请的任务的列表，简单信息
     * @param taskCity 工作城市
     * @param workerId 申请人ID
     * @param taskStatus 任务状态
     * @param taskPoint 工作地点
     * @param startDate 起始日期
     * @param endDate  终止日期
     * @return
     */
    List<Task> queryTask(@Param("taskCity") String taskCity, @Param("workerId") int workerId,
                         @Param("taskStatus") int taskStatus, @Param("taskPoint") String taskPoint,
                         @Param("startDate") long startDate, @Param("endDate") long endDate);

    List<Task> querySimpleTask();

    /**
     * 查询某个表单的详细信息
     * @param taskId
     * @return
     */
    Task queryTaskDetail(@Param("taskId") int taskId);

    /**
     * 修改任务状态
     * @param taskId
     * @param taskStatus
     * @return
     */
    int updateTaskStatus(@Param("taskId") int taskId, @Param("taskStatus") int taskStatus);

    /**
     * 查询数据库中所有任务处于各个状态的数目
     * Mybatis有问题，取出的int类型会变成long
     * @return status ： 1
     *         num : 1
     */
    List<Map<String,Object>> queryTaskStatusNum();
}
