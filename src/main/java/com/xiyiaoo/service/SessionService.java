/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.service;

import com.xiyiaoo.entity.User;

import java.util.Set;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-5-12 上午11:29
 * 会话服务
 */
public interface SessionService {
    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    User getByUsername(String username);

    /**
     * 根据角色id列表获取权限列表
     * @param username 角色id列表
     * @return 权限列表
     */
    Set<String> getPermissions(String username);

    /**
     * 获取当前用户的权限列表
     * @return 权限列表
     */
    Set<String> getPermissions();

    /**
     * 将用户相关信息放入会话中
     * @param username 用户名
     */
    void createUserSession(String username);

}
