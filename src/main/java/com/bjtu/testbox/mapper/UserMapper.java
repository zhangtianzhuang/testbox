package com.bjtu.testbox.mapper;

import com.bjtu.testbox.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User Sel(int id);

    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);
}