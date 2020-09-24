package org.framework.hsven.quartz.core.builder;

import org.framework.hsven.quartz.core.entity.CronQuartzTrigger;
import org.framework.hsven.quartz.core.entity.QuartzJob;
import org.framework.hsven.quartz.core.entity.QuartzTrigger;
import org.framework.hsven.quartz.core.entity.SimpleQuartzTrigger;
import org.springframework.util.StringUtils;

import java.util.Date;

public class QuartzJobBuilder {
    //job标志
    private String jobName;
    private String jobGroup;
    private String jobDescription;
    //触发器标志
    private String triggerName;
    private String triggerGroup;
    private String triggerDescription;
    //触发器触发方式
    //true:指定开始执行时间,指定contable触发方式
    //false:指定执行次数,执行间隔时间,指定开始执行时间的简单触发方式
    private boolean isCon = true;
    //crontab 执行时的触发表达式(0 15 1 * * ? *)
    private String cronExpression;
    //开始定制任务时间
    private Date startTime;
    //结束定制任务时间
    private Date endTime;
    //第一次触发延时时间,单位秒
    private long startDelay;
    //简单触发方式时的,重复执行间隔,单位毫秒
    private long repeatInterval;
    //简单触发方式时的,重复执行次数,总执行次数为 repeatCount + 1  次
    private int repeatCount = -1;

    public static QuartzJobBuilder builder(boolean isCon) {
        QuartzJobBuilder quartzJobBuilder = new QuartzJobBuilder();
        quartzJobBuilder.isCon = isCon;
        return quartzJobBuilder;
    }

    public QuartzJobBuilder withJobIdentity(String name, String group, String description) {
        this.jobName = name;
        this.jobGroup = group;
        this.jobDescription = description;
        this.triggerName = name;
        this.triggerGroup = group;
        this.triggerDescription = description;
        return this;
    }

    public QuartzJobBuilder withTriggerIdentity(String name, String group, String description) {
        this.triggerName = name;
        this.triggerGroup = group;
        this.triggerDescription = description;
        return this;
    }

    public QuartzJobBuilder startTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public QuartzJobBuilder endTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public QuartzJobBuilder startDelay(int startDelay) {
        this.startDelay = startDelay;
        return this;
    }

    public QuartzJobBuilder repeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
        return this;
    }

    public QuartzJobBuilder repeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
        return this;
    }

    public QuartzJob getQuartzJob() {
        QuartzJob quartzJob = new QuartzJob();
        quartzJob.setJobName(jobName);
        quartzJob.setJobGroup(jobGroup);
        quartzJob.setDescription(jobDescription);
        return quartzJob;
    }

    public QuartzTrigger getQuartzTrigger() {
        if (isCon) {
            CronQuartzTrigger cronQuartzTrigger = new CronQuartzTrigger();
            cronQuartzTrigger.setTriggerName(triggerName);
            cronQuartzTrigger.setTriggerGroup(triggerGroup);
            cronQuartzTrigger.setDescription(triggerDescription);
            if (StringUtils.isEmpty(cronExpression)) {
                throw new RuntimeException("cronExpression can't empty!");
            }
            cronQuartzTrigger.setCronExpression(cronExpression);
            cronQuartzTrigger.setStartTime(startTime);
            cronQuartzTrigger.setEndTime(endTime);
            cronQuartzTrigger.setStartDelay(startDelay);
            return cronQuartzTrigger;
        } else {
            SimpleQuartzTrigger simpleQuartzTrigger = new SimpleQuartzTrigger();
            simpleQuartzTrigger.setTriggerName(triggerName);
            simpleQuartzTrigger.setTriggerGroup(triggerGroup);
            simpleQuartzTrigger.setDescription(triggerDescription);
            simpleQuartzTrigger.setStartTime(startTime);
            simpleQuartzTrigger.setEndTime(endTime);
            simpleQuartzTrigger.setRepeatInterval(repeatInterval);
            simpleQuartzTrigger.setRepeatCount(repeatCount);
            return simpleQuartzTrigger;
        }
    }
}
