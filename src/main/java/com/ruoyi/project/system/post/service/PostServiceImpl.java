package com.ruoyi.project.system.post.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.system.post.domain.Post;
import com.ruoyi.project.system.post.mapper.PostMapper;
import com.ruoyi.project.system.user.mapper.UserPostMapper;

/**
 * 岗位信息 服务层处理
 * 
 * @author ruoyi
 */
@Service("postService")
public class PostServiceImpl implements IPostService
{
    @Autowired
    private PostMapper postMapper;
    
    @Autowired
    private UserPostMapper userPostMapper;

    /**
     * 查询岗位信息集合
     * 
     * @param post 岗位信息
     * @return 岗位信息集合
     */
    @Override
    public List<Post> selectPostList(Post post)
    {
        return postMapper.selectPostList(post);
    }

    /**
     * 查询所有岗位
     * 
     * @return 岗位列表
     */
    @Override
    public List<Post> selectPostAll()
    {
        return postMapper.selectPostAll();
    }

    /**
     * 根据用户ID查询岗位
     * 
     * @param userId 用户ID
     * @return 岗位列表
     */
    @Override
    public List<Post> selectPostsByUserId(Long userId)
    {
        List<Post> userPosts = postMapper.selectPostsByUserId(userId);
        List<Post> posts = postMapper.selectPostAll();
        for (Post post : posts)
        {
            for (Post userRole : userPosts)
            {
                if (post.getPostId() == userRole.getPostId())
                {
                    post.setFlag(true);
                    break;
                }
            }
        }
        return posts;
    }

    /**
     * 通过岗位ID查询岗位信息
     * 
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    @Override
    public Post selectPostById(Long postId)
    {
        return postMapper.selectPostById(postId);
    }

    /**
     * 通过岗位ID删除岗位信息
     * 
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public int deletePostById(Long postId)
    {
        return postMapper.deletePostById(postId);
    }

    /**
     * 批量删除岗位信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int batchDeletePost(Long[] ids)
    {
        return postMapper.batchDeletePost(ids);
    }

    /**
     * 保存岗位信息
     * 
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public int savePost(Post post)
    {
        Long postId = post.getPostId();
        int count = 0;
        if (StringUtils.isNotNull(postId))
        {
            post.setUpdateBy(ShiroUtils.getLoginName());
            // 修改岗位信息
            count = postMapper.updatePost(post);
        }
        else
        {
            post.setCreateBy(ShiroUtils.getLoginName());
            // 新增岗位信息
            count = postMapper.insertPost(post);
        }
        return count;
    }

    /**
     * 通过岗位ID查询岗位使用数量
     * 
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public int selectCountPostById(Long postId)
    {
        return userPostMapper.selectCountPostById(postId);
    }

}
