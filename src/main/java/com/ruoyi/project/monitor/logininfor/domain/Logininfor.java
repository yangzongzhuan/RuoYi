package com.ruoyi.project.monitor.logininfor.domain;

import java.util.Date;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 系统访问日志情况信息 sys_logininfor
 * 
 * @author ruoyi
 */
public class Logininfor extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    /** ID */
    private Integer infoId;
    /** 用户账号 */
    private String loginName;
    /** 登录状态 0成功 1失败 */
    private String status;
    /** 登录IP地址 */
    private String ipaddr;
    /** 浏览器类型 */
    private String browser;
    /** 操作系统 */
    private String os;
    /** 提示消息 */
    private String msg;
    /** 访问时间 */
    private Date loginTime;

    public Integer getInfoId()
    {
        return infoId;
    }

    public void setInfoId(Integer infoId)
    {
        this.infoId = infoId;
    }

    public String getLoginName()
    {
        return loginName;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getIpaddr()
    {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr)
    {
        this.ipaddr = ipaddr;
    }

    public String getBrowser()
    {
        return browser;
    }

    public void setBrowser(String browser)
    {
        this.browser = browser;
    }

    public String getOs()
    {
        return os;
    }

    public void setOs(String os)
    {
        this.os = os;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Date getLoginTime()
    {
        return loginTime;
    }

    public void setLoginTime(Date loginTime)
    {
        this.loginTime = loginTime;
    }

    @Override
    public String toString()
    {
        return "Logininfor [infoId=" + infoId + ", loginName=" + loginName + ", status=" + status + ", ipaddr=" + ipaddr
                + ", browser=" + browser + ", os=" + os + ", msg=" + msg + ", loginTime=" + loginTime + "]";
    }

}