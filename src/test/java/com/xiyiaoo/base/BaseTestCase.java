/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.base;

import com.xiyiaoo.entity.User;
import com.xiyiaoo.service.SessionService;
import com.xiyiaoo.service.UserService;
import com.xiyiaoo.util.WebUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-5-8 上午11:03
 * 测试类的基础类，提供了登录和spring上下文
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = "classpath:spring/spring-context.xml"
)
public class BaseTestCase {
    @Autowired
    private SessionService sessionService;
    @Before
    public void setUp() throws Exception {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin");
        subject.login(token);
        User user = sessionService.getByUsername((String) subject.getPrincipal());
        sessionService.createUserSession(user.getUsername());
    }

    @After
    public void tearDown() throws Exception {
        SecurityUtils.getSubject().logout();
    }
}
