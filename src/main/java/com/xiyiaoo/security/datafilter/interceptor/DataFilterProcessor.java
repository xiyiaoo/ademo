package com.xiyiaoo.security.datafilter.interceptor;

import com.xiyiaoo.security.datafilter.annotation.AccessFilter;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.beanvalidation.MethodValidationInterceptor;

import java.lang.annotation.Annotation;

/**
 * User: xiyiaoo@gmail.com
 * Date: 2015-05-14 04:59:46
 */
public class DataFilterProcessor extends AbstractAdvisingBeanPostProcessor implements InitializingBean {
    /**
     *
     */
    private Class<? extends Annotation> annotationPoint = Service.class;

    private DataFilterInterceptor interceptor;

    public void setAnnotationPoint(Class<? extends Annotation> annotationPoint) {
        this.annotationPoint = annotationPoint;
    }

    public void setInterceptor(DataFilterInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(annotationPoint);
        Assert.notNull(interceptor);
        Pointcut pointcut = new AnnotationMatchingPointcut(this.annotationPoint, AccessFilter.class);
        this.advisor = new DefaultPointcutAdvisor(pointcut, interceptor);
    }
}
