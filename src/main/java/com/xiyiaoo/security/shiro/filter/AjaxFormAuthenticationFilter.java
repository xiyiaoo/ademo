/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.security.shiro.filter;

import com.xiyiaoo.service.SessionService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-5-4 上午11:03
 * ajax方式的登录拦截器
 */
public class AjaxFormAuthenticationFilter extends FormAuthenticationFilter {
    @Autowired
    private SessionService sessionService;
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        sessionService.createUserSession((String) token.getPrincipal());
        return super.onLoginSuccess(token, subject, request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        return super.onLoginFailure(token, e, request, response);
    }

}
