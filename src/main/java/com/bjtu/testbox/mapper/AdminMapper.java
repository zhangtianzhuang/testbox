package com.bjtu.testbox.mapper;

import com.bjtu.testbox.entity.Admin;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper {

    @Select("select * from admin where admin_id = #{adminId}")
    Admin showPersonInfo(Integer adminId);
}
