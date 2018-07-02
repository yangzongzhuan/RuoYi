package com.ruoyi.project.system.config.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.support.Convert;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.system.config.domain.Config;
import com.ruoyi.project.system.config.mapper.ConfigMapper;

/**
 * 参数配置 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class ConfigServiceImpl implements IConfigService
{
    @Autowired
    private ConfigMapper configMapper;

    /**
     * 查询参数配置信息
     * 
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    @Override
    public Config selectConfigById(Integer configId)
    {
        return configMapper.selectConfigById(configId);
    }

    /**
     * 根据键名查询参数配置信息
     * 
     * @param configName 参数名称
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey)
    {
        Config config = configMapper.selectConfigByKey(configKey);
        return StringUtils.isNotNull(config) ? config.getConfigValue() : "";
    }

    /**
     * 查询参数配置列表
     * 
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    @Override
    public List<Config> selectConfigList(Config config)
    {
        return configMapper.selectConfigList(config);
    }

    /**
     * 新增参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int insertConfig(Config config)
    {
        return configMapper.insertConfig(config);
    }

    /**
     * 修改参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int updateConfig(Config config)
    {
        return configMapper.updateConfig(config);
    }

    /**
     * 保存参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int saveConfig(Config config)
    {
        Integer configId = config.getConfigId();
        int rows = 0;
        if (StringUtils.isNotNull(configId))
        {
            rows = configMapper.updateConfig(config);
        }
        else
        {
            rows = configMapper.insertConfig(config);
        }
        return rows;
    }

    /**
     * 删除参数配置信息
     * 
     * @param configId 参数配置ID
     * @return 结果
     */
    @Override
    public int deleteConfigById(Integer configId)
    {
        return configMapper.deleteConfigById(configId);
    }

    /**
     * 批量删除参数配置对象
     * 
     * @param configIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteConfigByIds(String ids)
    {
        return configMapper.deleteConfigByIds(Convert.toIntArray(ids));
    }

    /**
     * 校验参数键名是否唯一
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public String checkConfigKeyUnique(Config config)
    {
        if (config.getConfigId() == null)
        {
            config.setConfigId(-1);
        }
        Integer configId = config.getConfigId();
        Config info = configMapper.selectConfigByKey(config.getConfigKey());
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getConfigId())
                && info.getConfigId().intValue() != configId.intValue())
        {
            return UserConstants.CONFIG_KEY_NOT_UNIQUE;
        }
        return UserConstants.CONFIG_KEY_UNIQUE;
    }

}
