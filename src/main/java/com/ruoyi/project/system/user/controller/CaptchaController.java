package com.ruoyi.project.system.user.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.ruoyi.framework.web.controller.BaseController;

/**
 * 图片验证码（支持算术形式）
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController extends BaseController
{
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    /**
     * 验证码生成
     */
    @GetMapping(value = "/captchaImage")
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response)
    {
        ServletOutputStream out = null;
        try
        {
            HttpSession session = request.getSession();
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");

            String type = request.getParameter("type");
            String capStr = null;
            String code = null;
            BufferedImage bi = null;
            if ("math".equals(type))
            {
                String capText = captchaProducerMath.createText();
                capStr = capText.substring(0, capText.lastIndexOf("@"));
                code = capText.substring(capText.lastIndexOf("@") + 1);
                bi = captchaProducerMath.createImage(capStr);
            }
            else if ("char".equals(type))
            {
                capStr = code = captchaProducer.createText();
                bi = captchaProducer.createImage(capStr);
            }
            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, code);
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
}