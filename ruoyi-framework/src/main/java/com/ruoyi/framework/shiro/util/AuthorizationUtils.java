package com.ruoyi.framework.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import com.ruoyi.framework.shiro.realm.UserRealm;

/**
 * 用户授权信息
 * 
 * @author ruoyi
 */
public class AuthorizationUtils
{
    /**
     * 清理所有用户授权信息缓存
     */
    public static void clearAllCachedAuthorizationInfo()
    {
        UserRealm ur = getUserRealm();
        if (ur != null)
        {
            ur.clearAllCachedAuthorizationInfo();
        }
    }

    /**
     * 获取自定义Realm
     */
    public static UserRealm getUserRealm()
    {
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        if (rsm.getRealms().isEmpty()) 
        {
            return null;
        }
        return (UserRealm) rsm.getRealms().iterator().next();
    }
}
