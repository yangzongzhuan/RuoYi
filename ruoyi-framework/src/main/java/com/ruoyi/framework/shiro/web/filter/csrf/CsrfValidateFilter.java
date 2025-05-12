package com.ruoyi.framework.shiro.web.filter.csrf;

import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.web.filter.AccessControlFilter;
import com.ruoyi.common.constant.ShiroConstants;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;

/**
 * csrf过滤器
 * 
 * @author ruoyi
 */
public class CsrfValidateFilter extends AccessControlFilter
{
    /**
     * 白名单链接
     */
    private List<String> csrfWhites;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (!isAllowMethod(httpServletRequest))
        {
            return true;
        }
        if (StringUtils.matches(httpServletRequest.getServletPath(), csrfWhites))
        {
            return true;
        }
        return validateResponse(httpServletRequest, httpServletRequest.getHeader(ShiroConstants.X_CSRF_TOKEN));
    }

    public boolean validateResponse(HttpServletRequest request, String requestToken)
    {
        Object obj = ShiroUtils.getSession().getAttribute(ShiroConstants.CSRF_TOKEN);
        String sessionToken = Convert.toStr(obj, "");
        if (StringUtils.isEmpty(requestToken) || !requestToken.equalsIgnoreCase(sessionToken))
        {
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
    {
        ServletUtils.renderString((HttpServletResponse) response, "{\"code\":\"1\",\"msg\":\"当前请求的安全验证未通过，请刷新页面后重试。\"}");
        return false;
    }

    private boolean isAllowMethod(HttpServletRequest request)
    {
        String method = request.getMethod();
        return "POST".equalsIgnoreCase(method);
    }

    public List<String> getCsrfWhites()
    {
        return csrfWhites;
    }

    public void setCsrfWhites(List<String> csrfWhites)
    {
        this.csrfWhites = csrfWhites;
    }
}
