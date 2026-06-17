# RULE-STACK-003

## Rule

工具链、构建、依赖或验证入口变更必须同步更新相关 harness context 和 verification skill。

## Details

如果变更 `pom.xml`、module `pom.xml`、`Makefile`、verification script、CI/hook 配置或新增锁文件/构建 wrapper，就会改变 agent 的操作判断。同步对象至少包括 `.harnesskit/facts.md`、`AGENTS.md`、`ARCHITECTURE.md`、`RULES.md` details，以及 `.agents/skills/code-change-verification/` 中的项目特定验证说明。

证据：

- `AGENTS.md`
- `ARCHITECTURE.md`
- `.harnesskit/facts.md`
- `Makefile`
- `.agents/skills/code-change-verification/SKILL.md`
- `.agents/skills/code-change-verification/scripts/run_validation.py`

验证 / runner：

- Runner：review / harness consistency check。
- 自动检查：未配置。
