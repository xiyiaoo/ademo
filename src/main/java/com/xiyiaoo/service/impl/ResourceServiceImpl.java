/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.service.impl;

import com.xiyiaoo.constants.Constants;
import com.xiyiaoo.dao.ResourceDao;
import com.xiyiaoo.entity.Resource;
import com.xiyiaoo.service.ResourceService;
import com.xiyiaoo.util.CollectionUtil;
import com.xiyiaoo.util.StringUtil;
import com.xiyiaoo.util.WebUtil;
import com.xiyiaoo.validation.group.Create;
import com.xiyiaoo.validation.group.Query;
import com.xiyiaoo.validation.group.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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
    @Validated({Create.class})
    public int add(Resource resource) {
        //resource.setId(StringUtil.uuid());
        resource.setCreator(WebUtil.getCurrentUserId());
        return resourceDao.add(resource);
    }

    @Override
    @Validated({Query.class})
    public int delete(Resource resource) {
        if (Constants.ROOT_ID.equals(resource.getId())){
            return 0;
        }
        //TODO 级联删除
        return resourceDao.delete(resource);
    }

    @Override
    @Validated({Update.class})
    public int update(Resource resource) {
        if (Constants.ROOT_ID.equals(resource.getId())){
            return 0;
        }
        resource.setModifier(WebUtil.getCurrentUserId());
        return resourceDao.update(resource);
    }

    @Override
    @Validated({Query.class})
    public Resource get(Resource resource) {
        return resourceDao.get(resource);
    }
}
