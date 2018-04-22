package com.ruoyi.framework.web.page;

import java.util.Map;

/**
 * 表格请求参数封装
 * 
 * @author ruoyi
 */
public class PageUtilEntity
{
    /** 当前记录起始索引 */
    private int page;
    /** 每页显示记录数 */
    private int size;
    /** 排序列 */
    private String orderByColumn;
    /** 排序的方向 "desc" 或者 "asc". */
    private String isAsc;
    /** true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属性 */
    private boolean entityOrField;
    /** 总记录数 */
    private int totalResult;
    /** 搜索值 */
    private String searchValue;
    /** 请求参数 */
    protected Map<String, Object> reqMap;

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public String getOrderByColumn()
    {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn)
    {
        this.orderByColumn = orderByColumn;
    }

    public String getIsAsc()
    {
        return isAsc;
    }

    public void setIsAsc(String isAsc)
    {
        this.isAsc = isAsc;
    }

    public boolean isEntityOrField()
    {
        return entityOrField;
    }

    public void setEntityOrField(boolean entityOrField)
    {
        this.entityOrField = entityOrField;
    }

    public int getTotalResult()
    {
        return totalResult;
    }

    public void setTotalResult(int totalResult)
    {
        this.totalResult = totalResult;
    }

    public String getSearchValue()
    {
        return searchValue;
    }

    public void setSearchValue(String searchValue)
    {
        this.searchValue = searchValue;
    }

    public Map<String, Object> getReqMap()
    {
        return reqMap;
    }

    public void setReqMap(Map<String, Object> reqMap)
    {
        this.reqMap = reqMap;
    }

}
