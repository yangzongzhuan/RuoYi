package com.ruoyi.project.system.role.mapper;

import com.ruoyi.project.system.role.domain.RoleDept;

import java.util.List;

/**
 * 角色与部门关联表(用于数据权限) 数据层
 */
public interface RoleDeptMapper {
    /**
     * 通过角色ID删除角色和部门关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleDeptByRoleId(Long roleId);

    /**
     * 批量删除角色部门关联信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRoleDept(Long[] ids);

    /**
     * 查询部门使用数量
     *
     * @param detpId 部门ID
     * @return 结果
     */
    public int selectCountRoleDeptByDetpId(Long detpId);

    /**
     * 批量新增角色部门信息（数据权限）
     *
     * @param roleDeptList 角色菜单列表
     * @return 结果
     */
    public int batchRoleDept(List<RoleDept> roleDeptList);

}
