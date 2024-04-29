package com.ruoyi.park.mapper;

import java.util.List;
import com.ruoyi.park.domain.AdminInfo;

/**
 * 查询管理员Mapper接口
 * 
 * @author bigcar
 * @date 2024-04-29
 */
public interface AdminInfoMapper 
{
    /**
     * 查询查询管理员
     * 
     * @param id 查询管理员主键
     * @return 查询管理员
     */
    public AdminInfo selectAdminInfoById(Long id);

    /**
     * 查询查询管理员列表
     * 
     * @param adminInfo 查询管理员
     * @return 查询管理员集合
     */
    public List<AdminInfo> selectAdminInfoList(AdminInfo adminInfo);

    /**
     * 新增查询管理员
     * 
     * @param adminInfo 查询管理员
     * @return 结果
     */
    public int insertAdminInfo(AdminInfo adminInfo);

    /**
     * 修改查询管理员
     * 
     * @param adminInfo 查询管理员
     * @return 结果
     */
    public int updateAdminInfo(AdminInfo adminInfo);

    /**
     * 删除查询管理员
     * 
     * @param id 查询管理员主键
     * @return 结果
     */
    public int deleteAdminInfoById(Long id);

    /**
     * 批量删除查询管理员
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAdminInfoByIds(String[] ids);
}
