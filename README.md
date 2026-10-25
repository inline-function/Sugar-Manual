# Sugar-Manual（语法糖手册）

面向精神科患者/照护者的**用药语法糖手册**应用：结构化药物资料、联用预警引擎、药代动力学浓度曲线，基于 Sublimation 语言与 SfA（Android）编译目标实现。本仓库由 `Sublimation 生态` 团队按 SfA 仓库布局建立，为编译器注解处理器链路、Android 标准库、平台桥的实际工程样本。

## 仓库布局（SfA layout）

- `app/` —— 语言模块（编译入口）：
  - `main.subl` 主界面（生命周期注册 + 预警汇总 + 浓度曲线 + 保存示例药库按钮）
  - `drugs.subl` 药物资料数据模型 + JSON 编解码 + 药库持久化（`saveDrugs`/`loadDrugs`/`loadDrugNames`）
  - `warn.subl` 预警引擎（ADR 共享反应加权 + 酶抑制相互作用加权）
  - `pk.subl` 药代动力学（一阶消除 + 多剂量叠加 + 对数剂量效应）
  - `fluvoxamine.subl` 氟伏沙明完整资料（按 `docs/语法糖手册.md` 逐条录入）
  - `interact.subl` 帕罗西汀最小演示资料（联用预警引擎验收场景）
  - 其余 `.subl` 为 Android 标准库快照（Box/Text/Button/TextField/TimelineView/log/platform/toast…，与 SfA 仓库同步）
  - `core/` —— 语言核心库快照（`core/json` 由模块内 `app/core/` 提供）
- `annotation processer/` —— 编译期注解处理器（`@android`/`@root` 桥接、`@view`/`@composable` 视图变换；与 SfA 同步）
- `android-app/` —— Android 壳工程（Gradle wrapper、MainActivity、DshBridge、ChartView、res 等；`app.js` 由编译生成）
- `docs/语法糖手册.md` —— 需求与数据规格
- `scripts/sync-deps.sync.ps1` —— 从主仓库同步标准库/处理器快照
- `.gitignore`

## 数据模型

```
Rated(level: Str, name: Str)                  // 频率级距条目（十分常见/常见/少见/罕见；药效极弱…极强；酶抑制弱效…极强）
PkProfile(route, form, halfLife: Nat, tmax,
          clearance, curve, bio: Rat,
          metabolites: List[Rated])           // 给药途径/剂型/半衰期h/达峰/清除级/曲线类型/生物利用度/代谢物
Drug(name, category, tags: List[Str], description,
     effects, enzymes: List[Str], inhibitions: List[Rated],
     pk: List[PkProfile],
     acuteTher, chronicTher,                  // 急性/慢性治疗效应
     acuteAdv, chronicAdv,                    // 急性/慢性不良反应（频率加权）
     withdraw, overdose)                      // 戒断 / 过量
```

氟伏沙明资料按需求文档逐字录入：7 标签、4 条药效、CYP2D6/CYP1A2 代谢、4 条酶抑制、T½=15h、F=0.5、tmax「不稳定」、一级消除/对数曲线、急性镇静、慢性抗强迫/抗抑郁/抗焦虑、急性血清素综合征（罕见）、慢性 ADR 10 条、戒断 9 条、过量 >1000mg。

## 预警引擎

- **ADR 共享反应**：同一反应名在急性+慢性各段出现即累计权重（罕见1/少见2/常见3/十分常见5），阈值 提示3 / 警告5 / 危险7，≥2 条目触发。
  演示联用（氟伏沙明 + 帕罗西汀）：恶心 5+3=**8 → 危险**；失眠 3+3=6 → 警告；嗜睡 3+2=5 → 警告。
- **酶抑制相互作用**：某酶被 ≥2 药物关联（抑制或经其代谢）时，取抑制剂权重和（弱效1/中效2/强效3/极强5），阈值 提示1 / 警告3 / 危险5。
  演示：CYP2D6（氟伏沙明代谢 + 帕罗西汀极强抑制）= 5 → **危险**。
