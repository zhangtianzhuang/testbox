package com.bjtu.testbox.config.shiro;

import com.bjtu.testbox.config.shiro.jwt.JWTUtil;
import com.bjtu.testbox.entity.User;
import com.bjtu.testbox.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppSecurityUtils {

    @Autowired
    private UserMapper userMapper;
	/**
	 * 获取当前登录用户
	 * @return
	 */
	public String getUsername() {
		Subject subject = SecurityUtils.getSubject();
		if(subject == null || subject.getPrincipal() == null) {
			return null;
		}
		String token = (String) subject.getPrincipal();
		// 当前用户名
        String username = JWTUtil.getUsername(token);
        // 根据用户名获取所有数据
		return username;
	}

	// 获取当前的登录用户，包括用户名，类型（工人，审批者，管理员），绑定ID
	public User getUser(){
        User user = userMapper.selectByUsername(getUsername());
        return user;
    }

    // 获取当前登录用户的绑定ID
    public Integer getBindId(){
	    return getUser().getBindUser();
    }
}