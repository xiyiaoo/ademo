/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.util;

import com.xiyiaoo.entity.User;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-30 下午4:35
 * 用户密码加密器
 */
public interface UserPasswordEncryptor {
    /**
     * 加密用户的密码
     * @param user 用户信息
     */
    void encrypt(User user);

    /**
     * 匹配密码是否一致
     * @param user 真正的用户信息（包括用户的真正的密码和盐）
     * @param password 要匹配的密码
     * @return 是否一致
     */
    boolean match(User user, String password);
}
