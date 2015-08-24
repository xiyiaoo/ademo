/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.service.impl;

import com.xiyiaoo.dao.RoleDao;
import com.xiyiaoo.entity.Role;
import com.xiyiaoo.service.RoleService;
import com.xiyiaoo.util.CollectionUtil;
import com.xiyiaoo.util.StringUtil;
import com.xiyiaoo.util.WebUtil;
import com.xiyiaoo.validation.group.Create;
import com.xiyiaoo.validation.group.Query;
import com.xiyiaoo.validation.group.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-24 下午9:00
 */
@Service
public class RoleServiceImpl implements RoleService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private RoleDao roleDao;
    @Override
    public List<Role> getRoles() {
        return roleDao.getAll();
    }

    @Override
    public List<Role> getRoles(String... ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return roleDao.getRoles(ids);
    }

    @Override
    public List<String> getResourceIds(List<Role> roles) {
        if (CollectionUtil.isEmpty(roles)) {
            return Collections.emptyList();
        }
        return roleDao.getResourceIds(roles);
    }

    @Override
    public void addResourceIds(String roleId, Set<String> resourceIds) {
        if (CollectionUtil.isNotEmpty(resourceIds)){
            Role role = new Role();
            role.setId(roleId);
            role = get(role);
            if (role == null) {
                throw new IllegalArgumentException(String.format("无效的角色id:'%s'", roleId));
            }
            roleDao.addResource2Role(roleId, resourceIds);

            update(role);//更新主表的修改人
        }
    }

    @Override
    public void updateResourceIds(String roleId, Set<String> resourceIds) {
        Role role = new Role();
        role.setId(roleId);
        role = get(role);
        if (role == null) {
            throw new IllegalArgumentException(String.format("无效的角色id:'%s'", roleId));
        }
        //先删除之前授权的资源
        roleDao.removeResource4Role(roleId);
        if (CollectionUtil.isNotEmpty(resourceIds)){
            //再添加授权资源
            roleDao.addResource2Role(roleId, resourceIds);
        }
        update(role);//更新主表的修改人
    }

    @Override
    @Validated({Create.class})
    public int add(Role role) {
        role.setId(StringUtil.uuid());
        role.setCreator(WebUtil.getCurrentUserId());
        return roleDao.add(role);
    }

    @Override
    @Validated({Query.class})
    public int delete(Role role) {
        //级联清除授权的资源
        roleDao.removeResource4Role(role.getId());
        return roleDao.delete(role);
    }

    @Override
    @Validated({Update.class})
    public int update(Role role) {
        role.setModifier(WebUtil.getCurrentUserId());
        return roleDao.update(role);
    }

    @Override
    @Validated({Query.class})
    public Role get(Role role) {
        return roleDao.get(role);
    }
}
