package com.kevin.excel.exceldemo.date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Jinyugai
 * @description:
 * @date: Create in 17:42 2019/11/23
 * @modified By:
 */
public class DateUtils {
    private static final Logger log = LoggerFactory.getLogger(DateUtils.class);

    /**
     * 定义常量
     **/
    public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SMALL_STR = "yyyy-MM-dd";
    public static final String DATE_POSTFIX_STR = "yyyyMMddHHmmssSSS";
    public static final String TIME_STR = "HH:mm:ss";
    public static final String HOURS_STR = "小时前";
    public static final String MINUTE_STR = "分钟前";
    public static final String DAY_STR = "天前";


    /**
     * 一天的毫秒数
     */
    public static final Long ONE_DAY_MS = 86400000L;

    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate 日期字符串
     */
    public static Date parse(String strDate) {
        return parse(strDate, DATE_FULL_STR);
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            log.error("DateUtils parse is error " + e);
            return null;
        }
    }

    /**
     * 日期转字符串
     *
     * @param date    日期字符串
     * @param pattern 日期格式
     */
    public static String parseDate2String(Date date, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.format(date);
        } catch (Exception e) {
            log.error("DateUtils parse is error " + e);
            return null;
        }
    }

    /**
     * 两个时间比较
     */
    public static int compareDateWithNow(Date date1, Date date2) {
        return date1.compareTo(date2);
    }

    /**
     * 获取前一天时间 yyyy-mm-dd
     */
    public static String getLateDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int day = cal.get(Calendar.DATE);
        cal.set(Calendar.DATE, day - 1);
        SimpleDateFormat df = new SimpleDateFormat(DATE_SMALL_STR);
        return df.format(cal.getTime());
    }

    /**
     * 获取下一天 0点date
     */
    public static Date getNextDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DATE);
        cal.set(Calendar.DATE, day + 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取前n天 0点date
     */
    public static Date getBeforeDay(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DATE);
        cal.set(Calendar.DATE, day - days);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取后n天 0点date
     */
    public static Date getAfterDay(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DATE);
        cal.set(Calendar.DATE, day + days);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取今天 0点date
     */
    public static Date getToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取系统当前时间
     */
    public static String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
        return df.format(new Date());
    }

    /**
     * 获取系统当前时间
     *
     * @param pattern 日期格式
     */
    public static String getNowTime(String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date());
    }

    /**
     * 分钟差
     *
     * @param start
     * @param end
     * @return
     */
    public static String getDifferenceMinutes(Date start, Date end) {
        long startTime = start.getTime();
        long endTime = end.getTime();
        long minutes = (startTime - endTime) / (1000 * 60);
        long hours = 0;
        long day = 0;
        if (minutes > 60) {
            hours = minutes / 60;
            minutes = 0;
        }
        if (hours > 24) {
            day = hours / 24;
            hours = 0;
        }
        if (hours > 0) {
            return hours + HOURS_STR;
        } else if (minutes > 0) {
            return minutes + MINUTE_STR;
        } else if (day > 0) {
            return day + DAY_STR;
        }
        return "刚刚";
    }
}
