/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.mybatis.interceptor;

import com.alibaba.druid.util.StringUtils;
import com.xiyiaoo.entity.PageResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;

import static org.apache.ibatis.reflection.MetaObject.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-28 下午3:58
 * 分页拦截器
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare",args = {Connection.class})})
public class PagingInterceptor implements Interceptor {

    private static Log logger = LogFactory.getLog(PagingInterceptor.class);
    /**
     * 数据库方言
     */
    private String dialect = "oracle";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
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

        //获取statementHandler的mappedStatement属性
        MappedStatement mappedStatement = (MappedStatement)metaStatementHandler.getValue("delegate.mappedStatement");
        //获取statementHandler的boundSql属性
        BoundSql boundSql = statementHandler.getBoundSql();
        Object parameterObject = boundSql.getParameterObject();
        if (parameterObject instanceof PageResult<?>){
            if(logger.isDebugEnabled()){
                logger.debug("*******************开始分页查询*******************");
            }
            //拦截到的prepare方法参数是一个Connection对象
            Connection connection = (Connection)invocation.getArgs()[0];
            PageResult<?> page = (PageResult<?>) parameterObject;
            //查询并设置总记录数
            this.setTotalRecord(page, mappedStatement, connection);
            //重设查询数据的sql
            // 重写查询数据的sql
            String pageSql = buildDataSql(boundSql.getSql(), page);
            if(logger.isDebugEnabled()){
                logger.debug("查询分页记录sql：" + pageSql);
            }
            metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
        }
        //执行，交给拦截器链上后面的拦截器
        return invocation.proceed();
    }

    /**
     * 查询总记录数并设置到分页信息中
     * @param page 分页信息和映射语句的参数对象
     * @param mappedStatement mybatis的映射相关
     * @param connection 数据库连接
     */
    private void setTotalRecord(PageResult<?> page, MappedStatement mappedStatement, Connection connection) {
        //获取对应的BoundSql，这个BoundSql其实跟我们利用StatementHandler获取到的BoundSql是同一个对象。
        //delegate里面的boundSql也是通过mappedStatement.getBoundSql(paramObj)方法获取到的。
        BoundSql boundSql = mappedStatement.getBoundSql(page);
        //获取到我们自己写在Mapper映射语句中对应的Sql语句
        String sql = boundSql.getSql();
        if(logger.isDebugEnabled()){
            logger.debug("查询总记录数sql：" + sql);
        }
        //通过查询Sql语句获取到对应的计算总记录数的sql语句
        String countSql = this.buildCountSql(sql);
        //通过BoundSql获取对应的参数映射
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        //利用Configuration、查询记录数的countSql、参数映射关系parameterMappings和参数对象page建立查询记录数对应的BoundSql对象。
        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page);
        //通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立一个用于设定参数的ParameterHandler对象
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, countBoundSql);
        //通过connection建立一个countSql对应的PreparedStatement对象。
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = connection.prepareStatement(countSql);
            //通过parameterHandler给PreparedStatement对象设置参数
            parameterHandler.setParameters(preparedStatement);
            //之后就是执行获取总记录数的Sql语句和获取结果了。
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int totalRecord = rs.getInt(1);
                page.setTotalResults(totalRecord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 构建查询总记录数的sql
     * @param sql 原sql
     * @return 构建好的sql
     */
    private String buildCountSql(String sql) {
        return "select count(*) from (" + sql + ")";
    }

    /**
     * 构建查询分页记录的sql
     * @param sql 原sql
     * @param page 分页信息
     * @return 构建好的分页sql
     */
    private String buildDataSql(String sql, PageResult<?> page){
        if("oracle".equals(dialect)){
            return buildDataSqlOfOracle(sql,page);
        }
        return sql;
    }

    /**
     * 构建Oracle数据库查询分页记录的sql
     * @param sql 原sql
     * @param page 分页信息
     * @return 构建好的分页sql
     */
    private String buildDataSqlOfOracle(String sql, PageResult<?> page) {
        return "select * from (" +
                "select x.*,rownum rn from (" + sql + ") x " +
                "where rownum < " + (page.getPageIndex() * page.getPageSize() + 1) + ") y" +
                " where y.rn > " + (page.getPageIndex() - 1) * page.getPageSize();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        setDialect(properties.getProperty("dialect"));
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        if(StringUtils.isEmpty(dialect))
            this.dialect = dialect;
    }
}