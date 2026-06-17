# RULE-ENG-004

## Rule

不要把没有仓库配置或 runner 证据的检查写成完成条件。

## Details

agent 只能要求真实存在的检查。未配置的 lint、format、typecheck、coverage、docs build、CI、branch protection 或平台 gate 可以记录为待确认或未配置，但不能写成完成门槛。本仓库当前未发现 lint/format config、test tree、CI workflow、pre-commit 或 configured validation checks。

证据：

- `.harnesskit/facts.md`
- `AGENTS.md`
- `ARCHITECTURE.md`
- `Makefile`
- `.agents/skills/code-change-verification/scripts/run_validation.py`
- `.github/FUNDING.yml` 是当前唯一发现的 `.github` 文件。

验证 / runner：

- Runner：review / harness consistency check。
- 自动阻断：未配置。
