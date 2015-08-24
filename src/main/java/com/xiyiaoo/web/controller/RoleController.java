package com.xiyiaoo.web.controller;

import com.xiyiaoo.entity.Role;
import com.xiyiaoo.service.RoleService;
import com.xiyiaoo.web.entity.DefaultResponse;
import com.xiyiaoo.web.entity.Response;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * User: xiyiaoo@gmail.com
 * Date: 2015-06-05 10:06:40
 * 角色控制器
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @RequiresPermissions("role:view")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Role> getRoles(){
        return roleService.getRoles();
    }

    @RequiresPermissions("role:view")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Role get(Role role) {
        return roleService.get(role);
    }

    @RequiresPermissions("role:create")
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Response create(Role role){
        roleService.add(role);
        return DefaultResponse.successResponse().setData(role.getId());
    }

    @RequiresPermissions("role:update")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Response update(Role role){
        roleService.update(role);
        return DefaultResponse.successResponse();
    }

    @RequiresPermissions("role:delete")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Response delete(Role role){
        roleService.delete(role);
        return DefaultResponse.successResponse();
    }

    @RequiresPermissions("role:update")
    @RequestMapping(value = "/{id}/resource", method = RequestMethod.PUT)
    @ResponseBody
    public Response addResource(@PathVariable("id")String id,
                                @RequestParam("resourceIds")String resourceIds){
        roleService.addResourceIds(id, toSet(resourceIds));
        return DefaultResponse.successResponse();
    }
    @RequiresPermissions("role:update")
    @RequestMapping(value = "/{id}/resource", method = RequestMethod.POST)
    @ResponseBody
    public Response updateResource(@PathVariable("id")String id,
                                   @RequestParam("resourceIds")String resourceIds){
        roleService.updateResourceIds(id, toSet(resourceIds));
        return DefaultResponse.successResponse();
    }
    @RequiresPermissions("role:update")
    @RequestMapping(value = "/{id}/resource", method = RequestMethod.DELETE)
    @ResponseBody
    public Response removeResource(@PathVariable("id")String id){
        roleService.updateResourceIds(id, null);
        return DefaultResponse.successResponse();
    }

    @RequiresPermissions("role:update")
    @RequestMapping(value = "/{id}/resource", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getResource(Role role){
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        return roleService.getResourceIds(roles);
    }
    private Set<String> toSet(String ids){
        Set<String> set = new HashSet<>();
        Collections.addAll(set, ids.split(","));
        return set;
    }
}
