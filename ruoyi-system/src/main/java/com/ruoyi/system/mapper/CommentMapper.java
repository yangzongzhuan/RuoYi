package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Comment;

/**
 * Comment_checkMapper接口
 * 
 * @author ruoyi
 * @date 2021-05-09
 */
public interface CommentMapper 
{
    /**
     * 查询Comment_check
     * 
     * @param id Comment_checkID
     * @return Comment_check
     */
    public Comment selectCommentById(Long id);

    /**
     * 查询Comment_check列表
     * 
     * @param comment Comment_check
     * @return Comment_check集合
     */
    public List<Comment> selectCommentList(Comment comment);

    /**
     * 新增Comment_check
     * 
     * @param comment Comment_check
     * @return 结果
     */
    public int insertComment(Comment comment);

    /**
     * 修改Comment_check
     * 
     * @param comment Comment_check
     * @return 结果
     */
    public int updateComment(Comment comment);

    /**
     * 删除Comment_check
     * 
     * @param id Comment_checkID
     * @return 结果
     */
    public int deleteCommentById(Long id);

    /**
     * 批量删除Comment_check
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCommentByIds(String[] ids);
}
