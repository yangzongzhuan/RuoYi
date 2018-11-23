package com.ruoyi.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义导出Excel数据注解
 * 
 * @author ruoyi
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel
{
    /**
     * 导出到Excel中的名字.
     */
    public abstract String name();
    
    /**
     * 日期格式, 如: yyyy-MM-dd
     */
    public abstract String dateFormat() default "";

    /**
     * 读取内容转表达式 (如: 0=男,1=女,2=未知)
     */
    public abstract String readConverterExp() default "";

    /**
     * 提示信息
     */
    public abstract String prompt() default "";

    /**
     * 设置只能选择不能输入的列内容.
     */
    public abstract String[] combo() default {};

    /**
     * 是否导出数据,应对需求:有时我们需要导出一份模板,这是标题需要但内容需要用户手工填写.
     */
    public abstract boolean isExport() default true;
}