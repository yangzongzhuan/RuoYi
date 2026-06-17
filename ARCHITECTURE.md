# RuoYi 架构地图

本文件是当前仓库的粗粒度架构地图。它帮助 agent 和贡献者在修改前找到正确的代码区域。本文件不是工作流规范、API 参考或详细设计文档。

路径说明来自真实目录结构、Maven 清单、资源配置、脚本和现有文档。更细的事实快照见 [.harnesskit/facts.md](.harnesskit/facts.md)；操作入口见 [AGENTS.md](AGENTS.md)；硬约束见 [RULES.md](RULES.md)。

## 顶层地图

- [pom.xml](pom.xml)：父级 Maven project `com.ruoyi:ruoyi:4.8.3`；集中管理 Java 17、Spring Boot 4.0.6、Shiro、MyBatis、Druid、Quartz、Velocity、Springdoc 等版本，并声明所有业务模块。
- [ruoyi-admin/](ruoyi-admin/)：Web 服务入口模块，打包为 `ruoyi-admin`；包含启动类、Servlet initializer、Web controllers、Spring 配置资源、Thymeleaf 页面、静态 UI assets、MyBatis/日志配置和 application YAML。
- [ruoyi-framework/](ruoyi-framework/)：框架核心模块；承载 Spring MVC/AOP 配置、Shiro 集成、数据源、过滤器、拦截器、异常处理、异步管理和运行时 Web support。
- [ruoyi-system/](ruoyi-system/)：系统业务模块；承载用户、角色、菜单、部门、字典、参数、通知、日志、在线用户、岗位等 system domain/service/mapper 和 MyBatis XML。
- [ruoyi-common/](ruoyi-common/)：通用基础模块；承载常量、注解、公共 domain/page/session 类型、异常、配置 helper、XSS support、JSON、Excel/POI、HTTP、枚举和工具类。
- [ruoyi-quartz/](ruoyi-quartz/)：定时任务模块；承载 Quartz job domain/service/mapper/controller/config、任务工具、job 页面模板和 mapper XML。
- [ruoyi-generator/](ruoyi-generator/)：代码生成模块；承载生成器 controller/service/domain/mapper、Velocity utilities、生成配置、生成器页面和生成输出模板。
- [sql/](sql/)：数据库资产，包括 RuoYi schema/data、Quartz SQL、PDM 模型和 HTML 数据库说明。
- [bin/](bin/)、[ry.sh](ry.sh)、[ry.bat](ry.bat)：Windows/Linux 清理、打包、运行和进程控制脚本。
- [doc/](doc/)：用户或环境说明文档，目前包含 `若依环境使用手册.docx`。
- [docs/practices/](docs/practices/)：agent-facing 代码、产品、安全、可靠性判断指导；不替代 [RULES.md](RULES.md) 的硬约束。
- [.agents/skills/](.agents/skills/)：本地 generated agent skills；任务流程放在各 skill 文件中。
- [.harnesskit/](.harnesskit/)：HarnessKit 状态、facts、rule details 和 validation receipts。
- [.github/](.github/)：当前只发现 `FUNDING.yml`；没有 CI workflow 证据。

## 关键文件

- [AGENTS.md](AGENTS.md)：agent 操作入口和顶层路由器。
- [CLAUDE.md](CLAUDE.md)：companion agent 指南 symlink，当前指向 [AGENTS.md](AGENTS.md)。
- [RULES.md](RULES.md)：规则索引；details 位于 [.harnesskit/rules/](.harnesskit/rules/)。
- [.harnesskit/facts.md](.harnesskit/facts.md)：`$scan-facts` 生成的 scan/fill 事实快照。
- [Makefile](Makefile)：HarnessKit 验证入口 wrapper；调用 `.agents/skills/code-change-verification/scripts/run_validation.py`。
- [.agents/skills/code-change-verification/scripts/run_validation.py](.agents/skills/code-change-verification/scripts/run_validation.py)：验证 receipt runner；当前 `CHECKS` 为空，运行会报告 `not_configured`。
- [ruoyi-admin/src/main/java/com/ruoyi/RuoYiApplication.java](ruoyi-admin/src/main/java/com/ruoyi/RuoYiApplication.java)：Spring Boot 应用启动类。
- [ruoyi-admin/src/main/resources/application.yml](ruoyi-admin/src/main/resources/application.yml)：应用主配置，包含 RuoYi metadata、server、Thymeleaf、MyBatis、Shiro、Springdoc、XSS/CSRF 等配置。
- [ruoyi-admin/src/main/resources/application-druid.yml](ruoyi-admin/src/main/resources/application-druid.yml)：Druid/MySQL datasource 和 Druid monitor 配置。
- [ruoyi-generator/src/main/resources/generator.yml](ruoyi-generator/src/main/resources/generator.yml)：代码生成配置；默认 package 为 `com.ruoyi.system`，table prefix 为 `sys_`，`allowOverwrite: false`。
- [README.md](README.md)：项目定位、功能列表、版本分支和演示信息。

