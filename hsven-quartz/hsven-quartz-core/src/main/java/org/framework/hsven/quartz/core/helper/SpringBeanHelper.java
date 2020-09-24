package org.framework.hsven.quartz.core.helper;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

public class SpringBeanHelper {
    private static SpringBeanHelper instance = null;
    protected ApplicationContext applicationContext = null;

    private SpringBeanHelper() {
    }

    public static SpringBeanHelper getInstance() {
        if (instance == null) {
            createInstance();
        }
        return instance;
    }

    private static synchronized void createInstance() {
        if (instance == null) {
            instance = new SpringBeanHelper();
        }
    }

    public Object getBean(String beanName) throws BeansException {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(beanName);
    }

    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        if (applicationContext == null) {
            return null;
        }

        return applicationContext.getBean(beanName, requiredType);
    }

    public <T> T getBean(Class<T> requiredType) throws BeansException {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(requiredType);
    }
}
