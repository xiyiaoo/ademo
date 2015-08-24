/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.entity;

import com.xiyiaoo.validation.group.Create;
import com.xiyiaoo.validation.group.Update;
import org.hibernate.validator.constraints.NotBlank;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-24 下午8:53
 * 组织机构
 */
public class Organization extends BaseEntity {
    /**
     * 上级机构ID
     */
    @NotBlank(message = "{Organization.parentId.null}", groups = {Create.class, Update.class})
    private String parentId;
    /**
     * 所有上级id用/连接,如root/parent/
     */
    private String parentIds;
    /**
     * 所有上级的name用/连接,如root/parent/
     */
    private String parentNames;
    /**
     * 序号
     */
    private int ordinal;
    /**
     * 机构名
     */
    @NotBlank(message = "{Organization.name.null}", groups = {Create.class, Update.class})
    private String name;
    /**
     * 机构描述
     */
    private String description;

    @NotBlank(message = "{default.id.null}", groups = {Create.class, Update.class})
    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public int hashCode() {
        return this.getId() == null ? super.hashCode() : this.getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Organization){
            Organization organization = (Organization) obj;
            if(this.getId() != null){
                return this.getId().equals(organization.getId());
            }
        }
        return false;
    }

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

    public String getParentNames() {
        return parentNames;
    }

    public void setParentNames(String parentNames) {
        this.parentNames = parentNames;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
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
}
