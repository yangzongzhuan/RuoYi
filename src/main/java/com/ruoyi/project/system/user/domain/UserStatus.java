package com.ruoyi.project.system.user.domain;

/**
 * 用户状态
 * 
 * @author ruoyi
 *
 */
public enum UserStatus
{
    OK(0, "正常"), DISABLE(1, "禁用"), DELETED(2, "删除");

    private final int code;
    private final String info;

    UserStatus(int code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public int getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
