/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.web.handler;

import com.xiyiaoo.entity.DefaultResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-5-12 下午4:10
 * 默认异常处理
 */
@ControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler({
            Exception.class
    })
    @ResponseBody
    public DefaultResponse handleException(Exception e){
        return DefaultResponse.failResponse().setMessage(e.getMessage());
    }
}
