package org.framework.hsven.quartz.core.api;

import org.framework.hsven.quartz.core.entity.JobIdentity;
import org.framework.hsven.quartz.core.entity.QuartzJob;
import org.framework.hsven.quartz.core.entity.QuartzTrigger;
import org.framework.hsven.quartz.core.entity.ResponseScheduler;
import org.framework.hsven.quartz.core.entity.TriggerIdentity;
import org.springframework.lang.NonNull;

/**
 * 调度任务操作服务接口定义
 */
public interface ISchedulerOperatorService {
    /**
     * 列出所有的
     *
     * @return
     */
    public ResponseScheduler listJob();

    public ResponseScheduler pauseJob(JobIdentity jobIdentity);

    public ResponseScheduler pauseTrigger(TriggerIdentity triggerIdentity);

    public ResponseScheduler resumeJob(JobIdentity jobIdentity);

    public ResponseScheduler resumeTrigger(TriggerIdentity triggerIdentity);

    public ResponseScheduler deleteJob(JobIdentity jobIdentity);

    public ResponseScheduler scheduleJob(@NonNull String targetBeanName, @NonNull String targetMethod, ISchedulerArguments targetMethodArgmuents, @NonNull QuartzJob quartzJob, @NonNull QuartzTrigger quartzTrigger);
}
