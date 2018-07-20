package com.ruoyi.project.monitor.job.util;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.constant.ScheduleConstants;
import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.common.exception.job.TaskException.Code;
import com.ruoyi.project.monitor.job.domain.Job;

/**
 * 定时任务工具类
 * 
 * @author ruoyi
 *
 */
public class ScheduleUtils
{
    private static final Logger log = LoggerFactory.getLogger(ScheduleUtils.class);

    /**
     * 获取触发器key
     */
    public static TriggerKey getTriggerKey(Long jobId)
    {
        return TriggerKey.triggerKey(ScheduleConstants.TASK_CLASS_NAME + jobId);
    }

    /**
     * 获取jobKey
     */
    public static JobKey getJobKey(Long jobId)
    {
        return JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME + jobId);
    }

    /**
     * 获取表达式触发器
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId)
    {
        try
        {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        }
        catch (SchedulerException e)
        {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler, Job job)
    {
        try
        {
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(job.getJobId())).build();

            // 表达式调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

            // 按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(job.getJobId())).withSchedule(cronScheduleBuilder).build();

            // 放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);

            scheduler.scheduleJob(jobDetail, trigger);

            // 暂停任务
            if (job.getStatus().equals(ScheduleConstants.Status.PAUSE.getValue()))
            {
                pauseJob(scheduler, job.getJobId());
            }
        }
        catch (SchedulerException e)
        {
            log.error(e.getMessage());
        }
        catch (TaskException e)
        {
            log.error(e.getMessage());
        }
    }

    /**
     * 更新定时任务
     */
    public static void updateScheduleJob(Scheduler scheduler, Job job)
    {
        try
        {
            TriggerKey triggerKey = getTriggerKey(job.getJobId());

            // 表达式调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

            CronTrigger trigger = getCronTrigger(scheduler, job.getJobId());

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();

            // 参数
            trigger.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);

            scheduler.rescheduleJob(triggerKey, trigger);

            // 暂停任务
            if (job.getStatus().equals(ScheduleConstants.Status.PAUSE.getValue()))
            {
                pauseJob(scheduler, job.getJobId());
            }

        }
        catch (SchedulerException e)
        {
            log.error(e.getMessage());
        }
        catch (TaskException e)
        {
            log.error(e.getMessage());
        }
    }

    /**
     * 立即执行任务
     */
    public static int run(Scheduler scheduler, Job job)
    {
        int rows = 0;
        try
        {
            // 参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(ScheduleConstants.TASK_PROPERTIES, job);

            scheduler.triggerJob(getJobKey(job.getJobId()), dataMap);
            rows = 1;
        }
        catch (SchedulerException e)
        {
            log.error(e.getMessage());
        }
        return rows;
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(Scheduler scheduler, Long jobId)
    {
        try
        {
            scheduler.pauseJob(getJobKey(jobId));
        }
        catch (SchedulerException e)
        {
            log.error(e.getMessage());
        }
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, Long jobId)
    {
        try
        {
            scheduler.resumeJob(getJobKey(jobId));
        }
        catch (SchedulerException e)
        {
            log.error(e.getMessage());
        }
    }

    /**
     * 删除定时任务
     */
    public static void deleteScheduleJob(Scheduler scheduler, Long jobId)
    {
        try
        {
            scheduler.deleteJob(getJobKey(jobId));
        }
        catch (SchedulerException e)
        {
            log.error(e.getMessage());
        }
    }

    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(Job job, CronScheduleBuilder cb)
            throws TaskException
    {
        switch (job.getMisfirePolicy())
        {
            case ScheduleConstants.MISFIRE_DEFAULT:
                return cb;
            case ScheduleConstants.MISFIRE_IGNORE_MISFIRES:
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case ScheduleConstants.MISFIRE_FIRE_AND_PROCEED:
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case ScheduleConstants.MISFIRE_DO_NOTHING:
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                throw new TaskException("The task misfire policy '" + job.getMisfirePolicy()
                        + "' cannot be used in cron schedule tasks", Code.CONFIG_ERROR);
        }
    }
}
