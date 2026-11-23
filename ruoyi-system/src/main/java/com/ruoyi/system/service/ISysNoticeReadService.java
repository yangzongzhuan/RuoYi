package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.SysNotice;

/**
 * 公告已读记录 服务层
 *
 * @author ruoyi
 */
public interface ISysNoticeReadService
{
    /**
     * 标记已读（幂等，重复调用不报错）
     *
     * @param noticeId 公告ID
     * @param userId   用户ID
     */
    public void markRead(Long noticeId, Long userId);

    /**
     * 查询某用户未读公告数量
     *
     * @param userId 用户ID
     * @return 未读数量
     */
    public int selectUnreadCount(Long userId);

    /**
     * 查询公告列表并标记当前用户已读状态（用于首页展示）
     *
     * @param userId 用户ID
     * @param limit  最多返回条数
     * @return 带 isRead 标记的公告列表
     */
    public List<SysNotice> selectNoticeListWithReadStatus(Long userId, int limit);

    /**
     * 批量标记已读
     *
     * @param userId    用户ID
     * @param noticeIds 公告ID数组
     */
    public void markReadBatch(Long userId, Long[] noticeIds);

    /**
     * 删除公告时清理对应已读记录
     *
     * @param ids 公告ID字符串（逗号分隔）
     */
    public void deleteByNoticeIds(String ids);

    /**
     * 查询已阅读某公告的用户列表
     *
     * @param noticeId  公告ID
     * @param searchValue 搜索值
     * @return 已读用户列表
     */
    public List<Map<String, Object>> selectReadUsersByNoticeId(Long noticeId, String searchValue);
}
