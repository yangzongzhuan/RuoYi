package com.ruoyi.framework.shiro.session;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.ruoyi.common.core.session.OnlineSession;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.UserAgentUtils;
import com.ruoyi.system.domain.SysUserOnline;

/**
 * 自定义sessionFactory会话
 * 
 * @author ruoyi
 */
@Component
public class OnlineSessionFactory implements SessionFactory
{
    private static final Logger log = LoggerFactory.getLogger(OnlineSessionFactory.class);

    public Session createSession(SysUserOnline userOnline)
    {
        // 优先从序列化数据恢复完整 Session（包含 principals 认证信息）
        if (userOnline.getSessionData() != null && userOnline.getSessionData().length > 0)
        {
            try
            {
                ByteArrayInputStream bis = new ByteArrayInputStream(userOnline.getSessionData());
                ObjectInputStream ois = new ObjectInputStream(bis);
                OnlineSession onlineSession = (OnlineSession) ois.readObject();
                ois.close();
                // 确保 sessionId 和 DB 一致
                if (onlineSession.getId() == null)
                {
                    onlineSession.setId(userOnline.getSessionId());
                }
                return onlineSession;
            }
            catch (Exception e)
            {
                log.warn("deserialize OnlineSession failed, sessionId={}", userOnline.getSessionId(), e);
            }
        }
        // 降级：仅用基础字段重建（无认证信息，会触发重新登录）
        OnlineSession onlineSession = userOnline.getSession();
        if (StringUtils.isNotNull(onlineSession) && onlineSession.getId() == null)
        {
            onlineSession.setId(userOnline.getSessionId());
        }
        return onlineSession;
    }
    
    @Override
    public Session createSession(SessionContext initData)
    {
        OnlineSession session = new OnlineSession();
        if (initData != null && initData instanceof WebSessionContext)
        {
            WebSessionContext sessionContext = (WebSessionContext) initData;
            HttpServletRequest request = (HttpServletRequest) sessionContext.getServletRequest();
            if (request != null)
            {
                String userAgent = request.getHeader("User-Agent");
                // 获取客户端操作系统
                String os = UserAgentUtils.getOperatingSystem(userAgent);
                // 获取客户端浏览器
                String browser = UserAgentUtils.getBrowser(userAgent);
                session.setHost(IpUtils.getIpAddr(request));
                session.setBrowser(browser);
                session.setOs(os);
            }
        }
        return session;
    }
}
