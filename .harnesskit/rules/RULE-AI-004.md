# RULE-AI-004

## Rule

未经用户明确要求，不要重排、合并、删除已有客户手写规则。

## Details

已有规则是用户资产。fill skills 可以补占位符、追加缺失规则或标记待确认；重构现有规则结构必须先获得明确授权。本轮 `$fill-rules` 保留了既有 rule ID 结构，并只用仓库证据填充内容。

证据：

- `RULES.md`
- `.harnesskit/rules/`
- `.agents/skills/fill-rules/SKILL.md`

验证 / runner：

- Runner：review。
- 自动检查：未配置。
