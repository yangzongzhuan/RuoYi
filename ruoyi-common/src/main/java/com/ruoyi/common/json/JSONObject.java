package com.ruoyi.common.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.utils.StringUtils;

/**
 * 通用消息对象，基于Map实现的可嵌套数据结构。 支持JSON数据结构。
 * 
 * @author ruoyi
 */
public class JSONObject extends LinkedHashMap<String, Object>
{
    private static final long serialVersionUID = 1L;
    private static final Pattern arrayNamePattern = Pattern.compile("(\\w+)((\\[\\d+\\])+)");
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 数组结构。
     */
    public static class JSONArray extends ArrayList<Object>
    {
        private static final long serialVersionUID = 1L;

        public JSONArray()
        {
            super();
        }

        public JSONArray(int size)
        {
            super(size);
        }

        @Override
        public String toString()
        {
            try
            {
                return JSON.marshal(this);
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Object set(int index, Object element)
        {
            return super.set(index, transfer(element));
        }

        @Override
        public boolean add(Object element)
        {
            return super.add(transfer(element));
        }

        @Override
        public void add(int index, Object element)
        {
            super.add(index, transfer(element));
        }
    }

    public JSONObject()
    {
        super();
    }

    public JSONObject(final JSONObject other)
    {
        super(other);
    }

    @Override
    public String toString()
    {
        try
        {
            return JSON.marshal(this);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 转换为紧凑格式的字符串。
     * 
     * @return 返回本对象紧凑格式字符串。
     */
    public String toCompactString()
    {
        try
        {
            return objectMapper.writeValueAsString(this);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取指定字段的整数值。如果字段不存在，或者无法转换为整数，返回null。
     * 
     * @param name 字段名，支持多级。
     * @return 返回指定的整数值，或者null。
     */
    public Integer intValue(final String name)
    {
        return valueAsInt(value(name));
    }

    /**
     * 获取指定字段的整数值。如果字段不存在，或者无法转换为整数，返回defaultValue。
     * 
     * @param name 字段名，支持多级。
     * @param defaultValue 查询失败时，返回的值。
     * @return 返回指定的整数值，或者defaultValue。
     */
    public Integer intValue(final String name, final Integer defaultValue)
    {
        return StringUtils.nvl(intValue(name), defaultValue);
    }

    /**
     * 获取指定字段的长整数值。如果字段不存在，或者无法转换为长整数，返回null。
     * 
     * @param name 字段名，支持多级。
     * @return 返回指定的长整数值，或者null。
     */
    public Long longValue(final String name)
    {
        return valueAsLong(value(name));
    }

    /**
     * 获取指定字段的长整数值。如果字段不存在，或者无法转换为长整数，返回defaultValue。
     * 
     * @param name 字段名，支持多级。
     * @param defaultValue 查询失败时，返回的值。
     * @return 返回指定的长整数值，或者defaultValue。
     */
    public Long longValue(final String name, final Long defaultValue)
    {
        return StringUtils.nvl(longValue(name), defaultValue);
    }

    /**
     * 获取指定字段的布尔值。如果字段不存在，或者无法转换为布尔型，返回null。
     * 
     * @param name 字段名，支持多级。
     * @return 返回指定的布尔值，或者null。
     */
    public Boolean boolValue(final String name)
    {
        return valueAsBool(value(name));
    }

    /**
     * 获取指定字段的布尔值。如果字段不存在，或者无法转换为布尔型，返回defaultValue。
     * 
     * @param name 字段名，支持多级。
     * @param defaultValue 查询失败时，返回的值。
     * @return 返回指定的布尔值，或者defaultValue。
     */
    public Boolean boolValue(final String name, final Boolean defaultValue)
    {
        return StringUtils.nvl(boolValue(name), defaultValue);
    }

    /**
     * 获取指定字段的字符串值。如果字段不存在，返回null。
     * 
     * @param name 字段名，支持多级。
     * @return 返回指定的字符串值，或者null。
     */
    public String strValue(final String name)
    {
        return valueAsStr(value(name));
    }

    /**
     * 获取指定字段的字符串值。如果字段不存在，返回defaultValue。
     * 
     * @param name 字段名，支持多级。
     * @param defaultValue 查询失败时，返回的值。
     * @return 返回指定的字符串值，或者defaultValue。
     */
    public String strValue(final String name, final String defaultValue)
    {
        return StringUtils.nvl(strValue(name), defaultValue);
    }

    /**
     * 获取指定字段的值。
     * 
     * @param name 字段名，支持多级，支持数组下标。
     * @return 返回指定字段的值。
     */
    public Object value(final String name)
    {
        final int indexDot = name.indexOf('.');
        if (indexDot >= 0)
        {
            return obj(name.substring(0, indexDot)).value(name.substring(indexDot + 1));
        }
        else
        {
            final Matcher matcher = arrayNamePattern.matcher(name);
            if (matcher.find())
            {
                return endArray(matcher.group(1), matcher.group(2), new EndArrayCallback<Object>()
                {
                    @Override
                    public Object callback(JSONArray arr, int index)
                    {
                        return elementAt(arr, index);
                    }
                });
            }
            else
            {
                return get(name);
            }
        }
    }

    /**
     * 设置指定字段的值。
     * 
     * @param name 字段名，支持多级，支持数组下标。
     * @param value 字段值。
     * @return 返回本对象。
     */
    public JSONObject value(final String name, final Object value)
    {
        final int indexDot = name.indexOf('.');
        if (indexDot >= 0)
        {
            obj(name.substring(0, indexDot)).value(name.substring(indexDot + 1), value);
        }
        else
        {
            final Matcher matcher = arrayNamePattern.matcher(name);
            if (matcher.find())
            {
                endArray(matcher.group(1), matcher.group(2), new EndArrayCallback<Void>()
                {
                    @Override
                    public Void callback(JSONArray arr, int index)
                    {
                        elementAt(arr, index, value);
                        return null;
                    }
                });
            }
            else
            {
                set(name, value);
            }
        }
        return this;
    }

    /**
     * 获取对象（非标量类型）字段。返回的数据是一个结构体。当不存在指定对象时，则为指定的名字创建一个空的MessageObject对象。
     * 
     * @param name 字段名。不支持多级名字，支持数组下标。
     * @return 返回指定的对象。如果对象不存在，则为指定的名字创建一个空的MessageObject对象。
     */
    public JSONObject obj(final String name)
    {
        final Matcher matcher = arrayNamePattern.matcher(name);
        if (matcher.find())
        {
            return endArray(matcher.group(1), matcher.group(2), new EndArrayCallback<JSONObject>()
            {
                @Override
                public JSONObject callback(JSONArray arr, int index)
                {
                    return objAt(arr, index);
                }
            });
        }
        else
        {
            JSONObject obj = getObj(name);
            if (obj == null)
            {
                obj = new JSONObject();
                put(name, obj);
            }
            return obj;
        }
    }

    /**
     * 获取数组字段。将名字对应的对象以数组对象返回，当指定的字段不存在时，创建一个空的数组。
     * 
     * @param name 字段名。不支持多级名字，不支持下标。
     * @return 返回一个数组（List）。
     */
    public JSONArray arr(final String name)
    {
        JSONArray arr = getArr(name);
        if (arr == null)
        {
            arr = new JSONArray();
            put(name, arr);
        }
        return arr;
    }

    /**
     * 获取对象（非标量类型）字段。返回的数据是一个结构体。
     * 
     * @param name 字段名。
     * @return 返回指定的对象字段。
     */
    public JSONObject getObj(final String name)
    {
        return (JSONObject) get(name);
    }

    /**
     * 获取数组类型字段。
     * 
     * @param name 字段名。
     * @return 返回数组类型字段。
     */
    public JSONArray getArr(final String name)
    {
        return (JSONArray) get(name);
    }

    /**
     * 返回字段整数值。如果不存在，返回null。
     * 
     * @param name 字段名。
     * @return 返回指定字段整数值。
     */
    public Integer getInt(final String name)
    {
        return valueAsInt(get(name));
    }

    /**
     * 返回字段整数值。如果不存在，返回defaultValue。
     * 
     * @param name 字段名。
     * @param defaultValue 字段不存在时，返回的值。
     * @return 返回指定字段整数值。
     */
    public Integer getInt(final String name, Integer defaultValue)
    {
        return StringUtils.nvl(getInt(name), defaultValue);
    }

    /**
     * 返回字段长整数值。如果不存在，返回null。
     * 
     * @param name 字段名。
     * @return 返回指定字段长整数值。
     */
    public Long getLong(final String name)
    {
        return valueAsLong(get(name));
    }

    /**
     * 返回字段长整数值。如果不存在，返回defaultValue。
     * 
     * @param name 字段名。
     * @param defaultValue 字段不存在时，返回的值。
     * @return 返回指定字段长整数值。
     */
    public Long getLong(final String name, Long defaultValue)
    {
        return StringUtils.nvl(getLong(name), defaultValue);
    }

    /**
     * 返回字段字符串值。如果不存在，返回null。
     * 
     * @param name 字段名。
     * @return 返回指定字段字符串值。
     */
    public String getStr(final String name)
    {
        return valueAsStr(get(name));
    }

    /**
     * 返回字段字符串值。如果不存在，返回defaultValue。
     * 
     * @param name 字段名。
     * @param defaultValue 字段不存在时，返回的值。
     * @return 返回指定字段字符串值。
     */
    public String getStr(final String name, final String defaultValue)
    {
        return StringUtils.nvl(getStr(name), defaultValue);
    }

    /**
     * 字段值按照布尔类型返回。如果不存在，返回null。
     * 
     * @param name 字段名。
     * @return 字段值。
     */
    public Boolean getBool(final String name)
    {
        return valueAsBool(get(name));
    }

    /**
     * 字段值按照布尔类型返回。如果不存在，返回defaultValue。
     * 
     * @param name 字段名。
     * @param defaultValue 字段不存在时，返回的值。
     * @return 字段值。
     */
    public Boolean getBool(final String name, final Boolean defaultValue)
    {
        return StringUtils.nvl(getBool(name), defaultValue);
    }

    /**
     * 设置字段值
     * 
     * @param name 字段名
     * @param value 字段值（标量：数字、字符串、布尔型；结构体：MessageObject）。 如果是Map类型同时非MessageObject类型，则自动转换为MessageObject类型再存入
     *            （此时，再修改Map中的数据，将不会体现到本对象中）。
     * @return 返回本对象
     */
    public JSONObject set(final String name, final Object value)
    {
        put(name, value);
        return this;
    }

    /**
     * 将本对象转换为Java Bean。
     * 
     * @param beanClass Java Bean的类对象。
     * @return 返回转换后的Java Bean。
     */
    public <T> T asBean(Class<T> beanClass)
    {
        try
        {
            return JSON.unmarshal(JSON.marshal(this), beanClass);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 重载基类的方法。如果 value 是 Map 类型，但不是 MessageObject 类型，则创建一个包含内容等同于原 Map 的 MessageObject 作为 value（注意：此后再更改 Map 的内容，将不会反映到
     * MessageObject 中）。 重载此方法的目的是为了使JSON能够正确地解析为MessageObject对象。不建议直接调用此方法，请使用 set(name, value)方法设置字段值。
     */
    @Override
    public Object put(String key, Object value)
    {
        return super.put(key, transfer(value));
    }

    public static Integer valueAsInt(Object value)
    {
        if (value instanceof Integer)
        {
            return (Integer) value;
        }
        else if (value instanceof Number)
        {
            return ((Number) value).intValue();
        }
        else if (value instanceof String)
        {
            return Integer.valueOf((String) value);
        }
        else if (value instanceof Boolean)
        {
            return ((Boolean) value) ? 1 : 0;
        }
        else
        {
            return null;
        }
    }

    public static Long valueAsLong(Object value)
    {
        if (value instanceof Long)
        {
            return (Long) value;
        }
        else if (value instanceof Number)
        {
            return ((Number) value).longValue();
        }
        else if (value instanceof String)
        {
            return Long.valueOf((String) value);
        }
        else if (value instanceof Boolean)
        {
            return ((Boolean) value) ? 1L : 0L;
        }
        else
        {
            return null;
        }
    }

    public static String valueAsStr(Object value)
    {
        if (value instanceof String)
        {
            return (String) value;
        }
        else if (value != null)
        {
            return value.toString();
        }
        else
        {
            return null;
        }
    }

    public static Boolean valueAsBool(Object value)
    {
        if (value instanceof Boolean)
        {
            return (Boolean) value;
        }
        else if (value instanceof Number)
        {
            return ((Number) value).doubleValue() != 0.0;
        }
        else if (value instanceof String)
        {
            return Boolean.valueOf((String) value);
        }
        else
        {
            return null;
        }
    }

    /**
     * 将所有层次中凡是Map类型同时又不是MessageObject的类型，转换为MessageObject类型。
     * 
     * @param value 值。
     * @return 返回转换后的值。
     */
    @SuppressWarnings("unchecked")
    private static Object transfer(final Object value)
    {
        if (!(value instanceof JSONObject) && value instanceof Map)
        {
            return toObj((Map<String, Object>) value);
        }
        else if (!(value instanceof JSONArray) && value instanceof Collection)
        {
            return toArr((Collection<Object>) value);
        }
        else
        {
            return value;
        }
    }

    private static JSONArray toArr(final Collection<Object> list)
    {
        final JSONArray arr = new JSONArray(list.size());
        for (final Object element : list)
        {
            arr.add(element);
        }
        return arr;
    }

    private static JSONObject toObj(final Map<String, Object> map)
    {
        final JSONObject obj = new JSONObject();
        for (final Map.Entry<String, Object> ent : map.entrySet())
        {
            obj.put(ent.getKey(), transfer(ent.getValue()));
        }
        return obj;
    }

    /**
     * 将指定下标元素作为数组返回，如果不存在，则在该位置创建一个空的数组。
     * 
     * @param arr 当前数组。
     * @param index 下标。
     * @return 返回当前数组指定下标的元素，该元素应该是一个数组。
     */
    private static JSONArray arrayAt(JSONArray arr, int index)
    {
        expand(arr, index);
        if (arr.get(index) == null)
        {
            arr.set(index, new JSONArray());
        }
        return (JSONArray) arr.get(index);
    }

    /**
     * 将指定下标元素作为结构体返回，如果不存在，则在该位置创建一个空的结构体。
     * 
     * @param arr 当前数组。
     * @param index 下标。
     * @return 返回当前数组指定下标元素，该元素是一个结构体。
     */
    private static JSONObject objAt(final JSONArray arr, int index)
    {
        expand(arr, index);
        if (arr.get(index) == null)
        {
            arr.set(index, new JSONObject());
        }
        return (JSONObject) arr.get(index);
    }

    /**
     * 设置数组指定下标位置的值。
     * 
     * @param arr 数组。
     * @param index 下标。
     * @param value 值。
     */
    private static void elementAt(final JSONArray arr, final int index, final Object value)
    {
        expand(arr, index).set(index, value);
    }

    /**
     * 获取数组指定下标元素的值。
     * 
     * @param arr 数组。
     * @param index 下标。
     * @return 值。
     */
    private static Object elementAt(final JSONArray arr, final int index)
    {
        return expand(arr, index).get(index);
    }

    /**
     * 扩展数组到指定下标，以防止访问时下标越界。
     * 
     * @param arr 数组
     * @param index 下标
     * @return 返回传入的数组
     */
    private static JSONArray expand(final JSONArray arr, final int index)
    {
        while (arr.size() <= index)
        {
            arr.add(null);
        }
        return arr;
    }

    /**
     * 最后数组回调。
     * 
     * @author Mike
     *
     * @param <T> 回调返回数据类型。
     */
    private interface EndArrayCallback<T>
    {
        /**
         * 当定位到最后一级数组，将调用本方法。
         * 
         * @param arr 最后一级数组对象。
         * @param index 最后一级索引。
         * @return 返回回调的返回值。
         */
        T callback(JSONArray arr, int index);
    }

    /**
     * 处理多维数组的工具函数（包括一维数组）。多维数组的名字如：arrary[1][2][3]， 则name=array，indexStr=[1][2][3]，在callback中，endArr将是
     * array[1][2]指定的对象，indexe=3。
     * 
     * @param name 不带下标的名字，不支持多级名字。
     * @param indexesStr 索引部分的字符串，如：[1][2][3]
     * @param callback 回调函数。
     * @return 返回回调函数的返回值。
     */
    private <T> T endArray(final String name, final String indexesStr, final EndArrayCallback<T> callback)
    {
        JSONArray endArr = arr(name);
        final int[] indexes = parseIndexes(indexesStr);
        int i = 0;
        while (i < indexes.length - 1)
        {
            endArr = arrayAt(endArr, indexes[i++]);
        }
        return callback.callback(endArr, indexes[i]);
    }

    private static int[] parseIndexes(final String s)
    {
        int[] indexes = null;
        List<Integer> list = new ArrayList<Integer>();

        final StringTokenizer st = new StringTokenizer(s, "[]");
        while (st.hasMoreTokens())
        {
            final int index = Integer.valueOf(st.nextToken());
            if (index < 0)
            {
                throw new RuntimeException(String.format("Illegal index %1$d in \"%2$s\"", index, s));
            }

            list.add(index);
        }

        indexes = new int[list.size()];
        int i = 0;
        for (Integer tmp : list.toArray(new Integer[list.size()]))
        {
            indexes[i++] = tmp;
        }

        return indexes;
    }
}
