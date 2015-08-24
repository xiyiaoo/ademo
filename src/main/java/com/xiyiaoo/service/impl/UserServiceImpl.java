/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.service.impl;

import com.xiyiaoo.constants.Constants;
import com.xiyiaoo.dao.UserDao;
import com.xiyiaoo.entity.PageResult;
import com.xiyiaoo.entity.Role;
import com.xiyiaoo.entity.User;
import com.xiyiaoo.exception.AccessDeniedException;
import com.xiyiaoo.security.datafilter.annotation.AccessFilter;
import com.xiyiaoo.service.UserService;
import com.xiyiaoo.util.CollectionUtil;
import com.xiyiaoo.util.StringUtil;
import com.xiyiaoo.util.UserPasswordEncryptor;
import com.xiyiaoo.util.WebUtil;
import com.xiyiaoo.validation.group.Create;
import com.xiyiaoo.validation.group.Query;
import com.xiyiaoo.validation.group.Update;
import com.xiyiaoo.validation.validator.ValidChangePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-24 下午8:59
 * user service 实现
 */
@Service
public class UserServiceImpl implements UserService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserPasswordEncryptor encryptor;
    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public User getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        User currentUser = WebUtil.getCurrentUser();
        if (encryptor.match(currentUser, oldPassword)){//匹配旧密码是否正确
            User user = new User();
            user.setId(currentUser.getId());
            user.setPassword(newPassword);
            encryptor.encrypt(user);
            userDao.modifyPassword(user);
            //盐变了，需要同步更新回session
            currentUser.setSalt(user.getSalt());
            currentUser.setPassword(user.getPassword());
        } else {
            throw new AccessDeniedException("旧密码错误");
        }
    }

    @Override
    public void resetPassword(String username, String newPassword) {
        User user = getByUsername(username);
        user.setPassword(newPassword);
        encryptor.encrypt(user);
        userDao.modifyPassword(user);
    }

    @Override
    public List<Role> getRoleIds(String useId) {
        return userDao.getRoleIds(useId);
    }

    @Override
    @AccessFilter
    @Validated({Query.class})
    public void addRoleIds(User user, Set<Role> roles) {
        if (CollectionUtil.isNotEmpty(roles)){
            String userId = user.getId();
            user = get(user);
            if (user == null) {
                throw new IllegalArgumentException(String.format("无效的userId:'%s'", userId));
            }
            //给授权角色
            userDao.addRoleIds(userId, roles);
            //更新主表的修改人
            update(user);
        }
    }

    @Override
    @AccessFilter
    @Validated({Query.class})
    public void updateRoleIds(User user, Set<Role> roles) {
        String userId = user.getId();
        user = get(user);
        if (user == null) {
            throw new IllegalArgumentException(String.format("无效的userId:'%s'", userId));
        }
        //清空之前授权的角色
        userDao.removeRoleIds(userId);
        if (CollectionUtil.isNotEmpty(roles)){
            //给授权角色
            userDao.addRoleIds(userId, roles);
        }
        //更新主表的修改人
        update(user);
    }

    @AccessFilter(ignoreTarget = true)
    @Override
    public PageResult<User> getUsers(User user, int pageIndex, int pageSize) {
        PageResult<User> pageResult = new PageResult<>(pageIndex, pageSize);
        pageResult.setData(userDao.getUsers(pageResult));
        return pageResult;
    }

    /**
     * 添加用户
     * @param user 用户信息
     */
    @Override
    @Validated({Create.class})
    @AccessFilter
    public int add(User user) {
        user.setId(StringUtil.uuid());
        user.setCreator(WebUtil.getCurrentUserId());
        encryptor.encrypt(user);
        return userDao.add(user);
    }

    /**
     * 删除用户
     * @param user 用户信息
     */
    @Override
    @Validated({Query.class})
    @AccessFilter
    public int delete(User user) {
        if (Constants.ROOT_ID.equals(user.getId())){
            return 0;
        }
        int delNum = userDao.delete(user);
        if(delNum > 0){
            //级联删除授权的角色
            userDao.removeRoleIds(user.getId());
        }
        return delNum;
    }

    /**
     * 更新用户信息
     * @param user 用户信息
     */
    @Override
    @Validated({Update.class})
    @AccessFilter
    public int update(User user) {
        if (Constants.ROOT_ID.equals(user.getId())){
            return 0;
        }
        //TODO 修改机构时的权限过滤
        user.setModifier(WebUtil.getCurrentUserId());
        return userDao.update(user);
    }

    /**
     * 获取用户信息
     *  需要根据机构判断是否有权限操作
     * @param user 条件(机构+用户id)
     */
    @Override
    @Validated({Query.class})
    @AccessFilter
    public User get(User user) {
        return userDao.get(user);
    }
}
