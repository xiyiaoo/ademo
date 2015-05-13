/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.util;

import java.util.Collection;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-5-11 下午5:19
 * 集合工具类
 */
public class CollectionUtil {
    private CollectionUtil() {}

    /**
     * 集合是否为空
     * @param collection 集合
     * @return 是否空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 集合是否非空
     * @param collection 集合
     * @return 是否非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    public static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }

    /**
     * 数组是否非空
     * @param objects 集合
     * @return 是否非空
     */
    public static boolean isNotEmpty(Object[] objects) {
        return objects != null && objects.length > 0;
    }
}
