# RULE-SEC-001

## Rule

不要把 secret、token、私有凭据或机器特定敏感信息写入模板、facts、rules、报告或生成产物。

## Details

安全实践和 review 提示放在 [docs/practices/SECURITY.md](../../docs/practices/SECURITY.md)。这条规则只记录最小硬约束：agent-facing context 和生成资产不应携带真实敏感信息。仓库存在 datasource、Druid monitor、Shiro/session、remember-me、XSS/CSRF 等配置面；修改这些文件时尤其要避免泄漏真实凭据或机器私有路径。

证据：

- `ruoyi-admin/src/main/resources/application.yml`
- `ruoyi-admin/src/main/resources/application-druid.yml`
- `.harnesskit/facts.md`
- `AGENTS.md`

验证 / runner：

- Runner：review；secret scan 未配置。
