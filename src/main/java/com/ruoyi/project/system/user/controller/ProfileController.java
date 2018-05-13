package com.ruoyi.project.system.user.controller;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.FileUploadUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.Message;
import com.ruoyi.project.system.user.domain.User;
import com.ruoyi.project.system.user.service.IUserService;

/**
 * 个人信息 业务处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/user/profile")
public class ProfileController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    private String prefix = "system/user/profile";

    @Autowired
    private IUserService userService;

    /**
     * 个人信息
     */
    @GetMapping()
    public String profile(Model model)
    {
        User user = getUser();
        String sex = user.getSex();
        if ("0".equals(sex))
        {
            user.setSex("性别：男");
        }
        else if ("1".equals(sex))
        {
            user.setSex("性别：女");
        }
        String roleGroup = userService.selectUserRoleGroup(user.getUserId());
        String postGroup = userService.selectUserPostGroup(user.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("roleGroup", roleGroup);
        model.addAttribute("postGroup", postGroup);
        return prefix + "/profile";
    }

    @RequestMapping("/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password)
    {
        User user = getUser();
        String encrypt = new Md5Hash(user.getLoginName() + password + user.getSalt()).toHex().toString();
        if (user.getPassword().equals(encrypt))
        {
            return true;
        }
        return false;
    }

    @Log(title = "系统管理", action = "个人信息-重置密码")
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, Model model)
    {
        User user = userService.selectUserById(userId);
        model.addAttribute("user", user);
        return prefix + "/resetPwd";
    }

    @Log(title = "系统管理", action = "个人信息-重置密码")
    @PostMapping("/resetPwd")
    @ResponseBody
    public Message resetPwd(User user)
    {
        int rows = userService.resetUserPwd(user);
        if (rows > 0)
        {
            setUser(userService.selectUserById(user.getUserId()));
            return Message.success();
        }
        return Message.error();
    }

    /**
     * 修改用户
     */
    @Log(title = "系统管理", action = "个人信息-修改用户")
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, Model model)
    {
        User user = userService.selectUserById(userId);
        model.addAttribute("user", user);
        return prefix + "/edit";
    }

    /**
     * 修改头像
     */
    @Log(title = "系统管理", action = "个人信息-修改头像")
    @GetMapping("/avatar/{userId}")
    public String avatar(@PathVariable("userId") Long userId, Model model)
    {
        User user = userService.selectUserById(userId);
        model.addAttribute("user", user);
        return prefix + "/avatar";
    }

    /**
     * 修改用户
     */
    @Log(title = "系统管理", action = "个人信息-保存用户")
    @PostMapping("/update")
    @ResponseBody
    public Message update(User user)
    {
        if (userService.updateUser(user) > 0)
        {
            setUser(userService.selectUserById(user.getUserId()));
            return Message.success();
        }
        return Message.error();
    }

    /**
     * 保存头像
     */
    @Log(title = "系统管理", action = "个人信息-保存头像")
    @PostMapping("/updateAvatar")
    @ResponseBody
    public Message updateAvatar(User user, @RequestParam("avatarfile") MultipartFile file)
    {
        try
        {
            if (!file.isEmpty())
            {
                String avatar = FileUploadUtils.upload(file);
                user.setAvatar(avatar);
                if (userService.updateUser(user) > 0)
                {
                    setUser(userService.selectUserById(user.getUserId()));
                    return Message.success();
                }
            }
            return Message.error();
        }
        catch (Exception e)
        {
            log.error("updateAvatar failed!", e);
            return Message.error(e.getMessage());
        }
    }
}
