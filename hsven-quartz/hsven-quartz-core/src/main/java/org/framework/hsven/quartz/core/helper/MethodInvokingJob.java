package org.framework.hsven.quartz.core.helper;

import org.framework.hsven.quartz.core.api.ISchedulerArguments;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.support.ArgumentConvertingMethodInvoker;
import org.springframework.scheduling.quartz.JobMethodInvocationFailedException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.MethodInvoker;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;

public final class MethodInvokingJob extends QuartzJobBean {
    public final static String SCHEDULER_ARGUMENTS_KEY = "SCHEDULER_ARGUMENTS_KEY";
    public final static String JOB_targetBeanName = "targetBeanName";
    public final static String JOB_targetMethod = "targetMethod";

    private final static Logger logger = LoggerFactory.getLogger(MethodInvokingJob.class);

    /**
     * Invoke the method via the MethodInvoker.
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        MethodInvoker methodInvoker = new ArgumentConvertingMethodInvoker();
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        //设置需要的key参数
        String targetBeanName = jobDataMap.get(JOB_targetBeanName) == null ? null : ((String) jobDataMap.get(JOB_targetBeanName));
        String targetMethod = jobDataMap.get(JOB_targetMethod) == null ? null : ((String) jobDataMap.get(JOB_targetMethod));
        if (StringUtils.isEmpty(targetBeanName) || StringUtils.isEmpty(targetMethod)) {
            String jobName = context.getJobDetail().getKey().getName();
            throw new JobExecutionException("can't obtain jobName=" + jobName + " to targetBeanName=[" + targetBeanName + "] and targetMethod=[" + targetMethod + "]");
        }

        if (SpringBeanHelper.getInstance() == null) {
            String jobName = context.getJobDetail().getKey().getName();
            throw new JobExecutionException("can't obtain jobName=" + jobName + " to spring bean " + targetBeanName);
        }
        methodInvoker.setTargetObject(SpringBeanHelper.getInstance().getBean(targetBeanName));
        methodInvoker.setTargetMethod(targetMethod);
        if (jobDataMap.containsKey(SCHEDULER_ARGUMENTS_KEY)) {
            ISchedulerArguments schedulerArguments = (ISchedulerArguments) jobDataMap.get(SCHEDULER_ARGUMENTS_KEY);
            methodInvoker.setArguments(schedulerArguments.arguments());
        }

        try {
            methodInvoker.prepare();
        } catch (ClassNotFoundException | NoSuchMethodException ex) {
            throw new JobMethodInvocationFailedException(methodInvoker, ex);
        }

        try {
            context.setResult(methodInvoker.invoke());
        } catch (InvocationTargetException ex) {
            if (ex.getTargetException() instanceof JobExecutionException) {
                // -> JobExecutionException, to be logged at info level by Quartz
                throw (JobExecutionException) ex.getTargetException();
            } else {
                // -> "unhandled exception", to be logged at error level by Quartz
                throw new JobMethodInvocationFailedException(methodInvoker, ex.getTargetException());
            }
        } catch (Exception ex) {
            // -> "unhandled exception", to be logged at error level by Quartz
            throw new JobMethodInvocationFailedException(methodInvoker, ex);
        }
    }
}
