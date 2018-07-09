package com.ruoyi.project.system.dept.domain;

import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 部门对象 sys_dept
 * 
 * @author ruoyi
 */
public class Dept extends BaseEntity
{
    private static final long serialVersionUID = 1L;
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
    private String status;
    /** 父部门名称 */
    private String parentName;

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

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
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

    @Override
    public String toString()
    {
        return "Dept [deptId=" + deptId + ", parentId=" + parentId + ", deptName=" + deptName + ", orderNum=" + orderNum
                + ", leader=" + leader + ", phone=" + phone + ", email=" + email + ", status=" + status
                + ", parentName=" + parentName + "]";
    }

}
