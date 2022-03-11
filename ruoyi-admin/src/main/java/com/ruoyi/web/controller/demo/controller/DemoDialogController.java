package com.ruoyi.web.controller.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 模态窗口
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/demo/modal")
public class DemoDialogController
{
    private String prefix = "demo/modal";

    /**
     * 模态窗口
     */
    @GetMapping("/dialog")
    public String dialog()
    {
        return prefix + "/dialog";
    }

    /**
     * 弹层组件
     */
    @GetMapping("/layer")
    public String layer()
    {
        return prefix + "/layer";
    }

    /**
     * 表单
     */
    @GetMapping("/form")
    public String form()
    {
        return prefix + "/form";
    }

    /**
     * 表格
     */
    @GetMapping("/table")
    public String table()
    {
        return prefix + "/table";
    }

    /**
     * 表格check
     */
    @GetMapping("/check")
    public String check()
    {
        return prefix + "/table/check";
    }

    /**
     * 表格radio
     */
    @GetMapping("/radio")
    public String radio()
    {
        return prefix + "/table/radio";
    }

    /**
     * 表格回传父窗体
     */
    @GetMapping("/parent")
    public String parent()
    {
        return prefix + "/table/parent";
    }

    /**
     * 多层窗口frame1
     */
    @GetMapping("/frame1")
    public String frame1()
    {
        return prefix + "/table/frame1";
    }

    /**
     * 多层窗口frame2
     */
    @GetMapping("/frame2")
    public String frame2()
    {
        return prefix + "/table/frame2";
    }
}
