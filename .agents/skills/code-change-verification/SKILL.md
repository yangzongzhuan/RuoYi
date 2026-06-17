---
name: code-change-verification
description: 当变更影响运行时代码、测试、用户可见生成输出、模板或构建/验证行为时，运行仓库强制验证栈。
---

# 代码变更验证

## 概览

确保只有在仓库自己的 verification checks 运行后，才把工作标记为完成。变更影响运行时代码、测试、用户可见 generated outputs、templates 或 build/test configuration 时使用本 skill。纯文档或仓库元数据变更可跳过，除非用户要求完整验证栈。

## 仓库验证栈

- Setup command: 未配置；未发现 Maven wrapper、setup script、CI 或文档化 install command。
- Format command: 未配置；未发现 formatter 配置或 format check command。
- Lint command: 未配置；未发现 lint 配置或 lint command。
- Typecheck command: 未配置；`pom.xml` 配置了 Java 17 compiler plugin，但没有仓库确认的 compile/typecheck runner。
- Test command: 未配置；未发现 `src/test*` 测试目录、CI workflow 或文档化 test command。
- Build command: 未配置；Maven lifecycle 可由 `pom.xml` 推断存在，但没有仓库确认的 official build runner。
- Full verification command: `make verify` 存在，但 `scripts/run_validation.py` 当前 `CHECKS` 为空，会写 receipt、记录 `not_configured` 并以非零状态退出。
- Notes: 不要把 `make verify` 的存在等同于通过完整验证；只有在 `CHECKS` 被仓库事实填充并成功运行后，才能报告 full verification passed。

## 快速开始

1. 保持本 skill 位于 [`.agents/skills/code-change-verification`](./)，让仓库能自动加载它。
2. 对 runtime、template、SQL、configuration、generator、script 或 build/test config 变更，先核对是否已有仓库确认的 runner；当前没有可成功运行的 full verification gate。
3. 只有用户明确要求配置验证栈，或仓库新增 runner 证据时，才编辑 [`scripts/run_validation.py`](scripts/run_validation.py) 中的 `CHECKS`。
4. 如果运行 `make verify`，预期当前结果是 `not_configured`；最终回复必须如实说明，而不是报告验证通过。
5. 如果未来 command 失败，修复问题，重新运行相关 verification stack，并报告最终状态。

## 手动流程

- 当前没有已填充的 setup、format、lint、typecheck、test 或 build command。
- [`scripts/run_validation.py`](scripts/run_validation.py) 配置完成后，优先使用 `make verify`。
- 如果没有 configured full verification command，只能报告 verification not configured；不要用推断的 Maven 命令替代仓库 runner。
- 除非用户明确要求，不要作为本 skill 的一部分新增工具或创建新的 verification commands。
- 不要把 Maven lifecycle、templates 中的通用示例或 README 背景当成目标仓库已确认 runner。
- 确认 `.harnesskit/receipts/latest.json` 已写入；有帮助时在最终回复中引用 receipt 路径。
- 修复后重新运行失败检查，确保报告的验证状态对应最终 working tree。
