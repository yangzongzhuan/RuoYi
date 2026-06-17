# RULE-STACK-002

## Rule

当前未配置 lint、format、typecheck、test、build、hook 或 CI required gate；新增完成条件前必须先补 runner 证据。

## Details

存在 Maven lifecycle 不等于仓库已声明 official build/test gate。`make verify` 当前只是未配置 checks 的 harness wrapper。新增完成条件时，应先在 manifests、scripts、hooks、CI 或 verification skill 中建立可核对 runner。

证据：

- `Makefile`
- `.agents/skills/code-change-verification/scripts/run_validation.py`
- `.github/FUNDING.yml`
- `.harnesskit/facts.md`

验证 / runner：

- Runner：review。
- 自动检查：未配置。
