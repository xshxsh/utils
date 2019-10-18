package com.example.utils.date;


import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @fileName：DateTimeUtils
 * @createTime：2019-7-11 10:39
 * @author：XSH
 * @version：
 * @description：基于JDK8的DateTimeFormatter工具类
 */


public class DateTimeUtils {

    //    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");
    //    public static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
    //    public static final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
    //    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //    public static final DateTimeFormatter SHORT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss");
    //    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    //    public static final DateTimeFormatter LONG_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS");

    private final static ZoneId zoneId = ZoneId.systemDefault();

    /**
     * LocalDateTime转dateTime
     *
     * @param localDateTime
     */
    public static Date LocalToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }

    /**
     * dateTime转LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocal(Date date) {
        return date.toInstant().atZone(zoneId).toLocalDateTime();
    }

    /**
     * localDateTime转Timestamp
     *
     * @param localDateTime
     * @return
     */
    public static Timestamp localToStamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    public static LocalDateTime stampToLocal(Timestamp timestamp) {
        return timestamp.toInstant().atZone(zoneId).toLocalDateTime();
    }

    /**
     * 返回当前的日期
     * yyyy-MM-dd
     */
    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    /**
     * 返回当前的时间
     * HH:mm:ss.SSS
     */
    public static LocalTime getCurrentLocalTime() {
        return LocalTime.now();
    }

    /**
     * 返回当前日期时间
     * yyyy-MM-ddHH:mm:ssSSS
     */
    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 返回当前的指定格式的日期
     *
     * @param formatter
     */
    public static String getCurrentDateStr(DateTimeFormatter formatter) {
        return LocalDate.now().format(formatter);
    }

    /**
     * 返回当前的指定格式的日期时间
     *
     * @param formatter
     */
    public static String getCurrentDateTimeStr(DateTimeFormatter formatter) {
        return LocalDateTime.now().format(formatter);
    }

    /**
     * 返回当前的指定格式的日期
     *
     * @param pattern
     * @return
     */
    public static String getCurrentDateStr(String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }


    /**
     * 返回当前的指定格式的时间
     *
     * @param pattern
     * @return
     */
    public static String getCurrentTimeStr(String pattern) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 返回当前的指定格式的日期时间
     *
     * @param pattern
     * @return
     */
    public static String getCurrentDateTimeStr(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 根据模式返回指定日期时间
     *
     * @param dateTimeStr
     * @param pattern
     * @return
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr, String pattern) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 根据模式返回指定日期
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static LocalDate parseLocalDate(String dateStr, String pattern) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 根据模式返回指定时间
     *
     * @param timeStr
     * @param pattern
     * @return
     */
    public static LocalTime parseLocalTime(String timeStr, String pattern) {
        return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 根据模式返回指定日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatLocalDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 根据模式返回指定日期时间
     *
     * @param datetime
     * @param pattern
     * @return
     */
    public static String formatLocalDateTime(LocalDateTime datetime, String pattern) {
        return datetime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 根据模式返回指定时间
     *
     * @param time
     * @param pattern
     * @return
     */
    public static String formatLocalTime(LocalTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }


    /**
     * 日期相隔秒
     */
    public static long periodHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Duration.between(startDateTime, endDateTime).get(ChronoUnit.SECONDS);
    }

    /**
     * 日期相隔天数
     */
    public static long periodDays(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.DAYS);
    }

    /**
     * 日期相隔周数
     */
    public static long periodWeeks(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.WEEKS);
    }

    /**
     * 日期相隔月数
     */
    public static long periodMonths(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.MONTHS);
    }

    /**
     * 日期相隔年数
     */
    public static long periodYears(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.YEARS);
    }

    /**
     * 是否当天
     */
    public static boolean isToday(LocalDate date) {
        return getCurrentLocalDate().equals(date);
    }

    /**
     * 获取指定日期时间的毫秒数
     */
    public static Long toEpochMilli(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 判断是否为闰年
     */
    public static boolean isLeapYear(LocalDate localDate) {
        return localDate.isLeapYear();
    }

    /**
     * 将Long类型的时间戳转换成String 类型的时间格式
     */
    public static String convertTimeToString(Long time, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
    }

    /**
     * 将Long类型的时间戳转换成LocalDateTime
     */
    public static LocalDateTime convertTimeToLocalDateTime(Long time, String pattern) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
    }

    /**
     * 将字符串转日期成Long类型的时间戳
     */
    public static Long convertTimeToLong(String time, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime parse = LocalDateTime.parse(time, formatter);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 取本月第一天
     */
    public static LocalDate firstDayOfThisMonth() {
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 取本月第N天
     */
    public static LocalDate dayOfThisMonth(int n) {
        LocalDate today = LocalDate.now();
        return today.withDayOfMonth(n);
    }

    /**
     * 取本月最后一天
     */
    public static LocalDate lastDayOfThisMonth() {
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 取本月第一天的开始时间
     */
    public static LocalDateTime startOfThisMonth() {
        return LocalDateTime.of(firstDayOfThisMonth(), LocalTime.MIN);
    }


    /**
     * 取本月最后一天的结束时间
     */
    public static LocalDateTime endOfThisMonth() {
        return LocalDateTime.of(lastDayOfThisMonth(), LocalTime.MAX);
    }

    /**
     * 把ZonedDateTime字符串转换成LocalDateTime字符串
     *
     * @param dateTimeStr
     * @param pattern
     * @return
     */
    public static String parseLocalDateTimeStr(String dateTimeStr, String pattern) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimeStr);
        return zonedDateTime.toLocalDateTime().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取下一个整num分钟的时间
     *
     * @param time
     * @param num
     * @return
     */
    public static LocalDateTime nextNumMinute(LocalDateTime time, long num) {
        int second = time.getSecond() + time.getNano();
        LocalDateTime baseTime = time.withSecond(0).withNano(0);
        if (second > 0) {
            return baseTime.plusMinutes(num);
        } else {
            return baseTime;
        }
    }

}
