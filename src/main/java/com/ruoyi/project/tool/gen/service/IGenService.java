package com.ruoyi.project.tool.gen.service;

import java.util.List;
import com.ruoyi.project.tool.gen.domain.TableInfo;

/**
 * 代码生成 服务层
 * 
 * @author ruoyi
 */
public interface IGenService
{
    /**
     * 查询ry数据库表信息
     * 
     * @param tableInfo 表信息
     * @return 数据库表列表
     */
    public List<TableInfo> selectTableList(TableInfo tableInfo);

    /**
     * 生成代码
     * 
     * @param tableName 表名称
     * @return 数据
     */
    public byte[] generatorCode(String tableName);
    
    /**
     * 批量生成代码
     * 
     * @param tableNames 表数组
     * @return 数据
     */
    public byte[] generatorCode(String[] tableNames);
}
