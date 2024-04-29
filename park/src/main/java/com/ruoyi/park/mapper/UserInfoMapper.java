package com.ruoyi.park.mapper;

import java.util.List;
import com.ruoyi.park.domain.UserInfo;

/**
 * 用户Mapper接口
 * 
 * @author bigcar
 * @date 2024-04-29
 */
public interface UserInfoMapper 
{
    /**
     * 查询用户
     * 
     * @param id 用户主键
     * @return 用户
     */
    public UserInfo selectUserInfoById(Long id);

    /**
     * 查询用户列表
     * 
     * @param userInfo 用户
     * @return 用户集合
     */
    public List<UserInfo> selectUserInfoList(UserInfo userInfo);

    /**
     * 新增用户
     * 
     * @param userInfo 用户
     * @return 结果
     */
    public int insertUserInfo(UserInfo userInfo);

    /**
     * 修改用户
     * 
     * @param userInfo 用户
     * @return 结果
     */
    public int updateUserInfo(UserInfo userInfo);

    /**
     * 删除用户
     * 
     * @param id 用户主键
     * @return 结果
     */
    public int deleteUserInfoById(Long id);

    /**
     * 批量删除用户
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserInfoByIds(String[] ids);
}
