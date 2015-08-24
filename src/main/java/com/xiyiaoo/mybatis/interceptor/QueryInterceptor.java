package com.xiyiaoo.mybatis.interceptor;

import com.xiyiaoo.util.CollectionUtil;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;

import java.sql.Connection;
import java.util.Properties;

import static org.apache.ibatis.reflection.MetaObject.DEFAULT_OBJECT_FACTORY;
import static org.apache.ibatis.reflection.MetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY;

/**
 * User: xiyiaoo@gmail.com
 * Date: 2015-05-15 06:19:01
 * 查询拦截器
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare",args = {Connection.class})})
public class QueryInterceptor implements Interceptor {
    private PrepareQueryHandler[] handlers;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (CollectionUtil.isNotEmpty(handlers)){
            //StatementHandler都是RoutingStatementHandler，它只是一个分发者，他一个属性delegate用于指定用哪种具体的StatementHandler。
            //有SimpleStatementHandler、PreparedStatementHandler和CallableStatementHandler三种
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            //ibatis的工具类
            MetaObject metaStatementHandler = MetaObject.forObject(statementHandler,
                    DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
            // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环
            // 可以分离出最原始的的目标类)
            while (metaStatementHandler.hasGetter("h")) {//每个代理对象都有h属性
                Object object = metaStatementHandler.getValue("h");
                metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY,
                        DEFAULT_OBJECT_WRAPPER_FACTORY);
            }
            // 分离最后一个代理对象的目标类，得到原始类
            while (metaStatementHandler.hasGetter("target")) {
                Object object = metaStatementHandler.getValue("target");
                metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY,
                        DEFAULT_OBJECT_WRAPPER_FACTORY);
            }

            for (PrepareQueryHandler handler : handlers) {
                handler.handle(statementHandler, invocation);
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    public PrepareQueryHandler[] getHandlers() {
        return handlers;
    }

    public void setHandlers(PrepareQueryHandler[] handlers) {
        this.handlers = handlers;
    }
}
