# RuoYi 贡献者指南

本文件是当前仓库的 agent 启动入口：保留少量会影响操作判断的事实，并把 agent 路由到规则、架构地图、skills 和验证入口。它不是项目知识库；完整目录职责放在 [ARCHITECTURE.md](ARCHITECTURE.md)，工程约束放在 [RULES.md](RULES.md)，具体任务流程放在 [.agents/skills/](.agents/skills/)，产品背景放在 [README.md](README.md) 或 `docs/`。

除非已经从构建清单、锁文件、脚本、CI 配置、代码托管平台、[.harnesskit/facts.md](.harnesskit/facts.md) 或现有文档中验证，不要把模板示例、推测命令或旧说明当作仓库已支持的流程。

## 操作关键事实

- 本仓库是 RuoYi / 若依 `4.8.3`：Java 17 + Maven multi-module + Spring Boot 4.0.6 的后台管理系统和快速开发框架；主要模块包括 `ruoyi-admin`、`ruoyi-framework`、`ruoyi-system`、`ruoyi-common`、`ruoyi-quartz` 和 `ruoyi-generator`。
- 用户可见和兼容性敏感边界包括 Web controllers、Thymeleaf templates、static UI assets、`application*.yml` 配置、MyBatis mapper XML、`sql/` 数据库脚本、`ruoyi-generator/src/main/resources/vm/` 生成模板、`generator.yml`、以及 `bin/`、`ry.sh`、`ry.bat` 等运行/打包脚本。
- 默认应用配置使用 MySQL/Druid datasource、Shiro/session/remember-me/captcha、XSS/CSRF toggles 和 Springdoc/OpenAPI；这些配置变更应按外部配置或安全边界处理。
- `ruoyi-generator/src/main/resources/generator.yml` 当前 `allowOverwrite: false`；生成器模板或覆盖行为会影响用户可见生成输出。
- HarnessKit 当前 schema version 为 1，project name 为 `RuoYi`，default/installed integration 为 `codex`；[CLAUDE.md](CLAUDE.md) 是指向 [AGENTS.md](AGENTS.md) 的 companion guide symlink。

## 上下文路由

- 开始任务前先读 [RULES.md](RULES.md)，并按需查看 [.harnesskit/rules/](.harnesskit/rules/) 中对应 rule details、验证说明或证据来源。
- 涉及路径职责、模块边界、生成资产、持久配置、数据库脚本或旧/新实现取舍时，读 [ARCHITECTURE.md](ARCHITECTURE.md)。
- 需要产品定位、功能背景或用户文档时，读 [README.md](README.md)、`doc/` 和相关 `docs/` 文件。
- 涉及代码风格、产品体验、安全或可靠性判断时，按需阅读 [docs/practices/](docs/practices/)；这些文件是判断指导，不替代 [RULES.md](RULES.md) 的硬约束。
- 触发本地 skill 时，先读对应 skill 文件；当前技能目录是 [.agents/skills/](.agents/skills/)。
- [.harnesskit/facts.md](.harnesskit/facts.md) 是 `$scan-facts` 生成的事实快照；高影响判断仍要回到真实源码、配置、脚本或文档核对。

## 工作策略

- 修改用户可见行为、公开 API、外部配置、持久化数据、数据库 schema、模板输出、生成资产或运行/打包脚本前，先使用 `$implementation-strategy` 明确兼容性边界。
- 影响运行时代码、模板、测试、构建配置、锁文件、Markdown 链接或验证行为的变更，在完成前使用 `$code-change-verification`。当前 full verification runner 尚未配置成功 checks，不能把 `make verify` 的存在等同于通过完整验证。
- 初次补全或刷新 harness context 时，按需使用 `$harness-init` 或 `$scan-facts`；刷新后再用对应 `$fill-*` skill 更新目标 artifact。
- 刷新 [docs/practices/](docs/practices/) 判断指导时，使用 `$fill-practices`；如果发现稳定硬约束候选，再交给 `$fill-rules`。
- 中等及以上规模的运行时代码、测试、示例、构建/测试配置或有行为影响的文档变更完成后，按 `$pr-draft-summary` 准备交付说明；纯 repo metadata 或 guidance-only 变更可跳过。
- 发现可复用约定、规则候选、命令漂移或待确认事项时，记录到适当的 harness artifact：事实进入 [.harnesskit/facts.md](.harnesskit/facts.md)，硬约束进入 [RULES.md](RULES.md) 和 `.harnesskit/rules/`，目录职责进入 [ARCHITECTURE.md](ARCHITECTURE.md)，判断指导进入 `docs/practices/`。

## 验证入口

完整验证入口当前未配置为可成功运行的 gate。

当前已确认的验证 runner：

- `make verify` 调用 `.agents/skills/code-change-verification/scripts/run_validation.py`，并写入 `.harnesskit/receipts/latest.json` 和 `.harnesskit/receipts/runs/<run_id>.json`。
- 该脚本当前 `CHECKS` 为空；运行时会记录 `not_configured` 并以非零状态退出。

当前未配置或未证实的检查：

- Setup：未发现仓库确认的 setup command。
- Format：未发现 formatter 配置或 format check command。
- Lint：未发现 lint 配置或 lint command。
- Typecheck / compile：Maven compiler plugin 已配置 Java 17，但没有官方 typecheck/compile runner 绑定。
- Tests：未发现 `src/test*` 测试目录、CI workflow 或文档化 test command。
- Build：Maven lifecycle 可由 `pom.xml` 推断存在，但没有仓库确认的 official build runner。
- Hooks / CI：`.github/` 当前只发现 `FUNDING.yml`，未发现 CI workflow 或 pre-commit 配置。

维护验证说明时区分 Rule、Validation 和 Runner：Rule 是约束，Validation 是检查方式，Runner 是实际执行位置。没有 runner 证据的检查只能标记为人工执行、agent 执行或未绑定，不要写成完成条件。

## 漂移处理

如果 [AGENTS.md](AGENTS.md)、[RULES.md](RULES.md)、[ARCHITECTURE.md](ARCHITECTURE.md)、skills、验证入口、项目命令或仓库事实互相冲突，不要静默选择一边；先核对真实文件，再同步修复漂移的 context 文件。

文档职责保持分离：[AGENTS.md](AGENTS.md) 讲 agent 如何开始和路由，[RULES.md](RULES.md) 讲不能破坏的约束，[ARCHITECTURE.md](ARCHITECTURE.md) 讲仓库地图，skills 讲任务流程，[docs/practices/](docs/practices/) 讲判断指导，[README.md](README.md)、`doc/` 和 `docs/` 讲产品与使用背景。
