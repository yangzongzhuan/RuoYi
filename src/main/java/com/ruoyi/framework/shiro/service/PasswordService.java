package com.ruoyi.framework.shiro.service;

import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PostConstruct;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ruoyi.common.constant.CommonConstant;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.exception.user.UserPasswordRetryLimitExceedException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SystemLogUtils;
import com.ruoyi.project.system.user.domain.User;

/**
 * 登录密码方法
 * 
 * @author ruoyi
 */
@Component
public class PasswordService
{

    @Autowired
    private CacheManager cacheManager;

    private Cache<String, AtomicInteger> loginRecordCache;

    @Value(value = "${user.password.maxRetryCount}")
    private String maxRetryCount;

    @PostConstruct
    public void init()
    {
        loginRecordCache = cacheManager.getCache("loginRecordCache");
    }

    public void validate(User user, String password)
    {
        String loginName = user.getLoginName();

        AtomicInteger retryCount = loginRecordCache.get(loginName);

        if (retryCount == null)
        {
            retryCount = new AtomicInteger(0);
            loginRecordCache.put(loginName, retryCount);
        }
        if (retryCount.incrementAndGet() > Integer.valueOf(maxRetryCount).intValue())
        {
            SystemLogUtils.log(loginName, CommonConstant.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount));
            throw new UserPasswordRetryLimitExceedException(Integer.valueOf(maxRetryCount).intValue());
        }

        if (!matches(user, password))
        {
            SystemLogUtils.log(loginName, CommonConstant.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.count", retryCount, password));
            loginRecordCache.put(loginName, retryCount);
            throw new UserPasswordNotMatchException();
        }
        else
        {
            clearLoginRecordCache(loginName);
        }
    }

    public boolean matches(User user, String newPassword)
    {
        return user.getPassword().equals(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
    }

    public void clearLoginRecordCache(String username)
    {
        loginRecordCache.remove(username);
    }

    public String encryptPassword(String username, String password, String salt)
    {
        return new Md5Hash(username + password + salt).toHex().toString();
    }

    public static void main(String[] args)
    {
        //System.out.println(new PasswordService().encryptPassword("admin", "admin123", "111111"));
        //System.out.println(new PasswordService().encryptPassword("ry", "admin123", "222222"));
        System.out.println(new PasswordService().encryptPassword("ly", "admin123", "123456"));
        System.out.println(new PasswordService().encryptPassword("ce", "admin123", "123456"));
        System.out.println(new PasswordService().encryptPassword("zs", "admin123", "123456"));
        System.out.println(new PasswordService().encryptPassword("ls", "admin123", "123456"));
        System.out.println(new PasswordService().encryptPassword("ww", "admin123", "123456"));
        System.out.println(new PasswordService().encryptPassword("zl", "admin123", "123456"));
        System.out.println(new PasswordService().encryptPassword("sq", "admin123", "123456"));
        System.out.println(new PasswordService().encryptPassword("zb", "admin123", "123456"));
        System.out.println(new PasswordService().encryptPassword("wj", "admin123", "123456"));
        System.out.println(new PasswordService().encryptPassword("ys", "admin123", "123456"));
    }
}
