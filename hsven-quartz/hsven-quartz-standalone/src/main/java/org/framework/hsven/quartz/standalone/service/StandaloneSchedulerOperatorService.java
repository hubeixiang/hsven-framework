package org.framework.hsven.quartz.standalone.service;

import org.framework.hsven.quartz.core.api.ISchedulerArguments;
import org.framework.hsven.quartz.core.api.ISchedulerOperatorService;
import org.framework.hsven.quartz.core.entity.CronQuartzTrigger;
import org.framework.hsven.quartz.core.entity.JobIdentity;
import org.framework.hsven.quartz.core.entity.QuartzJob;
import org.framework.hsven.quartz.core.entity.QuartzTrigger;
import org.framework.hsven.quartz.core.entity.ResponseScheduler;
import org.framework.hsven.quartz.core.entity.SimpleQuartzTrigger;
import org.framework.hsven.quartz.core.entity.TriggerIdentity;
import org.framework.hsven.quartz.core.helper.SchedulerQuartzHelper;
import org.framework.hsven.quartz.standalone.entity.JobInfo;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class StandaloneSchedulerOperatorService implements ISchedulerOperatorService {
    private static Logger logger = LoggerFactory.getLogger(StandaloneSchedulerOperatorService.class);

    @Autowired
    private Scheduler scheduler;

    private JobKey jobKey(JobIdentity jobIdentity) {
        return JobKey.jobKey(jobIdentity.getName(), jobIdentity.getGroup());
    }

    private String jobId(JobIdentity jobIdentity) {
        return String.format("job(name=%s,group=%s)", jobIdentity.getName(), jobIdentity.getGroup());
    }

    private TriggerKey triggerKey(TriggerIdentity triggerIdentity) {
        return TriggerKey.triggerKey(triggerIdentity.getName(), triggerIdentity.getGroup());
    }

    private String triggerId(TriggerIdentity triggerIdentity) {
        return String.format("trigger(name=%s,group=%s)", triggerIdentity.getName(), triggerIdentity.getGroup());
    }

    @Override
    public ResponseScheduler listJob() {
        ResponseScheduler responseScheduler = new ResponseScheduler();
        try {
            Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.anyJobGroup());
            responseScheduler.setCode(ResponseScheduler.CODE_OK);
            final List<JobInfo> jobDetails = new ArrayList<>();
            final List<TriggerIdentity> allTriggers = new ArrayList<>();
            final List<Trigger> allTriggersStatues = new ArrayList<>();
            final List<Map> allStates = new ArrayList<>();
            for (JobKey jobKey : jobKeys) {
                JobInfo jobInfo = new JobInfo();
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                jobInfo.setName(jobKey.getName());
                jobInfo.setGroup(jobKey.getGroup());
                jobInfo.setParam(jobDetail.getJobDataMap());

                jobDetails.add(jobInfo);

                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                allTriggersStatues.addAll(triggers);
                List<TriggerIdentity> triggerIdentities = new ArrayList<>();
                if (triggers != null) {
                    for (Trigger trigger : triggers) {
                        TriggerIdentity triggerIdentity = new TriggerIdentity();
                        triggerIdentity.setName(trigger.getKey().getName());
                        triggerIdentity.setGroup(trigger.getKey().getGroup());
                        triggerIdentities.add(triggerIdentity);
                    }
                }
                allTriggers.addAll(triggerIdentities);
            }
            for (final Trigger trigger : allTriggersStatues) {
                final Trigger.TriggerState state = scheduler.getTriggerState(trigger.getKey());
                //noinspection serial
                allStates.add(new HashMap<String, Object>() {
                    {
                        put("key", trigger.getKey());
                        put("state", state);
                    }
                });
            }
            //noinspection serial
            responseScheduler.setData(new HashMap<String, Object>() {
                {
                    put("jobs", jobDetails);
                    put("triggers", allTriggers);
                    put("states", allStates);
                }
            });
        } catch (Exception e) {
            logger.error(String.format("list jobs exception:%s", e.getMessage()), e);
            responseScheduler.setCode(ResponseScheduler.CODE_INTERNAL_ERROR);
            responseScheduler.setError("Fail to get jobs.");
        }
        return responseScheduler;
    }

    @Override
    public ResponseScheduler pauseJob(JobIdentity jobIdentity) {
        String operator = "pause";
        ResponseScheduler responseScheduler;
        try {
            scheduler.pauseJob(jobKey(jobIdentity));
            responseScheduler = new ResponseScheduler(ResponseScheduler.CODE_OK);
        } catch (Exception e) {
            logger.error(String.format("scheduler %s %s exception:%s", operator, jobId(jobIdentity), e.getMessage()), e);
            responseScheduler = new ResponseScheduler(ResponseScheduler.CODE_INTERNAL_ERROR);
            responseScheduler.setError(String.format("%s %s exception:%s", operator, jobId(jobIdentity), e.getMessage()));
        }
        return responseScheduler;
    }

    @Override
    public ResponseScheduler pauseTrigger(TriggerIdentity triggerIdentity) {
        String operator = "pause";
        ResponseScheduler responseScheduler;
        try {
            scheduler.pauseTrigger(triggerKey(triggerIdentity));
            responseScheduler = new ResponseScheduler(ResponseScheduler.CODE_OK);
        } catch (Exception e) {
            logger.error(String.format("scheduler %s %s exception:%s", operator, triggerId(triggerIdentity), e.getMessage()), e);
            responseScheduler = new ResponseScheduler(ResponseScheduler.CODE_INTERNAL_ERROR);
            responseScheduler.setError(String.format("%s %s exception:%s", operator, triggerId(triggerIdentity), e.getMessage()));
        }
        return responseScheduler;
    }

    @Override
    public ResponseScheduler resumeJob(JobIdentity jobIdentity) {
        String operator = "resume";
        ResponseScheduler responseScheduler;
        try {
            scheduler.resumeJob(jobKey(jobIdentity));
            responseScheduler = new ResponseScheduler(ResponseScheduler.CODE_OK);
        } catch (Exception e) {
            logger.error(String.format("scheduler %s %s exception:%s", operator, jobId(jobIdentity), e.getMessage()), e);
            responseScheduler = new ResponseScheduler(ResponseScheduler.CODE_INTERNAL_ERROR);
            responseScheduler.setError(String.format("%s %s exception:%s", operator, jobId(jobIdentity), e.getMessage()));
        }
        return responseScheduler;
    }

    @Override
    public ResponseScheduler resumeTrigger(TriggerIdentity triggerIdentity) {
        String operator = "resume";
        ResponseScheduler responseScheduler;
        try {
            scheduler.resumeTrigger(triggerKey(triggerIdentity));
            responseScheduler = new ResponseScheduler(ResponseScheduler.CODE_OK);
        } catch (Exception e) {
            logger.error(String.format("scheduler %s %s exception:%s", operator, triggerId(triggerIdentity), e.getMessage()), e);
            responseScheduler = new ResponseScheduler(ResponseScheduler.CODE_INTERNAL_ERROR);
            responseScheduler.setError(String.format("%s %s exception:%s", operator, triggerId(triggerIdentity), e.getMessage()));
        }
        return responseScheduler;
    }

    @Override
    public ResponseScheduler deleteJob(JobIdentity jobIdentity) {
        String operator = "delete";
        ResponseScheduler responseScheduler;
        try {
            scheduler.deleteJob(jobKey(jobIdentity));
            responseScheduler = new ResponseScheduler(ResponseScheduler.CODE_OK);
        } catch (Exception e) {
            logger.error(String.format("scheduler %s %s exception:%s", operator, jobId(jobIdentity), e.getMessage()), e);
            responseScheduler = new ResponseScheduler(ResponseScheduler.CODE_INTERNAL_ERROR);
            responseScheduler.setError(String.format("%s %s exception:%s", operator, jobId(jobIdentity), e.getMessage()));
        }
        return responseScheduler;
    }

    @Override
    public ResponseScheduler scheduleJob(@NonNull String targetBeanName, @NonNull String targetMethod, ISchedulerArguments targetMethodArgmuents, @NonNull QuartzJob quartzJob, @NonNull QuartzTrigger quartzTrigger) {
        String operator = "schedule";
        ResponseScheduler responseScheduler;
        try {
            if (quartzTrigger instanceof CronQuartzTrigger) {
                CronQuartzTrigger cronQuartzTrigger = (CronQuartzTrigger) quartzTrigger;
                if (targetMethodArgmuents == null) {
                    SchedulerQuartzHelper.scheduleCronJob(scheduler, targetBeanName, targetMethod, quartzJob, cronQuartzTrigger);
                } else {
                    SchedulerQuartzHelper.scheduleCronJob(scheduler, targetBeanName, targetMethod, quartzJob, cronQuartzTrigger, targetMethodArgmuents);
                }
            } else if (quartzTrigger instanceof SimpleQuartzTrigger) {
                SimpleQuartzTrigger simpleQuartzTrigger = (SimpleQuartzTrigger) quartzTrigger;
                if (targetMethodArgmuents == null) {
                    SchedulerQuartzHelper.scheduleCronJob(scheduler, targetBeanName, targetMethod, quartzJob, simpleQuartzTrigger);
                } else {
                    SchedulerQuartzHelper.scheduleCronJob(scheduler, targetBeanName, targetMethod, quartzJob, simpleQuartzTrigger, targetMethodArgmuents);
                }
            }
            responseScheduler = new ResponseScheduler(ResponseScheduler.CODE_OK);
        } catch (Throwable e) {
            String jobId = String.format("job(name=%s,group=%s)", quartzJob.getJobName(), quartzJob.getJobGroup());
            String triggerId = String.format("trigger(name=%s,group=%s)", quartzTrigger.getTriggerName(), quartzTrigger.getTriggerGroup());
            String jobIdentity = String.format("%s,%s", jobId, triggerId);
            logger.error(String.format("%s %s exception:%s", operator, jobIdentity, e.getMessage()), e);
            responseScheduler = new ResponseScheduler(ResponseScheduler.CODE_INTERNAL_ERROR);
            responseScheduler.setError(String.format("%s %s exception:%s", operator, jobIdentity, e.getMessage()));
        }
        return responseScheduler;
    }
}
