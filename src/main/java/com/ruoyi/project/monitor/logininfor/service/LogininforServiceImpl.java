package com.ruoyi.project.monitor.logininfor.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.support.Convert;
import com.ruoyi.project.monitor.logininfor.domain.Logininfor;
import com.ruoyi.project.monitor.logininfor.mapper.LogininforMapper;

/**
 * 系统访问日志情况信息 服务层处理
 * 
 * @author ruoyi
 */
@Service
public class LogininforServiceImpl implements ILogininforService
{
    @Autowired
    private LogininforMapper logininforMapper;

    /**
     * 新增系统登录日志
     * 
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(Logininfor logininfor)
    {
        logininforMapper.insertLogininfor(logininfor);
    }

    /**
     * 查询系统登录日志集合
     * 
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<Logininfor> selectLogininforList(Logininfor logininfor)
    {
        return logininforMapper.selectLogininforList(logininfor);
    }

    /**
     * 批量删除系统登录日志
     * 
     * @param ids 需要删除的数据
     * @return
     */
    @Override
    public int deleteLogininforByIds(String ids)
    {
        return logininforMapper.deleteLogininforByIds(Convert.toStrArray(ids));
    }
    
    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor()
    {
        logininforMapper.cleanLogininfor();
    }
}
