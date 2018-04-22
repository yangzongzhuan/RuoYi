package com.ruoyi.common.utils.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.ruoyi.project.system.user.domain.User;

/**
 * shiro 工具类
 * 
 * @author ruoyi
 */
public class ShiroUtils
{

    public static Subject getSubjct()
    {
        return SecurityUtils.getSubject();
    }

    public static void logout()
    {
        getSubjct().logout();
    }

    public static User getUser()
    {
        return (User) getSubjct().getPrincipal();
    }

    public static Long getUserId()
    {
        return getUser().getUserId().longValue();
    }

    public static String getLoginName()
    {
        return getUser().getLoginName();
    }

    public static String getIp()
    {
        return getSubjct().getSession().getHost();
    }

    public static String getSessionId()
    {
        return String.valueOf(getSubjct().getSession().getId());
    }
}
