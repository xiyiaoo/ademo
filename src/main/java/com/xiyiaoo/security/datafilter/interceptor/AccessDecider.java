package com.xiyiaoo.security.datafilter.interceptor;

import com.xiyiaoo.entity.Organization;

import java.util.Set;

/**
 * User: xiyiaoo@gmail.com
 * Date: 2015-05-16 04:45:19
 * 访问决策
 */
public interface AccessDecider {
    /**
     * 是否允许访问
     * @param target 访问目标
     * @return boolean
     */
    boolean allow(String... target);

    /**
     * 允许访问的目标
     * @return 允许访问机构
     */
    Set<Organization> allows();
}
