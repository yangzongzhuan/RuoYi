package com.ruoyi.project.monitor.job.service;

import java.util.List;
import com.ruoyi.project.monitor.job.domain.JobLog;

/**
 * 定时任务调度日志信息信息 服务层
 * 
 * @author ruoyi
 */
public interface IJobLogService
{
    /**
     * 获取quartz调度器日志的计划任务
     * 
     * @param jobLog 调度日志信息
     * @return 调度任务日志集合
     */
    public List<JobLog> selectJobLogList(JobLog jobLog);

    /**
     * 通过调度任务日志ID查询调度信息
     * 
     * @param jobLogId 调度任务日志ID
     * @return 调度任务日志对象信息
     */
    public JobLog selectJobLogById(Long jobLogId);

    /**
     * 新增任务日志
     * 
     * @param jobLog 调度日志信息
     */
    public void addJobLog(JobLog jobLog);

    /**
     * 批量删除调度日志信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteJobLogByIds(String ids);

    /**
     * 删除任务日志
     * 
     * @param jobId 调度日志ID
     * @return 结果
     */
    public int deleteJobLogById(Long jobId);
    
    /**
     * 清空任务日志
     */
    public void cleanJobLog();
}