- 输出：`Alert(level: Nat, items: List[Trigger])`，`levelName` 0/1/2/3 → 无/提示/警告/危险。

## 药代动力学（保守简化，模型参数全部来自资料字段，无魔法数字）

- 一阶消除：`k = ln2 / T½`，`eliminationRate(tHalf)`；`decay(tHalf, dt) = 0.5 ^ (Δt/T½)`。
- 多剂量叠加：`concentrationAt(doses, T½, F, t) = Σ D·F·0.5^((t−tᵢ)/T½)`（仅对 t ≥ tᵢ 的剂量求和）。
- `seriesValues(from, to, step)` 采样浓度序列（演示：100mg@0h + 100mg@24h，T½=15，F=0.5，0..48h 步长 4h → 13 点）。
- `doseEffect(d, ed50) = d/(d+ed50)`（对数剂量-效应 Emax 型曲线，d≤0 → 0）。
- 保守简化（已记录于源码头注释）：忽略吸收相与分布容积（对 tmax「不稳定」的资料是保守上界）；浓度采用相对单位（F·D）；效应曲线以 Emax 形式表达。

## 药库持久化

编码：结构化数据 → 手工生成 JSON 文本 → `jsonParse` 校验 → `jsonToStr`（`core/json` 规范化）→ `storagePut("drugs", text)`。
解码：`storageGet` → `jsonParse` → 数组/字段投影 → `Drug`。全程走 core/json，无自定义分号编码。

## 编译与验证

```powershell
# 1. 模块编译（CWD = 本仓库根目录；处理器/标准库按 CWD 相对加载）
java -cp "<主仓库 5 模块 jar>;<kotlin-stdlib.jar>" sugared.functor.MainKt compile app -o app/out.js

# 2. 桌面断言运行（app/out.test.js = 在生成码闭包内注入 55 条断言 + __dshAndroid 存储桥 mock）
node app/out.test.js   # 期望输出 PASSES=55 FAILS=0，退出码 0
```

断言覆盖：氟伏沙明全部逐字字段、帕罗西汀演示字段、ADR **危险级**（恶心 8）与**警告级**（失眠 6/嗜睡 5）、酶代谢 **危险级**（CYP2D6 = 5）、`k=ln2/T½`、浓度序列与独立重算公式逐点一致（一阶叠加）、`doseEffect` 三点、JSON 读写往返（含 core/json 合法性 + storagePut/Get 桥调用）、`content()` Box 视图树构建、平台桥收到 registerLifecycle/storagePut/storageGet。

本次验收实际命令（2026-10-26）：
- 编译：`exit=0`（处理器参与编译，H-ANDROID-BUILTIN/H-ROOT-BUILTIN/H-ARITH-PRECEDENCE 提示正常输出）
- 运行：`PASSES=55 FAILS=0`，`nodeExit=0`

## 已知语言/编译器限制（验收中踩到，供队长文档参考）

1. **尾随 lambda 体内不能写 `var` 声明**（含 `unchecked var`）：解析期在 lambda 内出现声明语句时，会在**后面**某个调用点报误导性 `期望表达式，实际'{'`。规避：把可变状态声明在函数体（`@mut var`），lambda 内只做 `unchecked x = …` 赋值。
2. **ANF 阴影 bug**：`fun f(xs, i)` 的 lambda 参数被 ANF 重写为 `const i = i`（TDZ ReferenceError）。规避：参数改名（`i`→`n`）。样例：`nthStr`。
3. **模块目录扫描不递归**：`app/data/` 子目录下的 `.subl` 不会被编译（报 E-UNBOUND-NAME）。规避：数据文件平铺在模块根。

## 与 Android 链路的关系

`android-app/` 是 APK 壳工程（同 SfA 布局）：`-A` 编译链路把模块产物打包进 WebView shell。桌面 CLI 链路（本 README 上面两步）是引擎层验收入口；APK 打包走与 SfA 相同的 gradle/build-apk 流程（需 JDK，未在本任务中执行——本任务验收标准为桌面 CLI + node 断言）。
