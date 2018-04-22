package com.ruoyi.project.system.dict.domain;

import com.ruoyi.framework.web.page.PageDomain;

/**
 * 字典数据表 sys_dict_data
 * 
 * @author ruoyi
 */
public class DictData extends PageDomain
{
    /** 字典编码 */
    private Long dictCode;
    /** 字典排序 */
    private Long dictSort;
    /** 字典标签 */
    private String dictLabel;
    /** 字典键值 */
    private String dictValue;
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

    public Long getDictCode()
    {
        return dictCode;
    }

    public void setDictCode(Long dictCode)
    {
        this.dictCode = dictCode;
    }

    public Long getDictSort()
    {
        return dictSort;
    }

    public void setDictSort(Long dictSort)
    {
        this.dictSort = dictSort;
    }

    public String getDictLabel()
    {
        return dictLabel;
    }

    public void setDictLabel(String dictLabel)
    {
        this.dictLabel = dictLabel;
    }

    public String getDictValue()
    {
        return dictValue;
    }

    public void setDictValue(String dictValue)
    {
        this.dictValue = dictValue;
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
        return "DictData [dictCode=" + dictCode + ", dictSort=" + dictSort + ", dictLabel=" + dictLabel + ", dictValue="
                + dictValue + ", dictType=" + dictType + ", status=" + status + ", createBy=" + createBy
                + ", createTime=" + createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime + ", remark="
                + remark + "]";
    }

}
