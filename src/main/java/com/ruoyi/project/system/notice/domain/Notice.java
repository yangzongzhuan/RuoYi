package com.ruoyi.project.system.notice.domain;

import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 公告表 sys_notice
 * 
 * @author ruoyi
 */
public class Notice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 公告ID */
    private Integer noticeId;
    /** 公告标题 */
    private String noticeTitle;
    /** 公告类型（1通知 2公告） */
    private String noticeType;
    /** 公告内容 */
    private String noticeContent;
    /** 公告状态（0正常 1关闭） */
    private String status;

    public void setNoticeId(Integer noticeId)
    {
        this.noticeId = noticeId;
    }

    public Integer getNoticeId()
    {
        return noticeId;
    }

    public void setNoticeTitle(String noticeTitle)
    {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeTitle()
    {
        return noticeTitle;
    }

    public void setNoticeType(String noticeType)
    {
        this.noticeType = noticeType;
    }

    public String getNoticeType()
    {
        return noticeType;
    }

    public void setNoticeContent(String noticeContent)
    {
        this.noticeContent = noticeContent;
    }

    public String getNoticeContent()
    {
        return noticeContent;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

}
