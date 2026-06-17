---
name: fill-agents
description: 从 .harnesskit/facts.md 和已核对的仓库证据填充或刷新 AGENTS.md，使其保持为简洁的 agent 启动入口。用于 scan-facts 之后更新操作关键事实、上下文路由、skill 触发点、验证入口、漂移处理，或同步 root/template AGENTS.md 的结构意图。
---

# 填充 Agents

使用本 skill 更新 [AGENTS.md](../../../AGENTS.md)，把它保持为仓库的 agent 启动入口。[AGENTS.md](../../../AGENTS.md) 应只承载少量操作关键事实，并把 agent 路由到规则、架构地图、skills、验证入口和漂移处理。

[AGENTS.md](../../../AGENTS.md) 不是项目知识库。完整目录地图放到 [ARCHITECTURE.md](../../../ARCHITECTURE.md)，约束放到 [RULES.md](../../../RULES.md)，任务流程放到 [.agents/skills/](../../skills/)，产品背景放到 `README.md` 或 `docs/`。

当仓库同时存在 `templates/AGENTS.md` 时，根目录指南和模板指南要保持结构意图一致，但不要求内容一致。根目录 [AGENTS.md](../../../AGENTS.md) 描述当前仓库；`templates/AGENTS.md` 是生成到目标仓库的通用模板。不要把当前仓库事实无证据复制进模板，也不要把模板占位符当成当前仓库事实。

## 工作流

1. 读取 [.harnesskit/facts.md](../../../.harnesskit/facts.md)、当前 [AGENTS.md](../../../AGENTS.md)、[RULES.md](../../../RULES.md)、[ARCHITECTURE.md](../../../ARCHITECTURE.md)、[.agents/skills/](../../skills/) 中的 skill 文件，以及已存在的 `templates/AGENTS.md`。
2. 对高影响声明回到真实仓库文件核对；不要只依赖 [.harnesskit/facts.md](../../../.harnesskit/facts.md)、README 叙述、设计文档或模板示例。
3. 只保留会立刻改变 agent 行动的事实，例如项目类型、命令 runner、用户可见边界、schema/integration 状态、生成输出边界或 companion 指南行为。
4. 保持路由简洁：说明规则、架构事实、实践指导、产品/设计背景、本地 skills、扫描事实和验证入口在哪里。
5. 工作策略只写触发点和边界。详细步骤放进 skills；除非仓库确实需要固定步骤，否则不要写成固定流程清单。
6. 无法确认的内容保留为 `[NEEDS CLARIFICATION: ...]`。

## 输出

默认只更新 [AGENTS.md](../../../AGENTS.md)，除非用户明确要求同步更新生成模板。如果 `templates/AGENTS.md` 需要匹配的结构调整，把它作为带占位符的通用模板更新，而不是复制根目录内容。如果 rules、architecture、验证 skill 或其他资产也需要变化，在最终总结中说明，或调用对应 fill skill。

推荐的根目录结构：

- 职责或定位说明
- 操作关键事实
- 上下文路由
- 工作策略或 skill 触发点
- 验证入口
- 漂移处理

## 边界

- 不要把完整架构地图、规则全集、逐条 Rule 路由、skill 正文或长篇产品背景复制进 [AGENTS.md](../../../AGENTS.md)。
- 不要要求 [AGENTS.md](../../../AGENTS.md) 列出每条规则、每个流程、每个路径，或固定数量的事实。
- 不要虚构验证命令、CI、分支保护、PR 模板或发布流程。
- 不要把 `templates/AGENTS.md` 里的示例或占位符写成当前仓库事实。
- 不要把 [.harnesskit/facts.md](../../../.harnesskit/facts.md) 当成高影响声明的唯一事实来源；必须回到仓库文件核对。
