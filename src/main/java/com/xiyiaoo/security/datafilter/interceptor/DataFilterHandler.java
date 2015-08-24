package com.xiyiaoo.security.datafilter.interceptor;

import com.xiyiaoo.entity.Organization;
import com.xiyiaoo.entity.User;
import com.xiyiaoo.mybatis.interceptor.PrepareQueryHandler;
import com.xiyiaoo.service.OrganizationService;
import com.xiyiaoo.service.SessionService;
import com.xiyiaoo.util.CollectionUtil;
import com.xiyiaoo.util.SpringUtil;
import com.xiyiaoo.util.StringUtil;
import com.xiyiaoo.util.WebUtil;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.ibatis.reflection.MetaObject.DEFAULT_OBJECT_FACTORY;
import static org.apache.ibatis.reflection.MetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY;

/**
 * User: xiyiaoo@gmail.com
 * Date: 2015-05-15 04:20:38
 * 查询时过滤数据的处理器
 */
public class DataFilterHandler implements PrepareQueryHandler, AccessDecider {
    //@Autowired //自动注入会报错，循环依赖？但是注入SessionService，SessionService里再注入就不出错，why？
    private OrganizationService organizationService;

    @Override
    public void handle(StatementHandler statementHandler, Invocation invocation) {
        DataFilterInterceptor.Single single = DataFilterInterceptor.single.get();
        if (single != null) {
            MetaObject metaStatementHandler = MetaObject.forObject(statementHandler,
                    DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            BoundSql boundSql = statementHandler.getBoundSql();
            processBoundSql(mappedStatement, boundSql, single);
        }
    }


    private void processBoundSql(MappedStatement mappedStatement, BoundSql boundSql, DataFilterInterceptor.Single single) {
        if(single.getQueryIndex() == single.getIndex()){

            DataFilterInterceptor.single.remove();
            Set<Organization> allows = allows();
            DataFilterInterceptor.single.set(single);

            String column = single.getColumn();
            //List<ParameterMapping> mappings = new ArrayList<>();
            //mappings.addAll(boundSql.getParameterMappings());
            StringBuilder sql = new StringBuilder("select * from (")
                    .append(boundSql.getSql())
                    .append(") t where t.")
                    .append(column)
                    .append(" in(");
            int index = 0;
            for (Organization allow : allows) {
                if (allow != null) {
                    if (index++ != 0)
                        sql.append(" union all ");
                    sql.append("select id from t_sys_organization where parentIds like ")
                            .append("'").append(allow.getParentIds()).append("%'")
                            .append(" union all ")
                            .append("select '").append(allow.getId()).append("' from dual");
                }
            }
            if (index == 0)
                sql.append("");
            sql.append(')');

            MetaObject.forObject(boundSql).setValue("sql", sql.toString());
        }
        single.setIndex(single.getIndex() + 1);
    }

    @Override
    public boolean allow(String... target) {
        if (CollectionUtil.isNotEmpty(target)){
            Set<Organization> allows = allows();
            for (Organization allow : allows) {
                String pIds = StringUtil.buildParentsPath(allow.getId(), allow.getParentIds());
                for (String organizationId : target) {
                    Organization organization = getOrganizationService().getById(organizationId);
                    if (organization == null ||(!organizationId.equals(organization.getId()) && !organization.getParentIds().startsWith(pIds))){
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Set<Organization> allows() {
        User user = WebUtil.getCurrentUser();
        Set<Organization> allows = new HashSet<>();
        Organization organization = getOrganizationService().getById(user.getOrganizationId());
        if (organization != null) {
            allows.add(organization);
        }
        return allows;
    }

    public OrganizationService getOrganizationService() {
        if (organizationService == null) {
            organizationService = SpringUtil.getBean(OrganizationService.class);
        }
        return organizationService;
    }
}
