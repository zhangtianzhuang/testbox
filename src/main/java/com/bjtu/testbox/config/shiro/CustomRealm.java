package com.bjtu.testbox.config.shiro;

import com.bjtu.testbox.config.shiro.jwt.JWTToken;
import com.bjtu.testbox.config.shiro.jwt.JWTUtil;
import com.bjtu.testbox.entity.SysPermission;
import com.bjtu.testbox.entity.SysRole;
import com.bjtu.testbox.mapper.ShiroMapper;
import com.bjtu.testbox.mapper.UserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Author yuanhaoyue swithaoy@gmail.com
 * @Description 自定义 Realm
 * @Date 2018-04-09
 * @Time 16:58
 */
@Component
public class CustomRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(CustomRealm.class);

    private final UserMapper userMapper;

    @Resource
    private ShiroMapper shiroMapper;

    @Autowired
    public CustomRealm(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("doGetAuthenticationInfo" + ":");
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null || !JWTUtil.verify(token, username)) {
            logger.info("doGetAuthenticationInfo" + ":token认证失败！");
            throw new AuthenticationException("token认证失败！");
        }
        String hasUsername = userMapper.hasUsername(username);
        if (hasUsername == null) {
            logger.info("doGetAuthenticationInfo" + ":该用户不存在！");
            throw new AuthenticationException("该用户不存在！");
        }
        return new SimpleAuthenticationInfo(token, token, "MyRealm");
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("doGetAuthorizationInfo" + ":————权限认证————");
        String username = JWTUtil.getUsername(principals.toString());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<SysRole> roleList = shiroMapper.getRoleAndPerm(username).getRoleList();
        for(SysRole role:roleList){
            authorizationInfo.addRole(role.getRole());
            logger.info("doGetAuthorizationInfo" + ":添加角色"+role.getRole());
            for(SysPermission p:role.getPermissions()){
                authorizationInfo.addStringPermission(p.getPermission());
                logger.info("doGetAuthorizationInfo" + ":" + "添加权限:"+p.getPermission());
            }
        }
        //设置该用户拥有的角色和权限
        return authorizationInfo;
    }
}