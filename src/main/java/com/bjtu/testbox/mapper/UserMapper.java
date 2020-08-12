package com.bjtu.testbox.mapper;

import com.bjtu.testbox.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User Sel(int id);

    @Select("select type, bind_user from user where username = #{username}")
    User selectByUsername(String username);

    /**
     * 查询是否存在用户名username
     * @param username
     * @return
     */
    @Select("select username from user where username = #{username}")
    String hasUsername(String username);

    @Select("select password from user where username = #{username}")
    String selectPassword(String username);
}