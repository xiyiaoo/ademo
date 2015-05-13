/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.constants;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-28 上午10:26
 * 字符串代码枚举
 */
public interface CodeEnum<E extends Enum<E>> {
    /**
     * 获取枚举的代码
     * @return 代码
     */
    String getCode();
}
