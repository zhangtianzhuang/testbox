package com.bjtu.testbox.mapper;

import com.bjtu.testbox.entity.Examine;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamineMapper {

    /**
     * 插入一条审批记录
     * @param ex 审批对象
     * @return
     */
    int insertExamine(Examine ex);
}
