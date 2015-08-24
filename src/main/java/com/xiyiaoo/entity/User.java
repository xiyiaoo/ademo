/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.entity;

import com.xiyiaoo.constants.Gender;
import com.xiyiaoo.validation.group.Create;
import com.xiyiaoo.validation.group.Query;
import com.xiyiaoo.validation.group.Update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-24 下午8:53
 * 用户
 */
public class User extends BaseEntity {
    /**
     * 所属机构
     */
    @NotNull(message = "{User.organizationId.null}", groups = {Create.class, Update.class, Query.class})
    private String organizationId;
    /**
     * 用户名
     */
    @NotNull(message = "{User.username.null}", groups = {Create.class})
    @Pattern(regexp = "\\w{4,20}", message = "{User.username.pattern}", groups = {Create.class})
    private String username;
    /**
     * 密码
     */
    @NotNull(message = "{User.password.null}", groups = {Create.class})
    private String password;
    /**
     * 加密用的演
     */
    private String salt;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 锁定状态
     */
    private boolean locked;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private Gender gender;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 地址
     */
    private String address;
    /**
     * 说明
     */
    private String description;

    @NotNull(message = "{default.id.null}",groups = {Update.class, Query.class})
    @Override
    public String getId() {
        return super.getId();
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
