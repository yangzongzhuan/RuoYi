package com.ruoyi.project.monitor.job.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 定时任务调度日志信息 sys_job_log
 * 
 * @author ruoyi
 */
public class JobLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @Excel(name = "日志序号", column = "A")
    private Long jobLogId;

    /** 任务名称 */
    @Excel(name = "任务名称", column = "B")
    private String jobName;

    /** 任务组名 */
    @Excel(name = "任务组名", column = "C")
    private String jobGroup;

    /** 任务方法 */
    @Excel(name = "任务方法", column = "D")
    private String methodName;

    /** 方法参数 */
    @Excel(name = "方法参数", column = "E")
    private String params;

    /** 日志信息 */
    @Excel(name = "日志信息", column = "F")
    private String jobMessage;

    /** 执行状态（0正常 1失败） */
    @Excel(name = "执行状态", column = "G")
    private String status;

    /** 异常信息 */
    @Excel(name = "异常信息", column = "H")
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

    public String getParams()
    {
        return params;
    }

    public void setParams(String params)
    {
        this.params = params;
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
    public String toString()
    {
        return "JobLog [jobLogId=" + jobLogId + ", jobName=" + jobName + ", jobGroup=" + jobGroup + ", methodName="
                + methodName + ", params=" + params + ", jobMessage=" + jobMessage + ", status=" + status
                + ", exceptionInfo=" + exceptionInfo + "]";
    }

}
