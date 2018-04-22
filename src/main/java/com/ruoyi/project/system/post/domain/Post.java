package com.ruoyi.project.system.post.domain;

import com.ruoyi.framework.web.page.PageDomain;

/**
 * 岗位对象 sys_post
 * 
 * @author ruoyi
 */
public class Post extends PageDomain
{
    /** 岗位ID */
    private Long postId;
    /** 岗位编码 */
    private String postCode;
    /** 岗位名称 */
    private String postName;
    /** 岗位排序 */
    private String postSort;
    /** 状态（0正常 1停用） */
    private int status;
    /** 用户是否存在此岗位标识 默认不存在 */
    private boolean flag = false;
    /** 创建者 */
    private String createBy;
    /** 创建时间 */
    private String createTime;
    /** 更新时间 */
    private String updateTime;
    /** 更新者 */
    private String updateBy;
    /** 备注 */
    private String remark;

    public Long getPostId()
    {
        return postId;
    }

    public void setPostId(Long postId)
    {
        this.postId = postId;
    }

    public String getPostCode()
    {
        return postCode;
    }

    public void setPostCode(String postCode)
    {
        this.postCode = postCode;
    }

    public String getPostName()
    {
        return postName;
    }

    public void setPostName(String postName)
    {
        this.postName = postName;
    }

    public String getPostSort()
    {
        return postSort;
    }

    public void setPostSort(String postSort)
    {
        this.postSort = postSort;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public boolean isFlag()
    {
        return flag;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
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

    public String getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getUpdateBy()
    {
        return updateBy;
    }

    public void setUpdateBy(String updateBy)
    {
        this.updateBy = updateBy;
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
        return "Post [postId=" + postId + ", postCode=" + postCode + ", postName=" + postName + ", postSort=" + postSort
                + ", status=" + status + ", flag=" + flag + ", createBy=" + createBy + ", createTime=" + createTime
                + ", updateTime=" + updateTime + ", updateBy=" + updateBy + ", remark=" + remark + "]";
    }

}
