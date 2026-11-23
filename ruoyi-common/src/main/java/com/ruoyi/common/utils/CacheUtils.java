package com.ruoyi.common.utils;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.jcache.JCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.utils.spring.SpringUtils;

/**
 * Cache工具类
 * 
 * @author ruoyi
 */
public class CacheUtils
{
    private static Logger logger = LoggerFactory.getLogger(CacheUtils.class);

    private static CacheManager cacheManager = SpringUtils.getBean(CacheManager.class);

    private static final String SYS_CACHE = "sys-cache";

    /**
     * 获取SYS_CACHE缓存
     * 
     * @param key
     * @return
     */
    public static Object get(String key)
    {
        return get(SYS_CACHE, key);
    }

    /**
     * 获取SYS_CACHE缓存
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object get(String key, Object defaultValue)
    {
        Object value = get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 写入SYS_CACHE缓存
     * 
     * @param key
     * @return
     */
    public static void put(String key, Object value)
    {
        put(SYS_CACHE, key, value);
    }

    /**
     * 从SYS_CACHE缓存中移除
     * 
     * @param key
     * @return
     */
    public static void remove(String key)
    {
        remove(SYS_CACHE, key);
    }

    /**
     * 获取缓存
     * 
     * @param cacheName
     * @param key
     * @return
     */
    public static Object get(String cacheName, String key)
    {
        return getCache(cacheName).get(getKey(key));
    }

    /**
     * 获取缓存
     * 
     * @param cacheName
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object get(String cacheName, String key, Object defaultValue)
    {
        Object value = get(cacheName, getKey(key));
        return value != null ? value : defaultValue;
    }

    /**
     * 写入缓存
     * 
     * @param cacheName
     * @param key
     * @param value
     */
    public static void put(String cacheName, String key, Object value)
    {
        getCache(cacheName).put(getKey(key), value);
    }

    /**
     * 从缓存中移除
     * 
     * @param cacheName
     * @param key
     */
    public static void remove(String cacheName, String key)
    {
        getCache(cacheName).remove(getKey(key));
    }

    /**
     * 从缓存中移除所有
     * 
     * @param cacheName
     */
    public static void removeAll(String cacheName)
    {
        Cache<String, Object> cache = getCache(cacheName);
        cache.clear();
        logger.info("清理缓存： {}", cacheName);
    }

    /**
     * 从缓存中移除指定key
     * 
     * @param keys
     */
    public static void removeByKeys(Set<String> keys)
    {
        removeByKeys(SYS_CACHE, keys);
    }

    /**
     * 从缓存中移除指定key
     * 
     * @param cacheName
     * @param keys
     */
    public static void removeByKeys(String cacheName, Set<String> keys)
    {
        for (Iterator<String> it = keys.iterator(); it.hasNext();)
        {
            remove(it.next());
        }
        logger.info("清理缓存： {} => {}", cacheName, keys);
    }

    /**
     * 获取缓存键名
     * 
     * @param key
     * @return
     */
    private static String getKey(String key)
    {
        return key;
    }

    /**
     * 获得一个Cache，没有则显示日志。
     * 
     * @param cacheName
     * @return
     */
    public static Cache<String, Object> getCache(String cacheName)
    {
        Cache<String, Object> cache = cacheManager.getCache(cacheName);
        if (cache == null)
        {
            throw new RuntimeException("当前系统中没有定义“" + cacheName + "”这个缓存。");
        }
        return cache;
    }

    /**
     * 获取所有缓存
     * 
     * @return 缓存组
     */
    public static String[] getCacheNames()
    {
        return StreamSupport.stream(((JCacheManager) cacheManager).getCacheManager().getCacheNames().spliterator(), false).toArray(String[]::new);
    }

    public static Set<String> getCacheKeys(String cacheName)
    {
        javax.cache.Cache<?, ?> cache = ((JCacheManager) cacheManager).getCacheManager().getCache(cacheName);
        if (cache == null)
        {
            return new TreeSet<>();
        }
        return StreamSupport.stream(cache.spliterator(), false)
                .filter(e -> e != null && e.getKey() != null)
                .map(e -> e.getKey().toString()) 
                .collect(Collectors.toCollection(TreeSet::new));
    }
}
