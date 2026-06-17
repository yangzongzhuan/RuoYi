# RULE-AI-002

## Rule

不要把模板示例、设计愿景、旧文档或未验证 facts 当成当前实现事实。

## Details

当前实现事实应从源码、配置、脚本、Maven 清单、CI/hook 和已确认文档交叉验证。README 中的上游演示、文档站、多版本分支等背景不能自动转化为当前本地仓库的部署、CI、release 或 validation 能力。

证据：

- `README.md`
- `AGENTS.md`
- `ARCHITECTURE.md`
- `.harnesskit/facts.md`

验证 / runner：

- Runner：review / drift check。
- 自动检查：未配置。
