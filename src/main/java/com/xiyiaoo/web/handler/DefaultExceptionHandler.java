/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.web.handler;

import com.xiyiaoo.web.entity.DefaultResponse;
import com.xiyiaoo.web.entity.Response;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-5-12 下午4:10
 * 默认异常处理
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    //校验异常
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleValidException(ConstraintViolationException e){
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            message.append(violation.getMessage()).append(" ");
        }

        return DefaultResponse.failResponse().setMessage(message.toString());
    }

    //mvc层校验异常
    @ExceptionHandler({BindException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleBindException(BindException e){
        StringBuilder message = new StringBuilder();
        for (ObjectError objectError : e.getAllErrors()) {
            message.append(objectError.getDefaultMessage()).append(" ");
        }
        return DefaultResponse.failResponse().setMessage(message.toString());
    }

    //其他异常
    @ExceptionHandler({Exception.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public Response handleException(Exception e){
        return DefaultResponse.failResponse().setMessage(getMessage(e));
    }

    private String getMessage(Exception e){
        String message = e.getMessage();
        if (message == null) {
            message = e.getClass().getName();
        }
        return message;
    }
}
