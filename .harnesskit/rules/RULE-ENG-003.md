# RULE-ENG-003

## Rule

在验证 checks 配置前，不要把 `make verify` 当作已通过的完整验证 gate。

## Details

`Makefile` 提供 `verify` target，但它只调用 `.agents/skills/code-change-verification/scripts/run_validation.py`。该脚本当前 `CHECKS` 为空，运行时会写 validation receipt 并以 `not_configured` 非零退出。`make verify` 的存在说明有 harness wrapper，不说明已有完整验证栈。

证据：

- `Makefile`
- `.agents/skills/code-change-verification/scripts/run_validation.py`
- `AGENTS.md`
- `ARCHITECTURE.md`

验证 / runner：

- Runner：`make verify`
- 当前状态：not configured，因为 `CHECKS: tuple[Check, ...] = ()`。
- 只有当 `CHECKS` 被仓库事实填充并成功运行后，才能把它报告为 passed full verification。
