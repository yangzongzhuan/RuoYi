package com.ruoyi.project.system.role.domain;

import java.util.Arrays;
import java.util.Date;
import com.ruoyi.framework.web.page.PageDomain;

/**
 * 角色对象 sys_role
 * 
 * @author ruoyi
 */
public class Role extends PageDomain
{
    /** 角色ID */
    private Long roleId;
    /** 角色名 */
    private String roleName;
    /** 角色权限 */
    private String roleKey;
    /** 角色排序 */
    private String roleSort;
    /** 角色状态:0正常,1禁用 */
    private int status;
    /** 创建者 */
    private String createBy;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;
    /** 更新者 */
    private String updateBy;
    /** 备注 */
    private String remark;
    /** 用户是否存在此角色标识 默认不存在 */
    private boolean flag = false;
    /** 菜单组 */
    private Long[] menuIds;

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    public String getRoleKey()
    {
        return roleKey;
    }

    public void setRoleKey(String roleKey)
    {
        this.roleKey = roleKey;
    }

    public String getRoleSort()
    {
        return roleSort;
    }

    public void setRoleSort(String roleSort)
    {
        this.roleSort = roleSort;
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

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
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

    public boolean isFlag()
    {
        return flag;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    public Long[] getMenuIds()
    {
        return menuIds;
    }

    public void setMenuIds(Long[] menuIds)
    {
        this.menuIds = menuIds;
    }

    @Override
    public String toString()
    {
        return "Role [roleId=" + roleId + ", roleName=" + roleName + ", roleKey=" + roleKey + ", roleSort=" + roleSort
                + ", status=" + status + ", createBy=" + createBy + ", createTime=" + createTime + ", updateTime="
                + updateTime + ", updateBy=" + updateBy + ", remark=" + remark + ", flag=" + flag + ", menuIds="
                + Arrays.toString(menuIds) + "]";
    }

}
