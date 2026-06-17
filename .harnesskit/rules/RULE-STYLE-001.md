# RULE-STYLE-001

## Rule

跨 Maven 模块移动或复用代码前，先核对对应模块职责和 `pom.xml` 依赖方向。

## Details

模块职责和依赖方向会影响 Spring wiring、打包结果和运行时边界。当前结构中 `ruoyi-admin` 是 Web 入口并依赖 `ruoyi-framework`、`ruoyi-quartz`、`ruoyi-generator`；`ruoyi-framework` 依赖 `ruoyi-system`；`ruoyi-system`、`ruoyi-quartz`、`ruoyi-generator` 依赖 `ruoyi-common`。不要为了局部复用把代码移动到错误模块，或新增反向依赖。

证据：

- `ARCHITECTURE.md`
- `pom.xml`
- `ruoyi-admin/pom.xml`
- `ruoyi-framework/pom.xml`
- `ruoyi-system/pom.xml`
- `ruoyi-quartz/pom.xml`
- `ruoyi-generator/pom.xml`

验证 / runner：

- Runner：review；Maven compile/build runner 尚未绑定为官方 gate。
