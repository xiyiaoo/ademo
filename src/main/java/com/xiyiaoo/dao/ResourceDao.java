/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.dao;

import com.xiyiaoo.entity.Resource;

import java.util.List;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-28 下午4:33
 * Resource dao
 */
public interface ResourceDao {

    /**
     * 获取所有资源
     * @return 角色列表
     */
    List<Resource> getAll();

    /**
     * 获取指定id的所有资源
     * @param ids 资源id集合
     * @return 角色列表
     */
    List<Resource> getResources(List<String> ids);

    /**
     * 增加一个实体
     * @param t 实体信息
     * @return 影响记录数
     */
    int add(Resource t);

    /**
     * 删除一个实体
     * @param t 实体信息
     * @return 影响记录数
     */
    int delete(Resource t);

    /**
     * 更新一个实体
     * @param t 实体信息
     * @return 影响记录数
     */
    int update(Resource t);

    /**
     * 获取一个实体
     * @param t 实体信息
     */
    Resource get(Resource t);
}
