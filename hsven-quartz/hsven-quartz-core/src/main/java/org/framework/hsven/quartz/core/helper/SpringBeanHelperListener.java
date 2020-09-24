package org.framework.hsven.quartz.core.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class SpringBeanHelperListener implements ApplicationListener<ApplicationEvent> {
    private static Logger logger = LoggerFactory.getLogger(SpringBeanHelperListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            SpringBeanHelper.getInstance().applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();
            logger.info(String.format("SpringBeanHelperListener.onApplicationEvent(ContextRefreshedEvent)"));
        } else if (event instanceof ApplicationReadyEvent) {
            SpringBeanHelper.getInstance().applicationContext = ((ApplicationReadyEvent) event).getApplicationContext();
            logger.info(String.format("SpringBeanHelperListener.onApplicationEvent(ApplicationReadyEvent)"));
        }
    }
}
