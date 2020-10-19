package com.example.utils.date;


import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.*;
import java.util.Date;
import java.util.Locale;

/**
 * @program: DateUtil
 * @author: zzx
 * @date: Create in 18:00 2020/1/13
 * @description: 日期常用工具类，最常用为格式转换(工具周福峰的整理来的）
 * @version: 1.0.0
 */
public final class DateUtils {
    /**
     * 判断是否为5分钟的余数
     */
    private static final Integer FIVE_MINUTE = 5;
    /**
     * 判断是否为15分钟的余数
     */
    private static final Integer QUARTER_MINUTE = 15;
    /**
     * 判断是否为30分钟的余数
     */
    private static final Integer HALF_MINUTE = 30;
    /**
     * 年模式长度
     */
    private static final Integer YYYY_LENGTH = 4;
    /**
     * 默认每月第一天
     */
    private static final Integer DEFAULT_DAY_OF_MONTH = 1;

    private final static ZoneId zoneId = ZoneId.systemDefault();

    private DateUtils() {
    }


    /**
     * 返回当前日期 ，默认"yyyy-MM-dd"模式
     *
     * @return String型日期
     */
    public static String getCurLocalDateStr() {
        return getCurLocalDateStr(DateConstant.LONG_DATE_PATTERN);
    }

    /**
     * 返回当前日期，自定义模式
     *
     * @param pattern 模式
     * @return String型日期
     */
    public static String getCurLocalDateStr(String pattern) {
        return toString(LocalDate.now(), pattern);
    }

    /**
     * 日期转<code>String</code>，默认"yyyy-MM-dd"模式
     *
     * @param localDate 日期
     * @return String型日期
     */
    public static String toString(LocalDate localDate) {
        return toString(localDate, DateConstant.LONG_DATE_PATTERN);
    }

    /**
     * 日期转<code>String</code>，默认"yyyy-MM-dd"模式
     *
     * @param date 日期
     * @return String型日期
     */
    public static String toString(Date date) {
        return toString(date2LocalDate(date), DateConstant.LONG_DATE_PATTERN);
    }

