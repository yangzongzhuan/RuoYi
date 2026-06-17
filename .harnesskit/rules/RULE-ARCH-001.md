# RULE-ARCH-001

## Rule

修改配置、SQL、MyBatis mapper、模板、生成器或运行脚本时，按用户可见或持久化边界处理。

## Details

这些文件会影响运行时行为、数据库状态、数据访问、页面输出、生成输出或部署/运行方式。不要把它们当作普通内部 helper 改动处理；改动前应按 `$implementation-strategy` 判断兼容性和外部影响。

证据：

- `AGENTS.md`
- `ARCHITECTURE.md`
- `ruoyi-admin/src/main/resources/application.yml`
- `ruoyi-admin/src/main/resources/application-druid.yml`
- `sql/`
- `ruoyi-system/src/main/resources/mapper/`
- `ruoyi-generator/src/main/resources/vm/`
- `bin/`, `ry.sh`, `ry.bat`

验证 / runner：

- Runner：review；自动 schema/test/build runner 未配置。
