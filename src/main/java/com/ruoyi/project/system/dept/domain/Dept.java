package com.ruoyi.project.system.dept.domain;

/**
 * 部门对象 sys_dept
 * 
 * @author ruoyi
 */
public class Dept
{
    /** 部门ID */
    private Long deptId;
    /** 父部门ID */
    private Long parentId;
    /** 部门名称 */
    private String deptName;
    /** 显示顺序 */
    private String orderNum;
    /** 负责人 */
    private String leader;
    /** 联系电话 */
    private String phone;
    /** 邮箱 */
    private String email;
    /** 部门状态:0正常,1停用 */
    private int status;
    /** 父部门名称 */
    private String parentName;
    /** 创建者 */
    private String createBy;
    /** 创建时间 */
    private String createTime;
    /** 更新者 */
    private String updateBy;
    /** 更新时间 */
    private String updateTime;

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(String orderNum)
    {
        this.orderNum = orderNum;
    }

    public String getLeader()
    {
        return leader;
    }

    public void setLeader(String leader)
    {
        this.leader = leader;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
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

    @Override
    public String toString()
    {
        return "Dept [deptId=" + deptId + ", parentId=" + parentId + ", deptName=" + deptName + ", orderNum=" + orderNum
                + ", leader=" + leader + ", phone=" + phone + ", email=" + email + ", status=" + status
                + ", parentName=" + parentName + ", createBy=" + createBy + ", createTime=" + createTime + ", updateBy="
                + updateBy + ", updateTime=" + updateTime + "]";
    }

}
