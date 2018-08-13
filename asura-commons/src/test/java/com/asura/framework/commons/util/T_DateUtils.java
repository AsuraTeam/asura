/**
 * @FileName: T_DateUtils.java
 * @Package: com.asura.framework.commons.util
 * @author liusq23
 * @created 2017/4/22 下午5:37
 * <p>
 * Copyright 2015 ziroom
 */
package com.asura.framework.commons.util;

import com.asura.framework.commons.date.DateParser;
import com.asura.framework.commons.date.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liusq23
 * @since 1.0
 * @version 1.0
 */
public class T_DateUtils {

    @Test
    public void t_date_01(){
        String d = DateUtils.builder().withFirstDayOfMonth().withFirstMillsOfDay().formatDateTime();
        System.out.println(d);
    }

    @Test
    public void t_date_02(){
        String d = DateUtils.builder().withFirstDayOfMonth().withLastMillsOfDay().formatDateTime();
        System.out.println(d);
    }

    @Test
    public void t_date_03() {
        int d = DateUtils.builder().withCurrentDate().getHourOfDay();
        System.out.println(d);
    }

    @Test
    public void t_date_04() {
        Date originDate = new Date();
        Date d = DateUtils.builder().withDate(originDate).plusMinutes(-24 * 60).getDate();
        Assert.assertEquals(d.getTime(), DateUtils.builder().withDate(originDate).plusDays(-1).getDate().getTime());
    }

    @Test
    public void t_date_05() {
        Date d = DateParser.parse("19860504", "yyyyMMdd");
        Assert.assertNotNull(d);
    }

    @Test
    public void t_date_07() {
        int d = DateUtils.builder().withCurrentDate().plusDays(3).getDayOfWeek();
        System.out.println(d);
        // 获取2018年第5周的周一日期及周日日期
        DateUtils.Builder builder = DateUtils.builder().withYear(2018).withWeekOfWeekyear(5);
        System.out.println(builder.withFirstDayOfWeek().formatDate());
        System.out.println(builder.withLastDayOfWeek().formatDate());
    }

    @Test
    public void t_01(){
        Long d = System.currentTimeMillis();
        System.out.println(d);
        System.out.println(d >>> 8);
        System.out.println(UUIDGeneratorUtil.hexUUID());
        System.out.println(Integer.toBinaryString(-10));
        System.out.println(Integer.toBinaryString(-10 >>> 1));
        System.out.println(Integer.toBinaryString(-10 >> 1));
        List<?>[] ls = new ArrayList<?>[10];
        List<Integer> li = new ArrayList<Integer>();

    }
}
