# Reliability Practices

本文件记录当前仓库的可靠性、质量和防漂移判断指导。它不替代 [RULES.md](../../RULES.md) 的验证门槛。

## Protected Boundaries

- Runtime behavior：controllers、services、framework config、filters/interceptors、Shiro/session、Quartz jobs 和 MyBatis mapper XML。
- User-visible assets：Thymeleaf templates、static UI resources、Quartz job templates、demo pages 和 Springdoc/OpenAPI exposure。
- Persistent state：`sql/` scripts、MySQL/Druid datasource config、mapper XML 和任何影响数据库读写的 domain/service change。
- Generated output：`ruoyi-generator/src/main/resources/vm/`、`generator.yml` 和 generator UI templates。
- Operations surface：`bin/`, `ry.sh`, `ry.bat`, Maven packaging config and `Makefile`.
- Agent context：`AGENTS.md`, `ARCHITECTURE.md`, `RULES.md`, `.harnesskit/facts.md`, `.harnesskit/rules/`, `.agents/skills/` and `docs/practices/`.

## Guidance

- 先判断改动触及哪个 protected boundary，再选择验证策略。没有 runner 证据时，只能说 review 或未配置，不能声称自动验证通过。
- `make verify` 当前是 harness wrapper，但 `CHECKS` 为空；运行会记录 `not_configured`。不要把它当作完整 gate。
- Maven lifecycle 可由 `pom.xml` 推断存在，但尚未被仓库声明为 official build/test runner。需要把 Maven 命令写成 gate 时，先更新 verification skill 和 rules。
- 配置、SQL、mapper XML 和 generator templates 的改动风险通常高于普通内部 helper；最终说明要点明验证缺口。
- 如果验证失败，修复后重跑同一个已确认的验证入口，并只报告最终状态。当前没有已配置自动入口时，报告未运行原因。
- context 文件之间发生漂移时，回到真实文件核对，再同步 `.harnesskit/facts.md`、`AGENTS.md`、`ARCHITECTURE.md`、`RULES.md`、skills 或 practices。

## Review Questions

- 这次改动触及 runtime、配置、数据库、生成输出、运行脚本还是 agent context？
- 是否需要兼容性判断、schema review、手工页面检查或生成输出 review？
- 是否新增了 dependency、module、plugin 或 runner，需要同步 `pom.xml`、facts、rules 和 verification skill？
- 文档、templates 和 rules 是否仍指向真实存在的命令和路径？
- 最终回复是否区分了 passed、not configured、not run 和 review-only？

## 当前验证状态

- Full verification：`make verify` 存在，但当前 `run_validation.py` 的 `CHECKS` 为空，会返回 `not_configured`。
- Tests：未发现 `src/test*` 测试目录或文档化 test command。
- Lint / format / typecheck / CI / hook：未发现已绑定 runner。
- Build：Maven build config 存在，但 official build runner 未在 harness 中确认。

## 和 Rules 的关系

完成条件和 runner 事实见 `RULE-ENG-003`、`RULE-ENG-004`、`RULE-STACK-002`。本文件提供判断流程，不新增 gate。
