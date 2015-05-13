/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.exception;

import org.apache.shiro.dao.DataAccessException;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-5-8 上午10:14
 * 拒绝访问或操作资源
 */
public class AccessDeniedException extends DataAccessException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
