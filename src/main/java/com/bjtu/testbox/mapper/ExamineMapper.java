package com.bjtu.testbox.mapper;

import com.bjtu.testbox.entity.Approver;
import com.bjtu.testbox.entity.Examine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamineMapper {

    /**
     * 插入一条审批记录
     * @param ex 审批对象
     * @return
     */
    int insertExamine(Examine ex);


    /**
     * 根据审批人ID和任务ID查询审批结果
     * @param approverId
     * @param taskId
     * @return
     */
    /**
     * 判断某个任务是否被审批了
     * @param approverId
     * @param taskId
     * @return 如果存在记录返回对象不为null，如果不存在记录，则返回null
     */
    @Select("select * from examine where approver_id = #{approverId} " +
            "and task_id = #{taskId} limit 1")
    Examine hasExamined(@Param("approverId") Integer approverId,
                        @Param("taskId") Integer taskId);

    /**
     * 查询某个任务被审批的记录
     * @param taskId 被审批的任务ID
     * @return 审批记录的集合，可能有1条或者多条审批记录
     * 包括每一条记录包括审批ID，审批人ID，审批人级别，审批结果，审批日期，审批理由
     */
    List<Examine> queryTaskExamineRecord(@Param("taskId") Integer taskId);
}
