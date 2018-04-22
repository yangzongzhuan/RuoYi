package com.ruoyi.project.system.dept.controller;

import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.web.domain.JSON;
import com.ruoyi.project.system.dept.domain.Dept;
import com.ruoyi.project.system.dept.service.IDeptService;

/**
 * 部门信息
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/dept")
public class DeptController
{
    private String prefix = "system/dept";

    @Autowired
    private IDeptService deptService;

    @RequiresPermissions("system:dept:view")
    @GetMapping()
    public String dept()
    {
        return prefix + "/dept";
    }

    @RequiresPermissions("system:dept:list")
    @GetMapping("/list")
    @ResponseBody
    public List<Dept> list()
    {
        List<Dept> deptList = deptService.selectDeptAll();
        return deptList;
    }

    /**
     * 修改
     */
    @Log(title = "系统管理", action = "部门管理-修改部门")
    @RequiresPermissions("system:dept:edit")
    @GetMapping("/edit/{deptId}")
    public String edit(@PathVariable("deptId") Long deptId, Model model)
    {
        Dept dept = deptService.selectDeptById(deptId);
        model.addAttribute("dept", dept);
        return prefix + "/edit";
    }

    /**
     * 新增
     */
    @Log(title = "系统管理", action = "部门管理-新增部门")
    @RequiresPermissions("system:dept:add")
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, Model model)
    {
        Dept dept = deptService.selectDeptById(parentId);
        model.addAttribute("dept", dept);
        return prefix + "/add";
    }

    /**
     * 保存
     */
    @Log(title = "系统管理", action = "部门管理-保存部门")
    @RequiresPermissions("system:dept:save")
    @PostMapping("/save")
    @ResponseBody
    public JSON save(Dept dept)
    {
        if (deptService.saveDept(dept) > 0)
        {
            return JSON.ok();
        }
        return JSON.error();
    }

    /**
     * 删除
     */
    @Log(title = "系统管理", action = "部门管理-删除部门")
    @RequiresPermissions("system:dept:remove")
    @GetMapping("/remove/{deptId}")
    @ResponseBody
    public JSON remove(@PathVariable("deptId") Long deptId)
    {
        if (deptService.selectDeptCount(deptId) > 0)
        {
            return JSON.error(1, "存在下级部门,不允许删除");
        }

        if (deptService.checkDeptExistUser(deptId))
        {
            return JSON.error(1, "部门存在用户,不允许删除");
        }
        if (deptService.deleteDeptById(deptId) > 0)
        {
            return JSON.ok();
        }
        return JSON.error();
    }

    /**
     * 校验部门名称
     */
    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public String checkDeptNameUnique(Dept dept)
    {
        String uniqueFlag = "0";
        if (dept != null)
        {
            uniqueFlag = deptService.checkDeptNameUnique(dept);
        }
        return uniqueFlag;
    }

    /**
     * 选择部门树
     */
    @GetMapping("/selectDeptTree/{deptId}")
    public String selectDeptTree(@PathVariable("deptId") Long deptId, Model model)
    {
        model.addAttribute("treeName", deptService.selectDeptById(deptId).getDeptName());
        return prefix + "/tree";
    }

    /**
     * 加载部门列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Map<String, Object>> treeData()
    {
        List<Map<String, Object>> tree = deptService.selectDeptTree();
        return tree;
    }
}
