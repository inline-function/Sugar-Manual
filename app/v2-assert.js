// app/v2-assert.js —— t59 回归断言（视图协议 v2 + Compose 参照 1:1 改写）
// 用法：node app/v2-assert.js
// 覆盖：
//   A 协议形态（modifiers 数组/链顺序/无旧单对象键、布局一次、生命周期注册）
//   B 顶栏（primaryContainer 64dp + SpaceBetween + 3 个 TextButton 实例键互异）
//   C 45/55 精确分区（内容区 = 屏高 - 顶栏 64dp；图 45% + 下半区补齐，无 5% 空隙）
//   D 图表数据结构（多序列 series + 总线逐点求和 + 12h 刻度 + 图例/徽标）
//   E 预警标签流（wrap=true 平铺直接子级、半透明 #33 色、Clip/Padding 链）
//   F 记录卡（Card 圆角/阴影、4×20 色条链、weight 行、空台账占位、96dp 底留白）
//   G FAB（icon add + 文本）
//   H 表单交互（BottomSheet 显隐、选药行实例键互异且点哪行选哪行、TextField、
//     四级校验 toast、确认落台账并刷新曲线/记录）
//   I 弹层（设置/文档 AlertDialog 显隐、清空记录）
const fs = require('fs');
const path = require('path');

let pass = 0, fail = 0;
function check(name, ok, detail) {
  if (ok) { pass++; console.log("  PASS  " + name); }
  else { fail++; console.log("  FAIL  " + name + (detail ? " :: " + detail : "")); }
}
function eq(a, b) { return JSON.stringify(a) === JSON.stringify(b); }

// ---- 桩：__dshAndroid（layout 捕获 / storage 内存 / toast / 屏幕参数） ----
const storage = new Map();
const caps = [];
const toasts = [];
const logs = [];
const SCREEN_W = 1080, SCREEN_H = 2400, DENSITY_DPI = 420;
globalThis.__dshAndroid = (kind, args) => {
  caps.push([kind, args]);
  switch (kind) {
    case "storageGet": return storage.get(args[0]) || "";
    case "storagePut": storage.set(args[0], args[1]); return null;
    case "toast": toasts.push(args[0]); return null;
    case "log": logs.push(args[0]); return null;
    case "screenWidth": return SCREEN_W;
    case "screenHeight": return SCREEN_H;
    case "screenDensity": return DENSITY_DPI;
    default: return null;
  }
};

// ---- 载入编译产物（IIFE 自跑 main() → 注册生命周期） ----
const js = fs.readFileSync(path.join(__dirname, 'out.js'), 'utf8');
new Function(js)();

function callKey(k, arg) {
  const fn = globalThis["__dshFn_" + k];
  if (typeof fn !== "function") throw new Error("no fn for key " + k);
  return fn(arg === undefined ? null : arg);
}
function latestTree() {
  const lays = caps.filter(c => c[0] === 'layout');
  return JSON.parse(lays[lays.length - 1][1][0]);
}
function flatten(n, out) {
  out.push(n);
  for (const c of (n.children || [])) { if (c !== null && c !== undefined) flatten(c, out); }
  return out;
}
function findByTag(tree, tag) {
  const out = [];
  (function rec(n) { if (n !== null && n !== undefined && n.tag === tag) out.push(n); for (const c of (n.children || [])) { if (c !== null && c !== undefined) rec(c); } })(tree);
  return out;
}
function mods(n) {
  return (n.modifiers || []).map(m => {
    const ks = Object.keys(m).filter(k => k !== "tag").sort((a, b) => Number(a) - Number(b));
    return m.tag + (ks.length ? "(" + ks.map(k => m[k]).join(",") + ")" : "");
  });
}

console.log("== A 协议形态 ==");
const lifecycle = caps.filter(c => c[0] === 'registerLifecycle').map(c => c[1][0]);
check("main() 注册 onCreate/onStart", lifecycle.indexOf('onCreate') >= 0 && lifecycle.indexOf('onStart') >= 0, lifecycle.join(','));
globalThis.__dshLifecycle_onStart(null);
const layouts0 = caps.filter(c => c[0] === 'layout');
check("onStart 触发 layout 一次", layouts0.length === 1, "count=" + layouts0.length);
let tree = latestTree();
const all = flatten(tree, []);
check("无视图节点残留单对象 modifier 键", all.every(n => !('modifier' in n) || Array.isArray(n.modifier)));
check("根 Box modifiers = [FillMaxSize]", eq(mods(tree), ["FillMaxSize"]));

