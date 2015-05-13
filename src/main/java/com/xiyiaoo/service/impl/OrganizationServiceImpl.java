/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.service.impl;

import com.xiyiaoo.constants.Constants;
import com.xiyiaoo.dao.OrganizationDao;
import com.xiyiaoo.entity.Organization;
import com.xiyiaoo.entity.PageResult;
import com.xiyiaoo.service.OrganizationService;
import com.xiyiaoo.util.StringUtil;
import com.xiyiaoo.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-24 下午9:00
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private OrganizationDao organizationDao;
    @Override
    public PageResult<Organization> getOrganizations(int pageIndex, int pageSize, Organization organization) {
        PageResult<Organization> result = new PageResult<>(pageIndex, pageSize);
        result.setData(organizationDao.getOrganizations(result));
        return result;
    }

    @Override
    public List<Organization> getChildren(Organization organization) {
        return organizationDao.getChildren(organization);
    }

    @Override
    public List<Organization> getChildrenRecursively(Organization organization) {
        return organizationDao.getChildrenRecursively(organization);
    }

    @Override
    public void add(Organization organization) {
        if (StringUtil.isEmpty(organization.getId())) {
            organization.setId(StringUtil.uuid());
        }
        organization.setCreator(WebUtil.getCurrentUserId());
        prepareParentIds(organization);
        organizationDao.add(organization);
    }

    @Override
    public void delete(Organization organization) {
        organizationDao.delete(organization);
    }

    @Override
    public void update(Organization organization) {
        Organization old = get(organization);
        if (old != null) {
            //所属上级是否发生了改变
            if(StringUtil.isNotEqual(old.getParentId(), organization.getParentId())) {
                //发生了改变则要更新自己的上级ids和上级names，同时更新所有下级的上级ids和上级names
                prepareParentIds(organization);
                List<Organization> children = getChildrenRecursively(organization);
                for (Organization child : children) {
                    String oldIds = child.getParentIds(), oldNames = child.getParentNames();
                    child.setParentIds(oldIds.replaceFirst(old.getParentIds(), organization.getParentIds()));
                    child.setParentNames(oldNames.replaceFirst(old.getParentNames(), organization.getParentNames()));
                    organizationDao.updateForMove(child);
                }
            }
            organization.setModifier(WebUtil.getCurrentUserId());
            organizationDao.update(organization);
        }
    }

    /**
     * 设置上级ids和names
     * @param organization 机构
     */
    private void prepareParentIds(Organization organization){
        if (StringUtil.isEmpty(organization.getParentId())) {
            organization.setParentId(Constants.ROOT_ID);
        }
        Organization parent = new Organization();
        parent.setId(organization.getParentId());
        parent = get(parent);
        if (parent == null) {
            throw new IllegalArgumentException("无效的上级id");
        }
        organization.setParentIds(StringUtil.buildParentsPath(parent.getId(), parent.getParentIds()));
        organization.setParentNames(StringUtil.buildParentsPath(parent.getName(), parent.getParentNames()));
    }

    @Override
    public Organization get(Organization organization) {
        return organizationDao.get(organization);
    }
}
