# RULE-ARCH-002

## Rule

`ruoyi-generator` 模板/config 和 Web/Quartz 页面资源属于用户可见输出，不能当作内部实现细节处理。

## Details

`ruoyi-generator/src/main/resources/vm/` 是代码生成模板，`generator.yml` 控制生成配置和覆盖行为。`ruoyi-admin/src/main/resources/templates/`、`ruoyi-admin/src/main/resources/static/` 和 `ruoyi-quartz/src/main/resources/templates/` 是运行时页面/静态资源。修改这些位置会改变用户看到的页面或生成结果。

证据：

- `ARCHITECTURE.md`
- `ruoyi-generator/src/main/resources/generator.yml`
- `ruoyi-generator/src/main/resources/vm/`
- `ruoyi-admin/src/main/resources/templates/`
- `ruoyi-admin/src/main/resources/static/`
- `ruoyi-quartz/src/main/resources/templates/`

验证 / runner：

- Runner：review；template/generation tests 未配置。
