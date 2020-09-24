package org.framework.hsven.quartz.standalone.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.framework.hsven.quartz.core.api.ISchedulerOperatorService;
import org.framework.hsven.quartz.core.entity.JobIdentity;
import org.framework.hsven.quartz.core.entity.ResponseScheduler;
import org.framework.hsven.quartz.core.helper.SpringBeanHelper;
import org.framework.hsven.quartz.standalone.entity.CronScheduleParam;
import org.framework.hsven.quartz.standalone.entity.SimpleScheduleParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Quartz任务Restful操作")
@RequestMapping("/scheduler")
@RestController("schedulerController")
public class StandaloneSchedulerController {
    private static Logger logger = LoggerFactory.getLogger(StandaloneSchedulerController.class);

    @Autowired
    private ISchedulerOperatorService standaloneSchedulerOperatorService;


    @ApiOperation(value = "/scheduler/get", notes = "Helper列出当前任务", httpMethod = "GET", response = ResponseScheduler.class)
    @GetMapping("/get")
    public ResponseScheduler get() {
        ISchedulerOperatorService iSchedulerOperatorService = SpringBeanHelper.getInstance().getBean(ISchedulerOperatorService.class);
        ResponseScheduler result = iSchedulerOperatorService.listJob();
        return result;
    }

    @ApiOperation(value = "/scheduler/list", notes = "Autowired列出当前任务", httpMethod = "GET", response = ResponseScheduler.class)
    @GetMapping("/list")
    public ResponseScheduler listJob() {
        return standaloneSchedulerOperatorService.listJob();
    }

    @ApiOperation(value = "/cron/job", notes = "添加日程周期任务", httpMethod = "POST", response = ResponseScheduler.class)
    @PostMapping("/cron/job")
    public ResponseScheduler scheduleCronJob(
            @ApiParam(required = true, name = "scheduleParam", value = "定制的任务信息")
            @RequestBody CronScheduleParam scheduleParam) {
        return standaloneSchedulerOperatorService.scheduleJob(
                scheduleParam.getTargetBeanName(),
                scheduleParam.getTargetMethod(),
                scheduleParam.getTargetMethodArgmuents(),
                scheduleParam.getQuartzJob(),
                scheduleParam.getCronQuartzTrigger());
    }

    @ApiOperation(value = "/delete/job", notes = "删除指定任务", httpMethod = "DELETE", response = ResponseScheduler.class)
    @DeleteMapping("/delete/job")
    public ResponseScheduler deleteJob(@ApiParam(required = true, name = "jobIdentity", value = "任务id信息")
                                       @RequestBody JobIdentity jobIdentity) {
        return standaloneSchedulerOperatorService.deleteJob(jobIdentity);
    }

    @ApiOperation(value = "/simple/job", notes = "添加简单周期任务", httpMethod = "POST", response = ResponseScheduler.class)
    @PostMapping("/simple/job")
    public ResponseScheduler scheduleSimpleJob(@ApiParam(required = true, name = "scheduleParam", value = "定制的任务信息")
                                               @RequestBody SimpleScheduleParam scheduleParam) {
        return standaloneSchedulerOperatorService.scheduleJob(
                scheduleParam.getTargetBeanName(),
                scheduleParam.getTargetMethod(),
                scheduleParam.getTargetMethodArgmuents(),
                scheduleParam.getQuartzJob(),
                scheduleParam.getSimpleQuartzTrigger());
    }
}
