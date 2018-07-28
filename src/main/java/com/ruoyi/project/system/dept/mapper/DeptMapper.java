package com.ruoyi.project.system.dept.mapper;

import java.util.List;
import com.ruoyi.project.system.dept.domain.Dept;

/**
 * 部门管理 数据层
 * 
 * @author ruoyi
 */
public interface DeptMapper
{
    /**
     * 查询部门人数
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public int selectDeptCount(Dept dept);

    /**
     * 查询部门是否存在用户
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    public int checkDeptExistUser(Long deptId);

    /**
     * 查询部门管理数据
     * 
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<Dept> selectDeptList(Dept dept);

    /**
     * 查询部门所有数据
     * 
     * @return 部门信息集合
     */
    public List<Dept> selectDeptAll();

    /**
     * 删除部门管理信息
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);

    /**
     * 新增部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(Dept dept);

    /**
     * 修改部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDept(Dept dept);

    /**
     * 根据部门ID查询信息
     * 
     * @param deptId 部门ID
     * @return 部门信息
     */
    public Dept selectDeptById(Long deptId);

    /**
     * 校验部门名称是否唯一
     * 
     * @param deptName 部门名称
     * @return 结果
     */
    public Dept checkDeptNameUnique(String deptName);
}
