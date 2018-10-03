package com.ruoyi.project.system.menu.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.project.system.menu.domain.Menu;

/**
 * 菜单表 数据层
 * 
 * @author ruoyi
 */
public interface MenuMapper
{
    /**
     * 查询系统所有菜单（含按钮）
     * 
     * @return 菜单列表
     */
    public List<Menu> selectMenuAll();
    
    /**
     * 查询系统正常显示菜单（不含按钮）
     * 
     * @return 菜单列表
     */
    public List<Menu> selectMenuNormalAll();
    
    /**
     * 根据用户ID查询菜单
     * 
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<Menu> selectMenusByUserId(Long userId);

    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    public List<String> selectPermsByUserId(Long userId);

    /**
     * 根据角色ID查询菜单
     * 
     * @param roleId 角色ID
     * @return 菜单列表
     */
    public List<String> selectMenuTree(Long roleId);

    /**
     * 查询系统菜单列表
     * 
     * @param menu 菜单信息
     * @return 菜单列表
     */
    public List<Menu> selectMenuList(Menu menu);

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
     * 查询菜单数量
     * 
     * @param parentId 菜单父ID
     * @return 结果
     */
    public int selectCountMenuByParentId(Long parentId);

    /**
     * 新增菜单信息
     * 
     * @param menu 菜单信息
     * @return 结果
     */
    public int insertMenu(Menu menu);

    /**
     * 修改菜单信息
     * 
     * @param menu 菜单信息
     * @return 结果
     */
    public int updateMenu(Menu menu);

    /**
     * 校验菜单名称是否唯一
     * 
     * @param menuName 菜单名称
     * @param parentId 父菜单ID
     * @return 结果
     */
    public Menu checkMenuNameUnique(@Param("menuName") String menuName, @Param("parentId") Long parentId);
}
