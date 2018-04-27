package com.ruoyi.project.system.user.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.Message;

/**
 * 登录验证
 * 
 * @author ruoyi
 */
// @RestController
@Controller
public class LoginController extends BaseController
{

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Message ajaxLogin(String username, String password)
    {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
            return Message.ok();
        }
        catch (AuthenticationException e)
        {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return Message.error(msg);
        }
    }

    @GetMapping("/unauth")
    public String unauth()
    {
        return "/error/unauth";
    }
}
