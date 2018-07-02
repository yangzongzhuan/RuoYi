package com.ruoyi.project.system.config.controller;

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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.constant.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.config.domain.Config;
import com.ruoyi.project.system.config.service.IConfigService;

/**
 * 参数配置 信息操作处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/config")
public class ConfigController extends BaseController
{
    private String prefix = "system/config";

    @Autowired
    private IConfigService configService;

    @RequiresPermissions("system:config:view")
    @GetMapping()
    public String index()
    {
        return prefix + "/config";
    }

    /**
     * 查询参数配置列表
     */
    @RequiresPermissions("system:config:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Config config)
    {
        startPage();
        List<Config> list = configService.selectConfigList(config);
        return getDataTable(list);
    }
    

    @Log(title = "参数管理", action = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Config config) throws Exception
    {
        try
        {
            List<Config> list = configService.selectConfigList(config);
            ExcelUtil<Config> util = new ExcelUtil<Config>(Config.class);
            return util.exportExcel(list, "config");
        }
        catch (Exception e)
        {
            return error("导出Excel失败，请联系网站管理员！");
        }
    }

    /**
     * 新增参数配置
     */
    @RequiresPermissions("system:config:add")
    @Log(title = "参数管理", action = BusinessType.INSERT)
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 修改参数配置
     */
    @RequiresPermissions("system:config:edit")
    @Log(title = "参数管理", action = BusinessType.UPDATE)
    @GetMapping("/edit/{configId}")
    public String edit(@PathVariable("configId") Integer configId, Model model)
    {
        Config config = configService.selectConfigById(configId);
        model.addAttribute("config", config);
        return prefix + "/edit";
    }

    /**
     * 保存参数配置
     */
    @RequiresPermissions("system:config:save")
    @Log(title = "参数管理", action = BusinessType.SAVE)
    @PostMapping("/save")
    @ResponseBody
    public AjaxResult save(Config config)
    {
        if (configService.saveConfig(config) > 0)
        {
            return success();
        }
        return error();
    }

    /**
     * 删除参数配置
     */
    @RequiresPermissions("system:config:remove")
    @Log(title = "参数管理", action = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        if (configService.deleteConfigByIds(ids) > 0)
        {
            return success();
        }
        return error();
    }

    /**
     * 校验参数键名
     */
    @PostMapping("/checkConfigKeyUnique")
    @ResponseBody
    public String checkConfigKeyUnique(Config config)
    {
        String uniqueFlag = "0";
        if (config != null)
        {
            uniqueFlag = configService.checkConfigKeyUnique(config);
        }
        return uniqueFlag;
    }

}
