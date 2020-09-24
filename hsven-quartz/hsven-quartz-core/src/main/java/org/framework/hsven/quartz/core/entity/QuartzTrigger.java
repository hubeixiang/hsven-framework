package org.framework.hsven.quartz.core.entity;

import java.io.Serializable;

/**
 * quartz触发器的定义
 * 一个触发器可以对应多个任务进行触发
 */
public interface QuartzTrigger extends Serializable {
    public String getTriggerName();

    public void setTriggerName(String triggerName);

    public String getTriggerGroup();

    public void setTriggerGroup(String triggerGroup);

    public String getDescription();

    public void setDescription(String description);
}
