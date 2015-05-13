/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.dao;

import com.xiyiaoo.entity.PageResult;
import com.xiyiaoo.entity.Role;
import com.xiyiaoo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-28 下午4:33
 * user dao
 */
public interface UserDao {
    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    User getByUsername(String username);

    /**
     * 获取用户的角色id列表
     * @param useId 用户名
     * @return 角色id+序号
     */
    List<Role> getRoleIds(String useId);

    /**
     * 分页查询用户
     * @param result 参数和总记录数存放对象存放
     * @return 分页数据
     */
    List<User> getUsers(PageResult<User> result);

    /**
     * 更改密码
     * @param user userId+password
     */
    void modifyPassword(User user);

    /**
     * 为用户添加角色
     * @param userId 用户id
     * @param roles 角色列表
     */
    void addRoleIds(@Param("userId")String userId, @Param("roles")Set<Role> roles);

    /**
     * 情况用户的角色
     * @param userId 用户id
     */
    void removeRoleIds(String userId);

    /**
     * 增加一个实体
     * @param t 实体信息
     */
    void add(User t);

    /**
     * 删除一个实体
     * @param t 实体信息
     */
    int delete(User t);

    /**
     * 更新一个实体
     * @param t 实体信息
     */
    void update(User t);

    /**
     * 获取一个实体
     * @param t 实体信息
     */
    User get(User t);
}
