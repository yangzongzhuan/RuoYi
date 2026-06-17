---
name: pr-draft-summary
description: 生成所需的 PR-ready summary block、branch suggestion、title 和 draft description。用于中等及以上规模的运行时代码、测试、示例、构建/测试配置或有行为影响的文档变更完成后的最终交付；仅在 trivial/conversation-only、无行为影响的 repo-meta/doc-only 任务，或用户明确不要 PR draft block 时跳过。
---

# PR 草稿总结

## 目的
在实质性代码工作完成后，生成本仓库需要的 PR-ready summary：简短总结、可用 PR title，以及以 "This pull request <verb> ..." 开头的 draft description。输出块应可直接粘贴进 PR。

## 触发条件

- 本仓库任务已完成（或 ready for review），且触及运行时代码、SQL、templates、static assets、generator output、configuration、scripts、Maven build/dependency config 或有行为影响的 docs。
- 对实质性代码工作，把它作为默认 final handoff step。在所需验证或 changeset 工作完成后、发送“完成”回复前执行。
- 仅在 trivial/conversation-only、无行为影响的 repo-meta/guidance-only 任务，或用户明确不要 PR draft block 时跳过。

## 自动收集的输入（不要询问用户）

- 当前 branch：`git rev-parse --abbrev-ref HEAD`。
- Working tree：`git status -sb`。
- Untracked files：`git ls-files --others --exclude-standard`。
- Changed files：`git diff --name-only`（unstaged）和 `git diff --name-only --cached`（staged）；sizes 用 `git diff --stat` 和 `git diff --stat --cached`。
- 最新 release tag（可用时）：`LATEST_RELEASE_TAG=$(git describe --tags --abbrev=0 2>/dev/null || true)`。
- Base reference：
  - 优先使用 branch upstream：`BASE_REF=$(git rev-parse --abbrev-ref --symbolic-full-name @{upstream} 2>/dev/null || true)`。
  - 如果没有 upstream，尝试 remote default branch：`BASE_REF=${BASE_REF:-$(git symbolic-ref --quiet --short refs/remotes/origin/HEAD 2>/dev/null || true)}`。
  - 如果存在 base reference，计算 base commit：`BASE_COMMIT=$(git merge-base --fork-point "$BASE_REF" HEAD 2>/dev/null || git merge-base "$BASE_REF" HEAD 2>/dev/null || true)`。
- Base fork point 之后的 commits（可用时）：`test -n "$BASE_COMMIT" && git log --oneline --no-merges ${BASE_COMMIT}..HEAD`。
- 本仓库 category signals：
  - Runtime 或 product behavior：`ruoyi-admin/src/main/java/**`, `ruoyi-framework/src/main/java/**`, `ruoyi-system/src/main/java/**`, `ruoyi-common/src/main/java/**`, `ruoyi-quartz/src/main/java/**`, `ruoyi-generator/src/main/java/**`。
  - User-visible pages/assets：`ruoyi-admin/src/main/resources/templates/**`, `ruoyi-admin/src/main/resources/static/**`, `ruoyi-quartz/src/main/resources/templates/**`。
  - Generated output / generator behavior：`ruoyi-generator/src/main/resources/vm/**`, `ruoyi-generator/src/main/resources/templates/**`, `ruoyi-generator/src/main/resources/generator.yml`。
  - Persistence / SQL mapping：`sql/**`, `ruoyi-system/src/main/resources/mapper/**`, `ruoyi-quartz/src/main/resources/mapper/**`, `ruoyi-generator/src/main/resources/mapper/**`。
  - External config / security config：`ruoyi-admin/src/main/resources/application.yml`, `ruoyi-admin/src/main/resources/application-druid.yml`, `ruoyi-admin/src/main/resources/logback.xml`, `ruoyi-admin/src/main/resources/ehcache/**`, `ruoyi-admin/src/main/resources/mybatis/**`。
  - Build、packaging、dependency 或 scripts：`pom.xml`, `ruoyi-*/pom.xml`, `Makefile`, `bin/**`, `ry.sh`, `ry.bat`。
  - Tests：当前未发现 `src/test*` 测试目录；如果未来新增，按实际路径分类。
  - Documentation with behavior impact：`README.md`, `doc/**`, `docs/**`。
  - Agent 或 repository policy metadata：`AGENTS.md`, `ARCHITECTURE.md`, `RULES.md`, `.harnesskit/**`, `.agents/**`, `docs/practices/**`, `CLAUDE.md`。

## 工作流
1. 运行上述命令，不要询问用户。如果无法发现 `BASE_REF`、`BASE_COMMIT` 或 `LATEST_RELEASE_TAG`，继续执行；只有当不确定性影响 PR summary 时才说明。
2. 如果没有 staged/unstaged/untracked changes，且没有 `BASE_COMMIT` 或 `${BASE_COMMIT}` 之后没有 commits，简短说明未检测到代码变更，并跳过 PR block。
3. 根据 touched paths 和 diff content 推断 change type，分类为 feature、fix、refactor、docs-with-impact 或 repo-guidance。只有 diff 改变已发布 public behavior、external config、SQL/schema、generated output、serialized state、runtime routes 或 operation scripts 时，才标记 backward-compatibility risk。有 `LATEST_RELEASE_TAG` 时按它判断；没有 tag 时按已记录 compatibility policy 判断。
4. 用关键路径（top 5）和 `git diff --stat` output 在 1-3 句内总结变化；明确说明 untracked files，因为 `--stat` 不包含它们。如果 working tree 干净但 `${BASE_COMMIT}` 之后有 commits，用 commit messages 总结。
5. 选择 description lead verb：feature -> `adds`，bug fix -> `fixes`，refactor/perf -> `improves` 或 `updates`，docs-only -> `updates`。
6. 建议 branch name。如果已经不在 default branch，保留当前 branch；否则按主要领域建议 `feat/<slug>`、`fix/<slug>` 或 `docs/<slug>`。如果无法发现 default branch，不要假装已经知道。
7. 如果当前 branch 匹配 `issue-<number>`（仅数字），保留该 branch suggestion。只有在已知仓库托管平台支持 auto-closing syntax 时，才包含类似 `This pull request resolves #<number>.` 的 line；否则只提及 issue reference，不声称 auto-close。
8. 使用下方模板起草 PR title 和 description。
9. 只输出 "Output Format" 中的 block。

## 输出格式
结束任务时，除非任务属于记录的跳过条件，或用户说不需要，否则在简短状态说明后添加下面的 Markdown block。

```
# Pull Request Draft

## Branch name suggestion

git checkout -b <kebab-case suggestion, e.g., feat/add-login-flow>

## Title

<single-line imperative title; conventional commit prefix like feat:, fix:, chore: preferred>

## Description

<start with "This pull request adds/fixes/improves ..."; explain the background and what changed; use bullets for detail if needed>
```

保持精简：不要在 block 周围添加重复说明，也避免在 changes 和 description 之间重复细节。除非用户特别要求，否则不需要列出 tests。
