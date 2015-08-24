/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.service;

import com.xiyiaoo.validation.group.Create;
import com.xiyiaoo.validation.group.Query;
import com.xiyiaoo.validation.group.Update;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-28 下午3:35
 * 基础的service,定义了增删改查四个接口
 *  四个接口都开启了入参出参校验
 */
@Validated
public interface BaseService<T> {
    /**
     * 增加一个实体
     * @param t 实体信息
     */
    @Min(value = 1, message = "{default.null}", groups = {Create.class, Update.class, Query.class})
    int add(@Valid T t);

    /**
     * 删除一个实体
     * @param t 实体信息
     */
    @Min(value = 1, message = "{default.null}", groups = {Create.class, Update.class, Query.class})
    int delete(@Valid T t);

    /**
     * 更新一个实体
     * @param t 实体信息
     */
    @Min(value = 1, message = "{default.null}", groups = {Create.class, Update.class, Query.class})
    int update(@Valid T t);

    /**
     * 获取一个实体
     * @param t 实体信息
     */
    @NotNull(message = "{default.null}", groups = {Create.class, Update.class, Query.class})
    T get(@Valid T t);
}
