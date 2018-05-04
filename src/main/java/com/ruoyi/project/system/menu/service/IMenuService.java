package com.ruoyi.project.system.menu.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ruoyi.project.system.menu.domain.Menu;
import com.ruoyi.project.system.role.domain.Role;

/**
 * 菜单 业务层
 * 
 * @author ruoyi
 */
public interface IMenuService
{

    /**
     * 根据用户ID查询菜单
     * 
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<Menu> selectMenusByUserId(Long userId);

    /**
     * 查询菜单集合
     * 
     * @return 所有菜单信息
     */
    public List<Menu> selectMenuAll();

    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectPermsByUserId(Long userId);

    /**
     * 根据角色ID查询菜单
     * 
     * @param role 角色对象
     * @return 菜单列表
     */
    public List<Map<String, Object>> roleMenuTreeData(Role role);

    /**
     * 查询所有菜单信息
     * 
     * @return 菜单列表
     */
    public List<Map<String, Object>> menuTreeData();

    /**
     * 查询系统所有权限
     * 
     * @return 权限列表
     */
    public Map<String, String> selectPermsAll();

    /**
     * 删除菜单管理信息
     * 
     * @param menuId 菜单ID
     * @return 结果
     */
    public int deleteMenuById(Long menuId);

    /**
     * 根据菜单ID查询信息
     * 
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    public Menu selectMenuById(Long menuId);

    /**
     * 查询子菜单数量
     * 
     * @param menuId 菜单ID
     * @return 结果
     */
    public int selectCountMenuByParentId(Long parentId);

    /**
     * 查询菜单使用数量
     * 
     * @param menuId 菜单ID
     * @return 结果
     */
    public int selectCountRoleMenuByMenuId(Long menuId);

    /**
     * 保存菜单信息
     * 
     * @param menu 菜单信息
     * @return 结果
     */
    public int saveMenu(Menu menu);

    /**
     * 校验菜单名称是否唯一
     * 
     * @param menu 菜单信息
     * @return 结果
     */
    public String checkMenuNameUnique(Menu menu);

}
