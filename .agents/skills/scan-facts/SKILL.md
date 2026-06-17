---
name: scan-facts
description: 从已核对证据扫描仓库事实并写入 .harnesskit/facts.md。用于填充 AGENTS.md、ARCHITECTURE.md、RULES.md 或项目特定 skill section 前，启动或刷新 Context Harness facts。
---

# 扫描 Facts

使用本 skill 从仓库证据刷新 [.harnesskit/facts.md](../../../.harnesskit/facts.md)。本 skill 是唯一应该创建或刷新 facts artifact 的 generated skill。

## 输入

读取最小但足够有用的 repository-owned evidence：

- Existing guidance：[AGENTS.md](../../../AGENTS.md)、[CLAUDE.md](../../../CLAUDE.md)、[RULES.md](../../../RULES.md)、[.agents/skills/](../../skills/) skill files、architecture notes 和相关 docs。
- Project identity：`README*`、product/design docs、package metadata 和顶层目录名。
- Tech stack facts：manifests、lockfiles、workspace files、source/test layout、tool config、CI/pre-commit config 和 documented commands。
- Current state：`[NEEDS CLARIFICATION: ...]` placeholders、todo-checklist marker blocks、missing files、stale paths，或与仓库文件冲突的 guidance。

忽略 local/generated/vendor 噪音，例如 virtual environments、dependency folders、caches、build output、downloaded dependencies 和 editor metadata，除非用户明确询问。

## 工作流

1. 在提问前先检查仓库事实。
2. 写入 durable facts 前，先为用户准备简短的 "candidate facts" 确认消息。包含：
   - project name；
   - project purpose；
   - 有证据时的 primary audience；
   - language/runtime/platform 和 key frameworks；
   - package/build/test/lint/format entrypoints；
   - important source、test、docs、specs、scripts 和 config directories；
   - 任何需要人类选择的 high-impact uncertainty。
3. 用 single-choice MCQ 请求用户确认或修正 candidate facts。如果当前 Codex surface 支持 native single-choice UI，就使用它；否则把选项渲染成文本并等待用户字母或修正。
   - A. 确认所有 candidate facts 并写入。
   - B. 写入前先修正一个或多个 facts。
   - C. 暂时跳过 durable facts 写入。
   - D. 只写入 high-confidence repository facts，并把 human-owned items 保留为 `[NEEDS CLARIFICATION: ...]`。
   如果用户修正 fact，把该修正视为 user-confirmed evidence 并记录。
4. 只有确认后，才把 evidence-backed 或 user-confirmed facts 记录到 [.harnesskit/facts.md](../../../.harnesskit/facts.md)。
5. 对 unresolved items 保留 `[NEEDS CLARIFICATION: ...]`，并简短说明缺少什么证据。
6. 不要从本 skill 更新 [AGENTS.md](../../../AGENTS.md)、[ARCHITECTURE.md](../../../ARCHITECTURE.md)、[RULES.md](../../../RULES.md) 或其他 skills。
7. 如果 [.harnesskit/facts.md](../../../.harnesskit/facts.md) 缺失，使用 generated template 的相同 sections 重建。

## 用户确认协议

使用类似下面的简短 MCQ 确认提示：

```text
我检测到这些候选 Harness facts。写入 `.harnesskit/facts.md` 前请确认。

1. Project name: ...
   Evidence: ...

2. Project purpose: ...
   Evidence: ...

3. Tech stack: ...
   Evidence: ...

4. Validation entrypoints: ...
   Evidence: ...

5. Important directories: ...
   Evidence: ...

请选择：
A. 确认所有 candidate facts 并写入。
B. 写入前先修正一个或多个 facts。
C. 暂时跳过 durable facts 写入。
D. 只写入 high-confidence repository facts，并把 human-owned items 保留为 `[NEEDS CLARIFICATION: ...]`。
```

如果当前 Codex surface 有 native single-choice UI，用该 UI 展示四个选项；否则使用上方文本 MCQ。然后暂停等待用户回复。当扫描包含可由人类确认的用户可见 project identity、purpose、stack、validation commands 或 important boundaries 时，不要静默写入 durable facts。

如果用户明确要求 non-interactive scan，或当前环境无法 follow-up interaction，则写入 high-confidence repository facts，并把所有 uncertain 或 human-owned facts 保留为 `[NEEDS CLARIFICATION: ...]`。

## 事实模型

记录：

- Project identity 和 audience。
- Languages、runtimes、package managers、frameworks、build tools、test frameworks、linters、formatters 和 type checkers。
- Validation entrypoints：setup、full verify、test、lint、format check、typecheck、coverage、build、docs、link check、hook suite 和 CI/platform gates。
- Repository map candidates：重要 source、test、docs、config、generated-output 和 tooling paths。
- Agent-facing assets 和 installed local skills。
- 带 runner evidence 的 Rule 与 Validation candidates。
- 仓库事实无法确定的 open questions。

## 边界

- 不要虚构 commands、tools、URLs、CI、release processes、PR templates、architecture 或 compatibility policy。
- 不要把 generic template examples 当成目标仓库支持某工具的证据。
- 除非 command、script、hook、CI task 或 platform setting 提供明确 pass/fail 证据，否则不要把 Validation 标记为 deterministic。
- guidance-only refresh 不要运行 runtime test suites，除非 refresh 也改变运行时代码、模板、构建/测试配置或生成行为。
