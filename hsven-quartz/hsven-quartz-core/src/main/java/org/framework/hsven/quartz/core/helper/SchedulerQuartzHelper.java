package org.framework.hsven.quartz.core.helper;

import org.framework.hsven.quartz.core.api.ISchedulerArguments;
import org.framework.hsven.quartz.core.entity.CronQuartzTrigger;
import org.framework.hsven.quartz.core.entity.QuartzJob;
import org.framework.hsven.quartz.core.entity.SimpleQuartzTrigger;
import org.quartz.*;

import java.lang.reflect.Method;

public class SchedulerQuartzHelper {
    public final static String SCHEDULER_ARGUMENTS_KEY = "SCHEDULER_ARGUMENTS_KEY";

    public static void scheduleCronJob(Scheduler scheduler, String targetBeanName,
                                       String targetMethod, QuartzJob quartzJob, SimpleQuartzTrigger simpleQuartzTrigger) throws SchedulerException {
        scheduleCronJob(scheduler, targetBeanName, targetMethod, quartzJob, simpleQuartzTrigger, null);
    }

    public static void scheduleCronJob(Scheduler scheduler, String targetBeanName,
                                       String targetMethod, QuartzJob quartzJob, SimpleQuartzTrigger simpleQuartzTrigger, ISchedulerArguments schedulerArguments) throws SchedulerException {

        String jobId = String.format("job(name=%s,group=%s)", quartzJob.getJobName(), quartzJob.getJobGroup());
        String triggerId = String.format("trigger(name=%s,group=%s)", simpleQuartzTrigger.getTriggerName(), simpleQuartzTrigger.getTriggerGroup());
        String scheduleId = String.format("schedule(%s,%s)", jobId, triggerId);

        JobKey jobKey = new JobKey(quartzJob.getJobName(), quartzJob.getJobGroup());
        boolean jobExists = scheduler.getJobDetail(jobKey) != null;
        if (jobExists) {
            throw new RuntimeException(String.format("%s already exists!", jobId));
        }

        TriggerKey triggerKey = new TriggerKey(simpleQuartzTrigger.getTriggerName(), simpleQuartzTrigger.getTriggerGroup());
        boolean triggerExists = (scheduler.getTrigger(triggerKey) != null);
        if (triggerExists) {
            throw new RuntimeException(String.format("%s already exists!", scheduleId));
        }

        Object targetBean = SpringBeanHelper.getInstance().getBean(targetBeanName);
        if (targetBean == null) {
            throw new RuntimeException(
                    String.format("%s targetBeanName=%s bean Non-existent!", scheduleId, targetBeanName));
        }
        checkMethod(scheduleId, targetBean, targetMethod, schedulerArguments);

        JobDetail jobDetail = JobBuilder.newJob().withIdentity(jobKey).withDescription(quartzJob.getDescription()).ofType(MethodInvokingJob.class)
                .usingJobData("targetBeanName", targetBeanName).usingJobData("targetMethod", targetMethod).build();
        if (schedulerArguments != null) {
            jobDetail.getJobDataMap().put(SCHEDULER_ARGUMENTS_KEY, schedulerArguments);
        }

        TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(simpleQuartzTrigger.getDescription())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMilliseconds(simpleQuartzTrigger.getRepeatInterval())
                        .withRepeatCount(simpleQuartzTrigger.getRepeatCount()).withMisfireHandlingInstructionIgnoreMisfires()
                );
        if (simpleQuartzTrigger.getStartTime() != null) {
            triggerBuilder.startAt(simpleQuartzTrigger.getStartTime());
        }
        if (simpleQuartzTrigger.getEndTime() != null) {
            triggerBuilder.endAt(simpleQuartzTrigger.getEndTime());
        }

        Trigger trigger = triggerBuilder.build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public static void scheduleCronJob(Scheduler scheduler, String targetBeanName,
                                       String targetMethod, QuartzJob quartzJob, CronQuartzTrigger cronQuartzTrigger) throws SchedulerException {
        scheduleCronJob(scheduler, targetBeanName, targetMethod, quartzJob, cronQuartzTrigger, null);
    }

    public static void scheduleCronJob(Scheduler scheduler, String targetBeanName,
                                       String targetMethod, QuartzJob quartzJob, CronQuartzTrigger cronQuartzTrigger, ISchedulerArguments schedulerArguments) throws SchedulerException {

        String jobId = String.format("job(name=%s,group=%s)", quartzJob.getJobName(), quartzJob.getJobGroup());
        String triggerId = String.format("trigger(name=%s,group=%s)", cronQuartzTrigger.getTriggerName(), cronQuartzTrigger.getTriggerGroup());
        String scheduleId = String.format("schedule(%s,%s)", jobId, triggerId);

        JobKey jobKey = new JobKey(quartzJob.getJobName(), quartzJob.getJobGroup());
        boolean jobExists = scheduler.getJobDetail(jobKey) != null;
        if (jobExists) {
            throw new RuntimeException(String.format("%s already exists!", jobId));
        }

        TriggerKey triggerKey = new TriggerKey(cronQuartzTrigger.getTriggerName(), cronQuartzTrigger.getTriggerGroup());
        boolean triggerExists = (scheduler.getTrigger(triggerKey) != null);
        if (triggerExists) {
            throw new RuntimeException(String.format("%s already exists!", scheduleId));
        }

        Object targetBean = SpringBeanHelper.getInstance().getBean(targetBeanName);
        if (targetBean == null) {
            throw new RuntimeException(
                    String.format("%s targetBeanName=%s bean Non-existent!", scheduleId, targetBeanName));
        }
        checkMethod(scheduleId, targetBean, targetMethod, schedulerArguments);

        JobDetail jobDetail = JobBuilder.newJob().withIdentity(jobKey).withDescription(quartzJob.getDescription()).ofType(MethodInvokingJob.class)
                .usingJobData("targetBeanName", targetBeanName).usingJobData("targetMethod", targetMethod).build();
        if (schedulerArguments != null) {
            jobDetail.getJobDataMap().put(SCHEDULER_ARGUMENTS_KEY, schedulerArguments);
        }

        TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(cronQuartzTrigger.getDescription())
                .withSchedule(CronScheduleBuilder.cronSchedule(cronQuartzTrigger.getCronExpression()).withMisfireHandlingInstructionDoNothing());

        if (cronQuartzTrigger.getStartTime() != null) {
            triggerBuilder.startAt(cronQuartzTrigger.getStartTime());
        }
        if (cronQuartzTrigger.getEndTime() != null) {
            triggerBuilder.endAt(cronQuartzTrigger.getEndTime());
        }

        Trigger trigger = triggerBuilder.build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    private static void checkMethod(String scheduleId, Object targetBean, String targetMethod,
                                    ISchedulerArguments schedulerArguments) {
        try {
            Method method = null;
            if (schedulerArguments == null) {
                method = targetBean.getClass().getMethod(targetMethod);
            } else {
                method = targetBean.getClass().getMethod(targetMethod, schedulerArguments.parameterTypes());
            }
            if (method == null) {
                throw new RuntimeException(String.format("%s targetBeanName=%s,targetMethod=%s bean Non-existent!", scheduleId, targetMethod));
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format("%s targetBeanName=%s,targetMethod=%s bean Non-existent!Exception:%s", scheduleId, targetMethod, e.getMessage()));
        }
    }
}
