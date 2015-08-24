package com.xiyiaoo.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * User: xiyiaoo@gmail.com
 * Date: 2015-05-16 06:08:41
 * spring 工具类
 */
public class SpringUtil implements BeanFactoryPostProcessor {

    private static ConfigurableListableBeanFactory beanFactory; // Spring应用上下文环境

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtil.beanFactory = beanFactory;
    }

    /**
     * 获取bean
     * @param name bean名称
     * @return bean
     * @throws org.springframework.beans.BeansException
     *
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    /**
     * 获取类型为clazz的bean
     * @param clazz 类型
     * @return bean
     * @throws org.springframework.beans.BeansException
     *
     */
    public static <T> T getBean(Class<T> clazz) throws BeansException {
        @SuppressWarnings("unchecked")
        T result = (T) beanFactory.getBean(clazz);
        return result;
    }

    /**
     * 是否存在名称为name的bean
     * @param name bean名称
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    /**
     * bean是否时单例，不存在将会抛出一个异常（NoSuchBeanDefinitionException）
     * @param name bean名称
     * @return boolean
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     *
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.isSingleton(name);
    }

    /**
     * 获取bean的类型
     * @param name bean名称
     * @return Class 注册对象的类型
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getType(name);
    }

    /**
     * 如果的bean在定义中有别名，则返回这些别名
     * @param name bean名称
     * @return 别名列表
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     *
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getAliases(name);
    }

}
