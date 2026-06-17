# RULE-ARCH-003

## Rule

`ruoyi-admin/src/main/resources/templates/demo/` 位于运行时资源树内，不要把它当成测试 fixture 或可随意删除的样例。

## Details

虽然目录名是 `demo`，但它在 `ruoyi-admin/src/main/resources/templates/` 下，会随应用资源参与运行时页面展示。删除、移动或改写这些页面应按用户可见资源变更处理。

证据：

- `ARCHITECTURE.md`
- `ruoyi-admin/src/main/resources/templates/demo/`
- `ruoyi-admin/src/main/resources/application.yml` 中 `ruoyi.demoEnabled: true`

验证 / runner：

- Runner：review；UI/browser test runner 未配置。
