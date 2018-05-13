package com.ruoyi.project.monitor.operlog.domain;

import java.util.Date;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 操作日志记录 oper_log
 * 
 * @author ruoyi
 */
public class OperLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    /** 日志主键 */
    private Integer operId;
    /** 模块标题 */
    private String title;
    /** 功能请求 */
    private String action;
    /** 请求方法 */
    private String method;
    /** 来源渠道 */
    private String channel;
    /** 操作员名称 */
    private String loginName;
    /** 部门名称 */
    private String deptName;
    /** 请求url */
    private String operUrl;
    /** 操作地址 */
    private String operIp;
    /** 请求参数 */
    private String operParam;
    /** 状态0正常 1异常 */
    private int status;
    /** 错误消息 */
    private String errorMsg;
    /** 操作时间 */
    private Date operTime;

    public Integer getOperId()
    {
        return operId;
    }

    public void setOperId(Integer operId)
    {
        this.operId = operId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getChannel()
    {
        return channel;
    }

    public void setChannel(String channel)
    {
        this.channel = channel;
    }

    public String getLoginName()
    {
        return loginName;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getOperUrl()
    {
        return operUrl;
    }

    public void setOperUrl(String operUrl)
    {
        this.operUrl = operUrl;
    }

    public String getOperIp()
    {
        return operIp;
    }

    public void setOperIp(String operIp)
    {
        this.operIp = operIp;
    }

    public String getOperParam()
    {
        return operParam;
    }

    public void setOperParam(String operParam)
    {
        this.operParam = operParam;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public Date getOperTime()
    {
        return operTime;
    }

    public void setOperTime(Date operTime)
    {
        this.operTime = operTime;
    }

    @Override
    public String toString()
    {
        return "OperLog [operId=" + operId + ", title=" + title + ", action=" + action + ", method=" + method
                + ", channel=" + channel + ", loginName=" + loginName + ", deptName=" + deptName + ", operUrl="
                + operUrl + ", operIp=" + operIp + ", operParam=" + operParam + ", status=" + status + ", errorMsg="
                + errorMsg + ", operTime=" + operTime + "]";
    }

}
