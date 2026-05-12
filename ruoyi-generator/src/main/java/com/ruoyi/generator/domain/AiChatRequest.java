package com.ruoyi.generator.domain;

import java.util.List;

/**
 * AI聊天请求体
 *
 * @author ruoyi
 */
public class AiChatRequest
{
    /** API基础地址 */
    private String baseUrl;

    /** API密钥 */
    private String apiKey;

    /** 接口协议: OpenAI, Anthropic, OpenAIResponse */
    private String modelType;

    /** 模型名称 */
    private String modelName;

    /** 用户消息 */
    private String message;

    /** 工作目录 */
    private String workingDir;

    /** 历史对话 [{role:"user",content:"..."}, {role:"assistant",content:"..."}] */
    private List<AiChatMessage> history;

    public String getBaseUrl()
    {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    public String getApiKey()
    {
        return apiKey;
    }

    public void setApiKey(String apiKey)
    {
        this.apiKey = apiKey;
    }

    public String getModelType()
    {
        return modelType;
    }

    public void setModelType(String modelType)
    {
        this.modelType = modelType;
    }

    public String getModelName()
    {
        return modelName;
    }

    public void setModelName(String modelName)
    {
        this.modelName = modelName;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getWorkingDir()
    {
        return workingDir;
    }

    public void setWorkingDir(String workingDir)
    {
        this.workingDir = workingDir;
    }

    public List<AiChatMessage> getHistory()
    {
        return history;
    }

    public void setHistory(List<AiChatMessage> history)
    {
        this.history = history;
    }
}
