package com.bjtu.testbox.mapper;

import com.bjtu.testbox.entity.Task;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

    @Insert("insert into taskbox (task_id, box_id) values (#{taskId}, #{boxId})")
    void insertTaskBox(@Param("taskId") int taskId, @Param("boxId") int boxId);


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
    List<Task> queryTask(@Param("taskCity") String taskCity, @Param("workerId") Integer workerId,
                         @Param("taskStatus") Integer taskStatus, @Param("taskPoint") String taskPoint,
                         @Param("startDate") Long startDate, @Param("endDate") Long endDate);

    /**
     * 查询所有任务的信息
     * @return
     */
    List<Task> queryAllTasks();

    /**
     * 查询某个表单的详细信息
     * @param taskId
     * @return
     */
    Task queryTaskDetail(@Param("taskId") int taskId);

    /**
     * 查询ID为@param approverId的审批者 审批结果为@param examineResult的任务列表
     * @param approverId
     * @param examineResult
     * @return
     */
    List<Task> queryTaskByApprover(@Param("approverId")Integer approverId,
                                   @Param("examineResult")Integer examineResult);

    // 查询任务最大的ID
    @Select("select max(task_id) from task")
    Integer selectMaxId();
    /**
     * 修改任务状态
     * @param taskId
     * @param taskStatus
     * @return
     */
    int updateTaskStatus(@Param("taskId") int taskId, @Param("taskStatus") int taskStatus);

    /**
     * 查询数据库中所有任务处于各个状态的数目
     * @return status ： 1
     *         num : 1
     */
    List<Map<String,Object>> queryTaskStatusNum(@Param("workerId") int workerId);


    /**
     * 根据状态查询任务
     * @param taskStatus
     * @return
     */
    List<Task> queryTaskByStatus(@Param("taskStatus") int taskStatus);

    // 根据ID查询任务状态
    @Select("select task_status from task where task_id = #{taskId}")
    Integer statusById(Integer taskId);
}
