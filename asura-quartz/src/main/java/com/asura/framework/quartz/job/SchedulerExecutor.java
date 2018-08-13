/**
 * @FileName: SchedulerExecutor.java
 * @Package com.asura.amp.quartz.executor
 * @author zhangshaobin
 * @created 2012-12-18 上午8:11:32
 * <p>
 * Copyright 2011-2015 asura
 */
package com.asura.framework.quartz.job;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * Scheduler操作类
 * </p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class SchedulerExecutor {

	/**
	 * Scheduler对象
	 */
	private static Scheduler scheduler = null;

	private static Map<String,Scheduler> schedulerMap = new HashMap<>();

	private static final String DEFAULT_QUARTZ_CONFIG="quartz.properties";

	private static final String GROUP_NAME_PROPERTIY = "groupName";

	/**
	 * 默认配置 获取Scheduler实例
	 * @return
	 */
	public static Scheduler getScheduler() {
		if (null == scheduler) {
			SchedulerFactory sf;
			try {
				sf = new StdSchedulerFactory(DEFAULT_QUARTZ_CONFIG);
				scheduler = sf.getScheduler();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
		return scheduler;
	}



	/**
	 * 使用properties 获取Scheduler实例
	 * @param properties
	 * @return
	 */
	public static Scheduler getScheduler(Properties properties) {
		String groupName = properties.getProperty(GROUP_NAME_PROPERTIY);
		if (null == schedulerMap.get(groupName)) {
			SchedulerFactory sf;
			try {
				sf = new StdSchedulerFactory(properties);
				scheduler = sf.getScheduler();
				schedulerMap.put(groupName,scheduler);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}else {
			scheduler = schedulerMap.get(groupName);
		}
		return scheduler;
	}
}
