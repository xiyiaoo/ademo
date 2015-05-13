/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.util;

import com.xiyiaoo.constants.Constants;
import com.xiyiaoo.entity.User;
import com.xiyiaoo.exception.AccessDeniedException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-5-5 下午3:11
 * 工具类
 */
public class WebUtil {
    private static final String CURRENT_USER_KEY = "currentUser";
    private WebUtil() {}

    /**
     * 获取当前登录用户
     * @return 登录用户
     */
    public static User getCurrentUser() {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(CURRENT_USER_KEY);
        if (user == null) {
            throw new InvalidSessionException("未找到登录用户!");
        }
        return user;
    }

    /**
     * 设置当前用户
     * @param user 用户信息
     */
    public static void setCurrentUser(User user) {
        SecurityUtils.getSubject().getSession().setAttribute(CURRENT_USER_KEY, user);
    }

    /**
     * 获取当前登录用户的id
     * @return 当前用户id
     */
    public static String getCurrentUserId(){
        User user = getCurrentUser();
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    /**
     * 判断当前用户是否是超级管理员
     * @return is super admin
     */
    public static boolean isSuperAdmin(){
        return Constants.ROOT_ID.equals(getCurrentUserId());
    }
}
