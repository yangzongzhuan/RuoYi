---
name: harness-init
description: 在 harnesskit init 之后编排 Context Harness 初始化。用于通过 scan-facts、fill-agents、fill-architecture、fill-practices、fill-rules、fill-skills 和最终一致性检查来启动或刷新 generated harness。
---

# Harness 初始化

把本 skill 作为 `harnesskit init` 之后的第一个用户可见 workflow。它协调 fact-first fill flow，而不是把每个专项指令复制进一个巨大的 skill。

## 工作流

1. 使用 $scan-facts 检查仓库证据，并在写入 durable facts 前，请用户确认候选 project identity、purpose、stack、validation entrypoints 和重要边界。
2. 在写入 durable guidance 前，先解决 facts 无法确定的高影响问题。
3. 使用 $fill-architecture 更新 [ARCHITECTURE.md](../../../ARCHITECTURE.md)。
4. 使用 $fill-practices 更新 [docs/practices/](../../../docs/practices/) 判断指导。
5. 使用 $fill-rules 展示 candidate rule changes 并请求用户确认，然后更新 [RULES.md](../../../RULES.md) 和 [.harnesskit/rules/](../../../.harnesskit/rules/) 下的 `RULE-*.md` 文件。
6. 使用 $fill-agents 更新 [AGENTS.md](../../../AGENTS.md)。
7. 使用 $fill-skills 更新 generated skills 中的项目特定 section。
8. 检查 [.harnesskit/facts.md](../../../.harnesskit/facts.md)、[AGENTS.md](../../../AGENTS.md)、[ARCHITECTURE.md](../../../ARCHITECTURE.md)、[RULES.md](../../../RULES.md)、[docs/practices/](../../../docs/practices/)、[.agents/skills/](../../skills/)、[CLAUDE.md](../../../CLAUDE.md) 和 verification entrypoint 的一致性。

## 一致性检查

检查：

- [AGENTS.md](../../../AGENTS.md) 引用的 skills 都存在于 [.agents/skills/](../../skills/)；
- [AGENTS.md](../../../AGENTS.md) 将地图路由到 [ARCHITECTURE.md](../../../ARCHITECTURE.md)、rules 路由到 [RULES.md](../../../RULES.md)、procedures 路由到 skills；
- [docs/practices/](../../../docs/practices/) 承载判断指导，硬约束仍留在 [RULES.md](../../../RULES.md)；
- [RULES.md](../../../RULES.md) 中的 command bindings 与 verification skill 和 runner config 一致；
- [ARCHITECTURE.md](../../../ARCHITECTURE.md) 链接指向真实路径；
- 未解决的不确定性仍保留为 `[NEEDS CLARIFICATION: ...]`。

## 边界

- 不要让本 skill 取代专项 fill skills；按顺序调用它们。
- 不要虚构 repository facts、commands、CI、branch protection 或 release policy。
- 对 guidance-only edits 不要运行完整 runtime verification，除非这些编辑也影响运行时代码、模板、构建/测试配置或生成行为。
