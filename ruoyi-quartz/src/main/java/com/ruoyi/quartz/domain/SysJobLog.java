package com.ruoyi.quartz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.base.BaseEntity;

/**
 * 定时任务调度日志表 sys_job_log
 * 
 * @author ruoyi
 */
public class SysJobLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @Excel(name = "日志序号")
    private Long jobLogId;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String jobName;

    /** 任务组名 */
    @Excel(name = "任务组名")
    private String jobGroup;

    /** 任务方法 */
    @Excel(name = "任务方法")
    private String methodName;

    /** 方法参数 */
    @Excel(name = "方法参数")
    private String methodParams;

    /** 日志信息 */
    @Excel(name = "日志信息")
    private String jobMessage;

    /** 执行状态（0正常 1失败） */
    @Excel(name = "执行状态", readConverterExp = "0=正常,1=失败")
    private String status;

    /** 异常信息 */
    @Excel(name = "异常信息")
    private String exceptionInfo;

    public Long getJobLogId()
    {
        return jobLogId;
    }

    public void setJobLogId(Long jobLogId)
    {
        this.jobLogId = jobLogId;
    }

    public String getJobName()
    {
        return jobName;
    }

    public void setJobName(String jobName)
    {
        this.jobName = jobName;
    }

    public String getJobGroup()
    {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup)
    {
        this.jobGroup = jobGroup;
    }

    public String getMethodName()
    {
        return methodName;
    }

    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }

    public String getMethodParams()
    {
        return methodParams;
    }

    public void setMethodParams(String methodParams)
    {
        this.methodParams = methodParams;
    }

    public String getJobMessage()
    {
        return jobMessage;
    }

    public void setJobMessage(String jobMessage)
    {
        this.jobMessage = jobMessage;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getExceptionInfo()
    {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo)
    {
        this.exceptionInfo = exceptionInfo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("jobLogId", getJobLogId())
            .append("jobName", getJobName())
            .append("jobGroup", getJobGroup())
            .append("methodName", getMethodName())
            .append("methodParams", getMethodParams())
            .append("jobMessage", getJobMessage())
            .append("status", getStatus())
            .append("exceptionInfo", getExceptionInfo())
            .append("createTime", getCreateTime())
            .toString();
    }
}
