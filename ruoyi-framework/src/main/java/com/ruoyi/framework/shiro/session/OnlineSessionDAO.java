package com.ruoyi.framework.shiro.session;

import java.io.Serializable;
import java.util.Date;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.ruoyi.common.enums.OnlineStatus;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.shiro.service.SysShiroService;

/**
 * 针对自定义的ShiroSession的db操作
 * 
 * @author ruoyi
 */
public class OnlineSessionDAO extends EnterpriseCacheSessionDAO
{
    /**
     * 同步session到数据库的周期 单位为毫秒（默认1分钟）
     */
    @Value("${shiro.session.dbSyncPeriod}")
    private int dbSyncPeriod;

    /**
     * 上次同步数据库的时间戳
     */
    private static final String LAST_SYNC_DB_TIMESTAMP = OnlineSessionDAO.class.getName() + "LAST_SYNC_DB_TIMESTAMP";

    @Autowired
    private SysShiroService sysShiroService;

    public OnlineSessionDAO()
    {
        super();
    }

    public OnlineSessionDAO(long expireTime)
    {
        super();
    }

    /**
     * 根据会话ID获取会话
     *
     * @param sessionId 会话ID
     * @return ShiroSession
     */
    @Override
    protected Session doReadSession(Serializable sessionId)
    {
        return sysShiroService.getSession(sessionId);
    }

    @Override
    public void update(Session session) throws UnknownSessionException
    {
        super.update(session);
    }

    /**
     * 更新会话；如更新会话最后访问时间/停止会话/设置超时时间/设置移除属性等会调用
     */
    public void syncToDb(OnlineSession onlineSession)
    {
        Date lastSyncTimestamp = (Date) onlineSession.getAttribute(LAST_SYNC_DB_TIMESTAMP);
        if (lastSyncTimestamp != null)
        {
            boolean needSync = true;
            long deltaTime = onlineSession.getLastAccessTime().getTime() - lastSyncTimestamp.getTime();
            if (deltaTime < dbSyncPeriod * 60 * 1000)
            {
                // 时间差不足 无需同步
                needSync = false;
            }
            // isGuest = true 访客
            boolean isGuest = onlineSession.getUserId() == null || onlineSession.getUserId() == 0L;

            // session 数据变更了 同步
            if (!isGuest == false && onlineSession.isAttributeChanged())
            {
                needSync = true;
            }

            if (!needSync)
            {
                return;
            }
        }
        // 更新上次同步数据库时间
        onlineSession.setAttribute(LAST_SYNC_DB_TIMESTAMP, onlineSession.getLastAccessTime());
        // 更新完后 重置标识
        if (onlineSession.isAttributeChanged())
        {
            onlineSession.resetAttributeChanged();
        }
        AsyncManager.me().execute(AsyncFactory.syncSessionToDb(onlineSession));
    }

    /**
     * 当会话过期/停止（如用户退出时）属性等会调用
     */
    @Override
    protected void doDelete(Session session)
    {
        OnlineSession onlineSession = (OnlineSession) session;
        if (null == onlineSession)
        {
            return;
        }
        onlineSession.setStatus(OnlineStatus.off_line);
        sysShiroService.deleteSession(onlineSession);
    }
}
