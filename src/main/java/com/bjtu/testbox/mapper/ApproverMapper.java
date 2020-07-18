package com.bjtu.testbox.mapper;

import com.bjtu.testbox.entity.Approver;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ApproverMapper {
    @Select("select * from approver where approver_id = #{id}")
    Approver sel(int id);
}
