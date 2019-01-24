package com.ruoyi.framework.shiro.web.session;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.ruoyi.common.utils.Threads;

/**
 * 自定义任务调度器完成
 * 
 * @author ruoyi
 */
public class SpringSessionValidationScheduler implements SessionValidationScheduler
{
    private static final Logger log = LoggerFactory.getLogger(SpringSessionValidationScheduler.class);

    public static final long DEFAULT_SESSION_VALIDATION_INTERVAL = DefaultSessionManager.DEFAULT_SESSION_VALIDATION_INTERVAL;

    /**
     * 定时器，用于处理超时的挂起请求，也用于连接断开时的重连。
     */
    @Autowired
    @Qualifier("scheduledExecutorService")
    private ScheduledExecutorService executorService;

    private volatile boolean enabled = false;

    /**
     * The session manager used to validate sessions.
     */
    private ValidatingSessionManager sessionManager;

    /**
     * The session validation interval in milliseconds.
     */
    private long sessionValidationInterval = DEFAULT_SESSION_VALIDATION_INTERVAL;

    /**
     * Default constructor.
     */
    public SpringSessionValidationScheduler()
    {
    }

    /**
     * Constructor that specifies the session manager that should be used for validating sessions.
     *
     * @param sessionManager the <tt>SessionManager</tt> that should be used to validate sessions.
     */
    public SpringSessionValidationScheduler(ValidatingSessionManager sessionManager)
    {
        this.sessionManager = sessionManager;
    }

    public void setSessionManager(ValidatingSessionManager sessionManager)
    {
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean isEnabled()
    {
        return this.enabled;
    }

    /**
     * Specifies how frequently (in milliseconds) this Scheduler will call the
     * {@link org.apache.shiro.session.mgt.ValidatingSessionManager#validateSessions()
     * ValidatingSessionManager#validateSessions()} method.
     *
     * <p>
     * Unless this method is called, the default value is {@link #DEFAULT_SESSION_VALIDATION_INTERVAL}.
     *
     * @param sessionValidationInterval
     */
    public void setSessionValidationInterval(long sessionValidationInterval)
    {
        this.sessionValidationInterval = sessionValidationInterval;
    }

    /**
     * Starts session validation by creating a spring PeriodicTrigger.
     */
    @Override
    public void enableSessionValidation()
    {

        enabled = true;

        if (log.isDebugEnabled())
        {
            log.debug("Scheduling session validation job using Spring Scheduler with "
                    + "session validation interval of [" + sessionValidationInterval + "]ms...");
        }

        try
        {
            executorService.scheduleAtFixedRate(new Runnable()
            {
                @Override
                public void run()
                {
                    if (enabled)
                    {
                        sessionManager.validateSessions();
                    }
                }
            }, 1000, sessionValidationInterval, TimeUnit.MILLISECONDS);

            this.enabled = true;

            if (log.isDebugEnabled())
            {
                log.debug("Session validation job successfully scheduled with Spring Scheduler.");
            }

        }
        catch (Exception e)
        {
            if (log.isErrorEnabled())
            {
                log.error("Error starting the Spring Scheduler session validation job.  Session validation may not occur.", e);
            }
        }
    }

    @Override
    public void disableSessionValidation()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Stopping Spring Scheduler session validation job...");
        }

        if (this.enabled)
        {
            Threads.shutdownAndAwaitTermination(executorService);
        }
        this.enabled = false;
    }
}
