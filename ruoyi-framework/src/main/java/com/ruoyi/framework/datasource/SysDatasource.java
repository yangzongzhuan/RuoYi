package com.ruoyi.framework.datasource;

/**
 * 动态数据源表 sys_datasource
 * 
 * @author wangchl
 * @date 2018-10-10
 */
public class SysDatasource {
	
	/** ID */
	private String id;
	/** 密码 */
	private String pwd;
	/** 用户 */
	private String user;
	/** 地址 */
	private String url;
	/** 名称 */
	private String name;

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	public void setPwd(String pwd) 
	{
		this.pwd = pwd;
	}

	public String getPwd() 
	{
		return pwd;
	}
	public void setUser(String user) 
	{
		this.user = user;
	}

	public String getUser() 
	{
		return user;
	}
	public void setUrl(String url) 
	{
		this.url = url;
	}

	public String getUrl() 
	{
		return url;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}

}
