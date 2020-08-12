package com.bjtu.testbox.mapper;

import com.bjtu.testbox.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ShiroMapper {
    /**
     * 根据用户ID获取所拥有的角色和权限
     * @param username
     * @return
     */
    User getRoleAndPerm(@Param("username") String username);
}
