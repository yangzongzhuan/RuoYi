package com.ruoyi.common.utils.sql;

import java.util.regex.Pattern;
import com.ruoyi.common.exception.UtilException;
import com.ruoyi.common.utils.StringUtils;

/**
 * sql操作工具类
 * 
 * @author ruoyi
 */
public class SqlUtil
{
    /**
     * 定义常用的 sql关键字
     */
    public static String SQL_REGEX = "\u000B|%0A|and |extractvalue|updatexml|sleep|information_schema|exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |or |union |like |+|/*|user()";

    /**
     * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /**
     * 限制orderBy最大长度
     */
    private static final int ORDER_BY_MAX_LENGTH = 500;

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value)
    {
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value))
        {
            throw new UtilException("参数不符合规范，不能进行查询");
        }
        if (StringUtils.length(value) > ORDER_BY_MAX_LENGTH)
        {
            throw new UtilException("参数已超过最大限制，不能进行查询");
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value)
    {
        return value.matches(SQL_PATTERN);
    }

    /**
     * SQL关键字检查
     */
    public static void filterKeyword(String value)
    {
        if (StringUtils.isEmpty(value))
        {
            return;
        }
        String normalizedValue = value.replaceAll("\\p{Z}|\\s", "");
        String[] sqlKeywords = StringUtils.split(SQL_REGEX, "\\|");
        for (String sqlKeyword : sqlKeywords)
        {
            if (containsKeyword(value, normalizedValue, sqlKeyword))
            {
                throw new UtilException("请求参数包含敏感关键词'" + sqlKeyword + "'，可能存在安全风险");
            }
        }
    }

    /**
     * 检查 SQL 关键词，允许关键词内部包含空白字符，避免通过空白拆分绕过检测。
     */
    private static boolean containsKeyword(String value, String normalizedValue, String sqlKeyword)
    {
        if (StringUtils.isBlank(sqlKeyword))
        {
            return StringUtils.contains(value, sqlKeyword);
        }
        if (sqlKeyword.endsWith(" "))
        {
            String keyword = sqlKeyword.trim();
            StringBuilder regex = new StringBuilder();
            for (int i = 0; i < keyword.length(); i++)
            {
                if (i > 0)
                {
                    regex.append("[\\p{Z}\\s]*");
                }
                regex.append(Pattern.quote(String.valueOf(keyword.charAt(i))));
            }
            regex.append("[\\p{Z}\\s]+");
            return Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE).matcher(value).find();
        }
        String normalizedKeyword = sqlKeyword.replaceAll("\\p{Z}|\\s", "");
        return StringUtils.indexOfIgnoreCase(normalizedValue, normalizedKeyword) > -1;
    }
}
