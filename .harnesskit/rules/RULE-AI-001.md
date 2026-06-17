# RULE-AI-001

## Rule

高影响判断必须回到源码、配置、脚本、清单或现有文档核对，不能只依赖 `.harnesskit/facts.md`。

## Details

`.harnesskit/facts.md` 是 scan/fill 的事实快照，不是仓库事实本身的替代品。涉及 runtime behavior、配置、数据库、生成输出、验证入口、模块依赖或安全边界时，必须核对真实文件。

证据：

- `.harnesskit/facts.md`
- `AGENTS.md`
- `ARCHITECTURE.md`
- `pom.xml`
- `ruoyi-admin/src/main/resources/application.yml`
- `ruoyi-admin/src/main/resources/application-druid.yml`

验证 / runner：

- Runner：review / agent workflow。
- 自动检查：未配置。
