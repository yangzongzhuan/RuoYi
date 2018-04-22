package com.ruoyi.project.system.post.dao;

import java.util.List;
import com.ruoyi.project.system.post.domain.Post;

/**
 * 岗位信息 数据层
 * 
 * @author ruoyi
 */
public interface IPostDao
{

    /**
     * 查询系统操作日志集合
     * 
     * @param post 岗位信息
     * @return 操作日志集合
     */
    public List<Post> selectPostList(Post post);

    /**
     * 查询所有岗位
     * 
     * @return 岗位列表
     */
    public List<Post> selectPostAll();

    /**
     * 根据用户ID查询岗位
     * 
     * @param userId 用户ID
     * @return 岗位列表
     */
    public List<Post> selectPostsByUserId(Long userId);

    /**
     * 通过岗位ID查询岗位信息
     * 
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    public Post selectPostById(Long postId);

    /**
     * 通过岗位ID删除岗位信息
     * 
     * @param postId 岗位ID
     * @return 结果
     */
    public int deletePostById(Long postId);

    /**
     * 批量删除岗位信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int batchDeletePost(Long[] ids);

    /**
     * 修改岗位信息
     * 
     * @param post 岗位信息
     * @return 结果
     */
    public int updatePost(Post post);

    /**
     * 新增岗位信息
     * 
     * @param post 岗位信息
     * @return 结果
     */
    public int insertPost(Post post);

}
