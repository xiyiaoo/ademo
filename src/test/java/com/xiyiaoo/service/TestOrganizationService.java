/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.service;

import com.xiyiaoo.base.BaseTestCase;
import com.xiyiaoo.constants.Constants;
import com.xiyiaoo.entity.Organization;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-5-11 上午10:59
 * 测试OrganizationService
 */
public class TestOrganizationService extends BaseTestCase {
    @Autowired
    private OrganizationService organizationService;
    @Test
    public void testCRUD() throws Exception {
        Organization organization = new Organization(),
                child = new Organization(),
                grandchild = new Organization();
        organization.setId("root1");
        organization.setParentId(Constants.ROOT_ID);
        organization.setName("test");
        organization.setOrdinal(1);
        try {
            //测试增加
            organizationService.add(organization);
            Assert.assertNotNull(organizationService.get(organization));
            Assert.assertFalse(organizationService.getOrganizations(1, 10, organization).getData().isEmpty());
            organization.setDescription("测试");
            //测试修改
            organizationService.update(organization);
            Assert.assertEquals("测试", organizationService.get(organization).getDescription());
            child.setId("root1-1");
            child.setOrdinal(1);
            child.setParentId(organization.getId());
            child.setName("test2");
            organizationService.add(child);
            grandchild.setId("root1-2");
            grandchild.setOrdinal(1);
            grandchild.setParentId(child.getId());
            grandchild.setName("test3");
            organizationService.add(grandchild);
            List<Organization> children = organizationService.getChildren(organization);
            Assert.assertFalse(children.isEmpty());
            Assert.assertEquals(children.get(0).getName(), "test2");
            children = organizationService.getChildrenRecursively(organization);
            Assert.assertEquals(children.size(), 2);

            child.setParentId(Constants.ROOT_ID);
            child.setDescription("移到根节点下");
            organizationService.update(child);
            children = organizationService.getChildren(child);
            Assert.assertEquals(children.size(), 1);
            Assert.assertEquals(children.get(0).getParentIds(), Constants.ROOT_ID + '/' + child.getId() + '/');
        } finally {
            //测试删除
            organizationService.delete(organization);
            Assert.assertNull(organizationService.get(organization));
            organizationService.delete(child);
            organizationService.delete(grandchild);
        }
    }
}
