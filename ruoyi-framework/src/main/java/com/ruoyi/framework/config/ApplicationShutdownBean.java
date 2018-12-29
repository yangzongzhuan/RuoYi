package com.ruoyi.framework.config;

import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.shiro.web.session.SpringSessionValidationScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * AsyncManagerShutdownBean 类
 *
 * @Auther: cj
 * @Date: 2018/12/28
 */
@Component
public class ApplicationShutdownBean
{
    private static final Logger logger = LoggerFactory.getLogger("sys-user");

    @Autowired(required = false)
    private SpringSessionValidationScheduler springSessionValidationScheduler;

    @PreDestroy
    public void destroy()
    {
        shutdownSpringSessionValidationScheduler();
        shutdownAsyncManager();
    }

    private void shutdownSpringSessionValidationScheduler()
    {
        if (springSessionValidationScheduler != null && springSessionValidationScheduler.isEnabled())
        {
            try
            {
                logger.info("关闭会话验证任务");
                springSessionValidationScheduler.disableSessionValidation();
            }
            catch (Exception e)
            {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void shutdownAsyncManager()
    {
        try
        {
            logger.info("关闭后台任务线程池");
            AsyncManager.me().shutdown(10, TimeUnit.SECONDS);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }
}
