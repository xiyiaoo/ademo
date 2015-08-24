/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.web.controller;

import com.xiyiaoo.entity.PageResult;
import com.xiyiaoo.entity.Role;
import com.xiyiaoo.entity.User;
import com.xiyiaoo.service.UserService;
import com.xiyiaoo.util.StringUtil;
import com.xiyiaoo.web.entity.DefaultResponse;
import com.xiyiaoo.web.entity.Response;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-30 下午2:23
 * user 控制器
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequiresPermissions("user:view")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageResult<User> getPage(@RequestParam(value = "pi", defaultValue = "1")int pageIndex,
                                    @RequestParam(value = "ps", defaultValue = "10")int pageSize, User user) {
        return userService.getUsers(user, pageIndex, pageSize);
    }

    @RequiresPermissions("user:view")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User get(User user) {
        return userService.get(user);
    }

    @RequiresPermissions("user:create")
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Response create(User user){
        userService.add(user);
        return DefaultResponse.successResponse().setData(user.getId());
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Response update(User user){
        userService.update(user);
        return DefaultResponse.successResponse();
    }

    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Response delete(User user){
        userService.delete(user);
        return DefaultResponse.successResponse();
    }



    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/role", method = RequestMethod.PUT)
    @ResponseBody
    public Response addRole(User user, @RequestParam("roleIds")String roleIds){
        userService.addRoleIds(user, toRoles(roleIds));
        return DefaultResponse.successResponse();
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/role", method = RequestMethod.POST)
    @ResponseBody
    public Response updateRole(User user, @RequestParam("roleIds")String roleIds){
        userService.updateRoleIds(user, toRoles(roleIds));
        return DefaultResponse.successResponse();
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/role", method = RequestMethod.DELETE)
    @ResponseBody
    public Response deleteRole(User user){
        userService.updateRoleIds(user, null);
        return DefaultResponse.successResponse();
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/role", method = RequestMethod.GET)
    @ResponseBody
    public List<Role> getRole(@PathVariable("id")String id){
        return userService.getRoleIds(id);
    }

    @ResponseBody
    @RequestMapping(value = "/password/change", method = RequestMethod.POST)
    public Response changePassword(String password, String newPassword) {
        userService.changePassword(password, newPassword);
        return DefaultResponse.successResponse();
    }

    @ResponseBody
    @RequestMapping(value = "/password/reset", method = RequestMethod.POST)
    public Response resetPassword(String username, String newPassword) {
        userService.resetPassword(username, newPassword);
        return DefaultResponse.successResponse();
    }

    private Set<Role> toRoles(String roleIds){
        Set<Role> roles = new HashSet<>();
        String[] ids = roleIds.split(",");
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            if (StringUtil.isNotEmpty(id)){
                Role role = new Role();
                role.setId(id);
                role.setOrdinal(i);
                roles.add(role);
            }
        }
        return roles;
    }
}
