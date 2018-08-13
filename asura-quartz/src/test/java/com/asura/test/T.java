/**
 * @FileName: T.java
 * @Package com.asura.test18
 * @author zhangshaobin
 * @created 2014年12月2日 下午11:30:38
 * <p>
 * Copyright 2011-2015 asura
 */
package com.asura.test;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.commons.json.Json;
import com.asura.framework.quartz.job.annotation.AsuraSchedule;
import com.asura.test.jobs.B;
import com.asura.test.jobs.C;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import static org.quartz.CronScheduleBuilder.cronSchedule;

/**
 * <p>TODO</p>
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
public class T {

    private static Logger logger = LoggerFactory.getLogger(T.class);

    private static String addTrigger(String triggerName, String triggerGroup, String cronExpression, String paramJson,
                                     Class<? extends Job> jobClass) throws SchedulerException, ParseException {
        Trigger trigger = null;
        try {
            TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
            JobKey jobKey = new JobKey(jobClass.getCanonicalName(), Scheduler.DEFAULT_GROUP);
            if (!Check.NuNStr(triggerGroup) && !Check.NuNStr(cronExpression)) {
                trigger = SchedulerExecutor.getScheduler("zmc-rent").getTrigger(triggerKey);
            } else {
                logger.info("triggerGroup or cronExpression can not null");
                return "";
            }

            if (trigger == null) {// 判断是否有重复的触发器
                JobDataMap jobDataMap = new JobDataMap();
                if (!Check.NuNStr(paramJson)) {
                    jobDataMap.put("param", paramJson);
                }
                JobDetail jobDetail = JobBuilder.newJob(jobClass)
                        .withIdentity(jobKey)
                        .setJobData(jobDataMap).build();
                CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey).usingJobData(jobDataMap)
                        .startAt(new Date())
                        .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                        .build();

//                SchedulerExecutor.getScheduler("zmc-rent").addJob(jobDetail, true);
//                SchedulerExecutor.getScheduler("zmc-rent").scheduleJob(cronTrigger);
//                SchedulerExecutor.getScheduler("zmc-rent").rescheduleJob(triggerKey, cronTrigger);
                Scheduler scheduler = null;
                scheduler=SchedulerExecutor.getScheduler("zmc-rent");
                scheduler.scheduleJob(jobDetail, cronTrigger);

                System.out.println("Job add success, triggerName :" + triggerName + "triggerGroup :" + triggerGroup);
            } else {
                System.out.println("Job already exists");
            }
        } catch (SchedulerException e) {
            logger.error("", e);

        }
        return null;
    }

    private static boolean deleteTrigger(String triggerName, String triggerGroup) {
        try {
            SchedulerExecutor.getScheduler("zmc-rent").pauseTrigger(new TriggerKey(triggerName, triggerGroup));// 停止触发器
            return SchedulerExecutor.getScheduler("zmc-rent").unscheduleJob(new TriggerKey(triggerName, triggerGroup));// 移除触发器
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }


    private static void updateJob(String triggerName, String triggerGroup) {
        try {
            JobDetail jobDetail = SchedulerExecutor.getScheduler("zmc-rent").getJobDetail(new JobKey(triggerName, Scheduler.DEFAULT_GROUP));
            boolean bol = deleteTrigger(triggerName, triggerGroup);
            // 新增新的触发器
//            jobDetail.getJobDataMap().put("param","haha 新参数设置");
//            updateJobDetail(jobDetail);
            if (bol) {
                ParamRequest paramRequest = new ParamRequest();
                paramRequest.set("11", "test1111", "haha");

                triggerName = addTrigger(triggerName, triggerGroup, "0/10 * * * * ?", Json.toJsonString(paramRequest), jobDetail.getJobClass());
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws SchedulerException, ParseException {
//        Scheduler sc = SchedulerExecutor.getScheduler("zmc-rent");
        deleteTrigger("triggerB", "groupB");
        addTrigger("triggerB", "groupB", "0/10 * * * * ?", "aaaa", B.class);

//        updateJob("triggerB1", "groupB1");
    }
}
