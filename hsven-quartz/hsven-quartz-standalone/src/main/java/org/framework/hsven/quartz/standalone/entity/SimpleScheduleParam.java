package org.framework.hsven.quartz.standalone.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.framework.hsven.quartz.core.api.ISchedulerArguments;
import org.framework.hsven.quartz.core.entity.QuartzJob;
import org.framework.hsven.quartz.core.entity.SimpleQuartzTrigger;

import java.io.Serializable;

@ApiModel(value = "简单定时任务定制参数")
public class SimpleScheduleParam implements Serializable {
    @ApiModelProperty(value = "任务执行的spring bean的名字", required = true)
    private String targetBeanName;
    @ApiModelProperty(value = "任务执行的spring bean中对应的方法名", required = true)
    private String targetMethod;
    @ApiModelProperty(value = "任务执行的spring bean中对应的方法的参数描述")
    private ISchedulerArguments targetMethodArgmuents = null;
    @ApiModelProperty(value = "任务信息", required = true)
    private QuartzJob quartzJob = null;
    @ApiModelProperty(value = "任务执行的触发器信息", required = true)
    private SimpleQuartzTrigger simpleQuartzTrigger = null;

    public String getTargetBeanName() {
        return targetBeanName;
    }

    public void setTargetBeanName(String targetBeanName) {
        this.targetBeanName = targetBeanName;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }

    public ISchedulerArguments getTargetMethodArgmuents() {
        return targetMethodArgmuents;
    }

    public void setTargetMethodArgmuents(ISchedulerArguments targetMethodArgmuents) {
        this.targetMethodArgmuents = targetMethodArgmuents;
    }

    public QuartzJob getQuartzJob() {
        return quartzJob;
    }

    public void setQuartzJob(QuartzJob quartzJob) {
        this.quartzJob = quartzJob;
    }

    public SimpleQuartzTrigger getSimpleQuartzTrigger() {
        return simpleQuartzTrigger;
    }

    public void setSimpleQuartzTrigger(SimpleQuartzTrigger simpleQuartzTrigger) {
        this.simpleQuartzTrigger = simpleQuartzTrigger;
    }
}
