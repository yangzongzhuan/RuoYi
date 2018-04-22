package com.ruoyi.project.system.dict.domain;

import com.ruoyi.framework.web.page.PageDomain;

/**
 * 字典类型对象 sys_dict_type
 * 
 * @author ruoyi
 */
public class DictType extends PageDomain
{
    /** 字典主键 */
    private Long dictId;
    /** 字典名称 */
    private String dictName;
    /** 字典类型 */
    private String dictType;
    /** 状态（0正常 1禁用） */
    private int status;
    /** 创建者 */
    private String createBy;
    /** 创建时间 */
    private String createTime;
    /** 更新者 */
    private String updateBy;
    /** 更新时间 */
    private String updateTime;
    /** 备注 */
    private String remark;

    public Long getDictId()
    {
        return dictId;
    }

    public void setDictId(Long dictId)
    {
        this.dictId = dictId;
    }

    public String getDictName()
    {
        return dictName;
    }

    public void setDictName(String dictName)
    {
        this.dictName = dictName;
    }

    public String getDictType()
    {
        return dictType;
    }

    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getCreateBy()
    {
        return createBy;
    }

    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
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

    public String getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(String updateTime)
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

    @Override
    public String toString()
    {
        return "DictType [dictId=" + dictId + ", dictName=" + dictName + ", dictType=" + dictType + ", status=" + status
                + ", createBy=" + createBy + ", createTime=" + createTime + ", updateBy=" + updateBy + ", updateTime="
                + updateTime + ", remark=" + remark + "]";
    }

}
