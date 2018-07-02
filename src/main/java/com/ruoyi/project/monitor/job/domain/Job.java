package com.ruoyi.project.monitor.job.domain;

import java.io.Serializable;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 定时任务调度信息 sys_job
 * 
 * @author ruoyi
 */
public class Job extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    @Excel(name = "任务序号", column = "A")
    private Long jobId;

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

    /** cron执行表达式 */
    @Excel(name = "执行表达式 ", column = "F")
    private String cronExpression;

    /** 任务状态（0正常 1暂停） */
    @Excel(name = "任务状态", column = "G")
    private String status;

    public Long getJobId()
    {
        return jobId;
    }

    public void setJobId(Long jobId)
    {
        this.jobId = jobId;
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

    public String getCronExpression()
    {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression)
    {
        this.cronExpression = cronExpression;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "Job [jobId=" + jobId + ", jobName=" + jobName + ", jobGroup=" + jobGroup + ", methodName=" + methodName
                + ", params=" + params + ", cronExpression=" + cronExpression + ", status=" + status + "]";
    }

}
