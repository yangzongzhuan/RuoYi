package com.ruoyi.project.system.config.service;

import com.ruoyi.project.system.config.domain.Config;
import java.util.List;

/**
 * 参数配置 服务层
 * 
 * @author ruoyi
 */
public interface IConfigService
{

    /**
     * 查询参数配置信息
     * 
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    public Config selectConfigById(Integer configId);

    /**
     * 根据键名查询参数配置信息
     * 
     * @param configName 参数名称
     * @return 参数键值
     */
    public String selectConfigByKey(String configKey);

    /**
     * 查询参数配置列表
     * 
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    public List<Config> selectConfigList(Config config);

    /**
     * 新增参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    public int insertConfig(Config config);

    /**
     * 修改参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    public int updateConfig(Config config);

    /**
     * 保存参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    public int saveConfig(Config config);

    /**
     * 删除参数配置信息
     * 
     * @param configId 参数配置ID
     * @return 结果
     */
    public int deleteConfigById(Integer configId);

    /**
     * 批量删除参数配置信息
     * 
     * @param configIds 需要删除的数据ID
     * @return 结果
     */
    public int batchDeleteConfig(Integer[] configIds);

    /**
     * 校验参数键名是否唯一
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public String checkConfigKeyUnique(Config config);

}
