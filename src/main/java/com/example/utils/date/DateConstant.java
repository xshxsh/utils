package com.example.utils.date;

/**
 * @program: DateConstant
 * @author: zzx
 * @date: Create in 13:59 2020/1/13
 * @description: 日期格式的常量，字符串格式
 * @version: 1.0.0
 */
public class DateConstant {

    private DateConstant() {

    }

    public static final String LONG_DATE_MONTH_PATTERN = "yyyy-MM";
    public static final String LONG_DATE_PATTERN = "yyyy-MM-dd";

    public static final String EN_DATE_MONTH_PATTERN = "yyyy/MM";
    public static final String EN_DATE_PATTERN = "yyyy/MM/dd";

    public static final String SHORT_DATE_MONTH_PATTERN = "yyyyMM";
    public static final String SHORT_DATE_PATTERN = "yyyyMMdd";

    public static final String LONG_DATETIME_SECOND_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String LONG_DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String LONG_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String EN_DATETIME_SECOND_PATTERN = "yyyy/MM/dd HH:mm:ss";
    public static final String EN_DATETIME_MINUTE_PATTERN = "yyyy/MM/dd HH:mm";
    public static final String EN_DATETIME_PATTERN = "yyyy/MM/dd HH:mm:ss.SSS";

    public static final String SHORT_DATETIME_SECOND_PATTERN = "yyyyMMddHHmmss";
    public static final String SHORT_DATETIME_MINUTE_PATTERN = "yyyyMMddHHmm";
    public static final String SHORT_DATETIME_PATTERN = "yyyyMMddHHmmssSSS";
}
