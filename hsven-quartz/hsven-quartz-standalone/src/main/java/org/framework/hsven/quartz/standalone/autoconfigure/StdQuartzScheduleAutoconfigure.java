package org.framework.hsven.quartz.standalone.autoconfigure;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StdQuartzScheduleAutoconfigure {
    private static Logger logger = LoggerFactory.getLogger(StdQuartzScheduleAutoconfigure.class);

    @ConditionalOnMissingBean(Scheduler.class)
    @Bean
    public Scheduler scheduler() {
        SchedulerFactory schedulerfactory = new StdSchedulerFactory();
        Scheduler scheduler = null;
        try {
            scheduler = schedulerfactory.getScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            logger.error(String.format("init configuration quartz scheduler exception:%s", e.getMessage()), e);
            throw new RuntimeException(String.format("init configuration quartz scheduler exception:%s", e.getMessage()));
        }
        return scheduler;
    }
}
