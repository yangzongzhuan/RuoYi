---
name: fill-architecture
description: 从 .harnesskit/facts.md 和已核对的仓库证据填充或刷新 ARCHITECTURE.md，使其保持为粗粒度仓库地图。用于 scan-facts 之后更新重要路径、模块职责、生成资产、外部状态和容易混淆的实现边界。
---

# 填充 Architecture

使用本 skill 更新 [ARCHITECTURE.md](../../../ARCHITECTURE.md)，把它保持为给 agent 和贡献者使用的粗粒度仓库地图。它回答“修改前应该先看哪里”，不回答完整 API、完整工作流或详细设计。

[ARCHITECTURE.md](../../../ARCHITECTURE.md) 不是文件清单。只记录能帮助 agent 定位代码区域、理解职责边界或识别生成/外部状态的路径；不要因为某个 helper、测试文件或 rule module 存在就逐个列出。

当仓库同时存在 `templates/ARCHITECTURE.md` 时，根目录地图和模板地图要保持结构意图一致，但不要求内容一致。根目录 [ARCHITECTURE.md](../../../ARCHITECTURE.md) 描述当前仓库；`templates/ARCHITECTURE.md` 是生成到目标仓库的通用模板。不要把当前仓库路径无证据复制进模板，也不要把模板占位符当成当前仓库事实。

## 工作流

1. 读取 [.harnesskit/facts.md](../../../.harnesskit/facts.md)、当前 [ARCHITECTURE.md](../../../ARCHITECTURE.md)、[AGENTS.md](../../../AGENTS.md)、[RULES.md](../../../RULES.md)、顶层目录、构建清单、测试入口、脚本和现有文档。
2. 对路径职责和边界回到真实仓库文件核对；不要只依赖 [.harnesskit/facts.md](../../../.harnesskit/facts.md)、README 叙述、设计文档或模板示例。
3. 优先记录顶层目录、重要入口文件、生成资产、持久配置、外部状态、发布产物和容易混淆的边界。
4. 对子目录只展开到能改变 agent 查找路径的层级；不要展开到每个 helper 文件、每个测试文件或每个规则模块。
5. `<!-- harnesskit:coverage=direct-children -->` 只加在确实需要 direct-child coverage 的重要目录上，并且必须先验证路径和职责。
6. 无法确认的内容保留为 `[NEEDS CLARIFICATION: ...]`。

## 输出

默认只更新 [ARCHITECTURE.md](../../../ARCHITECTURE.md)，除非用户明确要求同步更新生成模板。如果 `templates/ARCHITECTURE.md` 需要匹配的结构调整，把它作为带占位符的通用模板更新，而不是复制根目录内容。如果 rules、agent 路由、skill 触发点或验证入口也需要变化，在最终总结中说明，或调用对应 fill skill。

推荐结构：

- 顶层地图
- 关键文件
- 生成资产和外部状态
- 边界说明
- 更新规则

## 边界

- 不要把 [ARCHITECTURE.md](../../../ARCHITECTURE.md) 写成工作流指南、API 参考、完整文件清单、测试清单或设计长文。
- 不要列出 generated、vendor、cache、build output，除非它们是 agent-facing contract、发布产物或必须人工维护的生成资产。
- 不要把旧 POC、示例或设计愿景写成当前产品入口；需要明确旧实现和当前 runtime 的边界。
- 不要把模板中的 `[NEEDS CLARIFICATION: ...]` 或示例路径写成当前仓库事实。
- 如果本 skill 的旧写法与 [AGENTS.md](../../../AGENTS.md) 中的职责分层原则冲突，以 [AGENTS.md](../../../AGENTS.md) 为准，并同步修正本 skill。
