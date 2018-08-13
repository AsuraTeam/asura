/**
 * @FileName: AsuraJob.java
 * @Package com.asura.framework.quartz.job
 * @author zhangshaobin
 * @created 2014年12月10日 下午9:22:54
 * <p>
 * Copyright 2011-2015 asura
 */
package com.asura.framework.quartz.job;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.commons.json.Json;
import com.asura.framework.conf.subscribe.ConfigSubscriber;
import com.asura.framework.quartz.job.annotation.AsuraSchedule;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * <p>可以控制空跑的定时任务</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhangshaobin
 * @version 1.0
 * @since 1.0
 */
public abstract class AsuraJob implements StatefulJob {

    private static final Logger logger = LoggerFactory.getLogger(AsuraJob.class);

    /* (non-Javadoc)
     * @see org.Job#execute(org.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (quartzKP()) {
            logger.info(context.getJobDetail().getJobClass().getName() + " 定时任务空跑.....");
        } else {
            logger.info("JOB: " + context.getJobDetail().getJobClass().getName() + " start .....");
            long start = System.currentTimeMillis();
            run(context);
            long end = System.currentTimeMillis();
            logger.info("JOB: " + context.getJobDetail().getJobClass().getName() + " end ..... 耗时：" + (end - start) + " 毫秒");
        }
    }

    public abstract void run(JobExecutionContext context);

    /**
     * 是否空跑定时任务
     *
     * @return true 空跑   ； false 不空跑
     * @author zhangshaobin
     * @created 2014年12月10日 下午9:32:07
     */
    private boolean quartzKP() {
        String type = EnumSysConfig.asura_quartzKP.getType();
        String code = EnumSysConfig.asura_quartzKP.getCode();
        String value = ConfigSubscriber.getInstance().getConfigValue(type, code);
        if (value == null) {
            value = EnumSysConfig.asura_quartzKP.getDefaultValue();
        }
        if ("on".equals(value)) { // 空跑
            return true;
        } else {
            return false;
        }
    }

    @PostConstruct
    public void addJob() {
        Class<? extends Job> clazz = this.getClass();
        AsuraSchedule annotation = clazz.getAnnotation(AsuraSchedule.class);// 获取Job注解对象
        String triggerName = clazz.getName();
        String triggerGroup = null;
        String cronExpression = null;
        String param = null;
        if (!Check.NuNObj(annotation)) {
            triggerGroup = annotation.triggerGroup();
            cronExpression = annotation.cronTrigger();
            param = annotation.param();
        }
        Class<? extends Job> jobClass = clazz;
        Trigger trigger = null;
        try {
            // triggerGroup必须和scheduleName相等，老的base依赖解决方案
            String scheduleName = SchedulerExecutor.getScheduler().getSchedulerName();
            if (!Check.NuNStrStrict(triggerGroup) && !triggerGroup.equals(scheduleName)) {
                logger.info("Job is not belong this cluster, triggerGroup : {}, scheduleName : {}", triggerGroup, scheduleName);
                return;
            }
            TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
            JobKey jobKey = new JobKey(triggerName, Scheduler.DEFAULT_GROUP);
            if (!Check.NuNStr(triggerGroup) && !Check.NuNStr(cronExpression)) {

                trigger = SchedulerExecutor.getScheduler().getTrigger(triggerKey);
            } else {
                logger.info("triggerGroup or cronExpression can not null");
                return;
            }

            if (trigger == null) {// 判断是否有重复的触发器
                JobDataMap jobDataMap = new JobDataMap();
                if (!Check.NuNStr(param)) {
                    jobDataMap.put("param", Json.parseObject(param, Map.class));
                }
                JobDetail jobDetail = JobBuilder.newJob(jobClass)
                        .withIdentity(jobKey).storeDurably(true)
                        .setJobData(jobDataMap).build();
                CronTrigger cronTrigger = TriggerBuilder.newTrigger().forJob(jobDetail)
                        .withIdentity(triggerKey).usingJobData(jobDataMap)
                        .startAt(DateUtil.intervalDate(10, DateUtil.IntervalUnit.MINUTE))
                        .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                        .build();

                SchedulerExecutor.getScheduler().addJob(jobDetail, true);
                SchedulerExecutor.getScheduler().scheduleJob(cronTrigger);
                SchedulerExecutor.getScheduler().rescheduleJob(triggerKey, cronTrigger);
                logger.info("Job add success, triggerName :" + triggerName + "triggerGroup :" + triggerGroup);
            } else {
                logger.info("Job already exists");
            }
        } catch (SchedulerException e) {
            logger.error("", e);
        }
    }

}
