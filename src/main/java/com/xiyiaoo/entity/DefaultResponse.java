/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.entity;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-5-12 下午4:12
 * 响应结果
 */
public class DefaultResponse<T> {
    /**
     * 成功标记
     */
    private boolean success;
    /**
     * 响应数据
     */
    private T data;
    /**
     * 响应消息
     */
    private String message;

    public DefaultResponse() {
    }

    public DefaultResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public DefaultResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public T getData() {
        return data;
    }

    public DefaultResponse setData(T data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public DefaultResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public static DefaultResponse failResponse() {
        return new DefaultResponse();
    }

    public static DefaultResponse successResponse() {
        return new DefaultResponse(true);
    }
}
