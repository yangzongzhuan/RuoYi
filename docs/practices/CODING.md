# Coding Practices

本文件记录当前仓库的代码判断指导。它不是硬规则索引；必须遵守的约束仍以 [RULES.md](../../RULES.md) 和 `.harnesskit/rules/` 为准。

## 判断原则

- 先按 Maven 模块职责定位改动：Web 入口和页面在 `ruoyi-admin`，框架集成在 `ruoyi-framework`，系统业务在 `ruoyi-system`，公共类型和工具在 `ruoyi-common`，定时任务在 `ruoyi-quartz`，代码生成在 `ruoyi-generator`。
- 跨模块复用前先核对 `pom.xml` 依赖方向。不要为了局部方便把业务逻辑下沉到 `ruoyi-common`，也不要引入反向依赖。
- 优先做贴近现有 Spring MVC / MyBatis / Thymeleaf / Shiro 写法的小改动。只有当抽象能减少真实重复、清楚职责或降低跨模块耦合时才新增抽象。
- 修改 controllers、templates、static resources、mapper XML、SQL、application YAML、generator templates 或 scripts 时，把它们当成用户可见或持久化边界，不混入无关格式化和顺手重构。
- 当前没有 formatter 或 lint runner 证据；编辑时尽量保持邻近代码风格，避免大面积 whitespace churn。

## Do

- 读相邻 controller/service/mapper/template 的现有模式，再决定放置位置和命名。
- 保持 Java package 与模块语义一致，例如 `com.ruoyi.system`、`com.ruoyi.quartz`、`com.ruoyi.generator`、`com.ruoyi.common`。
- 修改 MyBatis mapper XML 时同步核对对应 mapper interface、domain/service 调用和 SQL scripts 是否受影响。
- 修改页面模板或 static assets 时核对对应 controller route、权限、菜单和运行时资源边界。
- 对生成器改动同时核对 `generator.yml`、`vm/` templates、生成器页面和生成输出语义。

## Don't

- 不要把 `templates/demo/` 当成测试 fixture；它位于运行时资源树内。
- 不要把 README 的上游说明、示例命令或演示站点当成当前仓库的本地实现事实。
- 不要新增未配置工具链、格式化器、package manager 或验证命令作为完成条件。
- 不要因为缺少测试目录就跳过风险说明；要明确哪些检查未配置、哪些只做了 review。

## Review Questions

- 这个改动是否保持在正确的 Maven 模块和 package 边界里？
- 新依赖或代码移动是否改变了 `pom.xml` 依赖方向？
- 是否触及 Web 页面、静态资源、配置、SQL、mapper XML、generator templates 或运行脚本？
- 新抽象是否降低复杂度，还是只是移动代码？
- 是否混入无关重排、格式 churn 或顺手重构？
- 如果用户可见行为改变了，当前仓库是否有真实测试/验证 runner 可运行？没有的话是否如实说明？

## 和 Rules 的关系

违反硬约束时按 [RULES.md](../../RULES.md) 处理；本文件只帮助判断“怎样写得更像这个仓库”。发现新的稳定硬约束候选时，交给 `$fill-rules` 更新 `RULES.md` 和 `.harnesskit/rules/`。