console.log("== B 顶栏（TopAppBar） ==");
const topBar = tree.children[0];
check("顶栏链 [FillWidth, Height(48), Background(primary)]",
  eq(mods(topBar), ["FillWidth", "Height(48)", "Background(primary)"]), mods(topBar).join(","));
check("顶栏水平 + SpaceBetween 右推", topBar.orientation === "horizontal" && topBar.arrangement === "SpaceBetween");
check("顶栏 48dp", topBar.modifiers.some(m => m.tag === "Height" && m[0] === 48));
const tbKids = topBar.children[0].children;
check("标题行：语法糖手册(22 bold) + V1.0(11)", tbKids[0].text === "语法糖手册" && tbKids[2].text === "V1.0", JSON.stringify(tbKids.map(c => c.text || "[" + c.tag + "]")));
const titleT = topBar.children[0].children[0];
check("标题 22sp bold onPrimary", titleT.size === 22 && titleT.bold === true && titleT.color === "onPrimary");
const tbActions = topBar.children[1].children;
const tbIcons = tbActions.map(b => b.children[0] && b.children[0].name);
check("actions 三个 Icon-only TextButton：info/menu/settings", tbActions.length === 3 && eq(tbIcons, ["info", "menu", "settings"]), JSON.stringify(tbIcons));
const tbKeys = tbActions.map(b => b.onClick);
check("顶栏三个 TextButton 实例键互异", new Set(tbKeys).size === 3, tbKeys.join(","));

console.log("== C 45/55 精确分区 ==");
const chartN = tree.children[1];
const lowerN = tree.children[2];
const contentDp = (SCREEN_H * 160) / DENSITY_DPI - 64;   // 屏高 - 顶栏 64dp
const chartH = chartN.modifiers.find(m => m.tag === "Height")[0];
const lowerH = lowerN.modifiers.find(m => m.tag === "Height")[0];
check("图链 [FillWidth, Height(45%)]",
  mods(chartN).length === 2 && mods(chartN)[0] === "FillWidth" && chartN.modifiers.some(m => m.tag === "Height"), mods(chartN).join(","));
check("下半区链 [FillWidth, Height(55%)] 且 scrollable",
  mods(lowerN).length === 2 && mods(lowerN)[0] === "FillWidth" && lowerN.modifiers.some(m => m.tag === "Height") && lowerN.scrollable === true, mods(lowerN).join(","));
check("分区精确：chartH + lowerH == 内容区（无 5% 空隙）", Math.abs(chartH + lowerH - contentDp) < 0.01, chartH + " + " + lowerH + " vs " + contentDp);
check("图占 45%", Math.abs(chartH - contentDp * 0.45) < 0.01, chartH);

console.log("== D 图表数据结构 ==");
check("Chart kind=line color=primary legend badge=估算",
  chartN.kind === "line" && chartN.color === "primary" && chartN.legend === true && chartN.badge === "估算",
  JSON.stringify({k: chartN.kind, c: chartN.color, l: chartN.legend, b: chartN.badge}));
const pts = chartN.points.map(Number);
check("13 个采样点（0..48h 步长 4）", pts.length === 13, "n=" + pts.length);
check("刻度恰为 0/12/24/36/48（每 12h 一个）",
  eq(chartN.labels.filter(x => x !== ""), ["0", "12", "24", "36", "48"]) && chartN.labels.length === 13,
  JSON.stringify(chartN.labels));
