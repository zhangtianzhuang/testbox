package com.bjtu.testbox.config.shiroconfig;

import com.bjtu.testbox.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class AppSecurityUtils {
	
	/**
	 * 获取当前登录用户
	 * @return
	 */
	public static User obtainLoginedUser() {
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser == null || currentUser.getPrincipal() == null) {
			return null;
		}
		User shiroUser = (User) currentUser.getPrincipal();
		User u = new User();
		u.setUid(shiroUser.getUid());
		u.setType(shiroUser.getType());
		u.setUsername(shiroUser.getUsername());
		u.setBindUser(shiroUser.getBindUser());
		return u;
	}
	
	/**
	 * 获取当前用户token
	 * 
	 * @return
	 */
//	public static String obtainAccessToken() {
//		Subject currentUser = org.apache.shiro.SecurityUtils.getSubject();
//		if(currentUser == null || currentUser.getPrincipal() == null) {
//			return "";
//		}
//
//		AppShiroUser shiroUser = (AppShiroUser)currentUser.getPrincipal();
//
//		return shiroUser.getAccessToken();
//	}
}