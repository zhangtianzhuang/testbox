package com.bjtu.testbox.config.shiroconfig;

import com.bjtu.testbox.entity.User;
import org.apache.shiro.subject.Subject;

public class AppSecurityUtils {
	
	/**
	 * 获取当前登录用户的用户名
	 * @return
	 */
	public static User obtainLoginedUser() {
		Subject currentUser = org.apache.shiro.SecurityUtils.getSubject();
		if(currentUser == null || currentUser.getPrincipal() == null) {
			return null;
		}
		User shiroUser = (User) currentUser.getPrincipal();
		User u = new User();
		u.setUid(shiroUser.getUid());
		u.setUsername(shiroUser.getUsername());
		u.setWorkerId(shiroUser.getWorkerId());
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