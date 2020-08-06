package com.bjtu.testbox.mapper;

import com.bjtu.testbox.entity.Approver;
import com.bjtu.testbox.entity.Examine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamineMapper {

    /**
     * 插入一条审批记录
     * @param ex 审批对象
     * @return
     */
    int insertExamine(Examine ex);

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
}
