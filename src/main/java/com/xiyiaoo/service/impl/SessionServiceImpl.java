/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.service.impl;

import com.xiyiaoo.entity.Resource;
import com.xiyiaoo.entity.Role;
import com.xiyiaoo.entity.User;
import com.xiyiaoo.service.ResourceService;
import com.xiyiaoo.service.RoleService;
import com.xiyiaoo.service.SessionService;
import com.xiyiaoo.service.UserService;
import com.xiyiaoo.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-5-12 上午11:42
 */
@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceService resourceService;
    @Override
    public User getByUsername(String username) {
        return userService.getByUsername(username);
    }

    @Override
    public Set<String> getPermissions(String username) {
        User user = userService.getByUsername(username);
        return get(user);
    }

    @Override
    public Set<String> getPermissions() {
        return get(WebUtil.getCurrentUser());
    }

    @Override
    public void createUserSession(String username) {
        User user = userService.getByUsername(username);
        WebUtil.setCurrentUser(user);
    }

    private Set<String> get(User user){
        List<Role> roles = userService.getRoleIds(user.getId());
        List<String> resourceIds = roleService.getResourceIds(roles);
        List<Resource> resources = resourceService.getResources(resourceIds);
        Set<String> permissions = new HashSet<>();
        for (Resource resource : resources) {
            permissions.add(resource.getPermission());
        }
        return permissions;
    }
}
