package com.xiyiaoo.mybatis.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Invocation;

/**
 * User: xiyiaoo@gmail.com
 * Date: 2015-05-15 06:25:11
 * 查询前置处理
 */
public interface PrepareQueryHandler {
    /**
     * 查询预处理
     * @param statementHandler 查询
     * @param invocation 代理的查询方法
     */
    void handle(StatementHandler statementHandler, Invocation invocation);
}
