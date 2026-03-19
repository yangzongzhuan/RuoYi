package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.SysNoticeRead;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.mapper.SysNoticeReadMapper;
import com.ruoyi.system.service.ISysNoticeReadService;

/**
 * 公告已读记录 服务层实现
 *
 * @author ruoyi
 */
@Service
public class SysNoticeReadServiceImpl implements ISysNoticeReadService
{
    @Autowired
    private SysNoticeReadMapper noticeReadMapper;

    /**
     * 标记已读
     */
    @Override
    public void markRead(Long noticeId, Long userId)
    {
        SysNoticeRead record = new SysNoticeRead();
        record.setNoticeId(noticeId);
        record.setUserId(userId);
        noticeReadMapper.insertNoticeRead(record);
    }

    /**
     * 查询某用户未读公告数量
     */
    @Override
    public int selectUnreadCount(Long userId)
    {
        return noticeReadMapper.selectUnreadCount(userId);
    }

    /**
     * 查询公告列表并标记当前用户已读状态
     */
    @Override
    public List<SysNotice> selectNoticeListWithReadStatus(Long userId, int limit)
    {
        return noticeReadMapper.selectNoticeListWithReadStatus(userId, limit);
    }

    /**
     * 批量标记已读
     */
    @Override
    public void markReadBatch(Long userId, Long[] noticeIds)
    {
        if (noticeIds == null || noticeIds.length == 0)
        {
            return;
        }
        noticeReadMapper.insertNoticeReadBatch(userId, noticeIds);
    }

    /**
     * 删除公告时清理对应已读记录
     */
    @Override
    public void deleteByNoticeIds(String ids)
    {
        noticeReadMapper.deleteByNoticeIds(Convert.toStrArray(ids));
    }
}
