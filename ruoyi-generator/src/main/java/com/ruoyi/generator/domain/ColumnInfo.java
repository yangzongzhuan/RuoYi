package com.ruoyi.generator.domain;

import com.ruoyi.common.json.JSON;
import com.ruoyi.common.utils.StringUtils;

/**
 * ry数据库表列信息
 * 
 * @author ruoyi
 */
public class ColumnInfo
{
    /** 字段名称 */
    private String columnName;

    /** 字段类型 */
    private String dataType;

    /** 列描述 */
    private String columnComment;

    /** 列配置 */
    private ColumnConfigInfo configInfo;

    /** Java属性类型 */
    private String attrType;

    /** Java属性名称(第一个字母大写)，如：user_name => UserName */
    private String attrName;

    /** Java属性名称(第一个字母小写)，如：user_name => userName */
    private String attrname;

    /** 执行计划（包含了与索引相关的一些细节信息） */
    private String extra;

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public String getDataType()
    {
        return dataType;
    }

    public void setDataType(String dataType)
    {
        this.dataType = dataType;
    }

    public String getColumnComment()
    {
        return columnComment;
    }

    public void setColumnComment(String columnComment) throws Exception
    {
        // 根据列描述解析列的配置信息
        if (StringUtils.isNotEmpty(columnComment) && columnComment.startsWith("{"))
        {
            this.configInfo = JSON.unmarshal(columnComment, ColumnConfigInfo.class);
            this.columnComment = configInfo.getTitle();
        }
        else
        {
            this.columnComment = columnComment;
        }
    }

    public String getAttrName()
    {
        return attrName;
    }

    public void setAttrName(String attrName)
    {
        this.attrName = attrName;
    }

    public String getAttrname()
    {
        return attrname;
    }

    public void setAttrname(String attrname)
    {
        this.attrname = attrname;
    }

    public String getAttrType()
    {
        return attrType;
    }

    public void setAttrType(String attrType)
    {
        this.attrType = attrType;
    }

    public String getExtra()
    {
        return extra;
    }

    public void setExtra(String extra)
    {
        this.extra = extra;
    }

    public ColumnConfigInfo getConfigInfo()
    {
        return configInfo;
    }

    public void setConfigInfo(ColumnConfigInfo configInfo)
    {
        this.configInfo = configInfo;
    }
}
