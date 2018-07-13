package com.ruoyi.framework.web.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.ruoyi.common.utils.DateUtils;

/**
 * Entity基类
 * 
 * @author ruoyi
 */
public class BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 搜索值 */
    private String searchValue;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    private Date updateTime;

    /** 备注 */
    private String remark;

    /** 请求参数 */
    private Map<String, Object> reqParams;

    public String getSearchValue()
    {
        return searchValue;
    }

    public void setSearchValue(String searchValue)
    {
        this.searchValue = searchValue;
    }

    public String getCreateBy()
    {
        return createBy;
    }

    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }

    public String getCreateTimeStr()
    {
        return createTime != null ? DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, createTime) : "";
    }

    public String getCreateDateTimeStr()
    {
        return createTime != null ? DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, createTime) : "";
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getUpdateBy()
    {
        return updateBy;
    }

    public void setUpdateBy(String updateBy)
    {
        this.updateBy = updateBy;
    }

    public String getUpdateTimeStr()
    {
        return updateTime != null ? DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, updateTime) : "";
    }

    public String getUpdateDateTimeStr()
    {
        return updateTime != null ? DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, updateTime) : "";
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public Map<String, Object> getReqParams()
    {
        return reqParams;
    }

    public void setReqParams(Map<String, Object> reqParams)
    {
        this.reqParams = reqParams;
    }

}
