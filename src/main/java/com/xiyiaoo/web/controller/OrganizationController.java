package com.xiyiaoo.web.controller;

import com.xiyiaoo.constants.Constants;
import com.xiyiaoo.entity.Organization;
import com.xiyiaoo.entity.PageResult;
import com.xiyiaoo.service.OrganizationService;
import com.xiyiaoo.util.StringUtil;
import com.xiyiaoo.web.entity.DefaultResponse;
import com.xiyiaoo.web.entity.Response;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User: xiyiaoo@gmail.com
 * Date: 2015-05-20 05:27:14
 * organization 控制器
 */
@Controller
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @RequiresPermissions("organization:view")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageResult<Organization>getPage(@RequestParam(value = "pi", defaultValue = "1")int pageIndex,
                                           @RequestParam(value = "ps", defaultValue = "10")int pageSize,
                                           Organization organization){
        return organizationService.getOrganizations(organization, pageIndex, pageSize);
    }

    @RequiresPermissions("organization:view")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Organization get(Organization organization) {
        return organizationService.get(organization);
    }

    @RequiresPermissions("organization:create")
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Response create(Organization organization){
        if (StringUtil.isEmpty(organization.getParentId())){
            organization.setId(Constants.ROOT_ID);
        }
        organizationService.add(organization);
        return DefaultResponse.successResponse().setData(organization.getId());
    }

    @RequiresPermissions("organization:update")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Response update(Organization organization){
        if (StringUtil.isEmpty(organization.getParentId())){
            organization.setId(Constants.ROOT_ID);
        }
        organizationService.update(organization);
        return DefaultResponse.successResponse();
    }

    @RequiresPermissions("organization:delete")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Response delete(Organization organization){
        organizationService.delete(organization);
        return DefaultResponse.successResponse();
    }

    @RequestMapping("/{id}/children")
    @ResponseBody
    public List<Organization> getChildren(Organization organization){
        return organizationService.getChildren(organization);
    }

    @RequestMapping("/{id}/children/recursive")
    @ResponseBody
    public List<Organization> getChildrenRecursively(Organization organization){
        return organizationService.getChildrenRecursively(organization);
    }

}