## 生成资产和外部状态

- 代码生成入口位于 [ruoyi-generator/](ruoyi-generator/)；生成源模板集中在 [ruoyi-generator/src/main/resources/vm/](ruoyi-generator/src/main/resources/vm/)（Java、HTML、XML、SQL 等），生成器页面位于 [ruoyi-generator/src/main/resources/templates/](ruoyi-generator/src/main/resources/templates/)。
- Web 页面和前端静态资源是运行时用户可见资产：主要位于 [ruoyi-admin/src/main/resources/templates/](ruoyi-admin/src/main/resources/templates/) 和 [ruoyi-admin/src/main/resources/static/](ruoyi-admin/src/main/resources/static/)；Quartz job 页面位于 [ruoyi-quartz/src/main/resources/templates/](ruoyi-quartz/src/main/resources/templates/)。
- MyBatis SQL mapping 是运行时数据访问边界：system mappings 在 [ruoyi-system/src/main/resources/mapper/](ruoyi-system/src/main/resources/mapper/)，Quartz mappings 在 [ruoyi-quartz/src/main/resources/mapper/](ruoyi-quartz/src/main/resources/mapper/)，generator mappings 在 [ruoyi-generator/src/main/resources/mapper/](ruoyi-generator/src/main/resources/mapper/)。
- 持久化和外部状态主要由 [sql/](sql/)、[application.yml](ruoyi-admin/src/main/resources/application.yml)、[application-druid.yml](ruoyi-admin/src/main/resources/application-druid.yml) 和 MyBatis mapper XML 定义。变更这些文件时要按数据库 schema、外部配置或安全边界处理。
- 发布/运行产物由 Maven build 配置和脚本约定影响：`ruoyi-admin/pom.xml` 使用 Spring Boot Maven plugin repackage，`ry.sh` 期望运行 `ruoyi-admin.jar`。官方 build runner 尚未在 harness 中确认。

## 边界说明

- Maven module boundaries matter：`ruoyi-admin` 依赖 `ruoyi-framework`、`ruoyi-quartz` 和 `ruoyi-generator`；`ruoyi-framework` 依赖 `ruoyi-system`；`ruoyi-system`、`ruoyi-quartz` 和 `ruoyi-generator` 依赖 `ruoyi-common`。跨模块改动前先核对对应 `pom.xml`。
- `ruoyi-admin/src/main/resources/templates/demo/` 是演示页面资源，但仍在运行时资源树内；删除或改动前不要把它当成测试 fixture。
- 当前扫描未发现 `src/test*` 测试目录、CI workflow、pre-commit、lint config 或 formatter config。不要把未绑定检查写成完成条件。
- `make verify` 是 harness wrapper，不是已配置完整验证 gate；在 `run_validation.py` 的 `CHECKS` 补全前会返回 `not_configured`。
- README 提到线上演示、文档地址和多版本分支信息；这些是产品/上游背景，不等于当前本地仓库已配置的部署、CI 或发布流程。
- `sql/` 脚本看起来是 schema/data 资产，但当前没有迁移工具或 schema version runner 证据；数据库兼容策略需要在修改前单独确认或记录。

## 更新规则

当主要模块、关键配置、数据库资产、生成模板、运行/打包脚本、验证入口或 agent-facing harness 文件发生变化时，同步更新本地图。只有当新路径会改变 agent 首先应该查看的位置时，才把它加入这里；不要因为每个 helper 或页面文件移动就扩展成本文件清单。
