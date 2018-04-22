package com.ruoyi.framework.web.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据通用处理
 * 
 * @author ruoyi
 */
public class JSON extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;

    public JSON()
    {
        put("code", 0);
        put("msg", "操作成功");
    }

    public static JSON error()
    {
        return error(1, "操作失败");
    }

    public static JSON error(String msg)
    {
        return error(500, msg);
    }

    public static JSON error(int code, String msg)
    {
        JSON json = new JSON();
        json.put("code", code);
        json.put("msg", msg);
        return json;
    }

    public static JSON ok(String msg)
    {
        JSON json = new JSON();
        json.put("msg", msg);
        return json;
    }

    public static JSON ok(Map<String, Object> map)
    {
        JSON json = new JSON();
        json.putAll(map);
        return json;
    }

    public static JSON ok()
    {
        return new JSON();
    }

    @Override
    public JSON put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }
}
