/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.service;

import com.xiyiaoo.entity.Resource;

import java.util.List;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-24 下午8:58
 * 资源service接口
 */
public interface ResourceService extends BaseService<Resource> {
    /**
     * 获取所有资源
     * @return 资源集合
     */
    List<Resource> getResources();

    /**
     * 根据id列表获取资源列表
     * @param ids 资源id列表
     * @return 资源列表
     */
    List<Resource> getResources(List<String> ids);

}
