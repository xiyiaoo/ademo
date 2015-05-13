/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.service;

import com.xiyiaoo.entity.BaseEntity;
import com.xiyiaoo.entity.PageResult;
import com.xiyiaoo.entity.Role;
import com.xiyiaoo.entity.User;

import java.util.List;
import java.util.Set;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-24 下午8:57
 * 用户service接口
 */
public interface UserService extends BaseService<User> {
    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    User getByUsername(String username);

    /**
     * 修改密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void changePassword(String oldPassword, String newPassword);

    /**
     * 重置密码
     * @param username 用户名
     * @param newPassword 新密码
     */
    void resetPassword(String username, String newPassword);

    /**
     * 获取用户的角色id列表
     * @param useId 用户名
     * @return 角色id+序号
     */
    List<Role> getRoleIds(String useId);

    /**
     * 增加授权角色
     * @param user 用户信息(id + organizationId)
     * @param roles 角色列表
     */
    void addRoleIds(User user, Set<Role> roles);

    /**
     * 修改授权角色
     * @param user 用户信息(id + organizationId)
     * @param roles 角色列表
     */
    void updateRoleIds(User user, Set<Role> roles);

    /**
     * 分页查询用户
     * @param pageIndex 页号
     * @param pageSize 每页记录数
     * @param user 查询参数
     * @return 分页数据
     */
    PageResult<User> getUsers(int pageIndex, int pageSize, User user);
}
