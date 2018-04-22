package com.ruoyi.project.monitor.job.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.JSON;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.monitor.job.domain.JobLog;
import com.ruoyi.project.monitor.job.service.IJobLogService;

/**
 * 调度日志操作处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/monitor/jobLog")
public class JobLogController extends BaseController
{
    private String prefix = "monitor/job";

    @Autowired
    private IJobLogService jobLogService;

    @RequiresPermissions("monitor:job:view")
    @GetMapping()
    public String jobLog()
    {
        return prefix + "/jobLog";
    }

    @RequiresPermissions("monitor:job:list")
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(JobLog jobLog)
    {
        setPageInfo(jobLog);
        List<JobLog> list = jobLogService.selectJobLogList(jobLog);
        return getDataTable(list);
    }

    /**
     * 调度日志删除
     */
    @Log(title = "监控管理", action = "定时任务-删除调度日志")
    @RequiresPermissions("monitor:job:remove")
    @RequestMapping("/remove/{jobLogId}")
    @ResponseBody
    public JSON remove(@PathVariable("jobLogId") Long jobLogId)
    {
        JobLog jobLog = jobLogService.selectJobLogById(jobLogId);
        if (jobLog == null)
        {
            return JSON.error("调度任务不存在");
        }
        if (jobLogService.deleteJobLogById(jobLogId) > 0)
        {
            return JSON.ok();
        }
        return JSON.error();
    }

    @Log(title = "监控管理", action = "定时任务-批量删除日志")
    @RequiresPermissions("monitor:job:batchRemove")
    @PostMapping("/batchRemove")
    @ResponseBody
    public JSON batchRemove(@RequestParam("ids[]") Long[] ids)
    {
        try
        {
            jobLogService.batchDeleteJoblog(ids);
            return JSON.ok();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return JSON.error(e.getMessage());
        }
    }
}
