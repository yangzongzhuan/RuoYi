package com.ruoyi.common.utils.sql;

import com.ruoyi.common.utils.StringUtils;

/**
 * sql操作工具类
 * 
 * @author ruoyi
 */
public class SqlUtil
{
    /**
     * 防止sql注入 替换危险字符
     */
    public static String escapeSql(String value)
    {
        if (StringUtils.isNotEmpty(value))
        {
            value = value.replaceAll("\\(", "");
            value = value.replaceAll("\\)", "");
        }
        return value;
    }
}
