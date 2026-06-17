# RuoYi Harness Rules

本文件是 agent 约束索引。Rule 不是 workflow，也不是通用工程建议；Rule 只记录这个仓库里永远或局部必须成立的约束。

Skills 教 agent 怎么做一类任务；Rules 告诉 agent 在这个仓库里必须遵守什么；validation 负责把可检查的约束变成可执行反馈。[AGENTS.md](AGENTS.md) 负责路由，`.agents/skills/` 负责流程，[RULES.md](RULES.md) 只保留短约束句，不负责决定该调用哪个 skill。

每条规则必须有对应 details 文件，放在 [.harnesskit/rules/](.harnesskit/rules/)。[RULES.md](RULES.md) 负责告诉 agent “什么不能破坏”，details 文件负责说明“为什么、证据是什么、如何验证”。

除非已经从构建清单、锁文件、脚本、CI 配置、代码托管平台、[.harnesskit/facts.md](.harnesskit/facts.md) 或现有文档中验证，不要把模板示例当作仓库已支持的命令或流程。

## 通用工程实践

- RULE-ENG-001: 用户可见行为变更不能声称已有自动测试覆盖，除非新增或运行了仓库确认的测试/验证。([details](.harnesskit/rules/RULE-ENG-001.md))
- RULE-ENG-002: Maven 依赖、模块或插件变更必须同步对应 `pom.xml`，不要假设存在锁文件或 Maven wrapper。([details](.harnesskit/rules/RULE-ENG-002.md))
- RULE-ENG-003: 在验证 checks 配置前，不要把 `make verify` 当作已通过的完整验证 gate。([details](.harnesskit/rules/RULE-ENG-003.md))
- RULE-ENG-004: 不要把没有仓库配置或 runner 证据的检查写成完成条件。([details](.harnesskit/rules/RULE-ENG-004.md))

## 代码风格与维护性

- RULE-STYLE-001: 跨 Maven 模块移动或复用代码前，先核对对应模块职责和 `pom.xml` 依赖方向。([details](.harnesskit/rules/RULE-STYLE-001.md))

## AI Coding 规则

- RULE-AI-001: 高影响判断必须回到源码、配置、脚本、清单或现有文档核对，不能只依赖 `.harnesskit/facts.md`。([details](.harnesskit/rules/RULE-AI-001.md))
- RULE-AI-002: 不要把模板示例、设计愿景、旧文档或未验证 facts 当成当前实现事实。([details](.harnesskit/rules/RULE-AI-002.md))
- RULE-AI-003: 发现代码、文档、rules、skills 或验证入口冲突时，先核对仓库事实再同步 context。([details](.harnesskit/rules/RULE-AI-003.md))
- RULE-AI-004: 未经用户明确要求，不要重排、合并、删除已有客户手写规则。([details](.harnesskit/rules/RULE-AI-004.md))

## 技术栈规则

- RULE-STACK-001: 本仓库的已确认主栈是 Java 17 + Maven multi-module + Spring Boot 4.x；不要引入或要求未配置的包管理器/构建系统。([details](.harnesskit/rules/RULE-STACK-001.md))
- RULE-STACK-002: 当前未配置 lint、format、typecheck、test、build、hook 或 CI required gate；新增完成条件前必须先补 runner 证据。([details](.harnesskit/rules/RULE-STACK-002.md))
- RULE-STACK-003: 工具链、构建、依赖或验证入口变更必须同步更新相关 harness context 和 verification skill。([details](.harnesskit/rules/RULE-STACK-003.md))

## 架构规则

- RULE-ARCH-001: 修改配置、SQL、MyBatis mapper、模板、生成器或运行脚本时，按用户可见或持久化边界处理。([details](.harnesskit/rules/RULE-ARCH-001.md))
- RULE-ARCH-002: `ruoyi-generator` 模板/config 和 Web/Quartz 页面资源属于用户可见输出，不能当作内部实现细节处理。([details](.harnesskit/rules/RULE-ARCH-002.md))
- RULE-ARCH-003: `ruoyi-admin/src/main/resources/templates/demo/` 位于运行时资源树内，不要把它当成测试 fixture 或可随意删除的样例。([details](.harnesskit/rules/RULE-ARCH-003.md))

## 产品与体验规则

- RULE-PRODUCT-001: 不要把 README 中的上游演示、文档站或多版本分支说明写成本地仓库已配置的部署、CI 或发布能力。([details](.harnesskit/rules/RULE-PRODUCT-001.md))

## 安全规则

- RULE-SEC-001: 不要把 secret、token、私有凭据或机器特定敏感信息写入模板、facts、rules、报告或生成产物。([details](.harnesskit/rules/RULE-SEC-001.md))
- RULE-SEC-002: 生成器覆盖行为、文件写入路径、数据源、Shiro/session、XSS/CSRF 和 Druid monitor 配置变更必须按安全边界处理。([details](.harnesskit/rules/RULE-SEC-002.md))

## 产品 / 领域规则

- RULE-DOMAIN-001: 用户、角色、菜单、部门、字典、参数、日志、在线用户、定时任务和代码生成属于后台管理核心领域，不能作为无关重构顺手改变。([details](.harnesskit/rules/RULE-DOMAIN-001.md))
