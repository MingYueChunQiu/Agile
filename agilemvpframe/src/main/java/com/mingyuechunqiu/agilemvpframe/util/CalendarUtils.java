package com.mingyuechunqiu.agilemvpframe.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/06/26
 *     desc   : 时间相关的工具类
 *     version: 1.0
 * </pre>
 */
public class CalendarUtils {

    //一天的毫秒数
    public static final long DAY_MILLISECOND = 24 * 60 * 60 * 1000L;

    /**
     * 获取当前时间的秒级别时间戳
     *
     * @return 时间戳字符串
     */
    @NonNull
    public static String getTodaySecondTimeStamp() {
        return getSecondTimeStamp(getTodayTime());
    }

    /**
     * 获取今天的起始时间毫秒数
     *
     * @return 时间毫秒数
     */
    public static long getTodayTime() {
        Calendar calendar = Calendar.getInstance();
        resetTime(calendar);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取秒级别的时间戳
     *
     * @param time 秒数
     * @return 时间戳字符串
     */
    @NonNull
    public static String getSecondTimeStamp(long time) {
        String value = String.valueOf(time);
        return value.substring(0, value.length() - 3);
    }

    /**
     * 根据秒级别的时间戳返回格式化日期字符串
     *
     * @param timestamp 时间戳字符串
     * @return 如果转换成功则返回格式化字符串，否则返回null
     */
    @Nullable
    public static String transformSecondTimestampToDate(String timestamp) {
        if (!checkIsSecondTimestamp(timestamp)) return null;
        long time = Long.parseLong(timestamp);
        return getFormattedDateFromMilliseconds(time * 1000);
    }

    /**
     * 根据秒级别的时间戳返回格式化时间字符串
     *
     * @param timestamp 时间戳字符串
     * @return 如果转换成功则返回格式化字符串，否则返回null
     */
    @Nullable
    public static String transformSecondTimestampToTime(String timestamp) {
        if (!checkIsSecondTimestamp(timestamp)) return null;
        long time = Long.parseLong(timestamp);
        return getFormattedTimeFromMilliseconds(time * 1000);
    }

    /**
     * 根据毫秒格式化出日期
     *
     * @param millisecond 毫秒数
     * @return 如果转换成功则返回格式化日期字符串，否则返回null
     */
    @Nullable
    public static String getFormattedDateFromMilliseconds(long millisecond) {
        //如果毫秒数小于13位，则直接返回
        if (!checkIsMilliseconds(millisecond)) return null;
        Date date = new Date(millisecond);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 根据毫秒格式化出时间
     *
     * @param millisecond 毫秒数
     * @return 如果转换成功则返回格式化时间字符串，否则返回null
     */
    @Nullable
    public static String getFormattedTimeFromMilliseconds(long millisecond) {
        if (!checkIsMilliseconds(millisecond)) return null;
        Date date = new Date(millisecond);
        //hh是12小时制，HH是24小时制
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    /**
     * 获取毫秒数所代表的那天的开始时间戳
     *
     * @param milliseconds 毫秒数
     * @return 返回秒级别的时间戳
     */
    public static String getDateTimestamp(long milliseconds) {
        return getSecondTimeStamp(getDateTime(milliseconds));
    }

    /**
     * 获取毫秒数所在当天的开始时间毫秒数
     *
     * @param milliseconds 毫秒数
     * @return 当天的起始毫秒数
     */
    public static long getDateTime(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        resetTime(calendar);
        return calendar.getTimeInMillis();
    }

    /**
     * 根据年月日获取当天的起始时间
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 当天的起始毫秒数
     */
    public static long getDateTime(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return getDateTime(calendar.getTimeInMillis());
    }

    /**
     * 根据毫秒数返回当天所在的星期索引
     *
     * @param milliseconds 传入的毫秒数
     * @return 如果毫秒数符合要求则返回星期索引，否则返回-1
     */
    public static int getWeekDay(long milliseconds) {
        if (!checkIsMilliseconds(milliseconds)) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取当前时间所在周的第一天开始时间毫秒数
     *
     * @param milliseconds 毫秒数
     * @return 如果获取成功直接返回，否则返回-1
     */
    public static long getFirstWeekDayDate(long milliseconds) {
        if (!checkIsMilliseconds(milliseconds)) {
            return -1;
        }
        int weekday = getWeekDay(milliseconds);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        resetTime(calendar);
        long time = calendar.getTimeInMillis();
        if (weekday == 1) {
            time = time - 6 * DAY_MILLISECOND;
        } else {
            time = time - (weekday - 2) * DAY_MILLISECOND;
        }
        return time;
    }

    /**
     * 判断给定时间是否在指定时间所在的周内
     *
     * @param currentTime 给定需要判断的时间
     * @param weekTime    用于参考所在周的时间
     * @return 如果给定时间在所在周，则返回true，否则返回false
     */
    public static boolean isInWeek(long currentTime, long weekTime) {
        if (!checkIsMilliseconds(currentTime) || !checkIsMilliseconds(weekTime)) {
            return false;
        }
        long time = getFirstWeekDayDate(weekTime);
        if (currentTime >= time && currentTime <= time + 7 * DAY_MILLISECOND) {
            return true;
        }
        return false;
    }

    /**
     * 获取格式化后的天数字符串
     *
     * @param milliseconds 毫秒数
     * @return 如果毫秒数符合规范，则返回格式化字符串，否则返回null
     */
    public static String getFormattedDay(long milliseconds) {
        if (!checkIsMilliseconds(milliseconds)) return null;
        SimpleDateFormat format = new SimpleDateFormat("dd");
        String day = format.format(new Date(milliseconds));
        if (day.length() > 1 && day.substring(0).equals("0")) {
            return day.substring(1, day.length());
        }
        return day;
    }

    /**
     * 返回当天所在的星期索引
     *
     * @param milliseconds 毫秒数
     * @return 星期索引
     */
    public static int getDayOfWeek(long milliseconds) {
        if (!checkIsMilliseconds(milliseconds)) return -1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取每月的第一天时间
     *
     * @param year  年
     * @param month 月
     * @return 时间数
     */
    public static long getFirstDayTimeOfMonth(int year, int month) {
        if (year < 0 || month < 0) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        resetTime(calendar);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取每月的最后一天时间毫秒数
     *
     * @param year  年
     * @param month 月
     * @return 时间数
     */
    public static long getLastDayTimeOfMonth(int year, int month) {
        if (year < 0 || month < 0) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        resetTime(calendar);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取每月的最后一天时间
     *
     * @param year  年
     * @param month 月
     * @return 时间数
     */
    public static int getLastDayOfMonth(int year, int month) {
        if (year < 0 || month < 0) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 检查时间戳是否是秒级别的
     *
     * @param timestamp 时间戳字符串
     * @return 是则返回true，否则返回false
     */
    private static boolean checkIsSecondTimestamp(String timestamp) {
        if (TextUtils.isEmpty(timestamp) || timestamp.length() != 10) {
            return false;
        }
        return true;
    }

    /**
     * 检查毫秒数是否符合位数要求
     *
     * @param millisecond 传入的毫秒数
     * @return 如果符合则返回true，否则返回false
     */
    private static boolean checkIsMilliseconds(long millisecond) {
        if (millisecond < 10 * 12) {
            return false;
        }
        return true;
    }

    /**
     * 重置日历中时间
     *
     * @param calendar 需要重置的日历
     */
    private static void resetTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}
