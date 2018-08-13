/**
 * @FileName: B.java
 * @Package com.asura.test.jobs
 * 
 * @author zhangshaobin
 * @created 2014年12月10日 下午6:25:52
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.test.jobs;


import org.quartz.JobExecutionContext;

/**
 * <p>TODO</p>
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
public class B extends com.asura.framework.quartz.job.AsuraJob {


	@Override
	public void run(JobExecutionContext context) {
		System.out.println("B........" + context);

	}
}
