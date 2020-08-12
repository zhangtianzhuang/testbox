package com.bjtu.testbox.config.shiro;

import com.bjtu.testbox.config.shiro.jwt.JWTUtil;
import com.bjtu.testbox.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class AppSecurityUtils {

	/**
	 * 获取当前登录用户
	 * @return
	 */
	public static String getUsername() {
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
}