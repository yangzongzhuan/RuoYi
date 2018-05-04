package com.ruoyi.project.system.user.domain;

import java.util.Arrays;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import com.ruoyi.framework.web.page.PageDomain;
import com.ruoyi.project.system.dept.domain.Dept;

/**
 * 用户对象 sys_user
 * 
 * @author ruoyi
 */
public class User extends PageDomain
{
    /** 用户ID */
    private Long userId;
    /** 部门ID */
    private Long deptId;
    /** 部门父ID */
    private Long parentId;
    /** 登录名 */
    private String loginName;
    /** 用户名称 */
    private String userName;
    /** 用户邮箱 */
    private String email;
    /** 手机号码 */
    private String phonenumber;
    /** 用户性别 */
    private String sex;
    /** 用户头像 */
    private String avatar;
    /** 密码 */
    private String password;
    /** 盐加密 */
    private String salt;
    /** 类型:Y默认用户,N非默认用户 */
    private String userType;
    /** 帐号状态:0正常,1禁用 */
    private int status;
    /** 拒绝登录描述 */
    private String refuseDes;
    /** 创建者 */
    private String createBy;
    /** 创建时间 */
    private String createTime;
    /** 更新者 */
    private String updateBy;
    /** 更新时间 */
    private String updateTime;
    /** 部门对象 */
    private Dept dept;
    /** 角色组 */
    private Long[] roleIds;
    /** 岗位组 */
    private Long[] postIds;

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

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

    public String getLoginName()
    {
        return loginName;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getSalt()
    {
        return salt;
    }

    public void setSalt(String salt)
    {
        this.salt = salt;
    }

    /**
     * 生成随机盐
     */
    public void randomSalt()
    {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        setSalt(hex);
    }

    public String getUserType()
    {
        return userType;
    }

    public void setUserType(String userType)
    {
        this.userType = userType;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getRefuseDes()
    {
        return refuseDes;
    }

    public void setRefuseDes(String refuseDes)
    {
        this.refuseDes = refuseDes;
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

    public Dept getDept()
    {
        return dept;
    }

    public void setDept(Dept dept)
    {
        this.dept = dept;
    }

    public Long[] getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds)
    {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds()
    {
        return postIds;
    }

    public void setPostIds(Long[] postIds)
    {
        this.postIds = postIds;
    }

    @Override
    public String toString()
    {
        return "User [userId=" + userId + ", deptId=" + deptId + ", parentId=" + parentId + ", loginName=" + loginName
                + ", userName=" + userName + ", email=" + email + ", phonenumber=" + phonenumber + ", password="
                + password + ", salt=" + salt + ", userType=" + userType + ", status=" + status + ", refuseDes="
                + refuseDes + ", createBy=" + createBy + ", createTime=" + createTime + ", updateBy=" + updateBy
                + ", updateTime=" + updateTime + ", dept=" + dept + ", roleIds=" + Arrays.toString(roleIds)
                + ", postIds=" + Arrays.toString(postIds) + "]";
    }

}
