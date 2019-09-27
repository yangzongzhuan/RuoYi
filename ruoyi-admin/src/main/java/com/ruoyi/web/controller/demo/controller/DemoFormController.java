package com.ruoyi.web.controller.demo.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.core.domain.AjaxResult;

/**
 * 表单相关
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/demo/form")
public class DemoFormController
{
    private String prefix = "demo/form";

    private final static List<UserFormModel> users = new ArrayList<UserFormModel>();
    {
        users.add(new UserFormModel(1, "1000001", "测试1", "15888888888"));
        users.add(new UserFormModel(2, "1000002", "测试2", "15666666666"));
        users.add(new UserFormModel(3, "1000003", "测试3", "15666666666"));
        users.add(new UserFormModel(4, "1000004", "测试4", "15666666666"));
        users.add(new UserFormModel(5, "1000005", "测试5", "15666666666"));
    }

    /**
     * 按钮页
     */
    @GetMapping("/button")
    public String button()
    {
        return prefix + "/button";
    }

    /**
     * 下拉框
     */
    @GetMapping("/select")
    public String select()
    {
        return prefix + "/select";
    }

    /**
     * 时间轴
     */
    @GetMapping("/timeline")
    public String timeline()
    {
        return prefix + "/timeline";
    }

    /**
     * 表单校验
     */
    @GetMapping("/validate")
    public String validate()
    {
        return prefix + "/validate";
    }

    /**
     * 功能扩展（包含文件上传）
     */
    @GetMapping("/jasny")
    public String jasny()
    {
        return prefix + "/jasny";
    }

    /**
     * 拖动排序
     */
    @GetMapping("/sortable")
    public String sortable()
    {
        return prefix + "/sortable";
    }

    /**
     * 选项卡 & 面板
     */
    @GetMapping("/tabs_panels")
    public String tabs_panels()
    {
        return prefix + "/tabs_panels";
    }

    /**
     * 栅格
     */
    @GetMapping("/grid")
    public String grid()
    {
        return prefix + "/grid";
    }

    /**
     * 表单向导
     */
    @GetMapping("/wizard")
    public String wizard()
    {
        return prefix + "/wizard";
    }

    /**
     * 文件上传
     */
    @GetMapping("/upload")
    public String upload()
    {
        return prefix + "/upload";
    }

    /**
     * 日期和时间页
     */
    @GetMapping("/datetime")
    public String datetime()
    {
        return prefix + "/datetime";
    }

    /**
     * 左右互选组件
     */
    @GetMapping("/duallistbox")
    public String duallistbox()
    {
        return prefix + "/duallistbox";
    }

    /**
     * 基本表单
     */
    @GetMapping("/basic")
    public String basic()
    {
        return prefix + "/basic";
    }

    /**
     * 卡片列表
     */
    @GetMapping("/cards")
    public String cards()
    {
        return prefix + "/cards";
    }

    /**
     * summernote 富文本编辑器
     */
    @GetMapping("/summernote")
    public String summernote()
    {
        return prefix + "/summernote";
    }

    /**
     * 搜索自动补全
     */
    @GetMapping("/autocomplete")
    public String autocomplete()
    {
        return prefix + "/autocomplete";
    }

    /**
     * 获取用户数据
     */
    @GetMapping("/userModel")
    @ResponseBody
    public AjaxResult userModel()
    {
        AjaxResult ajax = new AjaxResult();

        ajax.put("code", 200);
        ajax.put("value", users);
        return ajax;
    }

    /**
     * 获取数据集合
     */
    @GetMapping("/collection")
    @ResponseBody
    public AjaxResult collection()
    {
        String[] array = { "ruoyi 1", "ruoyi 2", "ruoyi 3", "ruoyi 4", "ruoyi 5" };
        AjaxResult ajax = new AjaxResult();
        ajax.put("value", array);
        return ajax;
    }
}

class UserFormModel
{
    /** 用户ID */
    private int userId;

    /** 用户编号 */
    private String userCode;

    /** 用户姓名 */
    private String userName;

    /** 用户手机 */
    private String userPhone;

    public UserFormModel()
    {

    }

    public UserFormModel(int userId, String userCode, String userName, String userPhone)
    {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.userPhone = userPhone;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getUserCode()
    {
        return userCode;
    }

    public void setUserCode(String userCode)
    {
        this.userCode = userCode;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserPhone()
    {
        return userPhone;
    }

    public void setUserPhone(String userPhone)
    {
        this.userPhone = userPhone;
    }

}
