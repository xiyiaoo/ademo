/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.util;

import java.util.UUID;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-5-5 下午3:03
 * id生成器
 */
public class StringUtil {
    private StringUtil() {
    }

    /**
     * 生成uuid
     *
     * @return uuid
     */
    public static String uuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
    }

    /**
     * 两字符串是否相等
     * @param s1 字符串1
     * @param s2 字符串2
     * @return 是否相等
     */
    public static boolean isEqual(String s1, String s2) {
        if (s1 == null) {
            return s2 == null;
        } else {
            return s1.equals(s2);
        }
    }

    /**
     * 两字符串是否不相等
     * @param s1 字符串1
     * @param s2 字符串2
     * @return 是否不相等
     */
    public static boolean isNotEqual(String s1, String s2) {
        return !isEqual(s1, s2);
    }

    /**
     * 字符串是否为空（null || ""）
     * @param src 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String src) {
        return src == null || src.length() == 0;
    }

    /**
     * 字符串是否不为空（非null && 非""）
     * @param src 字符串
     * @return 是否不为空
     */
    public static boolean isNotEmpty(String src) {
        return src != null && src.length() > 0;
    }

    /**
     * 字符串是否不为空（非null && 非空白字符串）
     * @param src 字符串
     * @return 是否不为空
     */
    public static boolean isNotBlank(String src) {
        if (isEmpty(src)) {
            return false;
        }

        for (int i = 0; i < src.length(); i++) {
            if (Character.isWhitespace(src.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 生成上级路径
     * @param parent 上级(如:123)
     * @param parentsOfParent 上级的上级路径(如:0/)
     * @return path 如(0/123/)
     */
    public static String buildParentsPath(String parent, String parentsOfParent){
        return buildParentsPath(parent, parentsOfParent, "/");
    }
    /**
     * 生成上级路径
     * @param parent 上级(如:123)
     * @param parentsOfParent 上级的上级路径(如:0/)
     * @param divider 分隔符
     * @return path 如(0/123/)
     */
    public static String buildParentsPath(String parent, String parentsOfParent, String divider){
        if (parent != null) {
            if (isNotEmpty(parentsOfParent)) {
                return parentsOfParent + parent + divider;
            } else {
                return parent + divider;
            }
        }
        return null;
    }
}
