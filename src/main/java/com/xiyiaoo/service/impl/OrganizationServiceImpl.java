/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.service.impl;

import com.xiyiaoo.constants.Constants;
import com.xiyiaoo.dao.OrganizationDao;
import com.xiyiaoo.entity.Organization;
import com.xiyiaoo.entity.PageResult;
import com.xiyiaoo.security.datafilter.annotation.AccessFilter;
import com.xiyiaoo.service.OrganizationService;
import com.xiyiaoo.util.StringUtil;
import com.xiyiaoo.util.WebUtil;
import com.xiyiaoo.validation.group.Create;
import com.xiyiaoo.validation.group.Query;
import com.xiyiaoo.validation.group.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
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

    @AccessFilter(ignoreTarget = true, column = "id")//直接通过数据库id列过滤数据，
    @Override
    public PageResult<Organization> getOrganizations(Organization organization, int pageIndex, int pageSize) {
        PageResult<Organization> result = new PageResult<>(pageIndex, pageSize);
        result.setData(organizationDao.getOrganizations(result));
        return result;
    }

    @Override
    @AccessFilter(target = "id")//通过入参的id属性值，判断是否有权限访问
    @Validated({Query.class})
    public List<Organization> getChildren(Organization organization) {
        return organizationDao.getChildren(organization);
    }

    @Override
    @Validated({Query.class})
    @AccessFilter(target = "id")
    public List<Organization> getChildrenRecursively(Organization organization) {
        return organizationDao.getChildrenRecursively(organization);
    }

    //此方法不要用AccessFilter过滤数据权限
    @Override
    public Organization getById(String organizationId) {
        Organization organization = new Organization();
        organization.setId(organizationId);
        return organizationDao.get(organization);
    }

    @Override
    @Validated({Create.class})
    @AccessFilter(target = "id")
    public int add(Organization organization) {
        if (StringUtil.isEmpty(organization.getId())) {
            organization.setId(StringUtil.uuid());
        }
        organization.setCreator(WebUtil.getCurrentUserId());
        prepareParentIds(organization);
        return organizationDao.add(organization);
    }

    @Override
    @Validated({Query.class})
    @AccessFilter(target = "id")
    public int delete(Organization organization) {
        if (Constants.ROOT_ID.equals(organization.getId())){
            return 0;
        }
        //TODO 级联删除
        return organizationDao.delete(organization);
    }

    @Override
    @Validated({Update.class})
    @AccessFilter(target = "id")
    public int update(Organization organization) {
        if (Constants.ROOT_ID.equals(organization.getId())){
            return 0;
        }
        Organization old = get(organization);
        int upNum = 0;
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
            organization.setParentIds(old.getParentIds());
            organization.setParentNames(old.getParentNames());
            //prepareParentIds(organization);
            upNum = organizationDao.update(organization);
        }
        return upNum;
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
    @Validated({Query.class})
    @AccessFilter(target = "id")
    public Organization get(Organization organization) {
        return organizationDao.get(organization);
    }
}
