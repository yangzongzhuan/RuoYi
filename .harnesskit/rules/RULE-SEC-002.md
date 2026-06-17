# RULE-SEC-002

## Rule

生成器覆盖行为、文件写入路径、数据源、Shiro/session、XSS/CSRF 和 Druid monitor 配置变更必须按安全边界处理。

## Details

`generator.yml` 当前 `allowOverwrite: false`，其覆盖策略会影响文件写入安全和用户资产。`application.yml` 和 `application-druid.yml` 包含上传路径、datasource、Druid monitor、Shiro/session/remember-me、XSS/CSRF 等安全相关配置。改动这些设置前要明确外部影响，不能作为顺手配置清理。

证据：

- `ruoyi-generator/src/main/resources/generator.yml`
- `ruoyi-admin/src/main/resources/application.yml`
- `ruoyi-admin/src/main/resources/application-druid.yml`
- `AGENTS.md`
- `ARCHITECTURE.md`

验证 / runner：

- Runner：review；security scanner/path safety tests 未配置。
