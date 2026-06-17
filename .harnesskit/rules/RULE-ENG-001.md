# RULE-ENG-001

## Rule

用户可见行为变更不能声称已有自动测试覆盖，除非新增或运行了仓库确认的测试/验证。

## Details

RuoYi 的用户可见行为包括 Web controllers、Thymeleaf 页面、static UI assets、application YAML、SQL scripts、MyBatis mapper XML、generator templates/config 和运行/打包脚本。当前扫描没有发现 `src/test*` 测试目录、CI workflow 或文档化 test command，因此不能把“测试已覆盖”作为默认完成声明。

证据：

- `AGENTS.md`
- `ARCHITECTURE.md`
- `.harnesskit/facts.md`
- 当前仓库未发现 `src/test*` 测试目录。

验证 / runner：

- 自动测试 runner：未配置。
- 适用检查：review；如任务新增了仓库确认的测试或验证命令，最终说明必须区分已运行检查和未配置检查。
