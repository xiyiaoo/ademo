package com.xiyiaoo.security.datafilter.interceptor;

import com.xiyiaoo.exception.AccessDeniedException;
import com.xiyiaoo.security.datafilter.annotation.AccessFilter;
import com.xiyiaoo.util.SpringUtil;
import com.xiyiaoo.util.WebUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * User: xiyiaoo@gmail.com
 * Date: 2015-05-14 05:04:24数据过滤
 * spring bean拦截器
 */
public class DataFilterInterceptor implements MethodInterceptor {
    private AccessDecider accessDecider;
    private String accessDeciderBeanName;
    public static ThreadLocal<Single> single = new ThreadLocal<>();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if(!WebUtil.isSuperAdmin()){
            AccessFilter annotation = AnnotationUtils.findAnnotation(invocation.getMethod(), AccessFilter.class);
            if (annotation.ignoreTarget()) {
                //忽略访问目标，走重写sql
                single.set(new Single(annotation.column(), annotation.queryIndex()));
            } else {
                if (invocation.getArguments().length == 0) {
                    throw new IllegalArgumentException();
                }
                try {
                    String target = annotation.target();
                    String targetValue = (String) new SpelExpressionParser().parseRaw(target)
                            .getValue(invocation.getArguments()[0]);
                    if (targetValue == null) {
                        if (annotation.allowNull()){
                            //访问目标为空，走重写sql
                            single.set(new Single(annotation.column(), annotation.queryIndex()));
                        } else {
                            throw new IllegalArgumentException(String.format("无效的target：‘%s’", target));
                        }
                    }
                    if (!getAccessDecider().allow(targetValue)){
                        throw new AccessDeniedException(String.format("没有访问‘%s’数据的权限", targetValue));
                    }
                } catch (ParseException e) {
                    throw new IllegalArgumentException(String.format("参数找不到‘%s’属性", annotation.column()));
                }
            }
        }

        return invocation.proceed();
    }

    public void setAccessDeciderBeanName(String beanName) {
        this.accessDeciderBeanName = beanName;
    }

    public AccessDecider getAccessDecider() {
        if (accessDecider == null) {
            accessDecider = SpringUtil.getBean(accessDeciderBeanName);
        }
        return accessDecider;
    }

    static class Single {
        private String column;
        private int index = 1;
        private int queryIndex;

        Single(String column, int queryIndex) {
            this.column = column;
            this.queryIndex = queryIndex;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getQueryIndex() {
            return queryIndex;
        }

        public void setQueryIndex(int queryIndex) {
            this.queryIndex = queryIndex;
        }
    }

}
