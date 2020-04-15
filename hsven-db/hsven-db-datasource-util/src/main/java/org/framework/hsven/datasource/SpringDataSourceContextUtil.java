package org.framework.hsven.datasource;

import org.framework.hsven.datasource.util.DataSourceNameGenerator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * datasource 相关的spring bean 获取辅助
 */
public final class SpringDataSourceContextUtil implements ApplicationContextAware {

    private static ApplicationContext ctx = null;

    public SpringDataSourceContextUtil() {
    }

    public static Object getBean(String beanName) throws BeansException {
        return ctx == null ? null : ctx.getBean(beanName);
    }

    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return ctx == null ? null : ctx.getBean(requiredType);
    }

    public static boolean containsBean(String beanName) {
        return ctx != null && ctx.containsBean(beanName);
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        SpringDataSourceContextUtil.ctx = ctx;
    }

    public static <T> T getDataSourceRelevant(String dbName, Class<T> classzz) {
        return ctx == null ? null : (T) ctx.getBean(DataSourceNameGenerator.getMybatisMapperBeanName(dbName, classzz));
    }
}
