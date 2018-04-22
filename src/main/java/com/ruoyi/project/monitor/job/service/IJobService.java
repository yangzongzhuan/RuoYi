package com.ruoyi.project.monitor.job.service;

import java.util.List;
import com.ruoyi.project.monitor.job.domain.Job;

/**
 * 定时任务调度信息信息 服务层
 * 
 * @author ruoyi
 */
public interface IJobService
{

    /**
     * 获取quartz调度器的计划任务
     * 
     * @param job 调度信息
     * @return 调度任务集合
     */
    public List<Job> selectJobList(Job job);

    /**
     * 通过调度任务ID查询调度信息
     * 
     * @param jobId 调度任务ID
     * @return 调度任务对象信息
     */
    public Job selectJobById(Long jobId);

    /**
     * 暂停任务
     * 
     * @param job 调度信息
     * @return 结果
     */
    public int pauseJob(Job job);

    /**
     * 恢复任务
     * 
     * @param job 调度信息
     * @return 结果
     */
    public int resumeJob(Job job);

    /**
     * 删除任务后，所对应的trigger也将被删除
     * 
     * @param job 调度信息
     * @return 结果
     */
    public int deleteJob(Job job);

    /**
     * 批量删除调度信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public void batchDeleteJob(Long[] ids);

    /**
     * 任务调度状态修改
     * 
     * @param job 调度信息
     * @return 结果
     */
    public int changeStatus(Job job);

    /**
     * 立即运行任务
     * 
     * @param job 调度信息
     * @return 结果
     */
    public int triggerJob(Job job);

    /**
     * 新增任务表达式
     * 
     * @param job 调度信息
     * @return 结果
     */
    public int addJobCron(Job job);

    /**
     * 更新任务的时间表达式
     * 
     * @param job 调度信息
     * @return 结果
     */
    public int updateJobCron(Job job);

    /**
     * 保存任务的时间表达式
     * 
     * @param job 调度信息
     * @return 结果
     */
    public int saveJobCron(Job job);
}