// 一级吸收一室模型（t74 rev1）：t=0 处吸收相起点浓度=0；24h 第二剂在其吸收起点贡献 0，
// 只剩首剂残量（T½=15h, F=0.5, Tmax=5h：50·(15/13.71)·0.5^24/15 ≈ 18.05）。
check("演示主线 pt0 = 0（一级吸收：服药时刻无吸收）", pts[0] === 0, pts[0]);
check("pt24 = 首剂残量 ≈ 18.05（第二剂吸收起点贡献 0）", Math.abs(pts[6] - 18.05) < 0.05, pts[6]);
check("pt24 后进入第二剂吸收相（pt28 > pt24 且 pt28 > 首剂峰 pt8）", pts[7] > pts[6] && pts[7] > pts[2], pts[7] + " vs " + pts[6] + "/" + pts[2]);
check("series 平铺且逐药（演示=氟伏沙明金色线）",
  chartN.series.length === 1 && chartN.series[0].label === "氟伏沙明" && chartN.series[0].color === "#FFC107" && chartN.series[0].points.length === 13,
  JSON.stringify(chartN.series.map(s => [s.label, s.color, s.points.length])));
check("总线 = 逐药分量逐点求和", eq(pts, chartN.series[0].points.map(Number)));

console.log("== E 呼吸抑制预警空态 + CYP 代谢流（FlowRow 平铺） ==");
const respBox = lowerN.children[0];
check("空台账呼吸抑制预警：暂无预警占位", findByTag(respBox, "Text").some(t => t.text === "暂无预警"), JSON.stringify(findByTag(respBox, "Text").map(x => x.text)));
check("空台账无呼吸抑制标签", !findByTag(tree, "Text").some(t => typeof t.text === "string" && t.text.indexOf("呼吸抑制") === 0), JSON.stringify(findByTag(tree, "Text").map(x => String(x.text)).filter(s => s.indexOf("呼吸") >= 0)));
const metabBox = lowerN.children[1];
check("CYP 流标题", findByTag(metabBox, "Text").some(t => t.text === "CYP450 代谢相互作用"));
const flowBox = metabBox.children[1];
check("CYP 流容器 wrap=true spacing=8", flowBox.wrap === true && flowBox.spacing === 8, JSON.stringify({w: flowBox.wrap, s: flowBox.spacing}));
const tags = flowBox.children;
check("CYP 标签平铺为直接子级（6 个，非嵌套链；含 t75 新酶 UGT1A4/CYP1A2）", tags.length === 6 && tags.every(t => t.tag === "Box"), "n=" + tags.length + " " + tags.map(b => b.children[0].text).join(","));
const tagLevels = [["CYP2D6 抑制 ×27", "#33D32F2F", "#C62828"], ["CYP2C19 抑制 ×9", "#33D32F2F", "#C62828"], ["CYP2C9 抑制 ×7", "#33D32F2F", "#C62828"], ["UGT1A4 抑制 ×3", "#33F9A825", "#E65100"], ["CYP1A2 抑制 ×3", "#33F9A825", "#E65100"], ["CYP3A4 抑制 ×3", "#33F9A825", "#E65100"]];
tagLevels.forEach((exp, i) => {
  const t = tags.find(b => b.children[0].text === exp[0]) || tags[i], tx = t.children[0];
  check("CYP 标签 " + exp[0] + "：链 [Clip(12), Background(" + exp[1] + "), PaddingH(12), PaddingV(5)]",
    eq(mods(t), ["Clip(12)", "Background(" + exp[1] + ")", "PaddingH(12)", "PaddingV(5)"]), mods(t).join(","));
  check("CYP 标签 " + exp[0] + " 字 " + exp[2] + " 14sp bold", tx.text === exp[0] && tx.color === exp[2] && tx.size === 14 && tx.bold === true, JSON.stringify({t: tx.text, c: tx.color}));
  check("CYP 标签 " + exp[0] + " flash=false", tx.flash === false, String(tx.flash));
});

console.log("== F 记录卡 ==");
check("空台账 → 「暂无服药记录」占位", findByTag(tree, "Text").some(t => t.text === "暂无服药记录"));
// t77：预警区新增 profileStream 占位（空画像 → 占位 Box），下标 +1
const titleBox = lowerN.children[4];
check("记录标题链 [FillWidth, Padding4(24,16,24,8)]",
  eq(mods(titleBox), ["FillWidth", "Padding4(24,16,24,8)"]) && titleBox.children[0].text === "服药记录", mods(titleBox).join(","));
const tailBox = lowerN.children[6];
check("底部留白 Height(96)（LazyColumn contentPadding bottom=96）", eq(mods(tailBox), ["Height(96)"]), mods(tailBox).join(","));

