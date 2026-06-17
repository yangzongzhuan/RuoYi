---
name: implementation-strategy
description: 编辑代码前决定实现策略。用于任务会改变导出 API、运行时行为、序列化状态、测试或文档，并且需要判断兼容性边界、是否需要 shim/migration，以及未发布接口能否直接重写时。
---

# 实现策略

## 概览

当任务会改变运行时行为，或任何看起来可能涉及兼容性的内容时，在编辑代码前使用本 skill。目标是在保护真实已发布契约的同时，让实现保持简单。

## 快速开始

1. 识别正在改变的 surface：已发布 public API、未发布 branch-local API、internal helper、persisted schema、wire protocol、CLI/config/env surface，或仅 docs/examples。
2. 确定最新 release 边界。如果仓库指南指定 release tag pattern，使用该 pattern；否则使用最新可达 tag：
   ```bash
   BASE_TAG="$(git describe --tags --abbrev=0 2>/dev/null || true)"
   echo "$BASE_TAG"
   ```
3. 如果没有 release tag，明确说明，并只按 documented public contracts 和 durable external state 判断兼容性。
4. 按最新 release tag 或已记录 compatibility policy 判断 breaking-change risk，不要按未发布 branch churn 或 default branch 上 release 后变更判断。
5. 优先选择满足当前任务的最简单实现。直接更新 callers、tests、docs 和 examples，不要保留已被取代的未发布接口。
6. 只有存在具体 released consumer、受支持的 durable external state boundary 确实需要，或用户明确要求 migration path 时，才添加 compatibility layer。

## 兼容性边界规则

- Released public API 或 documented external behavior：保留兼容性，或提供明确 migration path。
- Persisted schema、serialized state、wire protocol、CLI flags、environment variables 和 externally consumed config：当它们属于最新 release，或仓库明确表示要跨 commit、process 或 machine 保留时，按 compatibility-sensitive 处理。
- 只在当前 branch 引入的 interface changes：不是 compatibility target，直接重写。
- 已在 default branch 但晚于最新 release tag 的 interface changes：本身不构成 breaking change。除非它们已经定义已发布或明确支持的 durable external state boundary，否则直接重写。
- Internal helpers、private types、same-branch tests、fixtures 和 examples：直接更新，不要添加 adapters。
- default branch 上未发布的 persisted schema versions：当中间快照明确不受支持时，可在 release 前重新编号或 squash。

## 默认实现立场

- 当旧形态未发布时，优先删除或替换，而不是添加 aliases、overloads、shims、feature flags 或 dual-write logic。
- 不要因为某个混乱抽象已经存在于当前 branch diff，就保留它。
- 如果 review feedback 声称某个改动是 breaking change，先按最新 release tag 和真实外部影响核对，再接受反馈。
- 如果变更确实跨越最新 released contract boundary，在 implementation notes、release notes context 和用户可见总结中明确说明。

## 项目特定兼容性规则

- 已发布边界：仓库存在 `v4.x.y` release tags，当前本地版本为 `v4.8.3`；默认使用 `git describe --tags --abbrev=0` 识别最新可达 release tag，除非用户或仓库文档另行指定。
- 用户可见 runtime surface：`ruoyi-admin` controllers、Thymeleaf templates、static assets、Springdoc/OpenAPI exposure、Quartz job pages、错误页、登录/注册/权限相关页面。
- 外部配置 surface：`ruoyi-admin/src/main/resources/application.yml`、`application-druid.yml`、Shiro/session/remember-me/captcha、XSS/CSRF、Druid monitor、upload profile、server/context path 和 datasource settings。
- 持久化 / schema surface：`sql/` scripts、MyBatis mapper XML、domain/service changes that alter database reads/writes、Quartz tables/jobs and generated SQL output。
- 生成输出 surface：`ruoyi-generator/src/main/resources/generator.yml`、`ruoyi-generator/src/main/resources/vm/` 和 generator UI templates；`allowOverwrite: false` 是当前文件覆盖边界。
- Operations / package surface：`pom.xml`、module `pom.xml` files、`Makefile`、`bin/`、`ry.sh`、`ry.bat` and packaged `ruoyi-admin` jar/war behavior.
- 未文档化边界：没有发现本仓库自定义 deprecation、migration 或 schema-version policy；触及 SQL schema、外部配置、生成输出或运行脚本时，在实现说明中明确兼容性判断和未配置验证缺口。

## 何时停止并确认

- 变更会改变最新 release tag 已发布的行为。
- 变更会修改 durable external data、protocol formats 或 serialized state。
- 变更会修改 `sql/` schema/data、MyBatis mapper XML、application YAML、generator overwrite behavior、生成模板或运行/打包脚本。
- 用户明确要求 backward compatibility、deprecation 或 migration support。

## 输出期望

当本 skill 实质影响实现方式时，在 reasoning 或 handoff 中简要说明决策，例如：

- `Compatibility boundary: latest release tag v0.x.y; branch-local interface rewrite, no shim needed.`
- `Compatibility boundary: released schema; preserve compatibility and add migration coverage.`
