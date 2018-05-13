package com.ruoyi.project.monitor.job.domain;

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
    private Long jobLogId;
    /** 任务名称 */
    private String jobName;
    /** 任务组名 */
    private String jobGroup;
    /** 任务方法 */
    private String methodName;
    /** 方法参数 */
    private String params;
    /** 日志信息 */
    private String jobMessage;
    /** 是否异常 */
    private int isException;
    /** 异常信息 */
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

    public int getIsException()
    {
        return isException;
    }

    public void setIsException(int isException)
    {
        this.isException = isException;
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
                + methodName + ", params=" + params + ", jobMessage=" + jobMessage + ", isException=" + isException
                + ", exceptionInfo=" + exceptionInfo + "]";
    }

}
