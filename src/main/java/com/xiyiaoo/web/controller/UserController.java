/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.web.controller;

import com.xiyiaoo.entity.DefaultResponse;
import com.xiyiaoo.entity.PageResult;
import com.xiyiaoo.entity.User;
import com.xiyiaoo.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        return userService.getUsers(pageIndex, pageSize, user);
    }
    @RequiresPermissions("user:view")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User get(@PathVariable("id")String id, @RequestParam("organizationId")String organizationId) {
        User user = new User();
        user.setId(id);
        user.setOrganizationId(organizationId);
        return userService.get(user);
    }
    @RequiresPermissions("user:create")
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public DefaultResponse create(User user){
        userService.add(user);
        return DefaultResponse.successResponse();
    }
    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public DefaultResponse update(@PathVariable("id")String id, User user){
        user.setId(id);
        userService.update(user);
        return DefaultResponse.successResponse();
    }
    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public DefaultResponse delete(@PathVariable("id")String id, @RequestParam("organizationId")String organizationId){
        User user = new User();
        user.setId(id);
        user.setOrganizationId(organizationId);
        userService.delete(user);
        return DefaultResponse.successResponse();
    }

    @ResponseBody
    @RequestMapping(value = "/password/change", method = RequestMethod.POST)
    public DefaultResponse changePassword(String password, String newPassword) {
        userService.changePassword(password, newPassword);
        return DefaultResponse.successResponse();
    }
    @ResponseBody
    @RequestMapping(value = "/password/reset", method = RequestMethod.POST)
    public DefaultResponse resetPassword(String username, String newPassword) {
        userService.resetPassword(username, newPassword);
        return DefaultResponse.successResponse();
    }
}
