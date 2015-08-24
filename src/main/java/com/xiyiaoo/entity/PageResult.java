/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.entity;

import org.springframework.util.Assert;

import java.util.List;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-28 下午2:55
 * 分页信息
 */
public class PageResult<T> {
    /**
     * 页号
     */
    private int pageIndex;
    /**
     * 每页记录数
     */
    private int pageSize;
    /**
     * 页数
     */
    private int totalPages;
    /**
     * 总记录数
     */
    private int totalResults;
    /**
     * 分页数据
     */
    private List<T> data;

    public PageResult() {}

    public PageResult(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        Assert.isTrue(pageIndex > 0);
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        Assert.isTrue(pageSize > 0);
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
        this.totalPages = totalResults / pageSize + 1;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
