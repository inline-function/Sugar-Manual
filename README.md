# Sugar-Manual（语法糖手册）

面向精神科患者/照护者的**用药语法糖手册**应用：结构化药物资料、联用预警引擎、药代动力学浓度曲线，基于 Sublimation 语言与 SfA（Android）编译目标实现。本仓库由 `Sublimation 生态` 团队按 SfA 仓库布局建立，为编译器注解处理器链路、Android 标准库、平台桥的实际工程样本。

## 仓库布局（SfA layout）

- `app/` —— 语言模块（编译入口）：
  - `main.subl` 主界面（MD 风格三段式首页：顶栏/浓度折线图/预警栏/药品列表/服用药物悬浮按钮；生命周期注册）
  - `drugs.subl` 药物资料数据模型 + JSON 编解码 + 药库持久化（`saveDrugs`/`loadDrugs`/`loadDrugNames`）
  - `warn.subl` 预警引擎（ADR 共享反应加权 + 酶抑制相互作用加权）
  - `pk.subl` 药代动力学（一阶消除 + 多剂量叠加 + 对数剂量效应）
  - `fluvoxamine.subl` 氟伏沙明完整资料（按 `docs/语法糖手册.md` 逐条录入）
  - `interact.subl` 帕罗西汀最小演示资料（联用预警引擎验收场景）
  - `ledger.subl` 服药台账（「服用药物」FAB 的记录层；core/json 序列化，storage 键 `ledger`；48h 滑动窗口换算成药代 Dose 序列）
  - `Chart.subl` / `FloatingButton.subl` 本任务新增的 @view 视图（折线图 / MD 悬浮圆角按钮）
  - 其余 `.subl` 为 Android 标准库快照（Box/Text/Button/TextField/TimelineView/Chart/FloatingButton/log/platform/toast…，与 SfA 仓库同步；Box 已加 `scrollable` 字段）
  - `core/` —— 语言核心库快照（`core/json` 由模块内 `app/core/` 提供）
- `annotation processer/` —— 编译期注解处理器（`@android`/`@root` 桥接、`@view`/`@composable` 视图变换；与 SfA 同步）
- `android-app/` —— Android 壳工程（Gradle wrapper、MainActivity、DshBridge、ChartView、res 等；`app.js` 由编译生成）
- `docs/语法糖手册.md` —— 需求与数据规格
- `docs/使用说明.md` —— 使用/构建/验收说明（含 -A 端到端 APK 命令）
- `docs/视图渲染协议补充.md` —— Chart/FloatingButton/Box.scrollable 视图渲染协议
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
- 多剂量叠加：`concentrationAt(doses, T½, F, t) = Σ D·F·0.5^((t−tᵢ)/T½)`（仅对 t ≥ tᵢ 的剂量求和；**必须**写成 `acc + (D * F * decay(...))`——语言无乘加优先级，见「已知限制」第 4 条）。
- `seriesValues(from, to, step)` 采样浓度序列（演示：100mg@0h + 100mg@24h，T½=15，F=0.5，0..48h 步长 4h → 13 点）。
- `doseEffect(d, ed50) = d/(d+ed50)`（对数剂量-效应 Emax 型曲线，d≤0 → 0）。
- 保守简化（已记录于源码头注释）：忽略吸收相与分布容积（对 tmax「不稳定」的资料是保守上界）；浓度采用相对单位（F·D）；效应曲线以 Emax 形式表达。

## 药库持久化

编码：结构化数据 → 手工生成 JSON 文本 → `jsonParse` 校验 → `jsonToStr`（`core/json` 规范化）→ `storagePut("drugs", text)`。
解码：`storageGet` → `jsonParse` → 数组/字段投影 → `Drug`。全程走 core/json，无自定义分号编码。

## 服药台账与首页 UI（t29）

