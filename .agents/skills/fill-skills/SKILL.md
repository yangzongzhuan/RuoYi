---
name: fill-skills
description: 从 .harnesskit/facts.md 填充 generated skills 中的项目特定 section。用于 scan-facts 之后配置验证命令、兼容性边界、PR summary category signals 或其他本地 skill placeholders。
---

# 填充 Skills

使用本 skill 更新 generated skills 内的项目特定 section。它读取 [.harnesskit/facts.md](../../../.harnesskit/facts.md)，并对高影响声明回到仓库证据核对。

## 工作流

1. 读取 [.harnesskit/facts.md](../../../.harnesskit/facts.md) 和 [.agents/skills/](../../skills/) 下的 generated skills。
2. 只更新 intended-to-fill 的项目特定 section，例如 verification stack、compatibility boundaries、PR summary category signals 和 local trigger notes。
3. 除非 generic procedural guidance 与仓库事实冲突，否则保持稳定。
4. 未解决事项保留为 `[NEEDS CLARIFICATION: ...]`。
5. 如果变更影响 [AGENTS.md](../../../AGENTS.md)、[ARCHITECTURE.md](../../../ARCHITECTURE.md) 或 [RULES.md](../../../RULES.md)，调用对应 fill skill，不要从这里直接编辑那些 artifact。

## 输出

只有当项目特定 placeholders 能从 facts 和仓库证据解析时，才更新 [.agents/skills/](../../skills/) 下的文件。

## 边界

- 不要新增工具或验证命令，除非仓库已经定义它们，或用户明确要求该变更。
- 不要把 skill 正文改写成项目文档。
- 不要移除仍代表真实不确定性的 placeholders。
