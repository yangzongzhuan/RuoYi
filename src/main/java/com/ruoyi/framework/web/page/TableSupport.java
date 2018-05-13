package com.ruoyi.framework.web.page;

import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.constant.CommonConstant;

/**
 * 表格数据处理
 * 
 * @author ruoyi
 */
public class TableSupport
{
    /**
     * 封装分页对象
     */
    public static PageDomain getPageDomain()
    {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getIntParameter(CommonConstant.PAGENUM));
        pageDomain.setPageSize(ServletUtils.getIntParameter(CommonConstant.PAGESIZE));
        pageDomain.setOrderByColumn(ServletUtils.getStrParameter(CommonConstant.ORDERBYCOLUMN));
        pageDomain.setIsAsc(ServletUtils.getStrParameter(CommonConstant.ISASC));
        return pageDomain;
    }

    public static PageDomain buildPageRequest()
    {
        return getPageDomain();
    }

}
