package org.framework.hsven.quartz.core.entity;

import java.util.Date;

/**
 * @see org.framework.hsven.quartz.core.builder.QuartzJobBuilder 中的单位描述
 */
public class CronQuartzTrigger implements QuartzTrigger {
    private String triggerName;
    private String triggerGroup;
    private String description;
    private Date startTime;
    private Date endTime;
    private long startDelay = 0;
    private String cronExpression;

    @Override
    public String getTriggerName() {
        return triggerName;
    }

    @Override
    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    @Override
    public String getTriggerGroup() {
        return triggerGroup;
    }

    @Override
    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public long getStartDelay() {
        return startDelay;
    }

    public void setStartDelay(long startDelay) {
        this.startDelay = startDelay;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}
