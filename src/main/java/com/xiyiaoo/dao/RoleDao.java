/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.dao;

import com.xiyiaoo.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-28 下午4:33
 * Role dao
 */
public interface RoleDao {

    /**
     * 获取所有角色
     * @return 角色列表
     */
    List<Role> getAll();

    /**
     * 根据id列表获取角色列表
     * @param ids id列表
     * @return 角色列表
     */
    List<Role> getRoles(String... ids);

    /**
     * 根据角色id列表获取权限列表
     * @param ids 角色id列表
     * @return 权限列表
     */
    Set<String> getPermissions(String... ids);

    /**
     * 授权资源给角色
     * @param roleId 角色id
     * @param resourceIds 资源id列表
     */
    void addResource2Role(@Param("roleId")String roleId, @Param("resourceIds")Set<String> resourceIds);

    /**
     * 清空角色授权的资源
     * @param roleId 角色id
     */
    void removeResource4Role(String roleId);

    /**
     * 增加一个实体
     * @param t 实体信息
     * @return 影响记录数
     */
    int add(Role t);

    /**
     * 删除一个实体
     * @param t 实体信息
     * @return 影响记录数
     */
    int delete(Role t);

    /**
     * 更新一个实体
     * @param t 实体信息
     * @return 影响记录数
     */
    int update(Role t);

    /**
     * 获取一个实体
     * @param t 实体信息
     */
    Role get(Role t);

    /**
     * 获取角色拥有的资源id集合
     * @param roles 角色
     * @return 资源id集合
     */
    List<String> getResourceIds(List<Role> roles);
}
