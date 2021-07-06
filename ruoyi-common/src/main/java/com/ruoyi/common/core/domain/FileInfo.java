package com.ruoyi.common.core.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 文件信息
 * 
 * @author ruoyi
 */
public class FileInfo
{
    /**
     * 文件名称
     */
     private String name;

     /**
     * 文件地址
     */
    private String url;

    public FileInfo()
    {

    }

    public FileInfo(String name, String url)
    {
        this.name = name;
        this.url = url;
    }

    public String getName()
     {
         return name;
     }

     public void setName(String name)
     {
         this.name = name;
     }

     public String getUrl()
     {
         return url;
     }

     public void setUrl(String url)
     {
         this.url = url;
     }

     @Override
     public String toString() {
         return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
             .append("name", getName())
             .append("url", getUrl())
             .toString();
     }
 }
