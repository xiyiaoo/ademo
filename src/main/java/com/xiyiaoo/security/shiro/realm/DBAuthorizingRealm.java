/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.security.shiro.realm;

import com.xiyiaoo.entity.User;
import com.xiyiaoo.service.SessionService;
import com.xiyiaoo.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-26 下午4:27
 */
public class DBAuthorizingRealm extends AuthorizingRealm {
    @Autowired
    private SessionService sessionService;
    /***
     * 获取认证实体的权限信息,每次访问都会调用
     * @param principalCollection 认证实体信息
     * @return 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = (String) principalCollection.getPrimaryPrincipal();
        authorizationInfo.setStringPermissions(sessionService.getPermissions(username));
        return authorizationInfo;
    }

    /**
     * 认证登录信息,登录时调用
     * @param authenticationToken 登录信息
     * @return 认证信息
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String)authenticationToken.getPrincipal();
        User user = sessionService.getByUsername(username);
        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        if(user.isLocked()) {
            throw new LockedAccountException(); //帐号锁定
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        return new SimpleAuthenticationInfo(
                user.getUsername(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getSalt()),//加密的盐
                getName()  //realm name
        );
    }

}
