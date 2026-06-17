# RULE-PRODUCT-001

## Rule

不要把 README 中的上游演示、文档站或多版本分支说明写成本地仓库已配置的部署、CI 或发布能力。

## Details

README 提供产品背景、功能列表、在线体验和上游分支说明；这些信息有助于理解 RuoYi，但不能替代本地仓库配置证据。当前本地仓库未发现 CI workflow、release gate、部署配置或已绑定官方验证命令。

证据：

- `README.md`
- `.github/FUNDING.yml`
- `AGENTS.md`
- `ARCHITECTURE.md`

验证 / runner：

- Runner：review。
- 自动检查：未配置。
