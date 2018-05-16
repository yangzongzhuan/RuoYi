package com.ruoyi.project.tool.gen.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.velocity.VelocityContext;
import com.ruoyi.common.constant.CommonConstant;
import com.ruoyi.common.constant.CommonMap;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.GenConfig;
import com.ruoyi.project.tool.gen.domain.ColumnInfo;
import com.ruoyi.project.tool.gen.domain.TableInfo;

/**
 * 代码生成器 工具类
 * 
 * @author ruoyi
 */
public class GenUtils
{
    /** 项目空间路径 */
    private static final String projectPath = "main/java/com/ruoyi/project";

    /** mybatis空间路径 */
    private static final String myBatisPath = "main/resources/mybatis";

    /** html空间路径 */
    private static final String templatesPath = "main/resources/templates/";

    /** js空间路径 */
    private static final String javascriptPath = "main/resources/static/ruoyi/";

    /**
     * 设置列信息
     */
    public static List<ColumnInfo> transColums(List<ColumnInfo> columns)
    {
        // 列信息
        List<ColumnInfo> columsList = new ArrayList<>();
        for (ColumnInfo column : columns)
        {
            // 列名转换成Java属性名
            String attrName = StringUtils.convertToCamelCase(column.getColumnName());
            column.setAttrName(attrName);
            column.setAttrname(StringUtils.uncapitalize(attrName));

            // 列的数据类型，转换成Java类型
            String attrType = CommonMap.javaTypeMap.get(column.getDataType());
            column.setAttrType(attrType);

            columsList.add(column);
        }
        return columsList;
    }

    /**
     * 获取模板信息
     * 
     * @return 模板列表
     */
    public static VelocityContext getVelocityContext(TableInfo table)
    {
        // java对象数据传递到模板文件vm
        VelocityContext velocityContext = new VelocityContext();
        String packageName = GenConfig.getPackageName();
        velocityContext.put("tableName", table.getTableName());
        velocityContext.put("tableComment", replaceKeyword(table.getTableComment()));
        velocityContext.put("primaryKey", table.getPrimaryKey());
        velocityContext.put("className", table.getClassName());
        velocityContext.put("classname", table.getClassname());
        velocityContext.put("moduleName", GenUtils.getModuleName(packageName));
        velocityContext.put("columns", table.getColumns());
        velocityContext.put("package", packageName + "." + table.getClassname());
        velocityContext.put("author", GenConfig.getAuthor());
        velocityContext.put("datetime", DateUtils.getDate());
        return velocityContext;
    }

    /**
     * 获取模板信息
     * 
     * @return 模板列表
     */
    public static List<String> getTemplates()
    {
        List<String> templates = new ArrayList<String>();
        templates.add("templates/vm/java/domain.java.vm");
        templates.add("templates/vm/java/Mapper.java.vm");
        templates.add("templates/vm/java/Service.java.vm");
        templates.add("templates/vm/java/ServiceImpl.java.vm");
        templates.add("templates/vm/java/Controller.java.vm");
        templates.add("templates/vm/xml/Mapper.xml.vm");
        templates.add("templates/vm/html/list.html.vm");
        templates.add("templates/vm/html/add.html.vm");
        templates.add("templates/vm/html/edit.html.vm");
        templates.add("templates/vm/js/list.js.vm");
        templates.add("templates/vm/js/add.js.vm");
        templates.add("templates/vm/js/edit.js.vm");
        templates.add("templates/vm/sql/sql.vm");
        return templates;
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName)
    {
        if (CommonConstant.AUTO_REOMVE_PRE.equals(GenConfig.getAutoRemovePre()))
        {
            tableName = tableName.substring(tableName.indexOf("_") + 1);
        }
        if (StringUtils.isNotEmpty(GenConfig.getTablePrefix()))
        {
            tableName = tableName.replace(GenConfig.getTablePrefix(), "");
        }
        return StringUtils.convertToCamelCase(tableName);
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, TableInfo table, String moduleName)
    {
        // 小写类名
        String classname = table.getClassname();
        // 大写类名
        String className = table.getClassName();
        String javaPath = projectPath + "/" + moduleName + "/";
        String mybatisPath = myBatisPath + "/" + moduleName + "/" + className;
        String htmlPath = templatesPath + "/" + moduleName + "/" + classname;
        String jsPath = javascriptPath + "/" + moduleName + "/" + classname;

        if (StringUtils.isNotEmpty(classname))
        {
            javaPath += classname.replace(".", "/") + "/";
        }

        if (template.contains("domain.java.vm"))
        {
            return javaPath + "domain" + "/" + className + ".java";
        }

        if (template.contains("Mapper.java.vm"))
        {
            return javaPath + "mapper" + "/" + className + "Mapper.java";
        }

        if (template.contains("Service.java.vm"))
        {
            return javaPath + "service" + "/" + "I" + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm"))
        {
            return javaPath + "service" + "/" + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm"))
        {
            return javaPath + "controller" + "/" + className + "Controller.java";
        }

        if (template.contains("Mapper.xml.vm"))
        {
            return mybatisPath + "Mapper.xml";
        }

        if (template.contains("list.html.vm"))
        {
            return htmlPath + "/" + classname + ".html";
        }
        if (template.contains("add.html.vm"))
        {
            return htmlPath + "/" + "add.html";
        }
        if (template.contains("edit.html.vm"))
        {
            return htmlPath + "/" + "edit.html";
        }

        if (template.contains("list.js.vm"))
        {
            return jsPath + "/" + classname + ".js";
        }
        if (template.contains("add.js.vm"))
        {
            return jsPath + "/" + "add.js";
        }
        if (template.contains("edit.js.vm"))
        {
            return jsPath + "/" + "edit.js";
        }
        if (template.contains("sql.vm"))
        {
            return classname + "Menu.sql";
        }
        return null;
    }

    /**
     * 获取模块名
     * 
     * @param packageName 包名
     * @return 模块名
     */
    public static String getModuleName(String packageName)
    {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        String moduleName = StringUtils.substring(packageName, lastIndex + 1, nameLength);
        return moduleName;
    }

    public static String replaceKeyword(String keyword)
    {
        String keyName = keyword.replaceAll("(?:表|信息)", "");
        return keyName;
    }

    public static void main(String[] args)
    {
        System.out.println(StringUtils.convertToCamelCase("user_name"));
        System.out.println(replaceKeyword("岗位信息表"));
        System.out.println(getModuleName("com.ruoyi.project.system"));
    }
}
