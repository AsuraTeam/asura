/**
 * @FileName: TestQuartz.java
 * @Package: com.asura.test
 * @author lig134
 * @created 2017/7/24 下午6:59
 * <p>
 * Copyright 2015 ziroom
 */
package com.asura.test;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author lig134
 * @since 1.0
 * @version 1.0
 */

import com.asura.test.jobs.C;
import org.quartz.*;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;

/**
 * 从当前时间开始，每隔2s执行一次JobImpl#execute()
 * @author hlx
 */
public class TestQuartz {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // 创建调度器
        Scheduler scheduler = SchedulerExecutor.getScheduler("zmc-rent");

        // 创建任务
        JobDetail jobDetail = JobBuilder.newJob(C.class).withIdentity("myJob1", "jobGroup").build();

        // 创建触发器
        // withIntervalInSeconds(2)表示每隔2s执行任务
        Date triggerDate = new Date();
//        SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever();
        TriggerBuilder<Trigger> triggerBuilder  = TriggerBuilder.newTrigger().withIdentity("myTrigger1", "triggerGroup1");
        Trigger trigger = triggerBuilder.startAt(triggerDate).withSchedule(cronSchedule("* * * * * ?")).build();

        // 将任务及其触发器放入调度器
        scheduler.scheduleJob(jobDetail, trigger);
        // 调度器开始调度任务
        scheduler.start();
    }
}