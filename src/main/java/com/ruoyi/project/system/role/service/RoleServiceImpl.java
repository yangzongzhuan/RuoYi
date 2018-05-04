package com.ruoyi.project.system.role.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.system.role.dao.IRoleDao;
import com.ruoyi.project.system.role.dao.IRoleMenuDao;
import com.ruoyi.project.system.role.domain.Role;
import com.ruoyi.project.system.role.domain.RoleMenu;
import com.ruoyi.project.system.user.dao.IUserRoleDao;

/**
 * 角色 业务层处理
 * 
 * @author ruoyi
 */
@Service("roleService")
public class RoleServiceImpl implements IRoleService
{

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IRoleMenuDao roleMenuDao;

    @Autowired
    private IUserRoleDao userRoleDao;

    /**
     * 根据条件分页查询角色数据
     * 
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public List<Role> selectRoleList(Role role)
    {
        return roleDao.selectRoleList(role);
    }

    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRoleKeys(Long userId)
    {
        List<Role> perms = roleDao.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (Role perm : perms)
        {
            if (StringUtils.isNotNull(perms))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询角色
     * 
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<Role> selectRolesByUserId(Long userId)
    {
        List<Role> userRoles = roleDao.selectRolesByUserId(userId);
        List<Role> roles = roleDao.selectRolesAll();
        for (Role role : roles)
        {
            for (Role userRole : userRoles)
            {
                if (role.getRoleId() == userRole.getRoleId())
                {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    /**
     * 查询所有角色
     * 
     * @return 角色列表
     */
    @Override
    public List<Role> selectRoleAll()
    {
        return roleDao.selectRolesAll();
    }

    /**
     * 通过角色ID查询角色
     * 
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public Role selectRoleById(Long roleId)
    {
        return roleDao.selectRoleById(roleId);
    }

    /**
     * 通过角色ID删除角色
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int deleteRoleById(Long roleId)
    {
        roleMenuDao.deleteRoleMenuByRoleId(roleId);
        return roleDao.deleteRoleById(roleId);
    }

    /**
     * 批量删除角色用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int batchDeleteRole(Long[] ids)
    {
        roleMenuDao.deleteRoleMenu(ids);
        return roleDao.batchDeleteRole(ids);
    }

    /**
     * 保存角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int saveRole(Role role)
    {
        Long roleId = role.getRoleId();
        if (StringUtils.isNotNull(roleId))
        {
            role.setUpdateBy(ShiroUtils.getLoginName());
            // 修改角色信息
            roleDao.updateRole(role);
            // 删除角色与菜单关联
            roleMenuDao.deleteRoleMenuByRoleId(roleId);
        }
        else
        {
            role.setCreateBy(ShiroUtils.getLoginName());
            // 新增角色信息
            roleDao.insertRole(role);
        }
        return insertRoleMenu(role);
    }

    /**
     * 新增角色菜单信息
     * 
     * @param user 角色对象
     */
    public int insertRoleMenu(Role role)
    {
        int rows = 1;
        // 新增用户与角色管理
        List<RoleMenu> list = new ArrayList<RoleMenu>();
        for (Long menuId : role.getMenuIds())
        {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0)
        {
            rows = roleMenuDao.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 校验角色名称是否唯一
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleNameUnique(Role role)
    {
        Long roleId = role.getRoleId();
        Role info = roleDao.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getRoleId()) && info.getRoleId() != roleId)
        {
            return UserConstants.NAME_NOT_UNIQUE;
        }
        return UserConstants.NAME_UNIQUE;
    }

    /**
     * 通过角色ID查询角色使用数量
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int selectCountUserRoleByRoleId(Long roleId)
    {
        return userRoleDao.selectCountUserRoleByRoleId(roleId);
    }

}
