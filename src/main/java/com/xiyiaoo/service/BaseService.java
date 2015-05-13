/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.service;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-28 下午3:35
 * 基础的service,定义了增删改查是个接口
 */
public interface BaseService<T> {
    /**
     * 增加一个实体
     * @param t 实体信息
     */
    void add(T t);

    /**
     * 删除一个实体
     * @param t 实体信息
     */
    void delete(T t);

    /**
     * 更新一个实体
     * @param t 实体信息
     */
    void update(T t);

    /**
     * 获取一个实体
     * @param t 实体信息
     */
    T get(T t);
}