console.log("== G FAB ==");
const fab = findByTag(tree, "FloatingButton")[0];
check("FAB icon=add + 文本 ＋服用药物 + primary", fab.icon === "add" && fab.text === "＋服用药物" && fab.color === "primary", JSON.stringify({i: fab.icon, t: fab.text, c: fab.color}));
check("FAB 有实例键 onClick", typeof fab.onClick === "string" && fab.onClick.length > 0, fab.onClick);

console.log("== H 表单交互（BottomSheet + 实例键回归） ==");
let sheet = findByTag(tree, "BottomSheet")[0];
check("表单初始 visible=false", sheet.visible === false);
callKey(fab.onClick);
tree = latestTree();
sheet = findByTag(tree, "BottomSheet")[0];
check("FAB 点击 → 表单 visible=true（BottomSheet 显隐是数据字段）", sheet.visible === true, String(sheet.visible));
const rowsBox = findByTag(sheet, "Box").find(b => b.scrollable === true && (b.modifiers || []).some(m => m.tag === "Height" && m[0] === 168));
check("药物列表高 168dp + spacing=6 + scrollable", rowsBox !== undefined && rowsBox.spacing === 6, JSON.stringify({h: !!rowsBox, s: rowsBox && rowsBox.spacing}));
const rows = rowsBox.children;
check("十药选药行平铺为直接子级", rows.length === 41 && rows.every(r => r.tag === "Box"), "n=" + rows.length);
const rowKeys = rows.map(r => r.onClick);
check("选药行实例键互异（__dshCb 每行独立槽位）", new Set(rowKeys).size === 41, rowKeys.join(","));
check("选药行整行可点 + 圆角 12 链", rows.every(r => (r.modifiers || []).some(m => m.tag === "Clip" && m[0] === 12) && typeof r.onClick === "string"));
// 串点击回归：点第 2 行只选第 2 行
callKey(rowKeys[1]);
check("点击第 2 行 → sm_pick=1", storage.get("sm_pick") === "1", storage.get("sm_pick"));
tree = latestTree();
const rows2 = findByTag(findByTag(tree, "BottomSheet")[0], "Box").find(b => b.scrollable === true && (b.modifiers || []).some(m => m.tag === "Height" && m[0] === 168)).children;
check("选中行底 primaryContainer、未选 surfaceVariant",
  rows2[1].modifiers.some(m => m.tag === "Background" && m[0] === "primaryContainer") &&
  rows2[0].modifiers.some(m => m.tag === "Background" && m[0] === "surfaceVariant"),
  mods(rows2[1]).join(","));
const check_ = findByTag(findByTag(tree, "BottomSheet")[0], "Text").filter(t => t.text === "✓");
check("选中行 ✓ 着色 primary、未选透明 #00000000",
  check_[1].color === "primary" && check_[0].color === "#00000000", check_.map(c => c.color).join(","));
// TextField 写入
const tf = findByTag(tree, "TextField")[0];
check("TextField number 键盘 + onChange 实例键", tf.keyboard === "number" && typeof tf.onChange === "string", JSON.stringify({k: tf.keyboard, c: tf.onChange}));
callKey(tf.onChange, "200");
check("onChange(200) → sm_dose=200", storage.get("sm_dose") === "200", storage.get("sm_dose"));
// 确认
const btnsRow = findByTag(findByTag(tree, "BottomSheet")[0], "Box").find(b => b.arrangement === "End");
const confirmBtn = btnsRow.children.find(c => c.tag === "Button");
check("确认/取消按钮在右端（arrangement=End）", !!confirmBtn && btnsRow.children.some(c => c.tag === "TextButton"));
callKey(confirmBtn.onClick);
tree = latestTree();
const ledger = JSON.parse(storage.get("ledger") || "[]");
check("确认 → 台账落 1 条（帕罗西汀 200mg）", ledger.length === 1 && ledger[0].drug === "帕罗西汀" && Number(ledger[0].amount) === 200, JSON.stringify(ledger));
sheet = findByTag(tree, "BottomSheet")[0];
check("确认后表单关闭（visible=false）", sheet.visible === false, String(sheet.visible));
const chart2 = findByTag(tree, "Chart")[0];
check("曲线刷新为台账序列（帕罗西汀 1 条线）",
  chart2.series.length === 1 && chart2.series[0].label === "帕罗西汀" && chart2.series[0].color === "#FFC107",
  JSON.stringify(chart2.series.map(s => s.label)));
