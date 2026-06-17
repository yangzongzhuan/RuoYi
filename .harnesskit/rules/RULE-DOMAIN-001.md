# RULE-DOMAIN-001

## Rule

用户、角色、菜单、部门、字典、参数、日志、在线用户、定时任务和代码生成属于后台管理核心领域，不能作为无关重构顺手改变。

## Details

README 将这些能力列为 RuoYi 内置功能；仓库中对应 controller、service、mapper、template、SQL 和 generator 资源分布在 `ruoyi-admin`、`ruoyi-system`、`ruoyi-quartz`、`ruoyi-generator` 和 `sql/`。修改这些领域行为时需要明确任务目的、兼容性边界和验证缺口。

证据：

- `README.md`
- `ARCHITECTURE.md`
- `ruoyi-admin/src/main/java/com/ruoyi/web/controller/`
- `ruoyi-system/`
- `ruoyi-quartz/`
- `ruoyi-generator/`
- `sql/`

验证 / runner：

- Runner：review；domain tests 未配置。
