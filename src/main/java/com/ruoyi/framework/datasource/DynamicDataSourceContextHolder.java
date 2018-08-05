package com.ruoyi.framework.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 当前线程数据源
 * 
 * @author ruoyi
 */
public class DynamicDataSourceContextHolder
{
    public static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源名
     */
    public static void setDB(String dbType)
    {
        log.info("切换到{}数据源", dbType);
        CONTEXT_HOLDER.set(dbType);
    }

    /**
     * 获取数据源名
     */
    public static String getDB()
    {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清理数据源名
     */
    public static void clearDB()
    {
        CONTEXT_HOLDER.remove();
    }

}
