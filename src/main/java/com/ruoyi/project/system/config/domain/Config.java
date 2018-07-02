package com.ruoyi.project.system.config.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 参数配置表 sys_config
 * 
 * @author ruoyi
 */
public class Config extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 参数主键 */
    @Excel(name = "参数主键", column = "A")
    private Integer configId;
    
    /** 参数名称 */
    @Excel(name = "参数名称", column = "B")
    private String configName;
    
    /** 参数键名 */
    @Excel(name = "参数键名", column = "C")
    private String configKey;
    
    /** 参数键值 */
    @Excel(name = "参数键值", column = "D")
    private String configValue;
    
    /** 系统内置（Y是 N否） */
    @Excel(name = "系统内置", column = "E")
    private String configType;
    

    public Integer getConfigId()
    {
        return configId;
    }

    public void setConfigId(Integer configId)
    {
        this.configId = configId;
    }

    public String getConfigName()
    {
        return configName;
    }

    public void setConfigName(String configName)
    {
        this.configName = configName;
    }

    public String getConfigKey()
    {
        return configKey;
    }

    public void setConfigKey(String configKey)
    {
        this.configKey = configKey;
    }

    public String getConfigValue()
    {
        return configValue;
    }

    public void setConfigValue(String configValue)
    {
        this.configValue = configValue;
    }

    public String getConfigType()
    {
        return configType;
    }

    public void setConfigType(String configType)
    {
        this.configType = configType;
    }

}
