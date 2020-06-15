package com.ruoyi.web.controller.demo.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 客户测试信息
 * 
 * @author ruoyi
 */
public class CustomerModel
{
    /**
     * 客户姓名
     */
    private String name;

    /**
     * 客户手机
     */
    private String phonenumber;

    /**
     * 客户性别
     */
    private String sex;

    /**
     * 客户生日
     */
    private String birthday;

    /**
     * 客户描述
     */
    private String remark;

    /**
     * 商品信息
     */
    private List<GoodsModel> goods;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }


    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public List<GoodsModel> getGoods()
    {
        return goods;
    }

    public void setGoods(List<GoodsModel> goods)
    {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("name", getName())
            .append("phonenumber", getPhonenumber())
            .append("sex", getSex())
            .append("birthday", getBirthday())
            .append("goods", getGoods())
            .append("remark", getRemark())
            .toString();
    }
}