check("分量线 pt0 = 0（一级吸收起点，帕罗西汀 F=1）", Number(chart2.series[0].points[0]) === 0, chart2.series[0].points[0]);
check("分量线进入吸收相上升（pt4 > 0）", Number(chart2.series[0].points[1]) > 0, chart2.series[0].points[1]);
check("总线 = 分量线", eq(chart2.points.map(Number), chart2.series[0].points.map(Number)));
const cards = findByTag(tree, "Card");
check("记录卡渲染 1 张（Card 组件）", cards.length === 1, "n=" + cards.length);
const card = cards[0];
check("卡链 [FillWidth, PaddingH(24), PaddingV(6)] cornerRadius=12 surfaceVariant",
  eq(mods(card), ["FillWidth", "PaddingH(24)", "PaddingV(6)"]) && card.cornerRadius === 12 && card.backgroundColor === "surfaceVariant",
  mods(card).join(",") + " r=" + card.cornerRadius);
const rowLine = card.children[0].children[0];
check("卡内行：4×20 色条（width(4).height(20) 一条链）",
  eq(mods(rowLine.children[0]), ["Width(4)", "Height(20)", "Background(#FFC107)"]), mods(rowLine.children[0]).join(","));
check("卡内行：Spacer width(10) + 药名 weight(1) + 右侧剂量",
  eq(mods(rowLine.children[1]), ["Width(10)"]) && rowLine.children[2].modifiers.some(m => m.tag === "Weight"), mods(rowLine).map(mods).join(" | "));
check("卡内文：药名 16 bold onSurface + 剂量 16 bold primary",
  rowLine.children[2].text === "帕罗西汀" && rowLine.children[3].text === "200 mg" && rowLine.children[3].color === "primary",
  JSON.stringify([rowLine.children[2].text, rowLine.children[3].text]));
const sub = card.children[0].children[2];
check("副行：给药时间 · 给药途径（12sp onSurfaceVariant）",
  /^给药时间 .* · 口服$/.test(sub.text) && sub.size === 12 && sub.color === "onSurfaceVariant", sub.text);
// 校验：未选药
callKey(findByTag(tree, "FloatingButton")[0].onClick);
tree = latestTree();
const sheet3 = findByTag(tree, "BottomSheet")[0];
const cf = findByTag(sheet3, "Box").find(b => b.arrangement === "End").children.find(c => c.tag === "Button");
callKey(cf.onClick);
check("校验①未选药 → toast 且表单不关", toasts[toasts.length - 1] === "请先选择药物" && findByTag(latestTree(), "BottomSheet")[0].visible === true, toasts[toasts.length - 1]);
// 校验：非法剂量
const rows4 = findByTag(findByTag(latestTree(), "BottomSheet")[0], "Box").find(b => b.scrollable === true).children;
callKey(rows4[0].onClick);
callKey(findByTag(latestTree(), "TextField")[0].onChange, "abc");
const sheet5 = findByTag(latestTree(), "BottomSheet")[0];
callKey(findByTag(sheet5, "Box").find(b => b.arrangement === "End").children.find(c => c.tag === "Button").onClick);
check("校验②非数字 → toast 且表单不关", toasts[toasts.length - 1] === "剂量必须为数字" && findByTag(latestTree(), "BottomSheet")[0].visible === true, toasts[toasts.length - 1]);

