package com.xiyiaoo.web.entity;

/**
 * User: xiyiaoo@gmail.com
 * Date: 2015-05-20 04:27:25
 * 请求的响应结果
 */
public interface Response {
    /**
     * 是否成功
     * @return bool
     */
    public boolean isSuccess();

    /**
     * 获取响应数据
     * @return object
     */
    public Object getData();

    /**
     * 获取响应消息
     * @return string
     */
    public String getMessage();
}
