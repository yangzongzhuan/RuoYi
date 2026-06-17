# RULE-AI-003

## Rule

发现代码、文档、rules、skills 或验证入口冲突时，先核对仓库事实再同步 context。

## Details

不要静默选择其中一边。先找可验证来源，再更新误导 agent 的 context 文件、facts、skills 或规则细节。当前 harness artifacts 由 `scan-facts` 和各 `fill-*` skills 维护；职责分层见 `AGENTS.md`。

证据：

- `AGENTS.md`
- `ARCHITECTURE.md`
- `RULES.md`
- `.harnesskit/facts.md`
- `.agents/skills/`

验证 / runner：

- Runner：review / harness consistency check。
- 自动检查：未配置。
