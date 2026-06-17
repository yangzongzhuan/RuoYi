# RuoYi Harness Facts

本文件位于 `.harnesskit/facts.md`，是 `scan -> fill` 工作流的事实交接快照。`$scan-facts` 从仓库事实刷新本文件；`$fill-agents`、`$fill-architecture`、`$fill-practices`、`$fill-rules` 和 `$fill-skills` 消费本文件来更新对应 artifact。

本文件不是仓库事实本身的替代品。填充任何 agent-facing 文档前，仍应优先核对真实源码、清单、脚本、锁文件、CI/hook 配置和现有文档。

## Project Identity

- **Project name**: RuoYi / 若依
- **Current version in repository**: 4.8.3
- **Project purpose**: 基于 Spring Boot 的轻量级 Java 快速开发框架和后台管理系统，覆盖用户、部门、岗位、菜单、角色、字典、参数、通知公告、操作日志、登录日志、在线用户、定时任务、代码生成、系统接口、服务监控、缓存监控、在线构建器和连接池监视等后台能力。
- **Primary audience**: 个人和企业开发者/维护者，用于搭建 Web 管理后台、网站会员中心、CMS、CRM、OA 等系统。
- **Evidence**: `README.md`, `pom.xml`, `ruoyi-admin/src/main/resources/application.yml`

## Tech Stack

| Category | Detected fact | Evidence | Confidence |
| --- | --- | --- | --- |
| Languages / runtimes | Java 17; browser-facing UI resources are HTML/CSS/JavaScript under Spring resources. | `pom.xml`; `ruoyi-admin/src/main/resources/templates/`; `ruoyi-admin/src/main/resources/static/` | high |
| Package managers | Maven multi-module project; no Maven wrapper was found. | `pom.xml`; `ruoyi-*/pom.xml`; absence of `mvnw` / `mvnw.cmd` in repo scan | high |
| Frameworks / libraries | Spring Boot 4.0.6, Spring Web MVC, Thymeleaf, Apache Shiro 2.2.0 Jakarta artifacts, MyBatis Spring Boot, Druid, PageHelper, Quartz, Velocity, Springdoc/OpenAPI, Fastjson, Apache POI, Bootstrap/jQuery static UI assets. | `pom.xml`; `ruoyi-admin/pom.xml`; `ruoyi-framework/pom.xml`; `ruoyi-common/pom.xml`; `ruoyi-quartz/pom.xml`; `ruoyi-generator/pom.xml`; `ruoyi-admin/src/main/resources/application.yml` | high |
| Build tools | Maven compiler plugin targets Java 17; Spring Boot Maven plugin repackages `ruoyi-admin`; `Makefile` exposes a harness verification wrapper. | `pom.xml`; `ruoyi-admin/pom.xml`; `Makefile` | high |
| Database / persistence | Default application profile uses Druid with MySQL datasource `jdbc:mysql://localhost:3306/ry`; SQL assets include RuoYi and Quartz schema/data files. | `ruoyi-admin/src/main/resources/application.yml`; `ruoyi-admin/src/main/resources/application-druid.yml`; `sql/ry_20260319.sql`; `sql/quartz.sql` | high |
| Security / access control | Shiro config, session/remember-me settings, captcha, XSS filter toggle, CSRF toggle, and Druid monitor credentials are configured in application resources. | `ruoyi-admin/src/main/resources/application.yml`; `ruoyi-admin/src/main/resources/application-druid.yml`; `ruoyi-framework/pom.xml` | high |
| Code generation | Generator uses Velocity templates and `generator.yml`; default package is `com.ruoyi.system`, table prefix is `sys_`, and `allowOverwrite` is false. | `ruoyi-generator/pom.xml`; `ruoyi-generator/src/main/resources/generator.yml`; `ruoyi-generator/src/main/resources/vm/` | high |

## Validation Entrypoints

| Kind | Command | Runner / binding | Evidence |
| --- | --- | --- | --- |
| Setup | 未配置 | not configured | No setup script, Maven wrapper, CI, or documented install command was found during scan. |
| Full verify | `make verify` exists, but currently records `not_configured` because no checks are configured. | local Makefile -> agent validation script | `Makefile`; `.agents/skills/code-change-verification/scripts/run_validation.py` has `CHECKS: tuple[Check, ...] = ()`. |
| Test | 未配置 | not configured | No `src/test*` tree, CI workflow, or documented test command was found. Maven lifecycle may provide `mvn test`, but it is not bound as this repo's verified runner. |
| Lint | 未配置 | not configured | No lint config or documented lint command was found. |
| Format check | 未配置 | not configured | No formatter config or documented format check command was found. |
| Type check | 未绑定 | not configured | Maven compiler plugin is configured, but no official compile/typecheck command is documented or bound in `run_validation.py`. |
| Build | 未绑定 | not configured | `pom.xml` and `ruoyi-admin/pom.xml` configure Maven build plugins; `bin/package.bat` exists, but no cross-platform official build command is documented in current harness. |
| Hooks / CI | 未配置 | not configured | `.github/` contains only `FUNDING.yml`; no pre-commit config found. |

## Repository Map Candidates

