/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-24 下午8:49
 * 基本对象
 */
public class BaseEntity implements Serializable {
    /**
     * id
     */
    private String id;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建世界
     */
    private Date createTime;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 是否可用
     */
    private boolean available = true;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
