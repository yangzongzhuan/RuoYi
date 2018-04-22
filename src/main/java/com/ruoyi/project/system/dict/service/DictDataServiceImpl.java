package com.ruoyi.project.system.dict.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.system.dict.dao.IDictDataDao;
import com.ruoyi.project.system.dict.domain.DictData;

/**
 * 字典 业务层处理
 * 
 * @author ruoyi
 */
@Service("dictDataService")
public class DictDataServiceImpl implements IDictDataService
{
    @Autowired
    private IDictDataDao dictDao;

    /**
     * 根据条件分页查询字典数据
     * 
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public List<DictData> selectDictDataList(DictData dictData)
    {
        return dictDao.selectDictDataList(dictData);
    }

    /**
     * 根据字典数据ID查询信息
     * 
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public DictData selectDictDataById(Long dictCode)
    {
        return dictDao.selectDictDataById(dictCode);
    }

    /**
     * 通过字典ID删除字典数据信息
     * 
     * @param dictCode 字典数据ID
     * @return 结果
     */
    @Override
    public int deleteDictDataById(Long dictCode)
    {
        return dictDao.deleteDictDataById(dictCode);
    }

    /**
     * 批量删除字典数据
     * 
     * @param ids 需要删除的数据
     * @return 结果
     */
    @Override
    public int batchDeleteDictData(Long[] ids)
    {
        return dictDao.batchDeleteDictData(ids);
    }

    /**
     * 保存字典数据信息
     * 
     * @param dictData 字典数据信息
     * @return 结果
     */
    @Override
    public int saveDictData(DictData dictData)
    {
        Long dictCode = dictData.getDictCode();
        if (StringUtils.isNotNull(dictCode))
        {
            dictData.setUpdateBy(ShiroUtils.getLoginName());
            return dictDao.updateDictData(dictData);
        }
        else
        {
            dictData.setCreateBy(ShiroUtils.getLoginName());
            return dictDao.insertDictData(dictData);
        }
    }

}
