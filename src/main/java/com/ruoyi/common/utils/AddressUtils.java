package com.ruoyi.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.Constants;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取地址类
 * 
 * @author ruoyi
 */
public class AddressUtils
{
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    /**
     * 获取查询结果
     * 
     * @param urlStr
     * @param content
     * @param encoding
     * @return
     */
    private static String sendPost(String content, String encoding)
    {
        URL url = null;
        HttpURLConnection connection = null;
        try
        {
            url = new URL(IP_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(content);
            out.flush();
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        }
        catch (IOException e)
        {
            log.error("温馨提醒：您的主机已经断网，请您检查主机的网络连接");
            log.error("根据IP获取所在位置----------错误消息：" + e.getMessage());
        }
        finally
        {
            if (connection != null)
            {
                connection.disconnect();
            }
        }
        return null;
    }

    public static String getRealAddressByIP(String ip)
    {
        String address = "";
        try
        {
            address = sendPost("ip=" + ip, Constants.UTF8);

            JSONObject json = JSONObject.parseObject(address);
            JSONObject object = json.getObject("data", JSONObject.class);
            String region = object.getString("region");
            String city = object.getString("city");
            address = region + " " + city;
        }
        catch (Exception e)
        {
            log.error("根据IP获取所在位置----------错误消息：" + e.getMessage());
        }
        return address;
    }
}
