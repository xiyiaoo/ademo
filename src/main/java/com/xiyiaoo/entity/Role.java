/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.entity;

import java.util.Set;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-24 下午8:53
 * 角色
 */
public class Role extends BaseEntity {
    /**
     * 角色名
     */
    private String name;
    /**
     * 角色值，权限判断会用到，如admin
     */
    private String value;
    /**
     * 角色描述
     */
    private String description;

    //-----------用户与角色关系表----------//
    /**
     * 序号
     */
    private int ordinal;

    @Override
    public int hashCode() {
        return this.getId() == null ? super.hashCode() : this.getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Role){
            Role role = (Role) obj;
            if(this.getId() != null){
                return this.getId().equals(role.getId());
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

}
