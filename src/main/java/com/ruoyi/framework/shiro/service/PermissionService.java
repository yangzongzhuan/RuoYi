package com.ruoyi.framework.shiro.service;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

/**
 * RuoYi首创 js调用 thymeleaf 实现按钮权限可见性
 * 
 * @author ruoyi
 */
@Component
public class PermissionService
{
    public String hasPermi(String permission)
    {
        return isPermittedOperator(permission) ? "" : "hidden";
    }

    private boolean isPermittedOperator(String permission)
    {
        if (SecurityUtils.getSubject().isPermitted(permission))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
