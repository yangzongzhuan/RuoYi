package com.ruoyi.project.tool.gen.domain;

import java.util.List;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.PageDomain;

/**
 * ry 数据库表
 * 
 * @author ruoyi
 */
public class TableInfo extends PageDomain
{
    /** 表名称 */
    private String tableName;

    /** 表描述 */
    private String tableComment;

    /** 表创建时间 */
    private String createTime;

    /** 表更新时间 */
    private String updateTime;

    /** 表的主键列信息 */
    private ColumnInfo primaryKey;

    /** 表的列名(不包含主键) */
    private List<ColumnInfo> columns;

    /** 类名(第一个字母大写) */
    private String className;

    /** 类名(第一个字母小写) */
    private String classname;

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getTableComment()
    {
        return tableComment;
    }

    public void setTableComment(String tableComment)
    {
        this.tableComment = tableComment;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public String getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }

    public List<ColumnInfo> getColumns()
    {
        return columns;
    }

    public ColumnInfo getColumnsLast()
    {
        ColumnInfo columnInfo = null;
        if (StringUtils.isNotNull(columns) && columns.size() > 0)
        {
            columnInfo = columns.get(0);
        }
        return columnInfo;
    }

    public void setColumns(List<ColumnInfo> columns)
    {
        this.columns = columns;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getClassname()
    {
        return classname;
    }

    public void setClassname(String classname)
    {
        this.classname = classname;
    }

    public ColumnInfo getPrimaryKey()
    {
        return primaryKey;
    }

    public void setPrimaryKey(ColumnInfo primaryKey)
    {
        this.primaryKey = primaryKey;
    }

    @Override
    public String toString()
    {
        return "Gen [tableName=" + tableName + ", tableComment=" + tableComment + ", createTime=" + createTime
                + ", updateTime=" + updateTime + "]";
    }

}
