# Security Practices

本文件记录当前仓库的安全判断指导。安全硬约束仍应抽到 [RULES.md](../../RULES.md) 和 `.harnesskit/rules/`，并尽可能绑定测试、lint 或 review。

## Scope

本仓库最重要的安全边界来自配置、权限、文件写入、生成输出和数据库访问：

- `application.yml`：server、upload profile、Shiro login/session/remember-me/captcha、XSS/CSRF toggles、Springdoc exposure。
- `application-druid.yml`：MySQL datasource、Druid monitor、数据库连接池和 wall/stat config。
- `ruoyi-generator/src/main/resources/generator.yml`：生成 package、table prefix 和 `allowOverwrite`。
- `ruoyi-generator/src/main/resources/vm/`：生成代码、SQL、XML 和页面输出。
- `sql/` and MyBatis mapper XML：schema/data and runtime SQL behavior.
- `bin/`, `ry.sh`, `ry.bat`：process control and deployment-adjacent scripts.

当前没有发现 security disclosure policy、secret scan、dependency scan、CI security gate 或支持版本/SLA 证据；不要虚构这些流程。

## Guidance

- 不要把 secret、token、私有凭据、真实用户数据、私有 URL 或机器特定敏感信息写入模板、facts、rules、报告或生成产物。
- 处理 datasource、Druid monitor、Shiro/session/remember-me、captcha、XSS/CSRF、upload path 或 generated file overwrite 时，先判断是否改变了部署安全默认值。
- 修改 `generator.yml` 或 generator templates 时，关注路径、覆盖策略、SQL/XML 输出和用户手写内容是否可能被覆盖。
- 修改 mapper XML、SQL scripts 或 dynamic SQL helper 时，关注权限过滤、数据范围、SQL 注入和多语句行为；当前没有自动 SQL/security runner，至少需要 review。
- 修改 shell/batch scripts 时，关注命令参数、路径引用、进程匹配和日志/输出泄漏。
- 依赖升级或新增依赖应回到 `pom.xml` 和模块 `pom.xml` 核对，不要引入未说明来源的 binary/vendor 内容。

## Review Questions

- 输出里是否可能包含真实 secret、token、私有 URL、用户名、密码或机器路径？
- 配置默认值是否扩大了登录、session、Druid monitor、Swagger/OpenAPI、XSS 或 CSRF 暴露面？
- 文件写入或生成输出是否可能覆盖用户手写内容、写到项目外，或生成危险 SQL/XML/HTML？
- Mapper、SQL 或 data scope 改动是否绕过权限、部门/角色过滤或审计日志？
- 安全声明是否有仓库证据，还是只是通用最佳实践？
- 如果没有自动安全检查，最终说明是否明确为 review-only / not configured？

## 和 Rules 的关系

硬约束见 `RULE-SEC-001` 和 `RULE-SEC-002`。本文件提供安全 review 角度；如果新增真实 scanner、policy 或 disclosure workflow，应同步 `$fill-rules`、`$fill-agents` 和 `$fill-skills`。
