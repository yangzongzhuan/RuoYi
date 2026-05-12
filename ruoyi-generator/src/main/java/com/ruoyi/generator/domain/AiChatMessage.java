package com.ruoyi.generator.domain;

/**
 * AI聊天消息（用于历史对话传递）
 *
 * @author ruoyi
 */
public class AiChatMessage
{
    /** 角色: user / assistant */
    private String role;

    /** 消息内容 */
    private String content;

    public AiChatMessage()
    {
    }

    public AiChatMessage(String role, String content)
    {
        this.role = role;
        this.content = content;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
