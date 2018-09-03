package com.ruoyi.framework.datascope;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.system.role.domain.Role;
import com.ruoyi.project.system.user.domain.User;

/**
 * 数据范围处理
 * 
 * @author ruoyi
 */
public class DataScopeUtils
{
    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * 自定数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * 数据范围过滤
     * 
     * @return 标准连接条件对象
     */
    public static String dataScopeFilter()
    {
        return dataScopeFilter("u");
    }

    /**
     * 数据范围过滤
     * 
     * @param da 部门表别名
     * @return 标准连接条件对象
     */
    public static String dataScopeFilter(String da)
    {
        User user = ShiroUtils.getUser();
        // 如果是超级管理员，则不过滤数据
        if (user.isAdmin())
        {
            return StringUtils.EMPTY;
        }

        StringBuilder sqlString = new StringBuilder();

        for (Role role : user.getRoles())
        {
            String dataScope = role.getDataScope();
            if (DATA_SCOPE_ALL.equals(dataScope))
            {
                sqlString = new StringBuilder();
                break;
            }
            else if (DATA_SCOPE_CUSTOM.equals(dataScope))
            {
                sqlString.append(StringUtils.format(" OR {}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ", da, role.getRoleId()));
            }
        }

        if (StringUtils.isNotBlank(sqlString.toString()))
        {
            return " AND (" + sqlString.substring(4) + ")";
        }
        return StringUtils.EMPTY;
    }
}
