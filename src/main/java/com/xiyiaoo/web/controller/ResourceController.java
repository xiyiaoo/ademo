package com.xiyiaoo.web.controller;

import com.xiyiaoo.entity.Resource;
import com.xiyiaoo.service.ResourceService;
import com.xiyiaoo.service.ResourceService;
import com.xiyiaoo.web.entity.DefaultResponse;
import com.xiyiaoo.web.entity.Response;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * User: xiyiaoo@gmail.com
 * Date: 2015-06-05 03:22:42
 * resource 控制器
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;
    @RequiresPermissions("resource:view")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Resource> getResources(){
        return resourceService.getResources();
    }

    @RequiresPermissions("resource:view")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Resource get(Resource resource) {
        return resourceService.get(resource);
    }

    @RequiresPermissions("resource:create")
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Response create(Resource resource){
        resourceService.add(resource);
        return DefaultResponse.successResponse().setData(resource.getId());
    }

    @RequiresPermissions("resource:update")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Response update(Resource resource){
        resourceService.update(resource);
        return DefaultResponse.successResponse();
    }

    @RequiresPermissions("resource:delete")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Response delete(Resource resource){
        resourceService.delete(resource);
        return DefaultResponse.successResponse();
    }
}
