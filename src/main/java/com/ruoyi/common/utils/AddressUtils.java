package com.ruoyi.common.utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 获取地址类
 */
public class AddressUtils {
    public static String getAddresses(String content, String encodingString) throws UnsupportedEncodingException {
       /** 根据IP查询地址 http://ip.taobao.com/service/getIpInfo.php？ip=111.111.111.111*/
        String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
        String returnStr = getResult(urlStr, content, encodingString);
        if (returnStr != null) {
            returnStr = decodeUnicode(returnStr);
            String[] temp = returnStr.split(",");
            if (temp.length < 3) {
                return "0";
            }
            return returnStr;
        }
        return null;
    }

    /**
     * 获取查询结果
     * @param urlStr
     * @param content
     * @param encoding
     * @return
     */
    private static String getResult(String urlStr, String content, String encoding) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlStr);
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
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } catch (IOException e) {
            System.out.println("温馨提醒：您的主机已经断网，请您检查主机的网络连接");
            System.out.println("根据IP获取所在位置----------错误消息："+e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    /**
     *
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed      encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    public static String getRealAddressByIP(String ip) {
        String address = "";
        try {

            address = getAddresses("ip=" + ip, "utf-8");

            ////把JSON文本parse成JSONObject,通俗就是把json文本转为json对象
            JSONObject json= JSONObject.parseObject(address);

            //通过其get的方法来获取data的value由于返回的是object对象，而data的value本身又是json字符串，所以我们可以进行强转
            JSONObject object = (JSONObject)json.get("data");
            String country=object.getString("country");
            String region = object.getString("region");
            String city = object.getString("city");
            address = country+""+region + "" + city;
        } catch (Exception e) {

        }
        return address;
    }

    public static void main(String[] args) {
        try {
            System.out.println(getAddresses("ip=111.85.32.37","utf-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(getRealAddressByIP("111.85.32.37"));
    }
}