- **台账**（`ledger.subl`）：`DoseRec(drug, amount, time)`，最新在前（cons 头插），落 storage 键 `ledger`（core/json，`time` 为毫秒字符串）；`saveLedger` 返回 JSON 合法性布尔。换算：固定 48h 滑动回看窗（t0 = now−48h），`hourOf = (time−t0)/3600000`，超窗记录过滤，空台账回退演示剂量。
- **首页**（`main.subl`）：顶栏（标题「语法糖手册」+ 「V1.0」+ 文档/设置按钮，`openUrl`/`openSettings`）；浓度折线图（`Chart(kind="line")`，0..48h 步 4h 共 13 点，T½/F 读资料库）；预警栏（`levelBg`/`levelFg` 四级 MD 着色：危险红 #FDECEA/#B3261E、警告橙、提示蓝、无预警绿）；药品列表（药名 剂量mg · 时间，按最近服药时间从上到下）；右下 `FloatingButton`「服用药物」（记录服药 → 持久化 → 刷新曲线/列表/预警 + toast）。
- **新增视图**（已同步 SfA 仓库 `app/`）：`Chart`（折线/柱状，KT 端 ChartView 自绘 Canvas）、`FloatingButton`（MaterialButton 圆角+tint，含 FAB 的 Box 渲染为 FrameLayout、Gravity BOTTOM|END 叠放）、`Box.scrollable`（ScrollView 包装）。KT 分支在 `android-app/.../DshBridge.kt`，协议详见 `docs/视图渲染协议补充.md`。

## 编译与验证

```powershell
# 1. 模块编译（CWD = 本仓库根目录；处理器/标准库按 CWD 相对加载）
java -cp "<主仓库 5 模块 jar>;<kotlin-stdlib.jar>" sugared.functor.MainKt compile app -o app/out.js

# 2. 桌面断言运行（app/out.test.js = 在生成码闭包内注入 68 条断言 + __dshAndroid 桥 mock）
node app/out.test.js   # 期望输出 PASSES=82 FAILS=0，退出码 0

# 3. 端到端 APK（-A 注解处理器链路；JAVA_HOME 指向 JDK 17，-A 选项必须绝对路径）
#    产物：build-apk/app-debug.apk（签名）；assets/app.js 由处理器注入
java -cp "<主仓库 5 模块 jar>;<kotlin-stdlib.jar>" sugared.functor.MainKt compile app -o app/out.js \
  -A appDir="<绝对路径>/Sugar-Manual/android-app" -A jsPath="<绝对路径>/Sugar-Manual/app/out.js" \
  -A sdkDir="<绝对路径>/sdk-lite" -A outDir="<绝对路径>/Sugar-Manual/build-apk"
```

断言覆盖：氟伏沙明全部逐字字段、帕罗西汀演示字段、ADR **危险级**（恶心 8）与**警告级**（失眠 6/嗜睡 5）、酶代谢 **危险级**（CYP2D6 = 5）、`k=ln2/T½`、浓度序列与独立重算公式逐点一致（一阶叠加）、`doseEffect` 三点、JSON 读写往返（含 core/json 合法性 + storagePut/Get 桥调用）、`content()` Box 视图树构建、平台桥收到 registerLifecycle/storagePut/storageGet。

本次验收实际命令（2026-10-26）：
- 编译：`exit=0`（处理器参与编译，H-ANDROID-BUILTIN/H-ROOT-BUILTIN/H-ARITH-PRECEDENCE 提示正常输出）
- 桌面断言：`PASSES=68 FAILS=0`，`nodeExit=0`（覆盖顶栏/图表逐点重算/预警着色/FAB/台账往返与点击端到端/引擎回归）
- 端到端 APK：`-A` 链路 `exit=0`，产物 `build-apk/app-debug.apk`（5.58MB、804 条目、`assets/app.js` 71354 字节，`jarsigner -verify` 通过）

## t47 首页布局改版（2026-10-27）

用户亲自给的首页规格与落地：

1. **顶栏**：保留（标题 + V1.0 + 文档/设置）。
2. **上半区浓度折线（填满）**：`Chart(modifier = Height(screenHeight()*45/100))`——DshBridge `buildChart` 新增一行 `setModifier(cv, node.opt("modifier"))`，**兑现已声明字段而不扩协议**，lp 变 (MATCH_PARENT, 屏高 45%) 实现填充；下半区 `Height(50%)` 定高 Box + 内层 scrollable Box（根 Box 因 FAB 被强制 MATCH/WRAP 不可滚，需定高容器获得有界测量规格）。
3. **中间预警按等级分绿/黄/红三组**：每级一行横向标签流（水平 Box scrollable），触发项按**各自引擎阈值**归级（ADR 3/5/7、代谢 1/3/5），空组给「：暂无」占位；色板：危险红 #FDECEA/#B3261E、警告黄 #FFF8E1/#B26A00、提示绿 #E8F5E9/#2E7D32。
4. **预警下方服药记录卡（四字段、台账驱动）**：每条 `DoseRec` 一张全宽背景卡（`Box(Width(screenWidth()), backgroundColor)` 填满左右 + 内层 `Padding(24)`），字段：药物名称（16sp 粗）、剂量（紫粗 "N mg"）、给药时间（`fmtTime` MM-dd HH:mm）、给药方式（药库 pk 首条 route，回退「口服」）。**圆角为已登记缺口**（Box 无 cornerRadius，见 docs/视图渲染协议补充.md §6.2），以全宽色块近似。
5. **「服用药物」FAB → 页内表单面板**：当前协议无 Dialog/Dropdown（缺口登记 §6.3），FAB 点击开关面板；面板内选药（Button×N，✓ 选中态）、剂量 TextField（number 键盘、onChange 只回写草稿不重渲染）、确认/取消。**确认**：`ratOr` 解析草稿（非法值 toast 且不关面板）→ 台账头插 → `saveLedger` → `layout(content)` 整树差量刷新首页（KT applyRootDiff）。

