package com.ruoyi.project.monitor.logininfor.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 系统访问日志情况信息 sys_logininfor
 * 
 * @author ruoyi
 */
public class Logininfor extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    /** ID */
    @Excel(name = "序号")
    private Long infoId;
    /** 用户账号 */
    @Excel(name = "用户账号")
    private String loginName;
    /** 登录状态 0成功 1失败 */
    @Excel(name = "登录状态")
    private String status;
    /** 登录IP地址 */
    @Excel(name = "登录地址")
    private String ipaddr;
    /** 登录地点 */
    @Excel(name = "登录地点")
    private String loginLocation;
    /** 浏览器类型 */
    @Excel(name = "浏览器")
    private String browser;
    /** 操作系统 */
    @Excel(name = "操作系统 ")
    private String os;
    /** 提示消息 */
    @Excel(name = "提示消息")
    private String msg;
    /** 访问时间 */
    @Excel(name = "访问时间")
    private Date loginTime;

    public Long getInfoId()
    {
        return infoId;
    }

    public void setInfoId(Long infoId)
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

    public String getLoginLocation()
    {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation)
    {
        this.loginLocation = loginLocation;
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
                + ",loginLocation=" + loginLocation + ", browser=" + browser + ", os=" + os + ", msg=" + msg
                + ", loginTime=" + loginTime + "]";
    }

}