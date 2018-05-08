package com.ruoyi.common.constant;

/**
 * 用户常量信息
 * 
 * @author ruoyi
 */
public class UserConstants
{

    /** 正常状态 */
    public static final int NORMAL = 0;

    /** 异常状态 */
    public static final int EXCEPTION = 1;

    /** 用户封禁状态 */
    public static final int USER_BLOCKED = 1;

    /** 角色封禁状态 */
    public static final int ROLE_BLOCKED = 1;

    /** 部门正常状态 */
    public static final int DEPT_NORMAL = 0;

    /**
     * 用户名长度限制
     */
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 20;

    /** 名称是否唯一的返回结果码 */
    public final static String NAME_UNIQUE = "0";
    public final static String NAME_NOT_UNIQUE = "1";

    /**
     * 密码长度限制
     */
    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 20;

}
