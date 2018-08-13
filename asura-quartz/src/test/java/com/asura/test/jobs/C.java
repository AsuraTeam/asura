/**
 * @FileName: C.java
 * @Package com.asura.test.jobs
 * @author zhangshaobin
 * @created 2014年12月10日 下午6:28:05
 * <p>
 * Copyright 2011-2015 asura
 */
package com.asura.test.jobs;

import com.asura.framework.commons.json.Json;
import com.asura.test.ParamRequest;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Map;

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
public class C extends AsuraJob {

    @Override
    public void run(JobExecutionContext context) {
//		Map dataMap=context.getJobDetail().getJobDataMap();
        Map dataMap = context.getMergedJobDataMap();
        String json = (String) dataMap.get("param");
//		ParamRequest paramRequest=Json.parseObject(json, ParamRequest.class);
        System.out.println("执行参数为：" + json);
    }
}
