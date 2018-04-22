package com.ruoyi.project.monitor.job.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.monitor.job.dao.IJobLogDao;
import com.ruoyi.project.monitor.job.domain.JobLog;

/**
 * 定时任务调度日志信息 服务层
 * 
 * @author ruoyi
 */
@Service("jobLogService")
public class JobLogServiceImpl implements IJobLogService
{

    @Autowired
    private IJobLogDao jobLogDao;

    /**
     * 获取quartz调度器日志的计划任务
     * 
     * @param jobLog 调度日志信息
     * @return 调度任务日志集合
     */
    @Override
    public List<JobLog> selectJobLogList(JobLog jobLog)
    {
        return jobLogDao.selectJobLogList(jobLog);
    }

    /**
     * 通过调度任务日志ID查询调度信息
     * 
     * @param jobId 调度任务日志ID
     * @return 调度任务日志对象信息
     */
    @Override
    public JobLog selectJobLogById(Long jobLogId)
    {
        return jobLogDao.selectJobLogById(jobLogId);
    }

    /**
     * 新增任务日志
     * 
     * @param jobLog 调度日志信息
     */
    @Override
    public void addJobLog(JobLog jobLog)
    {
        jobLogDao.insertJobLog(jobLog);
    }

    /**
     * 批量删除调度日志信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int batchDeleteJoblog(Long[] ids)
    {
        return jobLogDao.batchDeleteJobLog(ids);
    }

    /**
     * 删除任务日志
     * 
     * @param jobId 调度日志ID
     */
    @Override
    public int deleteJobLogById(Long jobId)
    {
        return jobLogDao.deleteJobLogById(jobId);
    }

}
