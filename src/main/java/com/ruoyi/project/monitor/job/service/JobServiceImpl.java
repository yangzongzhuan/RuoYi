package com.ruoyi.project.monitor.job.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.constant.ScheduleConstants;
import com.ruoyi.common.support.Convert;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.monitor.job.domain.Job;
import com.ruoyi.project.monitor.job.mapper.JobMapper;
import com.ruoyi.project.monitor.job.util.ScheduleUtils;

/**
 * 定时任务调度信息 服务层
 * 
 * @author ruoyi
 */
@Service("jobService")
public class JobServiceImpl implements IJobService
{
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobMapper jobMapper;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init()
    {
        List<Job> jobList = jobMapper.selectJobAll();
        for (Job job : jobList)
        {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, job.getJobId());
            // 如果不存在，则创建
            if (cronTrigger == null)
            {
                ScheduleUtils.createScheduleJob(scheduler, job);
            }
            else
            {
                ScheduleUtils.updateScheduleJob(scheduler, job);
            }
        }
    }

    /**
     * 获取quartz调度器的计划任务列表
     * 
     * @param job 调度信息
     * @return
     */
    @Override
    public List<Job> selectJobList(Job job)
    {
        return jobMapper.selectJobList(job);
    }

    /**
     * 通过调度任务ID查询调度信息
     * 
     * @param jobId 调度任务ID
     * @return 调度任务对象信息
     */
    @Override
    public Job selectJobById(Long jobId)
    {
        return jobMapper.selectJobById(jobId);
    }

    /**
     * 暂停任务
     * 
     * @param job 调度信息
     */
    @Override
    public int pauseJob(Job job)
    {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        job.setUpdateBy(ShiroUtils.getLoginName());
        int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            ScheduleUtils.pauseJob(scheduler, job.getJobId());
        }
        return rows;
    }

    /**
     * 恢复任务
     * 
     * @param job 调度信息
     */
    @Override
    public int resumeJob(Job job)
    {
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        job.setUpdateBy(ShiroUtils.getLoginName());
        int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            ScheduleUtils.resumeJob(scheduler, job.getJobId());
        }
        return rows;
    }

    /**
     * 删除任务后，所对应的trigger也将被删除
     * 
     * @param job 调度信息
     */
    @Override
    public int deleteJob(Job job)
    {
        int rows = jobMapper.deleteJobById(job);
        if (rows > 0)
        {
            ScheduleUtils.deleteScheduleJob(scheduler, job.getJobId());
        }
        return rows;
    }

    /**
     * 批量删除调度信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public void deleteJobByIds(String ids)
    {
        Long[] jobIds = Convert.toLongArray(ids);
        for (Long jobId : jobIds)
        {
            Job job = jobMapper.selectJobById(jobId);
            deleteJob(job);
        }
    }

    /**
     * 任务调度状态修改
     * 
     * @param job 调度信息
     */
    @Override
    public int changeStatus(Job job)
    {
        int rows = 0;
        String status = job.getStatus();
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status))
        {
            rows = resumeJob(job);
        }
        else if (ScheduleConstants.Status.PAUSE.getValue().equals(status))
        {
            rows = pauseJob(job);
        }
        return rows;
    }

    /**
     * 立即运行任务
     * 
     * @param job 调度信息
     */
    @Override
    public int triggerJob(Job job)
    {
        int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            ScheduleUtils.run(scheduler, job);
        }
        return rows;
    }

    /**
     * 新增任务
     * 
     * @param job 调度信息 调度信息
     */
    @Override
    public int addJobCron(Job job)
    {
        job.setCreateBy(ShiroUtils.getLoginName());
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = jobMapper.insertJob(job);
        if (rows > 0)
        {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
        return rows;
    }

    /**
     * 更新任务的时间表达式
     * 
     * @param job 调度信息
     */
    @Override
    public int updateJobCron(Job job)
    {
        int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            ScheduleUtils.updateScheduleJob(scheduler, job);
        }
        return rows;
    }

    /**
     * 保存任务的时间表达式
     * 
     * @param job 调度信息
     */
    @Override
    public int saveJobCron(Job job)
    {
        Long jobId = job.getJobId();
        int rows = 0;
        if (StringUtils.isNotNull(jobId))
        {
            rows = updateJobCron(job);
        }
        else
        {
            rows = addJobCron(job);
        }
        return rows;
    }

}
