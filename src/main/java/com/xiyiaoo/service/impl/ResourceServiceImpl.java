/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.service.impl;

import com.xiyiaoo.dao.ResourceDao;
import com.xiyiaoo.entity.Resource;
import com.xiyiaoo.service.ResourceService;
import com.xiyiaoo.util.CollectionUtil;
import com.xiyiaoo.util.StringUtil;
import com.xiyiaoo.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-24 下午9:01
 */
@Service
public class ResourceServiceImpl implements ResourceService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ResourceDao resourceDao;
    @Override
    public List<Resource> getResources() {
        return resourceDao.getAll();
    }

    @Override
    public List<Resource> getResources(List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return resourceDao.getResources(ids);
    }

    @Override
    public void add(Resource resource) {
        resource.setId(StringUtil.uuid());
        resource.setCreator(WebUtil.getCurrentUserId());
        resourceDao.add(resource);
    }

    @Override
    public void delete(Resource resource) {
        resourceDao.delete(resource);
    }

    @Override
    public void update(Resource resource) {
        resource.setModifier(WebUtil.getCurrentUserId());
        resourceDao.update(resource);
    }

    @Override
    public Resource get(Resource resource) {
        return resourceDao.get(resource);
    }
}
