/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.service;

import com.xiyiaoo.entity.Organization;
import com.xiyiaoo.entity.PageResult;

import java.util.List;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-24 下午8:57
 * 组织机构service接口
 */
public interface OrganizationService extends BaseService<Organization> {
    /**
     * 分页获取机构数据
     * @param organization 参数
     * @param pageIndex 页号
     * @param pageSize 每页记录数
     * @return 分页数据
     */
    PageResult<Organization> getOrganizations(Organization organization, int pageIndex, int pageSize);

    /**
     * 获取所有子机构(只包含直接子节点)
     * @param organization 参数
     * @return 机构数据
     */
    List<Organization> getChildren(Organization organization);

    /**
     * 获取所有下级机构(包含孙子节点)
     * @param organization 参数
     * @return 机构数据
     */
    List<Organization> getChildrenRecursively(Organization organization);

    /**
     * 通过id获取机构信息（不校验是数据权限）
     * @param organizationId id
     * @return 机构
     */
    Organization getById(String organizationId);
}