    /**
     * 日期转<code>String</code>，自定义日期&模式
     *
     * @param localDate 日期
     * @param pattern   模式
     * @return String型日期
     */
    public static String toString(LocalDate localDate, String pattern) {
        return toString(localDate, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 日期转<code>String</code>，自定义日期&模式
     *
     * @param date    日期
     * @param pattern 模式
     * @return String型日期
     */
    public static String toString(Date date, String pattern) {
        return toString(date2LocalDate(date), DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 日期转<code>String</code>，自定义日期&格式器
     *
     * @param localDate 日期
     * @param formatter 格式器
     * @return String型日期
     */
    public static String toString(LocalDate localDate, DateTimeFormatter formatter) {
        if (localDate == null) {
            throw new IllegalArgumentException("localDate must not be null");
        }
        return localDate.format(formatter);
    }

    /**
     * 日期转<code>String</code>，自定义日期&格式器
     *
     * @param date      日期
     * @param formatter 格式器
     * @return String型日期
     */
    public static String toString(Date date, DateTimeFormatter formatter) {
        LocalDate localDate = date2LocalDate(date);
        if (localDate == null) {
            throw new IllegalArgumentException("localDate must not be null");
        }
        return localDate.format(formatter);
    }

    /**
     * 返回当前日期时间 ，默认"yyyy-MM-dd HH:mm:ss.SSS"
     *
     * @return String型日期时间
     */
    public static String getCurLocalDateTimeStr() {
        return getCurLocalDateTimeStr(DateConstant.LONG_DATETIME_PATTERN);
    }

    /**
     * 以指定模式返回当前日期时间
     *
     * @param pattern 模式
     * @return String型日期时间
     */
    public static String getCurLocalDateTimeStr(String pattern) {
        return toString(LocalDateTime.now(), pattern);
    }


    /**
     * 日期时间转<code>String</code>,默认"yyyy-MM-dd HH:mm:ss.SSS"模式
     *
     * @param localDateTime 日期时间
     * @return String型日期时间
     */
    public static String toString(LocalDateTime localDateTime) {
        return toString(localDateTime, DateConstant.LONG_DATETIME_PATTERN);
    }

    /**
     * 日期时间转<code>String</code>,默认"yyyy-MM-dd HH:mm:ss.SSS"模式
     *
     * @param date 日期时间
     * @return String型日期时间
     */
    public static String toStringWithTime(Date date) {
        return toString(date2LocalDateTime(date), DateConstant.LONG_DATETIME_PATTERN);
    }

    /**
     * 日期时间转<code>String</code>,自定义日期时间&模式
     *
     * @param dateTime LocalDateTime
     * @param pattern  模式
     * @return String
     */
    public static String toString(LocalDateTime dateTime, String pattern) {
        return toString(dateTime, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 日期时间转<code>String</code>,自定义日期时间&模式
     *
     * @param dateTime Date
     * @param pattern  模式
     * @return String
     */
    public static String toStringWithTime(Date dateTime, String pattern) {
        return toString(date2LocalDateTime(dateTime), DateTimeFormatter.ofPattern(pattern));
    }


    /**
     * 日期时间转<code>String</code>，自定义日期时间&格式器
     *
     * @param dateTime  日期时间
     * @param formatter 格式器
     * @return String型日期时间
     */
    public static String toString(LocalDateTime dateTime, DateTimeFormatter formatter) {
        if (dateTime == null) {
            throw new IllegalArgumentException("localDateTime must not be null");
        }
        if (formatter == null) {
            formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        }

        return dateTime.format(formatter);
    }

    /**
     * 日期时间转<code>String</code>，自定义日期时间&格式器
     *
     * @param dateTime  日期时间
     * @param formatter 格式器
     * @return String型日期时间
     */
    public static String toStringWithTime(Date dateTime, DateTimeFormatter formatter) {
        if (dateTime == null) {
            throw new IllegalArgumentException("localDateTime must not be null");
        }
        LocalDateTime localDateTime = date2LocalDateTime(dateTime);
        if (formatter == null) {
            formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        }

        return localDateTime.format(formatter);
    }

    /**
     * <code>String</code>转日期，默认"yyyy-MM-dd"模式
     *
     * @param dateStr 日期字符串
     * @return LocalDate日期
     */
    public static LocalDate toLocalDate(String dateStr) {
        return toLocalDate(dateStr, DateConstant.LONG_DATE_PATTERN);
    }

    /**
     * <code>String</code>转日期，默认"yyyy-MM-dd"模式
     *
     * @param dateStr 日期字符串
     * @return Date日期
     */
    public static Date toDate(String dateStr) {
        return localDate2Date(toLocalDate(dateStr, DateConstant.LONG_DATE_PATTERN));
    }

    /**
     * <code>String</code>转日期，自定义日期&模式
     *
     * @param dateStr 日期字符串
     * @param pattern 模式
     * @return LocalDate日期
     */
    public static LocalDate toLocalDate(String dateStr, String pattern) {
        return toLocalDate(dateStr, new DateTimeFormatterBuilder().appendPattern(pattern)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, DEFAULT_DAY_OF_MONTH)
                .toFormatter());
    }

    /**
     * <code>String</code>转日期，自定义日期&模式
     *
     * @param dateStr 日期字符串
     * @param pattern 模式
     * @return Date日期
     */
    public static Date toDate(String dateStr, String pattern) {
        return localDate2Date(toLocalDate(dateStr, new DateTimeFormatterBuilder().appendPattern(pattern)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, DEFAULT_DAY_OF_MONTH)
                .toFormatter()));
    }

    /**
     * <code>String</code>转日期，自定义日期&格式器
     *
     * @param dateStr   日期字符串
     * @param formatter 格式器
     * @return LocalDate日期
     */
    public static LocalDate toLocalDate(String dateStr, DateTimeFormatter formatter) {
        if (StringUtils.isEmpty(dateStr)) {
            throw new IllegalArgumentException("dateStr must not be empty");
        }
        return LocalDate.parse(dateStr, formatter);
    }

    /**
     * <code>String</code>转日期，自定义日期&格式器
     *
     * @param dateStr   日期字符串
     * @param formatter 格式器
     * @return Date日期
     */
    public static Date toDate(String dateStr, DateTimeFormatter formatter) {
        if (StringUtils.isEmpty(dateStr)) {
            throw new IllegalArgumentException("dateStr must not be empty");
        }
        return localDate2Date(LocalDate.parse(dateStr, formatter));
    }

    /**
     * <code>String</code>转日期时间，默认"yyyy-MM-dd HH:mm:ss.SSS"模式
     *
     * @param dateStr 日期时间字符串
     * @return LocalDateTime日期时间
     */
    public static LocalDateTime toLocalDateTime(String dateStr) {
        return toLocalDateTime(dateStr, DateConstant.LONG_DATETIME_PATTERN);
    }

    /**
     * <code>String</code>转日期时间，默认"yyyy-MM-dd HH:mm:ss.SSS"模式
     *
     * @param dateStr 日期时间字符串
     * @return Date日期时间
     */
    public static Date toDateWithTime(String dateStr) {
        return localDateTime2Date(toLocalDateTime(dateStr, DateConstant.LONG_DATETIME_PATTERN));
    }

    /**
     * <code>String</code>转日期时间，自定义日期时间&模式
     *
     * @param dateStr 日期时间字符串
     * @param pattern 模式
     * @return LocalDateTime日期时间
     */
    public static LocalDateTime toLocalDateTime(String dateStr, String pattern) {
        if (pattern.equals(DateConstant.SHORT_DATETIME_PATTERN)) {
            pattern = DateConstant.LONG_DATETIME_PATTERN;
            dateStr = shortPatternToLongPattern(dateStr);
        }
        return toLocalDateTime(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * <code>String</code>转日期时间，自定义日期时间&模式
     *
     * @param dateStr 日期时间字符串
     * @param pattern 模式
     * @return Date日期时间
     */
    public static Date toDateWithTime(String dateStr, String pattern) {
        if (pattern.equals(DateConstant.SHORT_DATETIME_PATTERN)) {
            pattern = DateConstant.LONG_DATETIME_PATTERN;
            dateStr = shortPatternToLongPattern(dateStr);
        }
        return localDateTime2Date(toLocalDateTime(dateStr, DateTimeFormatter.ofPattern(pattern)));
    }

    /**
     * <code>String</code>转日期时间，自定义日期时间&格式器
     *
     * @param dateStr   string 日期时间
     * @param formatter 格式器
     * @return LocalDateTime日期时间
     */
    public static LocalDateTime toLocalDateTime(String dateStr, DateTimeFormatter formatter) {
        if (StringUtils.isEmpty(dateStr)) {
            throw new IllegalArgumentException("dateStr must not be empty");
        }
        return LocalDateTime.parse(dateStr, formatter);
    }

    /**
     * <code>String</code>转日期时间，自定义日期时间&格式器
     *
     * @param dateStr   string 日期时间
     * @param formatter 格式器
     * @return Date日期时间
     */
    public static Date toDateWithTime(String dateStr, DateTimeFormatter formatter) {
        if (StringUtils.isEmpty(dateStr)) {
            throw new IllegalArgumentException("dateStr must not be empty");
        }
        return localDateTime2Date(LocalDateTime.parse(dateStr, formatter));
    }

    /**
     * 将Short_..._Pattern字符串转为Long_..._Pattern<br>
     * 将"yyyyMMddHHmmssSSS"模式<code>String</code>转为yyyy-MM-dd HH:mm:ss.SSS,避免解析时遇到JDK8 BUG
     *
     * @param dateStr 短日期
     * @return 长日期字符串
     */
    public static String shortPatternToLongPattern(String dateStr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dateStr.length(); i++) {
            sb.append(dateStr.charAt(i));
            if ((i & 1) == 1 && i != dateStr.length() - 1) {
                if (i >= YYYY_LENGTH - 1 && i < DateConstant.SHORT_DATE_PATTERN.length() - 1) {
                    sb.append("-");
                } else if (i == DateConstant.SHORT_DATE_PATTERN.length() - 1) {
                    sb.append(" ");
                } else if (i > DateConstant.SHORT_DATE_PATTERN.length() - 1 && i < DateConstant.SHORT_DATETIME_SECOND_PATTERN.length() - 1) {
                    sb.append(":");
                } else if (i == DateConstant.SHORT_DATETIME_SECOND_PATTERN.length() - 1) {
                    sb.append(".");
                }
            }
        }
        return sb.toString();
    }

    /**
     * <code>LocalDateTime</code>转<code>Date</code>
     *
     * @param localDateTime 日期时间
     * @return Date日期时间
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }

    /**
     * <code>Date</code>转<code>LocalDateTime</code>
     *
     * @param date 日期
     * @return LocalDateTime日期时间
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return date.toInstant().atZone(zoneId).toLocalDateTime();
    }

    /**
     * <code>LocalDateTime</code>转<code>Timestamp</code>
     *
     * @param localDateTime 日期时间
     * @return Timestamps时间戳
     */
    public static Timestamp localDateTime2Timestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    /**
     * <code>Timestamp</code>转<code>LocalDateTime</code>
     *
     * @param timestamp 时间戳
     * @return LocalDateTime日期时间
     */
    public static LocalDateTime timestamp2LocalDateTime(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }

    /**
     * 日期相隔毫秒
     *
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @return 相隔毫秒数
     */
    public static long periodMillis(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Duration.between(startDateTime, endDateTime).toMillis();
    }

    /**
     * 日期相隔毫秒
     *
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @return 相隔毫秒数
     */
    public static long periodMillis(Date startDateTime, Date endDateTime) {
        return Duration.between(date2LocalDateTime(startDateTime), date2LocalDateTime(endDateTime)).toMillis();
    }


    /**
     * 日期相隔秒
     *
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @return 相隔秒数
     */
    public static long periodSeconds(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Duration.between(startDateTime, endDateTime).get(ChronoUnit.SECONDS);
    }

    /**
     * 日期相隔秒
     *
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @return 相隔秒数
     */
    public static long periodSeconds(Date startDateTime, Date endDateTime) {
        return Duration.between(date2LocalDateTime(startDateTime), date2LocalDateTime(endDateTime)).get(ChronoUnit.SECONDS);
    }


    /**
     * 日期相隔天
     *
     * @param startDate 开始日期
     * @param endDate   结束时间
     * @return 相隔天数
     */
    public static long periodDays(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.DAYS);
    }

    /**
     * 日期相隔天
     *
     * @param startDate 开始日期
     * @param endDate   结束时间
     * @return 相隔天数
     */
    public static long periodDays(Date startDate, Date endDate) {
        return date2LocalDate(startDate).until(date2LocalDate(endDate), ChronoUnit.DAYS);
    }


    /**
     * 日期相隔周
     *
     * @param startDate 开始日期
     * @param endDate   结束时间
     * @return 相隔周数
     */
    public static long periodWeeks(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.WEEKS);
    }

    /**
     * 日期相隔周
     *
     * @param startDate 开始日期
     * @param endDate   结束时间
     * @return 相隔周数
     */
    public static long periodWeeks(Date startDate, Date endDate) {
        return date2LocalDate(startDate).until(date2LocalDate(endDate), ChronoUnit.WEEKS);
    }

    /**
     * 日期相隔月
     *
     * @param startDate 开始日期
     * @param endDate   结束时间
     * @return 相隔月数
     */
    public static long periodMonths(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.MONTHS);
    }

    /**
     * 日期相隔月
     *
     * @param startDate 开始日期
     * @param endDate   结束时间
     * @return 相隔月数
     */
    public static long periodMonths(Date startDate, Date endDate) {
        return date2LocalDate(startDate).until(date2LocalDate(endDate), ChronoUnit.MONTHS);
    }


    /**
     * 日期相隔年
     *
     * @param startDate 开始日期
     * @param endDate   结束时间
     * @return 相隔年数
     */
    public static long periodYears(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.YEARS);
    }

    /**
     * 日期相隔年
     *
     * @param startDate 开始日期
     * @param endDate   结束时间
     * @return 相隔年数
     */
    public static long periodYears(Date startDate, Date endDate) {
        return date2LocalDate(startDate).until(date2LocalDate(endDate), ChronoUnit.YEARS);
    }

    /**
     * 是否为当天
     *
     * @param date 日期
     * @return 是否为当天
     */
    public static boolean isToday(LocalDate date) {
        return LocalDate.now().equals(date);
    }

    /**
     * 是否为当天
     *
     * @param date 日期
     * @return 是否为当天
     */
    public static boolean isToday(Date date) {
        return LocalDate.now().equals(date2LocalDate(date));
    }

    /**
     * 是否为闰年
     *
     * @param date 日期
     * @return 是否为闰年
     */
    public static boolean isLeapYear(LocalDate date) {
        return date.isLeapYear();
    }

    /**
     * 是否为闰年
     *
     * @param date 日期
     * @return 是否为闰年
     */
    public static boolean isLeapYear(Date date) {
        return date2LocalDate(date).isLeapYear();
    }


    /**
     * 是否同一天
     *
     * @param t1 时间
     * @param t2 时间
     * @return 是否同一天
     */
    public static boolean isSameDay(LocalDateTime t1, LocalDateTime t2) {
        if (t1 == null || t2 == null) {
            return false;
        }
        return isSameDay(t1.toLocalDate(), t2.toLocalDate());
    }

    /**
     * 是否同一天
     *
     * @param t1 时间
     * @param t2 时间
     * @return 是否同一天
     */
    public static boolean isSameDay(Date t1, Date t2) {
        if (t1 == null || t2 == null) {
            return false;
        }
        return isSameDay(date2LocalDate(t1), date2LocalDate(t1));
    }

    /**
     * 是否同一天
     *
     * @param d1 日期
     * @param d2 日期
     * @return 是否同一天
     */
    public static boolean isSameDay(LocalDate d1, LocalDate d2) {
        if (d1 == null || d2 == null) {
            return false;
        }
        return d1.equals(d2);
    }


    /**
     * 是否为整点
     *
     * @param time 时间
     * @return 是否为整点
     */
    public static boolean isFullClock(LocalDateTime time) {
        return time.getMinute() == 0;
    }

    /**
     * 是否为整点
     *
     * @param time 时间
     * @return 是否为整点
     */
    public static boolean isFullClock(Date time) {
        return date2LocalDateTime(time).getMinute() == 0;
    }


    /**
     * <code>LocalDate</code>转<code>Date</code>
     *
     * @param localDate 日期
     * @return Date类型日期
     */
    public static Date localDate2Date(LocalDate localDate) {
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        Instant instant1 = zonedDateTime.toInstant();
        return Date.from(instant1);
    }

    /**
     * <code>Date</code>转<code>LocalDate</code>
     *
     * @param date 日期
     * @return LocalDate类型日期
     */
    public static LocalDate date2LocalDate(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        return zdt.toLocalDate();
    }

    //获取月第一天

    /**
     * 获取月第一天
     *
     * @param date 日期字符串
     * @return 月的第一天
     */
    public static Date getStartDayOfMonth(String date) {
        LocalDate now = LocalDate.parse(date);
        return getStartDayOfMonth(now);
    }

    /**
     * 获取月第一天
     *
     * @param date 日期字符串
     * @return 月的第一天
     */
    public static Date getStartDayOfMonth(LocalDate date) {
        LocalDate now = date.with(TemporalAdjusters.firstDayOfMonth());
        return localDate2Date(now);
    }

    /**
     * 获取月第一天
     *
     * @return 月的第一天
     */
    public static Date getStartDayOfMonth() {
        return getStartDayOfMonth(LocalDate.now());
    }


    /**
     * 获取月最后一天
     *
     * @param date 日期字符串
     * @return 获取月最后一天
     */
    public static Date getEndDayOfMonth(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return getEndDayOfMonth(localDate);
    }

    /**
     * 获取月最后一天
     *
     * @param date 日期字符串
     * @return 获取月最后一天
     */
    public static Date getEndDayOfMonth(Date date) {
        return getEndDayOfMonth(date2LocalDate(date));
    }

    /**
     * 获取月最后一天
     *
     * @param date 日期字符串
     * @return 获取月最后一天
     */
    public static Date getEndDayOfMonth(LocalDate date) {
        LocalDate now = date.with(TemporalAdjusters.lastDayOfMonth());
        Date.from(now.atStartOfDay(ZoneId.systemDefault()).plusDays(1L).minusNanos(1L).toInstant());
        return localDate2Date(now);
    }

    /**
     * 获取月最后一天
     *
     * @return 获取月最后一天
     */
    public static Date getEndDayOfMonth() {
        return getEndDayOfMonth(LocalDate.now());
    }


    /**
     * 获取周的第一天
     *
     * @param date 日期字符串
     * @return 周的第一天
     */
    public static Date getStartDayOfWeek(String date) {
        LocalDate now = LocalDate.parse(date);
        return getStartDayOfWeek(now);
    }

    /**
     * 获取周的第一天
     *
     * @param date 日期字符串
     * @return 周的第一天
     */
    public static Date getStartDayOfWeek(TemporalAccessor date) {
        TemporalField fieldISO = WeekFields.of(Locale.CHINA).dayOfWeek();
        LocalDate localDate = LocalDate.from(date);
        localDate = localDate.with(fieldISO, 1);
        return localDate2Date(localDate);
    }

    /**
     * 获取周的最后一天
     *
     * @param date 日期字符串
     * @return 周的最后一天
     */
    public static Date getEndDayOfWeek(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return getEndDayOfWeek(localDate);
    }

    /**
     * 获取周的最后一天
     *
     * @param date 日期
     * @return 周的最后一天
     */
    public static Date getEndDayOfWeek(TemporalAccessor date) {
        TemporalField fieldISO = WeekFields.of(Locale.CHINA).dayOfWeek();
        LocalDate localDate = LocalDate.from(date);
        localDate = localDate.with(fieldISO, 7);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1L).minusNanos(1L).toInstant());
    }

    /**
     * 获取一天的开始时间
     *
     * @param date 日期字符串
     * @return 获取一天的开始
     */
    public static LocalDateTime getStartOfDay(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    /**
     * 获取当天的开始时间
     *
     * @return 获取当天的开始时间
     */
    public static LocalDateTime getStartOfDay() {
        return getStartOfDay(LocalDate.now());
    }

    /**
     * 获取一天的结束
     *
     * @param date 日期
     * @return 一天的结束时间
     */
    public static LocalDateTime getEndOfDay(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MAX);
    }

    /**
     * 获取当天的结束
     *
     * @return 获取当天的结束时间
     */
    public static LocalDateTime getEndOfDay() {
        return getEndOfDay(LocalDate.now());
    }

}
