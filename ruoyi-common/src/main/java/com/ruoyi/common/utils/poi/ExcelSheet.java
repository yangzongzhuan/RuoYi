package com.ruoyi.common.utils.poi;

import java.util.ArrayList;
import java.util.List;

/**
 * 多 Sheet 导出时的数据信息
 *
 * 使用示例：
 * <pre>
 *   List<ExcelSheet<?>> sheets = new ArrayList<>();
 *   sheets.add(new ExcelSheet<>("参数数据", configList, Config.class, "参数信息"));
 *   sheets.add(new ExcelSheet<>("岗位数据", postList, Post.class, "岗位信息"));
 *   return ExcelUtil.exportMultiSheet(sheets);
 * </pre>
 * 
 * @author ruoyi
 */
public class ExcelSheet<T>
{
    /** Sheet 名称 */
    private String sheetName;

    /** 导出数据集合 */
    private List<T> list;

    /** 数据对应的实体 Class */
    private Class<T> clazz;

    /** Sheet 顶部大标题（可为空） */
    private String title;

    public ExcelSheet(String sheetName, List<T> list, Class<T> clazz)
    {
        this(sheetName, list, clazz, "");
    }

    public ExcelSheet(String sheetName, List<T> list, Class<T> clazz, String title)
    {
        this.sheetName = sheetName;
        this.list = list != null ? list : new ArrayList<>();
        this.clazz = clazz;
        this.title = title != null ? title : "";
    }

    public String getSheetName()
    {
        return sheetName;
    }

    public List<T> getList()
    {
        return list;
    }

    public Class<T> getClazz()
    {
        return clazz;
    }

    public String getTitle()
    {
        return title;
    }

    public void setSheetName(String sheetName)
    {
        this.sheetName = sheetName;
    }

    public void setList(List<T> list)
    {
        this.list = list;
    }

    public void setClazz(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
