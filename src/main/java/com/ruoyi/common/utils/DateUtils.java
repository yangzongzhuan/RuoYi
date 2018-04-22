package com.ruoyi.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author ruoyi
 */
public class DateUtils
{
    public static final String DEFAULT_YYYYMMDD = "yyyyMMddHHmmss";

    public static final String DEFAULT_YYYY_MM_DD = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     * 
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow("yyyy-MM-dd");
    }

    public static final String dateTimeStr()
    {
        return dateTimeNow(DEFAULT_YYYY_MM_DD);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(DEFAULT_YYYYMMDD);
    }

    public static final String dateTimeNow(final String format)
    {
        return dateTime(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return dateTime(DEFAULT_YYYYMMDD, date);
    }

    public static final String dateTime(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

}
