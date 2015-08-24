/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.service;

import com.xiyiaoo.base.BaseTestCase;
import com.xiyiaoo.constants.Gender;
import com.xiyiaoo.entity.Role;
import com.xiyiaoo.entity.User;
import com.xiyiaoo.exception.AccessDeniedException;
import com.xiyiaoo.util.StringUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-5-5 下午2:45
 * 测试 UserService
 */
public class TestUserService extends BaseTestCase {
    
    @Autowired
    private UserService userService;

    @Test
    public void testCRUD() throws Exception {

        User user = new User();
        String username = StringUtil.uuid().substring(0, 20);
        user.setUsername(username);
        user.setPassword("123");
        user.setName("测试");
        user.setOrganizationId("0");
        user.setGender(Gender.MALE);
        try {
            userService.add(user);//增加
            Assert.assertNotNull(userService.getByUsername(username));
            Assert.assertNotNull(userService.get(user));

            user.setName("test");
            userService.update(user);//更新
            Assert.assertEquals(userService.get(user).getName(), "test");

            try {
                userService.changePassword("12", "test");//修改密码
            } catch (AccessDeniedException e) {
                userService.changePassword("admin", "test");
                userService.changePassword("test", "admin");
            }

            //授权角色
            Set<Role> roles = new HashSet<>();
            Role admin = new Role(), role = new Role();
            admin.setId("0");
            admin.setOrdinal(1);
            roles.add(admin);
            userService.addRoleIds(user, roles);
            Assert.assertEquals(userService.getRoleIds(user.getId()).size(), 1);
            role.setId("1");
            role.setOrdinal(2);
            roles.add(role);
            userService.updateRoleIds(user, roles);
            Assert.assertEquals(userService.getRoleIds(user.getId()).size(), 2);
        } finally {
            userService.resetPassword("admin", "admin");
            userService.delete(user);//删除
            Assert.assertNull(userService.get(user));
            Assert.assertTrue(userService.getRoleIds(user.getId()).isEmpty());
        }
    }

    @Test
    public void testValid() throws Exception {
        User user = new User();
        String username = StringUtil.uuid().substring(0, 20);
        user.setUsername(username);
        user.setPassword("123");
        user.setName("测试");
        user.setOrganizationId("0");
        user.setGender(Gender.MALE);
        try {
            userService.getRoleIds("");
        } finally {
            userService.delete(user);//删除
        }

    }
}
