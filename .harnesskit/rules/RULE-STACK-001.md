# RULE-STACK-001

## Rule

本仓库的已确认主栈是 Java 17 + Maven multi-module + Spring Boot 4.x；不要引入或要求未配置的包管理器/构建系统。

## Details

父级 `pom.xml` 声明 Java 17、Spring Boot 4.0.6 和 Maven modules。仓库未发现 Gradle、npm/pnpm/yarn、uv、Go、Cargo 等构建清单，也未发现 Maven wrapper。除非用户明确要求或仓库新增证据，否则不要把其他工具作为默认 runner。

证据：

- `pom.xml`
- `ruoyi-*/pom.xml`
- `.harnesskit/facts.md`

验证 / runner：

- Runner：review。
- 自动检查：未配置。
