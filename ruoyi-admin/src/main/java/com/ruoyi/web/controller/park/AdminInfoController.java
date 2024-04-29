package com.ruoyi.web.controller.park;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.park.domain.AdminInfo;
import com.ruoyi.park.service.IAdminInfoService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 查询管理员Controller
 * 
 * @author bigcar
 * @date 2024-04-29
 */
@Controller
@RequestMapping("/park/queryAdmin")
public class AdminInfoController extends BaseController
{
    private String prefix = "park/queryAdmin";

    @Autowired
    private IAdminInfoService adminInfoService;

    @RequiresPermissions("park:queryAdmin:view")
    @GetMapping()
    public String queryAdmin()
    {
        return prefix + "/queryAdmin";
    }

    /**
     * 查询查询管理员列表
     */
    @RequiresPermissions("park:queryAdmin:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AdminInfo adminInfo)
    {
        startPage();
        List<AdminInfo> list = adminInfoService.selectAdminInfoList(adminInfo);
        return getDataTable(list);
    }

    /**
     * 导出查询管理员列表
     */
    @RequiresPermissions("park:queryAdmin:export")
    @Log(title = "查询管理员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AdminInfo adminInfo)
    {
        List<AdminInfo> list = adminInfoService.selectAdminInfoList(adminInfo);
        ExcelUtil<AdminInfo> util = new ExcelUtil<AdminInfo>(AdminInfo.class);
        return util.exportExcel(list, "查询管理员数据");
    }

    /**
     * 新增查询管理员
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存查询管理员
     */
    @RequiresPermissions("park:queryAdmin:add")
    @Log(title = "查询管理员", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AdminInfo adminInfo)
    {
        return toAjax(adminInfoService.insertAdminInfo(adminInfo));
    }

    /**
     * 修改查询管理员
     */
    @RequiresPermissions("park:queryAdmin:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        AdminInfo adminInfo = adminInfoService.selectAdminInfoById(id);
        mmap.put("adminInfo", adminInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存查询管理员
     */
    @RequiresPermissions("park:queryAdmin:edit")
    @Log(title = "查询管理员", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AdminInfo adminInfo)
    {
        return toAjax(adminInfoService.updateAdminInfo(adminInfo));
    }

    /**
     * 删除查询管理员
     */
    @RequiresPermissions("park:queryAdmin:remove")
    @Log(title = "查询管理员", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(adminInfoService.deleteAdminInfoByIds(ids));
    }
}