console.log("== I 设置（个人画像）/文档弹层 ==");
tree = latestTree();
const dismissSheet = findByTag(tree, "BottomSheet")[0];
callKey(findByTag(dismissSheet, "Box").find(b => b.arrangement === "End").children.find(c => c.tag === "TextButton").onClick);
tree = latestTree();
check("取消/遮罩关闭表单 → 无 AlertDialog 残留（设置页 t77 改为 BottomSheet 分组页）", findByTag(tree, "AlertDialog").length === 0, "n=" + findByTag(tree, "AlertDialog").length);
// 设置（t77：CandyIDE 式个人画像分组页）
const topTree = latestTree();
const setBtn = findByTag(topTree, "TextButton").find(b => (b.children || []).some(c => c.tag === "Icon" && c.name === "settings"));
callKey(setBtn.onClick);
tree = latestTree();
const setSheet = findByTag(tree, "BottomSheet").find(s => s.visible === true);
check("设置页 visible=true（BottomSheet）", setSheet && setSheet.visible === true, String(setSheet && setSheet.visible));
const stexts = setSheet ? findByTag(setSheet, "Text").map(x => x.text) : [];
check("设置页 CandyIDE 分组（个人信息/先天疾病/已有疾病/关于）",
  ["个人信息", "先天疾病", "已有疾病", "关于"].every(t => stexts.indexOf(t) >= 0), JSON.stringify(stexts.filter(t => ["个人信息", "先天疾病", "已有疾病", "关于"].indexOf(t) >= 0)));
const setChips = setSheet ? findByTag(setSheet, "Box").filter(b => b.onClick !== undefined && b.onClick !== null && (b.children || []).some(c => c.tag === "Text" && (String(c.text).indexOf("✓ ") === 0 || String(c.text).indexOf("○ ") === 0))) : [];
check("疾病多选 chip 12 个（先天 1 + 已有 11）", setChips.length === 12, "n=" + setChips.length + " " + setChips.map(b => b.children[0].text).join(","));
const sexChips = setSheet ? findByTag(setSheet, "Box").filter(b => b.onClick !== undefined && b.onClick !== null && (b.children || []).some(c => c.tag === "Text" && ["男", "女", "其他"].indexOf(c.text) >= 0)) : [];
check("性别单选 chip 3 个", sexChips.length === 3, "n=" + sexChips.length);
const ageField = setSheet ? findByTag(setSheet, "TextField")[0] : null;
check("年龄数字输入框（label 年龄，keyboard number）", ageField && ageField.keyboard === "number" && String(ageField.label).indexOf("年龄") >= 0, JSON.stringify(ageField && { k: ageField.keyboard, l: ageField.label }));
check("设置页可滚动（scrollable 分组区）", setSheet ? findByTag(setSheet, "Box").some(b => b.scrollable === true) : false);
// chip 持久化：点「青光眼」→ sm_conds 含青光眼 + chip 变 ✓ 选中态
const glauChip = setChips.find(b => b.children[0].text === "○ 青光眼");
callKey(glauChip.onClick);
tree = latestTree();
const setSheet2 = findByTag(tree, "BottomSheet").find(s => s.visible === true);
check("画像持久化：点 chip → sm_conds=青光眼", storage.get("sm_conds") === "青光眼", storage.get("sm_conds"));
const chip2 = findByTag(setSheet2, "Box").filter(b => b.onClick !== undefined && b.onClick !== null && (b.children || []).some(c => c.tag === "Text" && (String(c.text).indexOf("✓ 青光眼") === 0 || String(c.text).indexOf("○ 青光眼") === 0)))[0];
check("chip 选中态切换（✓ + primaryContainer）", chip2 && chip2.children[0].text === "✓ 青光眼" && (chip2.modifiers || []).some(m => m.tag === "Background" && m["0"] === "primaryContainer"), chip2 && chip2.children[0].text);
// 清空记录（原设置弹层的确认动作，改由设置页内 TextButton 承载）
const clearBtn = findByTag(setSheet2, "TextButton").find(b => b.label === "清空记录" || (b.children || []).some(c => c.text === "清空记录"));
callKey(clearBtn.onClick);
tree = latestTree();
check("清空记录 → 台账空 + 设置页关", JSON.parse(storage.get("ledger")).length === 0 && !findByTag(tree, "BottomSheet").some(s => s.visible === true));
check("台账空后回到演示序列（氟伏沙明）", findByTag(tree, "Chart")[0].series[0].label === "氟伏沙明");
// 文档 → 药库名单抽屉（顶栏 info icon）→ 药物资料页
const docBtn = findByTag(tree, "TextButton").find(b => (b.children || []).some(c => c.tag === "Icon" && c.name === "info"));
callKey(docBtn.onClick);
tree = latestTree();
const dlist = findByTag(tree, "BottomSheet").find(s => s.visible === true);
check("药库名单抽屉打开", dlist && findByTag(dlist, "Text").some(x => x.text === "药库名单"), JSON.stringify(dlist && dlist.visible));
const drows = findByTag(dlist, "Box").filter(b => b.onClick && (b.children || []).length === 3 && (b.children[0].modifiers || []).some(m => m.tag === "Width" && m["0"] === 4));
check("药库名单 41 行", drows.length === 41, "n=" + drows.length + " " + drows.map(b => b.children[2].children[0].text).join(","));
const morphRow = drows.find(b => b.children[2].children[0].text === "吗啡");
callKey(morphRow.onClick);
tree = latestTree();
const docSheet = findByTag(tree, "BottomSheet").find(s => s.visible === true);
check("资料页打开（吗啡）", docSheet && findByTag(docSheet, "Text").some(x => x.text === "吗啡"));
const dtexts = findByTag(docSheet, "Text").map(x => x.text);
check("资料页含描述+分段+PK(F 标记)+相互作用链",
  dtexts.some(s => String(s).indexOf("天然阿片生物碱") >= 0)
  && dtexts.some(s => String(s).indexOf("T½ 2h") >= 0 && String(s).indexOf("F=34%") >= 0)
  && dtexts.some(s => String(s).indexOf("UGT2B7 链") === 0)
  && dtexts.some(s => String(s).indexOf(">60mg") >= 0)
  && dtexts.some(s => String(s).indexOf("药效") === 0), dtexts.slice(-8).join(","));
