/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.entity;

import com.xiyiaoo.constants.ResourceType;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-24 下午8:53
 * 资源(url)
 */
public class Resource extends BaseEntity {
    /**
     * 上级机构ID
     */
    private String parentId;
    /**
     * 所有上级id用/连接,如root/parent/
     */
    private String parentIds;
    /**
     * 资源名称(如果是菜单则会是菜单名称)
     */
    private String name;
    /**
     * 资源描述
     */
    private String description;
    /**
     * 权限标识
     */
    private String permission;
    /**
     * 序号
     */
    private int ordinal;
    /**
     * 资源类型
     */
    private ResourceType type;
    /***
     * 访问地址
     */
    private String url;
    /**
     * 图标的类名, 菜单类型的才需要,通过此class在页面上显示图标
     */
    private String iconClass;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }
}
