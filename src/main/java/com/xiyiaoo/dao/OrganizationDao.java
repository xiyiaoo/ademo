/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.dao;

import com.xiyiaoo.entity.Organization;
import com.xiyiaoo.entity.PageResult;
import com.xiyiaoo.entity.Resource;

import java.util.List;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-28 下午4:33
 * Organization dao
 */
public interface OrganizationDao {

    /**
     * 分页获取机构数据
     * @param result 参数
     * @return 分页数据
     */
    List<Organization> getOrganizations(PageResult<Organization> result);

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
     * 更新机构信息（针对移动位置改变了上下级关系，只更新与上级相关的字段，如parentId等）
     * @param t 实体信息
     */
    void updateForMove(Organization t);

    /**
     * 增加一个实体
     * @param t 实体信息
     */
    void add(Organization t);

    /**
     * 删除一个实体
     * @param t 实体信息
     */
    void delete(Organization t);

    /**
     * 更新一个实体
     * @param t 实体信息
     */
    void update(Organization t);

    /**
     * 获取一个实体
     * @param t 实体信息
     */
    Organization get(Organization t);
}