check("资料页过量行红色闪烁", findByTag(docSheet, "Text").some(x => x.flash === true && String(x.text).indexOf(">60mg") >= 0));
callKey(docSheet.onDismiss);
tree = latestTree();
check("资料页关闭", !findByTag(tree, "BottomSheet").some(s => s.visible === true));

// t77 端到端：画像（青光眼）+ 台账（苯海索）→ 首页效应词条流红条（不另起 UI）
storage.set("sm_conds", "青光眼");
callKey(findByTag(tree, "FloatingButton")[0].onClick);
tree = latestTree();
const sheet77 = findByTag(tree, "BottomSheet")[0];
const rows77 = findByTag(sheet77, "Box").find(b => b.scrollable === true).children;
const hxRow = rows77.find(b => flatten(b, []).some(n => n.text === "苯海索"));
check("选药行含苯海索（t75 药库）", !!hxRow, "rows=" + rows77.length);
callKey(hxRow.onClick);
callKey(findByTag(latestTree(), "TextField")[0].onChange, "2");
const sh77 = findByTag(latestTree(), "BottomSheet")[0];
callKey(findByTag(sh77, "Box").find(b => b.arrangement === "End").children.find(c => c.tag === "Button").onClick);
tree = latestTree();
const ptexts = findByTag(tree, "Text").map(t => t.text);
check("画像加权词条进效应词条流（青光眼×苯海索 红条）",
  ptexts.some(s => s.indexOf("青光眼×苯海索：青光眼发作风险") >= 0), ptexts.filter(s => s.indexOf("青光眼") >= 0).join(","));
const pcell = findByTag(tree, "Box").find(b => (b.children[0] || {}).text && String((b.children[0] || {}).text).indexOf("青光眼×苯海索") >= 0);
check("加权词条红底 flash（沿用 tagBg/tagFg 三级配色）",
  pcell && (pcell.modifiers || []).some(m => m.tag === "Background" && m["0"] === "#40D32F2F") && (pcell.children[0] || {}).flash === true && (pcell.children[0] || {}).color === "#D32F2F",
  pcell && JSON.stringify({ bg: (pcell.modifiers || []).find(m => m.tag === "Background") && (pcell.modifiers || []).find(m => m.tag === "Background")["0"], fg: pcell.children[0] && pcell.children[0].color, flash: pcell.children[0] && pcell.children[0].flash }));
// 清理画像以便 v2 幂等
storage.set("sm_conds", "");

console.log("\n" + "PASSES=" + pass + " FAILS=" + fail);
process.exit(fail === 0 ? 0 : 1);
