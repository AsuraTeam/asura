/**
 * @FileName: DateUtils.java
 * @Package: com.asura.framework.commons.date
 * @author liusq23
 * @created 2017/4/22 下午4:54
 * <p>
 * Copyright 2015 ziroom
 */
package com.asura.framework.commons.date;

import com.asura.framework.commons.util.Check;
import com.google.common.annotations.Beta;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * <p>
 * 基本是封装了jodaTime的 DateTime类，使用类流式api
 * </p>
 * <p>
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
@Beta
public class DateUtils {


    private DateUtils() {

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private DateTime dateTime;

        public Builder withCurrentDate() {
            dateTime = new DateTime(new Date());
            return this;
        }

        public Builder withDate(Date date) {
            dateTime = new DateTime(date);
            return this;
        }

        public Builder plusYears(int years) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            if (years == 0) {
                return this;
            }
            dateTime = dateTime.plusYears(years);
            return this;
        }

        public Builder withYear(int year) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.withYear(year);
            return this;
        }

        public Builder plusMonths(int months) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            if (months == 0) {
                return this;
            }
            dateTime = dateTime.plusMonths(months);
            return this;
        }

        public Builder withMonthOfYear(int month) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.withMonthOfYear(month);
            return this;
        }

        public Builder plusWeeks(int weeks) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            if (weeks == 0) {
                return this;
            }
            dateTime = dateTime.plusWeeks(weeks);
            return this;
        }

        public Builder withWeekOfWeekyear(int week) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.withWeekOfWeekyear(week);
            return this;
        }

        public Builder plusDays(int days) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            if (days == 0) {
                return this;
            }
            dateTime = dateTime.plusDays(days);
            return this;
        }

        public Builder withDayOfMonth(int dayOfMonth) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.withDayOfMonth(dayOfMonth);
            return this;
        }

        public Builder plusHours(int hours) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            if (hours == 0) {
                return this;
            }
            dateTime = dateTime.plusHours(hours);
            return this;
        }

        public Builder withHourOfDay(int hourOfDay) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.withHourOfDay(hourOfDay);
            return this;
        }

        public Builder plusMinutes(int minutes) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            if (minutes == 0) {
                return this;
            }
            dateTime = dateTime.plusMinutes(minutes);
            return this;
        }

        public Builder withMinuteOfHour(int minuteOfHour) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.withMinuteOfHour(minuteOfHour);
            return this;
        }

        public Builder plusSeconds(int seconds) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            if (seconds == 0) {
                return this;
            }
            dateTime = dateTime.plusSeconds(seconds);
            return this;
        }

        public Builder withSecondOfMinute(int secondOfMinute) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.withSecondOfMinute(secondOfMinute);
            return this;
        }

        public Builder plusMillis(int millis) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            if (millis == 0) {
                return this;
            }
            dateTime = dateTime.plusMillis(millis);
            return this;
        }

        public Builder withMillisOfSecond(int millisOfSecond) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.withMillisOfSecond(millisOfSecond);
            return this;
        }

        public Builder plus(long duration) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            if (duration == 0) {
                return this;
            }
            dateTime = dateTime.plus(duration);
            return this;
        }

        public Builder with(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute, int milliOfSecond) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.withYear(year)
                    .withMonthOfYear(monthOfYear)
                    .withDayOfMonth(dayOfMonth)
                    .withHourOfDay(hourOfDay)
                    .withMinuteOfHour(minuteOfHour)
                    .withSecondOfMinute(secondOfMinute)
                    .withMillisOfSecond(milliOfSecond);
            return this;
        }

        public Builder with(int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute, int milliOfSecond) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.withMonthOfYear(monthOfYear)
                    .withDayOfMonth(dayOfMonth)
                    .withHourOfDay(hourOfDay)
                    .withMinuteOfHour(minuteOfHour)
                    .withSecondOfMinute(secondOfMinute)
                    .withMillisOfSecond(milliOfSecond);
            return this;
        }

        public Builder with(int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute, int milliOfSecond) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.withDayOfMonth(dayOfMonth)
                    .withHourOfDay(hourOfDay)
                    .withMinuteOfHour(minuteOfHour)
                    .withSecondOfMinute(secondOfMinute)
                    .withMillisOfSecond(milliOfSecond);
            return this;
        }

        public Builder withLastDayOfWeek() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.dayOfWeek().withMaximumValue();
            return this;
        }

        public Builder withFirstDayOfWeek() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.dayOfWeek().withMinimumValue();
            return this;
        }

        public Builder withLastMillsOfDay() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.millisOfDay().withMaximumValue();
            return this;
        }

        public Builder withFirstMillsOfDay() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.millisOfDay().withMinimumValue();
            return this;
        }

        public Builder withLastDayOfMonth() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.dayOfMonth().withMaximumValue();
            return this;
        }

        public Builder withFirstDayOfMonth() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.dayOfMonth().withMinimumValue();
            return this;
        }

        public Builder with(int hourOfDay, int minuteOfHour, int secondOfMinute, int milliOfSecond) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.withHourOfDay(hourOfDay)
                    .withMinuteOfHour(minuteOfHour)
                    .withSecondOfMinute(secondOfMinute)
                    .withMillisOfSecond(milliOfSecond);
            return this;
        }


        public Builder with(int minuteOfHour, int secondOfMinute, int milliOfSecond) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            dateTime = dateTime.withMinuteOfHour(minuteOfHour)
                    .withSecondOfMinute(secondOfMinute)
                    .withMillisOfSecond(milliOfSecond);
            return this;
        }

        public int getYear() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            return dateTime.getYear();
        }

        public int getMonthOfYear() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            return dateTime.getMonthOfYear();
        }

        public int getDayOfYear() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            return dateTime.getDayOfYear();
        }

        public int getDayOfMonth() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            return dateTime.getDayOfMonth();
        }

        public int getDayOfWeek() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            return dateTime.getDayOfWeek();
        }

        public int getHourOfDay() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            return dateTime.getHourOfDay();
        }

        public int getMinuteOfHour() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            return dateTime.getMinuteOfHour();
        }

        public int getSecondOfMinute() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            return dateTime.getSecondOfMinute();
        }

        public int getMillsOfSecond() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            return dateTime.getMillisOfSecond();
        }

        public Date getDate() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            return dateTime.toDate();
        }

        public long getMillis() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            return dateTime.getMillis();
        }

        public String formatDate() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            return DateFormatter.formatDate(dateTime.toDate());
        }

        public String formatDateTime() {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            return DateFormatter.formatDateTime(dateTime.toDate());
        }

        public String format(String datePattern) {
            if (Check.isNull(dateTime)) {
                withCurrentDate();
            }
            return DateFormatter.format(dateTime.toDate(), datePattern);
        }
    }

}
