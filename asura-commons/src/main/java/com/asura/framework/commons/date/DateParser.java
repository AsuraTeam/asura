/**
 * @FileName: DateParser.java
 * @Package: com.asura.framework.commons.date
 * @author liusq23
 * @created 11/1/2016 5:44 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.asura.framework.commons.date;

import com.asura.framework.commons.util.Check;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

/**
 * <p>
 * 解析各种Date字符成为JDK Date类型
 * 基于joda-time解析
 * </p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liusq23
 * @version 1.0
 * @since 1.0
 */
public class DateParser {
    /**
     * 私有化构造
     */
    private DateParser(){

    }

    /**
     * 按照默认的日期格式(yyyy-MM-dd)转换为日期
     *
     * @param dateStr
     * @return
     */
    public static Date parseDate(@Nullable String dateStr) {
        return parse(dateStr, DatePattern.DEFAULT_FORMAT_DATE_PATTERN);
    }


    /**
     * 按照默认的日期格式(yyyy-MM-dd HH:mm:ss)转换为日期
     *
     * @param dateStr
     * @return
     */
    public static Date parseDateTime(@Nullable String dateStr) {
        return parse(dateStr, DatePattern.DEFAULT_FORMAT_DATETIME_PATTERN);
    }


    /**
     * 按照指定格式匹配解析字符串为日期
     * 如果需要解析的字符串为空或者null 则返回的null
     * @param dateStr
     * @param pattern
     * @return
     */
    public static
    @Nullable
    Date parse(@Nullable String dateStr, @NotNull String pattern) {
        return parse(dateStr, pattern, TimeZone.getTimeZone("Etc/GMT-8"));
    }

    /**
     * 按照指定格式匹配解析字符串为日期
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parse(@NotNull String dateStr, @NotNull String pattern, TimeZone timeZone) {
        if (Check.isNullOrEmpty(dateStr)) {
            return null;
        }
        Objects.requireNonNull(pattern, "date pattern must not null");
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(pattern).withZone(DateTimeZone.forTimeZone(timeZone));
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateStr);
        return dateTime.toDate();
    }


}
