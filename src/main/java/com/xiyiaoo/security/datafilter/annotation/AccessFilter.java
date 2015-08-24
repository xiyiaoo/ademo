package com.xiyiaoo.security.datafilter.annotation;

import com.xiyiaoo.constants.Constants;

import java.lang.annotation.*;

/**
 * User: xiyiaoo@gmail.com
 * Date: 2015-05-14 04:51:35
 * 访问数据过滤
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessFilter {

    /**
     * 访问目标：（通过该值去第一个参数里活取名称为该值的属性的值，如默认值为organizationId，则访问目标为arg[0].organizationId）
     *  有值：则直接判断是否有权限访问此目标
     *  无值：若allowNull为true则重写sql，过滤掉不能访问的数据
     *       若allowNull为false则抛异常
     */
    String target() default Constants.COLUMN_NAME;

    /**
     * 当没有访问目标，则通过此列重写sql过滤掉不能访问的数据
     */
    String column() default Constants.COLUMN_NAME;

    /**
     * 需要重写sql的查询的位置，从1开始
     *  假如被注解的方法里有三次查询，第三次才是正真的返回数据查询，则index为3
     */
    int queryIndex() default 1;

    /**
     * 是否允许访问目标为空
     *  true：允许访问目标为空，通过重写sql过滤数据
     *  false：不允许访问目标为空，为空时抛异常
     */
    boolean allowNull() default false;

    /**
     * 是否忽略访问目标
     *  true：忽略访问目标，总是重写sql过滤数据
     *  false：访问目标为空时才重写sql过滤数据
     */
    boolean ignoreTarget() default false;
}
