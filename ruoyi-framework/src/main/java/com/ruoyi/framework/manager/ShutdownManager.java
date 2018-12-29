package com.ruoyi.framework.manager;

import com.ruoyi.framework.shiro.web.session.SpringSessionValidationScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PreDestroy;

/**
 * 确保应用退出时能关闭后台线程
 *
 * @author cj
 */
@Component
public class ShutdownManager
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

    /**
     * 停止Seesion会话检查
     */
    private void shutdownSpringSessionValidationScheduler()
    {
        if (springSessionValidationScheduler != null && springSessionValidationScheduler.isEnabled())
        {
            try
            {
                logger.info("====关闭会话验证任务====");
                springSessionValidationScheduler.disableSessionValidation();
            }
            catch (Exception e)
            {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 停止异步执行任务
     */
    private void shutdownAsyncManager()
    {
        try
        {
            logger.info("====关闭后台任务任务线程池====");
            AsyncManager.me().shutdown();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }
}