**状态管理**：工程模式顶层不允许 `@mut var`（只允许声明），整树重渲染会重建 `content()` 的帧，故表单状态（`sm_form`/`sm_pick`/`sm_dose`）走 storage KV，layout 前后由 content() 读回。

**渲染分支同步**：`buildChart` modifier 兑现已同步到 SfA 主壳（`F:\SublimationProject\sublimation for android\android-app` 的 DshBridge.kt 与本仓壳一致）。协议缺口详见 `docs/视图渲染协议补充.md` §6。

**覆盖的坑（供队长文档）**：

- `@view` 内联基元参数阴影：`elementAt(drugs, i)` 在形参同名时生成 `const i = i`（TDZ）——规避为形参异名（`drugAt(drugs, n)`）。已登记 docs/使用说明.md §5。
- 根 Box 含 FAB 时强制 `(MATCH, WRAP)` 且与 scrollable 互斥（FAB 优先）——可滚内容必须放进单独的定高 Box 子树。
- out.js 编译产物尾部自带 `main();` 与 IIFE 闭合——测试驱动 splice 需移除，避免重复调用与括号失衡。

断言从 68 条增至 **82 条**（新增：Chart modifier 填充断言、三组预警结构/文案/着色、FAB 表单开/关、选药高亮、草稿回写、确认提交台账核对 + 卡片四字段 + 全宽/内边距、曲线首末点按剂量×生物利用度重算、非法剂量 toast 不关面板、取消复位、`routeOf`/`firstRoute`/`drugAt`/`pickMark`/`fabLabel` 纯函数层）。

## 已知语言/编译器限制（验收中踩到，供队长文档参考）

1. **尾随 lambda 体内不能写 `var` 声明**（含 `unchecked var`）：解析期在 lambda 内出现声明语句时，会在**后面**某个调用点报误导性 `期望表达式，实际'{'`。规避：把可变状态声明在函数体（`@mut var`），lambda 内只做 `unchecked x = …` 赋值。
2. **ANF 阴影 bug**：`fun f(xs, i)` 的 lambda 参数被 ANF 重写为 `const i = i`（TDZ ReferenceError）。规避：参数改名（`i`→`n`）。样例：`nthStr`。
3. **模块目录扫描不递归**：`app/data/` 子目录下的 `.subl` 不会被编译（报 E-UNBOUND-NAME）。规避：数据文件平铺在模块根。
4. **算术无优先级**：`acc + a * b` 生成 `((acc + a) * b)`（乘加混用时编译器输出 H-ARITH-PRECEDENCE 提示，非错误）。规避：显式括号（`acc + (a * b)`，本仓库 `pk.concentrationAt` 已修复）。
5. **`distinct` 会排序**：core 的 `distinct` 输出排序结果而非保序去重。规避：需要保序去重时手写 fold（见 `ledger.recentDrugs`）。

## 与 Android 链路的关系

`android-app/` 是 APK 壳工程（同 SfA 布局）：`-A` 编译链路把模块产物打包进 WebView shell。桌面 CLI 链路（本 README 第 1、2 步）是引擎层验收入口；APK 打包经 `-A` 注解处理器链路（处理器调用宿主 `buildAndroidApk`：注入 `assets/app.js` + sdk-lite 无 Gradle 构建 aapt2/d8/zipalign/apksigner，debug 签名）在本任务中已端到端跑通（命令与验收细节见 `docs/使用说明.md`）。
