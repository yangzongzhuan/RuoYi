# RULE-ENG-002

## Rule

Maven 依赖、模块或插件变更必须同步对应 `pom.xml`，不要假设存在锁文件或 Maven wrapper。

## Details

本仓库是 Maven multi-module 项目。父级 `pom.xml` 管理模块列表、Java 17 compiler config 和主要 dependency versions；各 `ruoyi-*/pom.xml` 声明模块职责和模块依赖。扫描没有发现 `mvnw`、`mvnw.cmd` 或 Maven lockfile。

证据：

- `pom.xml`
- `ruoyi-admin/pom.xml`
- `ruoyi-framework/pom.xml`
- `ruoyi-system/pom.xml`
- `ruoyi-common/pom.xml`
- `ruoyi-quartz/pom.xml`
- `ruoyi-generator/pom.xml`
- `.harnesskit/facts.md`

验证 / runner：

- Maven build runner：未绑定为官方 gate。
- 适用检查：review `pom.xml` diff；如果未来配置 `make verify` checks，再同步更新本 rule detail。
