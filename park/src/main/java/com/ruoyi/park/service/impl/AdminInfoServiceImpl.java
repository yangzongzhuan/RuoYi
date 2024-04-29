package com.ruoyi.park.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.park.mapper.AdminInfoMapper;
import com.ruoyi.park.domain.AdminInfo;
import com.ruoyi.park.service.IAdminInfoService;
import com.ruoyi.common.core.text.Convert;

/**
 * 查询管理员Service业务层处理
 * 
 * @author bigcar
 * @date 2024-04-29
 */
@Service
public class AdminInfoServiceImpl implements IAdminInfoService 
{
    @Autowired
    private AdminInfoMapper adminInfoMapper;

    /**
     * 查询查询管理员
     * 
     * @param id 查询管理员主键
     * @return 查询管理员
     */
    @Override
    public AdminInfo selectAdminInfoById(Long id)
    {
        return adminInfoMapper.selectAdminInfoById(id);
    }

    /**
     * 查询查询管理员列表
     * 
     * @param adminInfo 查询管理员
     * @return 查询管理员
     */
    @Override
    public List<AdminInfo> selectAdminInfoList(AdminInfo adminInfo)
    {
        return adminInfoMapper.selectAdminInfoList(adminInfo);
    }

    /**
     * 新增查询管理员
     * 
     * @param adminInfo 查询管理员
     * @return 结果
     */
    @Override
    public int insertAdminInfo(AdminInfo adminInfo)
    {
        return adminInfoMapper.insertAdminInfo(adminInfo);
    }

    /**
     * 修改查询管理员
     * 
     * @param adminInfo 查询管理员
     * @return 结果
     */
    @Override
    public int updateAdminInfo(AdminInfo adminInfo)
    {
        return adminInfoMapper.updateAdminInfo(adminInfo);
    }

    /**
     * 批量删除查询管理员
     * 
     * @param ids 需要删除的查询管理员主键
     * @return 结果
     */
    @Override
    public int deleteAdminInfoByIds(String ids)
    {
        return adminInfoMapper.deleteAdminInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除查询管理员信息
     * 
     * @param id 查询管理员主键
     * @return 结果
     */
    @Override
    public int deleteAdminInfoById(Long id)
    {
        return adminInfoMapper.deleteAdminInfoById(id);
    }
}
