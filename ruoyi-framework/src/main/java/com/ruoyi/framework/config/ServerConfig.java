package com.ruoyi.framework.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 服务相关配置
 * 
 * @author ruoyi
 *
 */
@Component
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent>
{
    private int serverPort;

    public String getUrl()
    {
        InetAddress address = null;
        try
        {
            address = InetAddress.getLocalHost();
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        return "http://" + address.getHostAddress() + ":" + this.serverPort;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event)
    {
        this.serverPort = event.getWebServer().getPort();
    }

}
