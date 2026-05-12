package com.ruoyi.generator.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import ink.icoding.llm.agent.*;
import ink.icoding.llm.core.entity.Message;
import ink.icoding.llm.core.entity.ModelType;
import ink.icoding.llm.core.model.LLMModel;
import ink.icoding.llm.core.tool.ToolDescriptor;
import ink.icoding.llm.core.tool.ToolStatus;
import ink.icoding.llm.core.tool.builtin.skill.BuiltInSkills;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.alibaba.fastjson.JSON;
import com.ruoyi.generator.domain.AiChatMessage;
import com.ruoyi.generator.domain.AiChatRequest;

/**
 * AI代码生成 操作处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/tool/ai-gen")
public class AiGenController
{
    private String prefix = "tool/gen";

    private final ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * AI代码生成页面
     */
    @GetMapping()
    public String aiGen()
    {
        return prefix + "/aiGen";
    }

    /**
     * 获取支持的模型类型列表
     */
    @GetMapping("/modelTypes")
    @ResponseBody
    public List<Map<String, String>> modelTypes()
    {
        List<Map<String, String>> list = new ArrayList<>();
        for (ModelType type : ModelType.values())
        {
            Map<String, String> item = new HashMap<>();
            item.put("value", type.name());
            switch (type)
            {
                case OpenAI:
                    item.put("label", "OpenAI Chat");
                    break;
                case OpenAIResponse:
                    item.put("label", "OpenAI Response");
                    break;
                case Anthropic:
                    item.put("label", "Anthropic");
                    break;
            }
            list.add(item);
        }
        return list;
    }

    /**
     * AI聊天 SSE流式接口
     */
    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public SseEmitter chat(@RequestBody AiChatRequest request)
    {
        SseEmitter emitter = new SseEmitter(300_000L);

        executor.submit(() ->
        {
            try
            {
                ModelType type = ModelType.valueOf(request.getModelType());
                LLMModel model = LLMModel.create(type, request.getBaseUrl(), request.getModelName(), request.getApiKey());

                AgentClient agent = new AgentClient();
                agent.setName("若依开发助手");
                agent.setDescription("一个专业的全栈开发工程师, 擅长结构化的Java开发. 你现在的任务就是遵循开发者提出的要求进行若依项目的代码开发(若依是一个Java脚手架).");
                agent.setModel(model);
                agent.getSkills().addAll(BuiltInSkills.all());

                String projectPath = new File("").getAbsolutePath();
                String workingDir = request.getWorkingDir() != null && !request.getWorkingDir().isEmpty()
                        ? request.getWorkingDir() : projectPath;

                agent.getSkills().add(new Skill("文件系统", "提供文件读写操作", List.of(), """
                    ## 开发规范
                    1. 非必要不要动ruoyi-framework模块下的内容, 可以使用, 但不要修改里面的代码. 若需要修改时, 必须先询问用户并征得用户同意.
                    2. 代码必须结构化清晰, 注释完整, 变量和方法命名规范, 符合Java编程习惯. 涉及到常量定义时, 优先使用字典表.
                    3. 当你新建了字典, 需要提醒用户更新字典表.
                    4. 当你新建了页面, 需要提醒用户更新菜单.
                    5. 当前项目路径: %s, 你可以在这个路径下开发, 非必要不要溢出这个路径.
                    6. 当前用户正停留在%s目录下.
                    """.formatted(projectPath, workingDir)));

                AgentClientSession session = agent.createSession();

                // 注入历史对话（最近5轮）
                if (request.getHistory() != null)
                {
                    for (int i = 0; i < request.getHistory().size() - 1; i++){
                        AiChatMessage msg = request.getHistory().get(i);
                        if ("user".equals(msg.getRole()))
                        {
                            session.getHistory().add(Message.fromUser(msg.getContent()));
                        }
                        else if ("assistant".equals(msg.getRole()))
                        {
                            session.getHistory().add(Message.fromAssistant(msg.getContent()));
                        }
                    }
                }

                AgentSessionResult result = session.command(request.getMessage());
                result.then(new AgentResultHandler()
                {
                    @Override
                    public void onMessage(String message)
                    {
                        sendEvent(emitter, "message", JSON.toJSONString(message));
                    }

                    @Override
                    public void onThink(String think)
                    {
                        sendEvent(emitter, "think", JSON.toJSONString(think));
                    }

                    @Override
                    public void onTool(ToolDescriptor tool, ToolStatus status)
                    {
                        Map<String, Object> data = new HashMap<>();
                        data.put("name", tool.getName());
                        data.put("description", tool.getDescription());
                        data.put("status", status.name());
                        if (tool.getInputParams() != null)
                        {
                            data.put("params", tool.getInputParams());
                        }
                        sendEvent(emitter, "tool", JSON.toJSONString(data));
                    }

                    @Override
                    public void onPlanCreated(Plan plan)
                    {
                        Map<String, Object> data = new HashMap<>();
                        data.put("name", plan.getName());
                        data.put("description", plan.getDescription());
                        data.put("steps", plan.getSteps());
                        sendEvent(emitter, "planCreated", JSON.toJSONString(data));
                    }

                    @Override
                    public void onPlanExecuted(Plan plan)
                    {
                        Map<String, Object> data = new HashMap<>();
                        data.put("name", plan.getName());
                        sendEvent(emitter, "planExecuted", JSON.toJSONString(data));
                    }

                    @Override
                    public void onPlanStepStart(Plan plan, int current, int total, String step)
                    {
                        Map<String, Object> data = new HashMap<>();
                        data.put("planName", plan.getName());
                        data.put("current", current);
                        data.put("total", total);
                        data.put("step", step);
                        sendEvent(emitter, "planStepStart", JSON.toJSONString(data));
                    }

                    @Override
                    public void onPlanStepComplete(Plan plan, int current, int total, String step, String result)
                    {
                        Map<String, Object> data = new HashMap<>();
                        data.put("planName", plan.getName());
                        data.put("current", current);
                        data.put("total", total);
                        data.put("step", step);
                        data.put("result", result);
                        sendEvent(emitter, "planStepComplete", JSON.toJSONString(data));
                    }

                    @Override
                    public void onPlanStepTool(Plan plan, ToolDescriptor tool, ToolStatus status)
                    {
                        Map<String, Object> data = new HashMap<>();
                        data.put("planName", plan.getName());
                        data.put("toolName", tool.getName());
                        data.put("toolDesc", tool.getDescription());
                        data.put("status", status.name());
                        sendEvent(emitter, "planStepTool", JSON.toJSONString(data));
                    }

                    @Override
                    public void onPlanStepError(Plan plan, int current, int total, String step, Exception error)
                    {
                        Map<String, Object> data = new HashMap<>();
                        data.put("planName", plan.getName());
                        data.put("current", current);
                        data.put("total", total);
                        data.put("step", step);
                        data.put("error", error.getMessage());
                        sendEvent(emitter, "planStepError", JSON.toJSONString(data));
                    }

                    @Override
                    public void onSubAgent(AgentClient subAgent, String message)
                    {
                        Map<String, Object> data = new HashMap<>();
                        data.put("name", subAgent.getName());
                        data.put("description", subAgent.getDescription());
                        data.put("message", message);
                        sendEvent(emitter, "subAgent", JSON.toJSONString(data));
                    }

                    @Override
                    public void onSubAgentStart(AgentClient subAgent, String task)
                    {
                        Map<String, Object> data = new HashMap<>();
                        data.put("name", subAgent.getName());
                        data.put("task", task);
                        sendEvent(emitter, "subAgentStart", JSON.toJSONString(data));
                    }

                    @Override
                    public void onSubAgentResult(AgentClient subAgent, String result)
                    {
                        Map<String, Object> data = new HashMap<>();
                        data.put("name", subAgent.getName());
                        data.put("result", result);
                        sendEvent(emitter, "subAgentResult", JSON.toJSONString(data));
                    }
                }).error(e ->
                {
                    sendEvent(emitter, "error", e.getMessage());
                }).execute();

                // 阻塞等待 agent 完成, 完成后才发 [DONE]
                try
                {
                    result.get();
                }
                catch (Exception e)
                {
                    sendEvent(emitter, "error", e.getMessage());
                }
                sendEvent(emitter, "done", "[DONE]");
                emitter.complete();
            }
            catch (Exception e)
            {
                sendEvent(emitter, "error", e.getMessage());
                emitter.completeWithError(e);
            }
        });

        emitter.onTimeout(emitter::complete);
        emitter.onError(e -> emitter.complete());

        return emitter;
    }

    private void sendEvent(SseEmitter emitter, String eventName, String data)
    {
        try
        {
            emitter.send(SseEmitter.event().name(eventName).data(data));
        }
        catch (Exception e)
        {
            // 连接已断开, 忽略
        }
    }
}
