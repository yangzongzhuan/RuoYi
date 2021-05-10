package com.ruoyi.system.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.ruoyi.common.utils.spring.SpringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Comment;
import com.ruoyi.system.service.ICommentService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
/**
 * Comment_checkController
 * 
 * @author ruoyi
 * @date 2021-05-09
 */
@Controller
@RequestMapping("/system/comment")
public class CommentController extends BaseController
{
    private String prefix = "system/comment";

    @Autowired
    private ICommentService commentService;

    @RequiresPermissions("system:comment:view")
    @GetMapping()
    public String comment()
    {
        return prefix + "/comment";
    }

    /**
     * 查询Comment_check列表
     */
    @RequiresPermissions("system:comment:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Comment comment)
    {
        startPage();
        List<Comment> list = commentService.selectCommentList(comment);
        return getDataTable(list);
    }

    /**
     * 导出Comment_check列表
     */
    @RequiresPermissions("system:comment:export")
    @Log(title = "Comment_check", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Comment comment)
    {
        List<Comment> list = commentService.selectCommentList(comment);
        ExcelUtil<Comment> util = new ExcelUtil<Comment>(Comment.class);
        return util.exportExcel(list, "Comment_check数据");
    }

    /**
     * 新增Comment_check
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存Comment_check
     */
    @RequiresPermissions("system:comment:add")
    @Log(title = "Comment_check", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Comment comment)
    {
        return toAjax(commentService.insertComment(comment));
    }

    /**
     * 修改Comment_check
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Comment comment = commentService.selectCommentById(id);
        mmap.put("comment", comment);
        return prefix + "/edit";
    }

    /**
     * 修改保存Comment_check
     */
    @RequiresPermissions("system:comment:edit")
    @Log(title = "Comment_check", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Comment comment,String captcha)
    {
        String timestamp = "0.3333336222553";
        Comment comment1 = commentService.selectCommentById(comment.getId());

        if(comment1.getState() == comment.getState())
            return error("审核状态未更改");
        else if (comment1.getState()==1)
            return error("已经审核,不可更改状态");
        // 不从其他状态变更为审核状态 则默认操作成功
        if (comment.getState()==2||comment.getState()==0)
            return toAjax(commentService.updateComment(comment));
        else if (comment.getState()<0||comment.getState()>2)
            return error("未知的状态");
        else if (captcha.length()==0)
            return error("请输入验证码");

        System.out.println(captcha);
        String result = post_comment(captcha,comment.getComment(),timestamp);
        if(result==null)
            error("未知错误");
        System.out.println(result);
        if(result.equals("True"))
            return toAjax(commentService.updateComment(comment));
        // 报错
        if(result.equals("False"))
            return error("验证码错误");
        return error("未知错误");
    }

    /**
     * 删除Comment_check
     */
    @RequiresPermissions("system:comment:remove")
    @Log(title = "Comment_check", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(commentService.deleteCommentByIds(ids));
    }
    @PostMapping( "/he")
    @ResponseBody
    public String post_comment(String captcha,String comment,String timestamp) {
        String result = "False";
        String[] args = new String[] { "python", "D:\\repository\\CS\\ruoyi-system\\src\\main\\java\\com\\ruoyi\\system\\controller\\AddComment.py",captcha,comment,timestamp};
        try {
            Process process = Runtime.getRuntime().exec(args);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            while( ( line = in.readLine() ) != null ) {
//                System.out.println(line);
//            }
            result = in.readLine();
            in.close();
            System.out.println("执行结果:" + process.waitFor());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
