package com.ruoyi.project.system.notice.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.project.system.notice.domain.Notice;
import com.ruoyi.project.system.notice.service.INoticeService;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.constant.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.framework.web.domain.AjaxResult;

/**
 * 公告 信息操作处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/notice")
public class NoticeController extends BaseController
{
    private String prefix = "system/notice";

    @Autowired
    private INoticeService noticeService;

    @RequiresPermissions("system:notice:view")
    @GetMapping()
    public String notice()
    {
        return prefix + "/notice";
    }

    /**
     * 查询公告列表
     */
    @RequiresPermissions("system:notice:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Notice notice)
    {
        startPage();
        List<Notice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 新增公告
     */
    @RequiresPermissions("system:notice:add")
    @Log(title = "通知公告", action = BusinessType.INSERT)
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 修改公告
     */
    @RequiresPermissions("system:notice:edit")
    @Log(title = "通知公告", action = BusinessType.UPDATE)
    @GetMapping("/edit/{noticeId}")
    public String edit(@PathVariable("noticeId") Integer noticeId, Model model)
    {
        Notice notice = noticeService.selectNoticeById(noticeId);
        model.addAttribute("notice", notice);
        return prefix + "/edit";
    }

    /**
     * 保存公告
     */
    @RequiresPermissions("system:notice:save")
    @Log(title = "通知公告", action = BusinessType.SAVE)
    @PostMapping("/save")
    @ResponseBody
    public AjaxResult save(Notice notice)
    {
        if (noticeService.saveNotice(notice) > 0)
        {
            return success();
        }
        return error();
    }

    /**
     * 删除公告
     */
    @RequiresPermissions("system:notice:remove")
    @Log(title = "通知公告", action = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        int rows = noticeService.deleteNoticeByIds(ids);
        if (rows > 0)
        {
            return success();
        }
        return error();
    }

}
