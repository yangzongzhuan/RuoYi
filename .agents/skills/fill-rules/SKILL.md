---
name: fill-rules
description: 从 .harnesskit/facts.md 和已核对的仓库证据填充或刷新 RULES.md 短规则索引与 .harnesskit/rules/RULE-*.md details。用于 scan-facts 之后更新可执行规则、rule details、validation context 和 runner bindings。
---

# 填充 Rules

使用本 skill 更新 [RULES.md](../../../RULES.md) 作为短约束索引，并更新 [.harnesskit/rules/](../../../.harnesskit/rules/) 下的 `RULE-*.md` 作为 details 层。

## 工作流

1. 读取 [.harnesskit/facts.md](../../../.harnesskit/facts.md)、当前 [RULES.md](../../../RULES.md)、[.harnesskit/rules/](../../../.harnesskit/rules/)、validation scripts、manifests、hook/CI config 和 agent guidance。
2. 只有当事项是仓库本地约束或已明确采纳、跨任务稳定、有清晰违规形态，并且有证据支撑时，才升级为 Rule。
3. 写入 [RULES.md](../../../RULES.md) 或 [.harnesskit/rules/](../../../.harnesskit/rules/) `RULE-*.md` 文件前，把 candidate rule changes 作为 single-choice MCQ 展示给用户。如果当前 Codex surface 支持 native single-choice UI，就使用它；否则把选项渲染成文本并等待用户字母或修正。
   - A. 确认所有 candidate rule changes 并写入。
   - B. 写入前先修正一个或多个 candidate rules。
   - C. 暂时跳过 rule changes 写入。
   - D. 只写入 high-confidence repository rules，其余保留为 `[NEEDS CLARIFICATION: ...]`。
   如果用户修正规则、分类、validation binding 或 runner status，把该修正视为 user-confirmed evidence 并记录。
4. category headings 只用于组织；不要把 general engineering、AI coding、stack、architecture 或 domain 等分类本身当成启用规则的证据。
5. [RULES.md](../../../RULES.md) 中每条 rule 保持为一句短约束，并链接到对应 details 文件。
6. 将 rationale、evidence、validation context、runner bindings、caveats 和 examples 放入 [.harnesskit/rules/](../../../.harnesskit/rules/) 中对应 details 文件的 `## Details` section；不要要求单独的 `## Validation` heading。
7. 对 unsupported 或 unverified rules 标记为 `[NEEDS CLARIFICATION: ...]`、`N/A`、`未配置` 或 `不适用`，不要从示例直接启用。
8. 在 details 文件中区分 rule、validation/check 和 runner；没有 runner 证据时，不要声称某个检查会阻断变更。
9. 除非用户明确要求迁移，否则保留已有客户手写 rules 和结构。

## 用户确认协议

使用类似下面的简短 MCQ 确认提示：

```text
我找到这些候选 Rule 变更。写入 `RULES.md` 和 `.harnesskit/rules/RULE-*.md` 前请确认。

1. 新增 / 更新 rule: RULE-...
   约束: ...
   分类: ...
   证据: ...
   Validation: ...
   Runner / binding: ...

2. 保留未解决事项: ...
   原因: ...

请选择：
A. 确认所有 candidate rule changes 并写入。
B. 写入前先修正一个或多个 candidate rules。
C. 暂时跳过 rule changes 写入。
D. 只写入 high-confidence repository rules，其余保留为 `[NEEDS CLARIFICATION: ...]`。
```

如果当前 Codex surface 有 native single-choice UI，用该 UI 展示四个选项；否则使用上方文本 MCQ。然后暂停等待用户回复。没有用户确认时，不要把 facts、通用工程建议、template examples 或 inferred commands 静默升级成 durable Rules。

## 输出

只更新 [RULES.md](../../../RULES.md) 和 [.harnesskit/rules/](../../../.harnesskit/rules/) 下的 `RULE-*.md` 文件。如果 [AGENTS.md](../../../AGENTS.md)、[ARCHITECTURE.md](../../../ARCHITECTURE.md) 或 skills 需要匹配变更，调用对应 fill skill。

## 边界

- 不要虚构 commands、CI、branch protection、coverage thresholds、typecheck、docs build 或 PR requirements。
- 不要把通用工程建议、任务步骤、工具教程、临时计划或未验证猜测变成 Rules。
- workflows 放进 skills 或 [AGENTS.md](../../../AGENTS.md)；目录地图放进 [ARCHITECTURE.md](../../../ARCHITECTURE.md)；产品背景放进 README 或 docs。
- 不要把局部 review judgment 标记为确定性 validation coverage。
- 不要把 [.harnesskit/facts.md](../../../.harnesskit/facts.md) 当成核对真实 runner config 的替代品。
- 除非用户明确要求迁移，否则不要重构现有非模板 [RULES.md](../../../RULES.md)。
