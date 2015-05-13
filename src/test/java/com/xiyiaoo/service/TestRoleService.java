/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.service;

import com.xiyiaoo.base.BaseTestCase;
import com.xiyiaoo.entity.Role;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-5-8 上午11:01
 * 测试 RoleService
 */
public class TestRoleService extends BaseTestCase {
    @Autowired
    private RoleService roleService;

    @Test
    public void testCRUD() throws Exception {
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("test");
        role.setValue("test");
        roles.add(role);
        try {
            roleService.add(role);//增加
            Assert.assertNotNull(roleService.get(role));
            Assert.assertFalse(roleService.getRoles(role.getId()).isEmpty());
            role.setName("TEST");

            //更新
            roleService.update(role);
            Assert.assertEquals(roleService.get(role).getName(), "TEST");

            //增加授权资源
            Set<String> resourceIds = new HashSet<>();
            resourceIds.add("0");
            roleService.addResourceIds(role.getId(), resourceIds);
            Assert.assertFalse(roleService.getResourceIds(roles).isEmpty());

            //更新授权资源
            resourceIds.add("1");
            roleService.updateResourceIds(role.getId(), resourceIds);
            Assert.assertEquals(roleService.getResourceIds(roles).size(), 2);

        } finally {
            try {
                //清空授权的资源
                roleService.updateResourceIds(role.getId(), null);
            } catch (Exception e) {
            }

            roleService.delete(role);//删除
            Assert.assertNull(roleService.get(role));
        }
    }
}
