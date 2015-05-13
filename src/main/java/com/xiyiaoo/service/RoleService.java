/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.service;

import com.xiyiaoo.entity.Role;

import java.util.List;
import java.util.Set;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-24 下午8:58
 * 角色service接口
 */
public interface RoleService extends BaseService<Role> {
    /**
     * 获取所有角色
     * @return 角色列表
     */
    List<Role> getRoles();

    /**
     * 根据角色id列表获取角色列表
     * @param ids 角色id列表
     * @return 角色列表
     */
    List<Role> getRoles(String... ids);

    /**
     * 获取角色拥有的资源id集合
     * @param roles 角色
     * @return 资源id集合
     */
    List<String> getResourceIds(List<Role> roles);

    /**
     * 给角色授权资源
     * @param roleId 角色id
     * @param resourceIds 资源id
     */
    void addResourceIds(String roleId, Set<String> resourceIds);

    /**
     * 更新角色授权的资源
     * @param roleId 角色id
     * @param resourceIds 资源id
     */
    void updateResourceIds(String roleId, Set<String> resourceIds);
}
