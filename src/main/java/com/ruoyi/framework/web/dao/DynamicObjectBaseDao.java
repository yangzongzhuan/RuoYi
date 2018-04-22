package com.ruoyi.framework.web.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import com.ruoyi.framework.web.page.PageUtilEntity;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 数据DAO层通用数据处理
 * 
 * @author ruoyi
 */
public class DynamicObjectBaseDao
{
    @Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 保存对象
     * 
     * @param str mapper 节点
     * @param obj 对象
     * @return 结果
     * @throws Exception
     */
    public int save(String str, Object obj)
    {
        return sqlSessionTemplate.insert(str, obj);
    }

    /**
     * 批量更新
     * 
     * @param str mapper 节点
     * @param obj 对象
     * @return 结果
     * @throws Exception
     */
    public int batchSave(String str, List<?> objs)
    {
        return sqlSessionTemplate.insert(str, objs);
    }

    /**
     * 修改对象
     * 
     * @param str mapper 节点
     * @param obj 对象
     * @return 结果
     * @throws Exception
     */
    public int update(String str, Object obj)
    {
        return sqlSessionTemplate.update(str, obj);
    }

    /**
     * 批量更新
     * 
     * @param str mapper 节点
     * @param obj 对象
     * @return 结果
     * @throws Exception
     */
    public void batchUpdate(String str, List<?> objs) throws Exception
    {
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        // 批量执行器
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try
        {
            if (objs != null)
            {
                for (int i = 0, size = objs.size(); i < size; i++)
                {
                    sqlSession.update(str, objs.get(i));
                }
                sqlSession.flushStatements();
                sqlSession.commit();
                sqlSession.clearCache();
            }
        }
        finally
        {
            sqlSession.close();
        }
    }

    /**
     * 批量删除 根据对象
     * 
     * @param str mapper 节点
     * @param obj 对象
     * @return 结果
     * @throws Exception
     */
    public int batchDelete(String str, List<?> objs) throws Exception
    {
        return sqlSessionTemplate.delete(str, objs);
    }

    /**
     * 批量删除 根据数组
     * 
     * @param str mapper 节点
     * @param obj 对象
     * @return 结果
     * @throws Exception
     */
    public int batchDelete(String str, Long[] objs)
    {
        return sqlSessionTemplate.delete(str, objs);
    }

    /**
     * 删除对象
     * 
     * @param str mapper 节点
     * @param obj 对象
     * @return 结果
     * @throws Exception
     */
    public int delete(String str, Object obj)
    {
        return sqlSessionTemplate.delete(str, obj);
    }

    /**
     * 查找单条对象
     * 
     * @param str mapper 节点
     * @param obj 对象
     * @return 结果
     * @throws Exception
     */
    public <T> T findForObject(String str, Object obj)
    {
        return sqlSessionTemplate.selectOne(str, obj);
    }

    /**
     * 查找总数
     * 
     * @param str mapper 节点
     * @param obj 对象
     * @return 结果
     * @throws Exception
     */
    public int count(String str, Object obj)
    {
        return sqlSessionTemplate.selectOne(str, obj);
    }

    /**
     * 查找对象 - 无条件
     * 
     * @param str mapper 节点
     * @param obj 对象
     * @return 结果
     * @throws Exception
     */
    public <E> List<E> findForList(String str)
    {
        return sqlSessionTemplate.selectList(str);
    }

    /**
     * 查找对象 - 有条件
     * 
     * @param str mapper 节点
     * @param obj 对象
     * @return 结果
     * @throws Exception
     */
    public <E> List<E> findForList(String str, Object obj)
    {
        return sqlSessionTemplate.selectList(str, obj);
    }
    
    /**
     * 自定义分页方法
     * 
     * @param str mapper 节点
     * @param obj 对象
     * @return 结果
     * @throws Exception
     */
    public TableDataInfo findForList(String str, PageUtilEntity pageUtilEntity)
    {
        List<?> pageList = sqlSessionTemplate.selectList(str, pageUtilEntity);
        TableDataInfo tableDataInfo = new TableDataInfo(pageList, pageUtilEntity.getTotalResult());
        return tableDataInfo;
    }

    public Object findForMap(String str, Object obj, String key, String value) throws Exception
    {
        return sqlSessionTemplate.selectMap(str, obj, key);
    }

}
