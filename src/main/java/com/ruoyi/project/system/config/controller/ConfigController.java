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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.project.system.config.domain.Config;
import com.ruoyi.project.system.config.service.IConfigService;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.framework.web.domain.Message;

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

    /**
     * 新增参数配置
     */
    @RequiresPermissions("system:config:add")
    @Log(title = "系统管理", action = "参数配置-新增参数")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 修改参数配置
     */
    @RequiresPermissions("system:config:edit")
    @Log(title = "系统管理", action = "参数配置-修改参数")
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
    @Log(title = "系统管理", action = "参数配置-保存参数")
    @PostMapping("/save")
    @ResponseBody
    public Message save(Config config)
    {
        if (configService.saveConfig(config) > 0)
        {
            return Message.success();
        }
        return Message.error();
    }

    /**
     * 删除参数配置
     */
    @RequiresPermissions("system:config:remove")
    @Log(title = "系统管理", action = "参数配置-删除参数")
    @PostMapping("/remove/{configId}")
    @ResponseBody
    public Message remove(@PathVariable("configId") Integer configId)
    {
        if (configService.deleteConfigById(configId) > 0)
        {
            return Message.success();
        }
        return Message.error();
    }

    /**
     * 批量删除参数配置
     */
    @RequiresPermissions("system:config:batchRemove")
    @Log(title = "系统管理", action = "参数配置-批量删除")
    @PostMapping("/batchRemove")
    @ResponseBody
    public Message remove(@RequestParam("ids[]") Integer[] configIds)
    {
        int rows = configService.batchDeleteConfig(configIds);
        if (rows > 0)
        {
            return Message.success();
        }
        return Message.error();
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
