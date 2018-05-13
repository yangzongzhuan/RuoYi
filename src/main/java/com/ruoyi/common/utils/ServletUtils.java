package com.ruoyi.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 客户端工具类
 * 
 * @author ruoyi
 */
public class ServletUtils
{
    /**
     * 获取getStrAttribute
     */
    public static String getStrAttribute(String name)
    {
        return StringUtils.valueAsStr(getRequestAttributes().getRequest().getAttribute(name));
    }

    /**
     * 获取getIntAttribute
     */
    public static int getIntAttribute(String name)
    {
        return StringUtils.valueAsInt(getRequestAttributes().getRequest().getAttribute(name));
    }

    /**
     * 获取getStrParameter
     */
    public static String getStrParameter(String name)
    {
        return StringUtils.valueAsStr(getRequestAttributes().getRequest().getParameter(name));
    }

    /**
     * 获取getIntParameter
     */
    public static Integer getIntParameter(String name)
    {
        return StringUtils.valueAsInt(getRequestAttributes().getRequest().getParameter(name));
    }

    public static ServletRequestAttributes getRequestAttributes()
    {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest()
    {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse()
    {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取session
     */
    public static HttpSession getSession()
    {
        return getRequest().getSession();
    }

    /**
     * 是否是Ajax异步请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request)
    {

        String accept = request.getHeader("accept");
        if (accept != null && accept.indexOf("application/json") != -1)
        {
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1)
        {
            return true;
        }

        String uri = request.getRequestURI();
        if (StringUtils.inStringIgnoreCase(uri, ".json", ".xml"))
        {
            return true;
        }

        String ajax = request.getParameter("__ajax");
        if (StringUtils.inStringIgnoreCase(ajax, "json", "xml"))
        {
            return true;
        }

        return false;
    }

}
