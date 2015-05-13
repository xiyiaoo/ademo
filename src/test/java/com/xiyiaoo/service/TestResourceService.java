/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.service;

import com.xiyiaoo.base.BaseTestCase;
import com.xiyiaoo.constants.Constants;
import com.xiyiaoo.constants.ResourceType;
import com.xiyiaoo.entity.Resource;
import com.xiyiaoo.util.StringUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-5-8 下午4:08
 * 测试ResourceService
 */
public class TestResourceService extends BaseTestCase {

    @Autowired
    private ResourceService resourceService;
    @Test
    public void testCRUD() throws Exception {
        Resource resource = new Resource(), child = new Resource();
        resource.setName("test");
        resource.setOrdinal(1);
        resource.setParentId(Constants.ROOT_ID);
        //resource.setParentIds(StringUtil.buildParentsPath(Constants.ROOT_ID, null));
        resource.setPermission("test:view");
        resource.setType(ResourceType.MENU);
        try {
            resourceService.add(resource);//增加
            Assert.assertNotNull(resourceService.get(resource));
            Assert.assertFalse(resourceService.getResources().isEmpty());
            child.setName("child");
            child.setOrdinal(2);
            child.setParentId(resource.getId());
            //child.setParentIds(StringUtil.buildParentsPath(resource.getId(), resource.getParentIds()));
            child.setType(ResourceType.BUTTON);
            child.setPermission("test:edit");
            resourceService.add(child);//增加
            Assert.assertNotNull(resourceService.get(child));
            child.setDescription("some");
            resourceService.update(child);
            Assert.assertEquals(resourceService.get(child).getDescription(), "some");
        } finally {
            resourceService.delete(resource);
            resourceService.delete(child);
            Assert.assertNull(resourceService.get(resource));
        }
    }
}