- `pom.xml`: parent Maven project `com.ruoyi:ruoyi:4.8.3`, dependency management, Java 17 compiler config, module list, and Aliyun Maven repository config.
- `ruoyi-admin/`: Web service entry module; contains `RuoYiApplication`, web controllers, Spring resources, Thymeleaf templates, static UI assets, `application.yml`, `application-druid.yml`, MyBatis config, logging config, and Springdoc/Swagger config.
- `ruoyi-framework/`: framework core module; contains Spring MVC/AOP/configuration, Shiro integration, datasource support, filters, interceptors, exception handling, async manager, and web services.
- `ruoyi-system/`: system business module; contains system domain objects, services, mappers, and MyBatis XML mappings for users, roles, menus, departments, dictionaries, config, notices, logs, sessions, posts, and related joins.
- `ruoyi-common/`: shared utilities, constants, annotations, core domain/page/session objects, exceptions, config helpers, XSS support, JSON, Excel/POI utilities, HTTP helpers, and common enums.
- `ruoyi-quartz/`: scheduled job module; contains Quartz domain/services/mappers/controllers/config, task utilities, templates, and MyBatis XML mappings.
- `ruoyi-generator/`: code generation module; contains generator controllers/services/domain/mappers, Velocity utilities, generator config, `generator.yml`, HTML templates, and `vm/` templates for Java, XML, SQL, JS, and HTML generated output.
- `sql/`: database assets, including `ry_20260319.sql`, `quartz.sql`, `ruoyi.pdm`, and `ruoyi.html`.
- `bin/`, `ry.sh`, `ry.bat`: packaging, clean, run, and process control scripts.
- `doc/`: user/operator documentation; currently contains `若依环境使用手册.docx`.
- `docs/practices/`: agent-facing coding, product sense, security, and reliability judgment guidance.
- `.agents/skills/`: local generated agent skills for scan/fill harness workflows, implementation strategy, PR summaries, and code-change verification.
- `.harnesskit/`: harness config, facts, rule details, and validation receipts.
- Test directories: no source test directories were found in the current scan.

## Agent-Facing Assets

| Asset | Status | Evidence / notes |
| --- | --- | --- |
| `AGENTS.md` | exists, filled | Contains RuoYi-specific contributor guide/routing, operation facts, validation status, skill routing, and drift handling. |
| `CLAUDE.md` | exists as symlink to `AGENTS.md` | `CLAUDE.md -> AGENTS.md`. |
| `ARCHITECTURE.md` | exists, filled | Contains RuoYi-specific module map, key files, generated assets, external state, and boundary notes. |
| `RULES.md` | exists, filled | Contains RuoYi-specific rule index; every rule links to a details file under `.harnesskit/rules/`. |
| `.harnesskit/facts.md` | exists and refreshed by `$scan-facts` | This file. |
| `.harnesskit/config.json` | exists | Schema version 1; project name `RuoYi`; default and installed integration `codex`; harnesskit version `0.1.1`. |
| `.harnesskit/rules/` | exists, filled | Rule detail files exist for current `RULES.md` entries and include evidence plus runner status. |
| `docs/practices/` | exists, filled | `CODING.md`, `PRODUCT_SENSE.md`, `SECURITY.md`, `RELIABILITY.md` contain RuoYi-specific guidance. |
| `.agents/skills/` | exists, project sections filled where evidence exists | `code-change-verification`, `implementation-strategy`, and `pr-draft-summary` have RuoYi-specific sections; generic fill/scan workflow instructions retain their reusable protocol language. |
| Validation receipt path | configured but checks empty | `make verify` writes `.harnesskit/receipts/latest.json` and `.harnesskit/receipts/runs/<run_id>.json` after `CHECKS` are configured or to report `not_configured`. |

## Rule / Guard Candidates

- Build/runtime stack candidate: Java 17 + Maven multi-module + Spring Boot 4.x should be treated as the primary stack unless future repo evidence changes it. Evidence: `pom.xml`.
- Validation candidate: `make verify` is the intended harness wrapper, but it is not usable as a successful full verification gate until `.agents/skills/code-change-verification/scripts/run_validation.py` has repository-confirmed checks. Evidence: `Makefile`, `run_validation.py`.
- Dependency candidate: Maven dependency versions are centralized in parent `pom.xml`; no lockfile was found. Evidence: `pom.xml`, module `pom.xml` files.
- User-visible boundary candidate: changes under controllers, templates, static resources, application YAML, SQL files, MyBatis mapper XML, generator templates/config, and startup/package scripts can affect runtime behavior, generated output, persistent config, or deployment behavior. Evidence: module layout and resource/config files.
- Generator safety candidate: `ruoyi-generator/src/main/resources/generator.yml` has `allowOverwrite: false`; changes to generator templates or overwrite behavior should be treated as user-visible generated output behavior. Evidence: `generator.yml`, `ruoyi-generator/src/main/resources/vm/`.
- Persistence/security candidate: SQL scripts, datasource configuration, Shiro/session/remember-me/captcha, XSS and CSRF settings, and Druid monitor config should be handled as high-impact configuration/security boundaries. Evidence: `sql/`, `application.yml`, `application-druid.yml`.
- Context drift candidate: `AGENTS.md`, `ARCHITECTURE.md`, `RULES.md`, `docs/practices/`, and verification skill content still contain placeholders and should be filled from this facts snapshot plus direct repo evidence.

## Open Questions

- Which Maven command should be considered the official local build or validation runner: `mvn test`, `mvn package`, `mvn clean package`, a module-specific command, or another team-defined command?
- Should `make verify` be configured to run Maven compile/test/package checks, and if so which checks are required vs optional?
- Are there expected tests outside conventional `src/test*` paths, or is the current repository intentionally distributed without tests?
- Are `bin/package.bat`, `bin/run.bat`, `ry.bat`, and `ry.sh` officially maintained release/ops entrypoints or legacy helper scripts?
- Should database scripts in `sql/` be treated as canonical schema/migration state, and what compatibility policy applies when changing them?
- Should demo functionality (`demoEnabled: true`) remain enabled by default in this repository, or is that only for sample deployments?
