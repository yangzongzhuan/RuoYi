# Product Sense Practices

本文件记录当前仓库的产品判断和体验指导。它解释取舍，不替代 [RULES.md](../../RULES.md) 中的硬约束。

## Product North Star

RuoYi 是面向个人和企业开发者/维护者的 Java 后台管理系统和快速开发框架。核心用户结果是：能以稳定、可理解、可扩展的方式搭建 Web 管理后台、CMS、CRM、OA 等系统，并复用内置的权限、组织、字典、日志、定时任务、监控和代码生成能力。

## Experience Principles

- 后台管理体验优先清晰、稳定和可维护。表单、列表、权限、菜单、日志和监控页面应保持可预期，不追求无证据的新交互风格。
- 生成器输出是产品体验的一部分。模板应生成可读、可维护、贴近当前模块结构的 Java/HTML/XML/SQL，而不是只满足一次性生成。
- 配置默认值要谨慎。`application.yml`、`application-druid.yml`、Shiro/session、XSS/CSRF、Druid monitor 和上传路径会影响部署体验和安全理解。
- 文案和 docs 只描述当前仓库能证实的能力。README 中的上游演示、文档地址和多版本分支说明不能写成本地仓库已经配置的 CI、部署或发布流程。
- demo 页面位于运行时资源树内；调整 demo 体验时仍按用户可见页面处理。

## Surface Guidance

- Web UI：保持现有 Spring MVC + Thymeleaf + Bootstrap/jQuery 风格；优先修正实际页面行为、权限入口和数据展示，不引入不一致的前端架构。
- API / controllers：用户能观察到的 URL、返回结构、权限、错误处理和导出行为都属于产品 surface；改动前核对 controller、service、mapper 和 template 的链路。
- Generated output：修改 `ruoyi-generator/src/main/resources/vm/` 或 `generator.yml` 时，关注生成代码的可读性、模块归属、覆盖策略和与 MyBatis/Thymeleaf 当前模式的一致性。
- Docs：`README.md` 讲产品和上游背景，`doc/` 讲使用/环境说明，`AGENTS.md` / `ARCHITECTURE.md` / `RULES.md` / `docs/practices/` 讲 agent-facing context。不要互相复制长篇内容。

## Review Questions

- 这个改动是否减少管理员、开发者或 agent 的猜测？
- 用户会不会误以为未配置的 CI、测试、部署、发布或安全流程已经存在？
- 生成器输出是否仍然像 RuoYi 当前代码，而不是引入孤立风格？
- 页面或配置默认值是否会改变部署、安全或权限体验？
- 错误、TODO、文档提示是否给出了真实下一步，而不是泛泛承诺？

## 和 Rules 的关系

产品相关硬约束见 `RULE-PRODUCT-001` 和 `RULE-DOMAIN-001`。本文件用于指导取舍；如果发现新的稳定产品不变量，应通过 `$fill-rules` 升级。
