  // ===== 桌面驱动：触发生命周期 =====
  main();
  globalThis.__dshLifecycle_onCreate(null);
  globalThis.__dshLifecycle_onStart(null);

  const __fs = require("fs");
  const __lines = [];
  let __smPass = 0, __smFail = 0;
  function ddrug(n) { return coadmin().find((d) => d.name === n); }
function __smA(name, cond, extra) {
    if (cond) { __smPass++; } else { __smFail++; __lines.push("FAIL " + name + (extra ? " :: " + extra : "")); }
  }
  function __smWalk(node, out) { out.push(node); if (Array.isArray(node.children)) node.children.forEach((c) => { if (c !== null && c !== undefined) __smWalk(c, out); }); return out; }
  function __smNodes(root) { return __smWalk(root, []); }
  function __smRoot(i) { return JSON.parse(__smLayouts[i === undefined ? __smLayouts.length - 1 : i]); }
  function __smBtnTexts(root) {
    return __smNodes(root).filter((n) => (n.tag === "Button" || n.tag === "TextButton") && Array.isArray(n.children))
      .map((n) => (n.label || (n.children[0] && n.children[0].text) || ""));
  }
  function __smHasText(root, s) { return __smNodes(root).some((n) => n.tag === "Text" && n.text === s); }
  function __smCall(node, field, arg) {
    const id = node && node[field];
    if (!id) return false;
    const fn = globalThis["__dshFn_" + id];
    if (typeof fn !== "function") return false;
    fn(arg === undefined ? null : arg);
    return true;
  }
  function __smFindButton(root, part) {
    return __smNodes(root).filter((n) => (n.tag === "Button" || n.tag === "TextButton") && Array.isArray(n.children))
      .find((n) => { const t = (n.label || (n.children[0] && String(n.children[0].text) || "")); return t && t.indexOf(part) >= 0; });
  }

  // ---------- 首屏（空台账） ----------
  __smA("首次 layout", __smLayouts.length === 1);
  const root0 = __smRoot(0);
  __smA("根 9 区（表单/设置/药库名单/资料页/效应包四+一弹层存根）", root0.children.length === 9, JSON.stringify(root0.children.map((c) => c.tag)));
  const topNode = root0.children[0];
  __smA("顶栏 Box", topNode.tag === "Box", "got " + topNode.tag);
  __smA("顶栏横向", topNode.orientation === "horizontal");
  __smA("顶栏 primary 底", (topNode.modifiers || []).some((m) => m.tag === "Background" && m["0"] === "primary"), "got " + JSON.stringify((topNode.modifiers || []).map((m) => m.tag)));
  __smA("顶栏标题", __smHasText(root0, "语法糖手册"));
  __smA("顶栏副标题 V1.0", __smNodes(root0).some((n) => n.tag === "Text" && n.text.indexOf("V1.0") >= 0));
  __smA("顶栏三按钮", (function () { const tb = __smNodes(topNode).filter((n) => n.tag === "TextButton").map((b) => (b.children[0] || {}).name); return tb.length === 3 && ["info", "menu", "settings"].every((t) => tb.includes(t)); })(), "got " + JSON.stringify(__smNodes(topNode).filter((n) => n.tag === "TextButton").map((b) => (b.children[0] || {}).name)));
  const chartNode = __smNodes(root0).find((n) => n.tag === "Chart");
  __smA("Chart 存在", !!chartNode);
  __smA("Chart line", chartNode.kind === "line");
  __smA("Chart 主色 primary", chartNode.color === "primary", "got " + chartNode.color);
  __smA("Chart 撑满宽", (chartNode.modifiers || []).some((m) => m.tag === "FillWidth"), "got " + JSON.stringify((chartNode.modifiers || []).map((m) => m.tag)));
  __smA("Chart 13 点", chartNode.points.length === 13, "got " + chartNode.points.length);
  const texts0 = __smNodes(root0).filter((n) => n.tag === "Text").map((n) => n.text);
  __smA("预警无区标题", !texts0.includes("相互作用预警"));
  __smA("预警无组标题", !texts0.some((s) => String(s).indexOf("危险") === 0 || String(s).indexOf("警告") === 0));
  __smA("标签名不带权重", !texts0.some((s) => /^\S+ \d+/.test(String(s))), "got " + JSON.stringify(texts0.slice(0, 12)));
  // CYP 代谢预警仍走 tagBg 三级色（联用不良反应流已下线）
  const tagBoxes = __smNodes(root0).filter((n) => n.tag === "Box" && (n.modifiers || []).some((m) => m.tag === "Background" && String(m["0"]).indexOf("D32F2F") >= 0));
  __smA("三级标签底色存在", tagBoxes.length >= 2, "got " + tagBoxes.map((b) => (b.modifiers || []).filter((m) => m.tag === "Background").map((m) => m["0"])).join(","));
  const dangerTag = tagBoxes.find((b) => (b.modifiers || []).some((m) => m.tag === "Background" && m["0"] === "#33D32F2F"));
  __smA("危险级 CYP 标签", dangerTag && (dangerTag.children[0] || {}).text.indexOf("CYP") === 0 && (dangerTag.children[0] || {}).color === "#C62828", "got " + JSON.stringify(dangerTag && dangerTag.children.map((c) => c.text)));
  __smA("标签横向流", __smNodes(root0).some((n) => n.wrap === true && n.tag === "Box"));
  // 空台账无呼吸抑制预警（单药未服，引擎闭置）
  __smA("空台账暂无预警", texts0.includes("暂无预警"));
  __smA("空台账无呼吸抑制标签", !texts0.some((s) => typeof s === "string" && s.indexOf("呼吸抑制") === 0), "got " + JSON.stringify(texts0.filter((s) => String(s).indexOf("呼吸") >= 0)));
  // CYP450 代谢相互作用预警链（SSRI 极强 CYP2D6/2C19 抑制 + 可待因/曲马多/羟考酮 2D6 底物）
  const cypTexts = __smNodes(root0).filter((n) => n.tag === "Text" && typeof n.text === "string" && n.text.indexOf("CYP") === 0).map((n) => n.text);
  __smA("CYP 预警标题", texts0.includes("CYP450 代谢相互作用"));
  __smA("CYP2D6 抑制标签", cypTexts.some((s) => s.indexOf("CYP2D6 抑制") === 0), "got " + JSON.stringify(cypTexts));
  __smA("CYP2C19 抑制标签", cypTexts.some((s) => s.indexOf("CYP2C19 抑制") === 0), "got " + JSON.stringify(cypTexts));
  // 呼吸抑制红色闪烁仅在两类同服时出现（空台账无 #40 flash）
  const flashCells0 = __smNodes(root0).filter((n) => n.tag === "Box" && (n.modifiers || []).some((m) => m.tag === "Background" && String(m["0"]).indexOf("#40") === 0));
  __smA("空台账无红色闪烁", flashCells0.length === 0, "got " + JSON.stringify(flashCells0.map((b) => (b.children[0] || {}).text)));
  __smA("记录区标题", texts0.includes("服药记录"));
  __smA("空台账占位", texts0.includes("暂无服药记录"));
  __smA("底部 96dp 留白", __smNodes(root0).some((n) => n.tag === "Box" && (n.modifiers || []).some((m) => m.tag === "Height" && m["0"] === 96)));
  const fab0 = __smNodes(root0).find((n) => n.tag === "FloatingButton");
  __smA("FAB 存在", !!fab0);
  __smA("FAB 文案", fab0.text === "＋服用药物", "got " + fab0.text);
  const drugPageBtn = __smNodes(root0).filter((n) => n.tag === "TextButton").find((b) => (b.children || []).some((c) => c.tag === "Icon" && c.name === "menu"));
  __smA("药物按钮存在", !!drugPageBtn);
  __smCall(drugPageBtn, "onClick");
  // menu/info 按钮打开药库名单抽屉（替代原无多页导航 toast）
  const listRoot = __smRoot();
  const dlistSheet = __smNodes(listRoot).find((n) => n.tag === "BottomSheet" && n.visible === true);
  __smA("药库名单打开", !!dlistSheet && __smHasText(listRoot, "药库名单"));
  const listRows = __smNodes(dlistSheet).filter((n) => n.tag === "Box" && typeof n.onClick === "string" && (n.children || []).length === 3 && (n.children[0].modifiers || []).some((m) => m.tag === "Width" && m["0"] === 4));
  __smA("药库名单 68 行（t78 扩展 + t79rev2 逆转剂，含物质/代谢物）", listRows.length === 68, "got " + listRows.length + " " + listRows.map((b) => b.children[2].children[0].text).join(","));
  // t78：分组浏览 —— 主要分类大组头齐（心境稳定剂/抗精神病药/抗抑郁药/抗焦虑药/安眠剂/镇静剂/促智药/抗帕金森药/镇痛药/精神活性物质/抗生素/代谢物）
  const groupHeads = __smNodes(dlistSheet).filter((n) => n.tag === "Text" && (n.size === 15) && n.bold === true && n.color === "primary").map((n) => n.text);
  __smA("药库名单按主要分类分组（12 组头齐且仅含非空组）",
    ["心境稳定剂", "抗精神病药", "促智药（抗痴呆）", "抗抑郁药", "抗焦虑药", "抗多动药（ADHD）", "安眠剂", "抗帕金森药", "镇痛药", "逆转剂", "精神活性物质", "抗生素", "激素类（避孕药）", "代谢物（仅资料）"].every((t) => groupHeads.indexOf(t) >= 0) && groupHeads.length === 14 && groupHeads.indexOf("镇静剂") < 0,
    JSON.stringify(groupHeads));
  // 点吗啡行开资料页并核验内容
  const morphRow = listRows.find((b) => b.children[2].children[0].text === "吗啡");
  __smCall(morphRow, "onClick");
  const docRoot = __smRoot();
  const docTexts = __smNodes(docRoot).map((n) => n.text).filter(Boolean);
  __smA("资料页打开·吗啡", __smHasText(docRoot, "吗啡") && docTexts.some((s) => String(s).indexOf("M6G") >= 0), docTexts.slice(-8).join(","));
  __smA("资料页药动学", docTexts.some((s) => String(s).indexOf("T½ 2h") >= 0 && String(s).indexOf("F=34%") >= 0), docTexts.slice(-6).join(","));
  __smA("资料页相互作用链", docTexts.some((s) => String(s).indexOf("UGT2B7 链") === 0), docTexts.slice(-4).join(","));
  __smA("资料页过量闪烁", __smNodes(docRoot).some((n) => n.tag === "Text" && n.flash === true && String(n.text).indexOf(">60mg") >= 0));
  // 关闭资料页后继续表单流
  const docSheet = __smNodes(docRoot).find((n) => n.tag === "BottomSheet" && n.visible === true);
  __smCall(docSheet, "onDismiss");
  __smA("资料页已关", !__smNodes(__smRoot()).some((n) => n.tag === "BottomSheet" && n.visible === true));

  // ---------- FAB 开表单 ----------
  const before = __smLayouts.length;
  __smA("FAB 回调执行", __smCall(fab0, "onClick"));
  const root1 = __smRoot();
  __smA("FAB 后新 layout", __smLayouts.length === before + 1, "got " + __smLayouts.length + " vs " + (before + 1));
  __smA("表单标题 ＋服用药物", __smHasText(root1, "＋服用药物"));
  __smA("表单副标题", __smHasText(root1, "选择药物并填写剂量（mg）；代谢物条目仅资料页，不入选"));
  __smA("表单药物标签", __smHasText(root1, "药物"));
  __smA("空台账提示消失", !__smHasText(root1, "尚无服药记录"));
  const listHolder = __smNodes(root1).find((n) => n.tag === "Box" && n.scrollable === true && (n.modifiers || []).some((m) => m.tag === "Height" && m["0"] === 168));
  __smA("药物列表定高 168", !!listHolder, "missing Height(168) holder");
  __smA("药物列表可滚", listHolder && listHolder.scrollable === true);
  const rowBtns = __smNodes(root1).filter((n) => n.tag === "Box" && typeof n.onClick === "string" && (n.children || [])[0] && n.children[0].tag === "Box" && (n.children[0].children || [])[0] && n.children[0].children[0].tag === "Text" && n.children[0].children[1] && n.children[0].children[1].tag === "Text");
  function __smRowName(b) { return b.children[0].children[0]; }
  function __smRowSub(b) { return b.children[0].children[1]; }
  function __smRowChecked(b) { return (b.children || []).some((c) => c.tag === "Text" && c.text.indexOf("✓") === 0 && c.color !== "#00000000"); } // v2：✓ 常驻，未选为透明 #00000000
  __smA("药物行 64 行（t79rev2：68 库 - 4 代谢物，表单不可选）", rowBtns.length === 64, "got " + rowBtns.length + " " + rowBtns.map((b) => __smRowName(b).text).join(","));
  __smA("表单选药列表过滤代谢物（去甲氟西汀/O-去甲基文拉法辛/去甲西酞普兰/M6G 不可见）",
    ["去甲氟西汀", "O-去甲基文拉法辛", "去甲西酞普兰", "M6G（吗啡-6-葡萄糖醛酸）"].every((n) => !rowBtns.some((b) => __smRowName(b).text.indexOf(n) === 0)));
  __smA("行 0 氟伏沙明未选", rowBtns.some((b) => __smRowName(b).text === "氟伏沙明" && __smRowName(b).bold === false), "got " + JSON.stringify(rowBtns.map((b) => __smRowName(b).text)));
  __smA("行 1 帕罗西汀", rowBtns.some((b) => __smRowName(b).text === "帕罗西汀"));
  __smA("行副标题：氟伏沙明", rowBtns.some((b) => __smRowSub(b).text === "处方药 · T½ 15h · F=50% · 口服"), "got " + JSON.stringify(rowBtns.map((b) => __smRowSub(b).text)));
  __smA("行副标题：帕罗西汀", rowBtns.some((b) => __smRowSub(b).text === "处方药 · T½ 24h · F=100% · 口服"));
  __smA("默认未选药", rowBtns.every((b) => __smRowName(b).bold === false && !__smRowChecked(b)));
  const tf = __smNodes(root1).find((n) => n.tag === "TextField" && n.placeholder === "剂量 (mg)");
  __smA("TextField 存在", !!tf);
  __smA("TextField 占位符", tf.placeholder === "剂量 (mg)", "got " + tf.placeholder);
  __smA("TextField 数字键盘", tf.keyboard === "number");
  __smA("TextField 草稿空", tf.text === "");
  __smA("确认/取消按钮", __smBtnTexts(root1).includes("确认") && __smBtnTexts(root1).includes("取消"), "got " + JSON.stringify(__smBtnTexts(root1)));

  // ---------- 四级校验 ----------
  const cBtn = __smFindButton(root1, "确认");
  const lenA = __smLayouts.length;
  __smCall(cBtn, "onClick");
  __smA("未选药不刷新", __smLayouts.length === lenA);
  __smA("未选药 toast", __smLastToast === "请先选择药物", "got " + __smLastToast);
  __smA("表单保持开", __smHasText(__smRoot(), "＋服用药物"));

  const rowBtnsW = __smNodes(__smRoot()).filter((n) => n.tag === "Box" && typeof n.onClick === "string" && (n.children || [])[0] && n.children[0].tag === "Box" && (n.children[0].children || [])[0] && n.children[0].children[0].tag === "Text" && (n.children[0].children || [])[1] && n.children[0].children[1].tag === "Text");
  const row1Btn = rowBtnsW.find((b) => b.children[0].children[0].text === "帕罗西汀");
  __smA("选药回调执行", __smCall(row1Btn, "onClick"));
  const root2 = __smRoot();
  const rowBtns2 = __smNodes(root2).filter((n) => n.tag === "Box" && typeof n.onClick === "string" && (n.children || [])[0] && n.children[0].tag === "Box" && (n.children[0].children || [])[0] && n.children[0].children[0].tag === "Text" && (n.children[0].children || [])[1] && n.children[0].children[1].tag === "Text");
  const selBtn = rowBtns2.find((b) => b.children[0].children[0].text === "帕罗西汀");
  __smA("选中 ✓ 前缀", __smRowChecked(selBtn) && selBtn.children.find((c) => c.tag === "Text").text.indexOf("✓") === 0, "got " + JSON.stringify((selBtn.children || []).filter((c) => c.tag === "Text").map((c) => c.text)));
  __smA("选中加粗", selBtn.children[0].children[0].bold === true);
  const unselBtn = rowBtns2.find((b) => b.children[0].children[0].text === "氟伏沙明");
  __smA("未选无 ✓", !__smRowChecked(unselBtn) && unselBtn.children[0].children[0].bold === false);

  __smCall(cBtn, "onClick");
  __smA("空剂量 toast", __smLastToast === "剂量必须为数字", "got " + __smLastToast);
  __smA("空剂量不刷新", __smLayouts.length === lenA + 1, "got " + __smLayouts.length + " vs " + (lenA + 1));
  const tf2 = __smNodes(__smRoot()).find((n) => n.tag === "TextField" && n.placeholder === "剂量 (mg)");
  __smCall(tf2, "onChange", "0");
  __smCall(cBtn, "onClick");
  __smA("0 剂量 toast", __smLastToast === "剂量必须大于 0", "got " + __smLastToast);
  __smCall(tf2, "onChange", "20000");
  __smCall(cBtn, "onClick");
  __smA("超上限 toast", __smLastToast === "单次剂量不得超过 10000 mg", "got " + __smLastToast);

  // ---------- 确认提交 ----------
  __smCall(tf2, "onChange", "2");
  __smA("草稿入 storage", __smStore["sm_dose"] === "2");
  const tBefore = Date.now();
  __smCall(cBtn, "onClick");
  const root3 = __smRoot();
  __smA("确认后刷新", __smLayouts.length === lenA + 2, "got " + __smLayouts.length + " vs " + (lenA + 2));
  __smA("表单已关", !__smNodes(root3).some((n) => n.tag === "BottomSheet" && n.visible === true));
  __smA("FAB 复原", __smNodes(root3).find((n) => n.tag === "FloatingButton").text === "＋服用药物");
  const ledger = JSON.parse(__smStore["ledger"]);
  __smA("台账 1 条", ledger.length === 1, "got " + __smStore["ledger"]);
  __smA("台账药名", ledger[0].drug === "帕罗西汀");
  __smA("台账剂量 2", ledger[0].amount === 2, "got " + ledger[0].amount);
  __smA("台账时间合理", Math.abs(ledger[0].time - tBefore) < 60000);
  const cardBg = __smNodes(root3).find((n) => n.tag === "Card");
  __smA("卡片 Card 存在", !!cardBg);
  __smA("卡圆角 12", cardBg.cornerRadius === 12, "got " + cardBg.cornerRadius);
  __smA("卡链 FillWidth", (cardBg.modifiers || []).some((m) => m.tag === "FillWidth"), "got " + JSON.stringify((cardBg.modifiers || []).map((m) => m.tag)));
  const barBox = __smNodes(cardBg).find((n) => n.tag === "Box" && (n.modifiers || []).some((m) => m.tag === "Background" && m["0"] === "#FFC107"));
  __smA("药名竖条金色", !!barBox, "no #FFC107 bar");
  __smA("竖条宽 4 高 20", (barBox.modifiers || []).some((m) => m.tag === "Width" && m["0"] === 4) && (barBox.modifiers || []).some((m) => m.tag === "Height" && m["0"] === 20), "got " + JSON.stringify(barBox.modifiers));
  const cardTexts = __smNodes(cardBg).filter((n) => n.tag === "Text").map((n) => n.text);
  __smA("卡药名", cardTexts.includes("帕罗西汀"), "got " + JSON.stringify(cardTexts));
  __smA("卡剂量", cardTexts.includes("2 mg"));
  __smA("卡给药时间/方式", cardTexts.some((s) => String(s).indexOf("给药时间 ") === 0 && String(s).indexOf(" · 口服") >= 0), "got " + JSON.stringify(cardTexts));
  __smA("记录区标题仍在", __smHasText(root3, "服药记录"));
  const chart3 = __smNodes(root3).find((n) => n.tag === "Chart");
  __smA("曲线 13 点", chart3.points.length === 13);
  // 一级吸收模型下末点 ≈ 解析消除值（吸收相残余项随 T_a 快速归零，偏差 <1%）。
  __smA("曲线末点≈2×F×0.5^(48÷T½)（一级吸收尾段）", Math.abs(chart3.points[12] - 0.5) < 0.01, "got " + chart3.points[12]);

  // ---------- 取消 ----------
  const fab3 = __smNodes(root3).find((n) => n.tag === "FloatingButton");
  __smCall(fab3, "onClick");
  const root4 = __smRoot();
  __smA("再开表单", __smHasText(root4, "选择药物并填写剂量（mg）；代谢物条目仅资料页，不入选"));
  __smA("再开默认未选药", __smNodes(root4).filter((n) => n.tag === "Box" && typeof n.onClick === "string" && (n.children || [])[0] && n.children[0].tag === "Box" && (n.children[0].children || [])[0] && n.children[0].children[0].tag === "Text" && (n.children[0].children || [])[1] && n.children[0].children[1].tag === "Text").every((b) => b.children[0].children[0].bold === false && !__smRowChecked(b)));
  const cancelBtn = __smFindButton(root4, "取消");
  __smCall(cancelBtn, "onClick");
  const root5 = __smRoot();
  __smA("取消后表单关", !__smNodes(root5).some((n) => n.tag === "BottomSheet" && n.visible === true));
  __smA("取消台账不变", JSON.parse(__smStore["ledger"]).length === 1);
  __smA("取消草稿复位", __smStore["sm_dose"] === "");
  __smA("取消未选复位", __smStore["sm_pick"] === "");

  // ---------- 呼吸抑制风险叠加（单类不报 / 双类红色危险） ----------
  const fab4 = __smNodes(__smRoot()).find((n) => n.tag === "FloatingButton");
  __smCall(fab4, "onClick");
  const rowsY = __smNodes(__smRoot()).filter((n) => n.tag === "Box" && typeof n.onClick === "string" && (n.children || [])[0] && n.children[0].tag === "Box" && (n.children[0].children || [])[0] && n.children[0].children[0].tag === "Text" && (n.children[0].children || [])[1] && n.children[0].children[1].tag === "Text");
  __smCall(rowsY.find((b) => b.children[0].children[0].text === "吗啡"), "onClick");
  const tfY = __smNodes(__smRoot()).find((n) => n.tag === "TextField" && n.placeholder === "剂量 (mg)");
  __smCall(tfY, "onChange", "10");
  __smCall(__smFindButton(__smRoot(), "确认"), "onClick");
  const rootM = __smRoot();
  const respTagM = __smNodes(rootM).filter((n) => n.tag === "Text" && typeof n.text === "string" && n.text.indexOf("呼吸抑制") === 0 && n.text.indexOf("权重×") > 0);
  __smA("单类吗啡=警告不红", respTagM.length === 1 && respTagM[0].text.indexOf("剂量依赖") >= 0 && respTagM[0].color === "#E65100" && respTagM[0].flash !== true, "got " + JSON.stringify(respTagM.map((n) => [n.text, n.color])));
  __smA("单类无红闪(flash)", !__smNodes(rootM).some((n) => n.tag === "Text" && n.flash === true && typeof n.text === "string" && n.text.indexOf("权重×") > 0));
  const fab5 = __smNodes(__smRoot()).find((n) => n.tag === "FloatingButton");
  __smCall(fab5, "onClick");
  const rowsZ = __smNodes(__smRoot()).filter((n) => n.tag === "Box" && typeof n.onClick === "string" && (n.children || [])[0] && n.children[0].tag === "Box" && (n.children[0].children || [])[0] && n.children[0].children[0].tag === "Text" && (n.children[0].children || [])[1] && n.children[0].children[1].tag === "Text");
  __smCall(rowsZ.find((b) => b.children[0].children[0].text === "地西泮"), "onClick");
  const tfZ = __smNodes(__smRoot()).find((n) => n.tag === "TextField" && n.placeholder === "剂量 (mg)");
  __smCall(tfZ, "onChange", "5");
  __smCall(__smFindButton(__smRoot(), "确认"), "onClick");
  const rootD = __smRoot();
  const respTagD = __smNodes(rootD).filter((n) => n.tag === "Text" && typeof n.text === "string" && n.text.indexOf("呼吸抑制") === 0 && n.text.indexOf("权重×") > 0);
  __smA("双类叠加红色危险", respTagD.length === 1 && respTagD[0].text.indexOf("联用叠加") >= 0 && respTagD[0].text.indexOf("权重×9") >= 0 && respTagD[0].color === "#D32F2F" && respTagD[0].flash === true, "got " + JSON.stringify(respTagD.map((n) => [n.text, n.color, n.flash])));
  const flashBoxD = __smNodes(rootD).find((n) => n.tag === "Box" && (n.modifiers || []).some((m) => m.tag === "Background" && m["0"] === "#40D32F2F"));
  __smA("红底 #40 通路", !!flashBoxD);
  __smA("呼吸抑制后台账 3 条", JSON.parse(__smStore["ledger"]).length === 3, "got " + __smStore["ledger"]);

  // ---------- 纯函数层 ----------
  __smA("tagBg 三级", tagBg(3, false) === "#33D32F2F" && tagBg(2, false) === "#33F9A825" && tagBg(1, false) === "#334CAF50", JSON.stringify([tagBg(3, false), tagBg(2, false), tagBg(1, false)]));
  __smA("tagFg 三级", tagFg(3, false) === "#C62828" && tagFg(2, false) === "#E65100" && tagFg(1, false) === "#2E7D32", JSON.stringify([tagFg(3, false), tagFg(2, false), tagFg(1, false)]));
  __smA("drugColor SSRI 金", drugColor("氟伏沙明") === "#FFC107" && drugColor("帕罗西汀") === "#FFC107" && drugColor("舍曲林") === "#FFC107");
  __smA("drugColor 回退绿", drugColor("未知药") === "#00695C");
  __smA("drugColor BZD 蓝", drugColor("地西泮") === "#1976D2" && drugColor("阿普唑仑") === "#1976D2");
  __smA("drugDetail", drugDetail(fluvoxamine()) === "处方药 · T½ 15h · F=50% · 口服", "got " + drugDetail(fluvoxamine()));
  __smA("drugDetail 舍曲林", drugDetail(sertraline()) === "处方药 · T½ 26h · F=44% · 口服", "got " + drugDetail(sertraline()));
  __smA("drugDetail 地西泮", drugDetail(diazepam()) === "处方药 · T½ 36h · F≈90% · 口服", "got " + drugDetail(diazepam()));
  __smA("drugDetail 氟西汀 F≈", drugDetail(fluoxetine()) === "处方药 · T½ 96h · F≈60% · 口服", "got " + drugDetail(fluoxetine()));
  __smA("drugDetail 吗啡", drugDetail(morphine()) === "处方药 · T½ 2h · F=34% · 口服", "got " + drugDetail(morphine()));
  __smA("drugDetail 咪达唑仑", drugDetail(midazolam()) === "处方药 · T½ 3h · F=44% · 口服", "got " + drugDetail(midazolam()));
  __smA("rowBold", rowBold(1, 1, true) === true && rowBold(1, 1, false) === false && rowBold(0, 1, true) === false);
  __smA("respWeight 阿片>BZD", respWeightOf(morphine()) === 5 && respWeightOf(diazepam()) === 4 && respWeightOf(morphine()) > respWeightOf(diazepam()), "got " + [respWeightOf(morphine()), respWeightOf(diazepam())]);
  __smA("respWeight 可待因", respWeightOf(codeine()) === 4);
  __smA("respAlert 单吗啡警告", (function () { const a = respAlert(core_collection__cons(morphine(), core_collection__emptyList())); return a.level === 2 && a.items.length === 1 && a.items[0].name === "呼吸抑制（剂量依赖）" && a.items[0].score === 5; })());
  __smA("respAlert 双类红危险", (function () { const a = respAlert(core_collection__cons(morphine(), core_collection__cons(diazepam(), core_collection__emptyList()))); return a.level === 3 && a.items[0].name === "呼吸抑制（联用叠加）" && a.items[0].score === 9; })());
  __smA("respAlert 双BZD不报红", (function () { const a = respAlert(core_collection__cons(diazepam(), core_collection__cons(estazolam(), core_collection__emptyList()))); return a.level === 1 && a.items.length === 0; })());
  __smA("respAlert 空集无预警", (function () { const a = respAlert(core_collection__emptyList()); return a.level === 0 && a.items.length === 0; })());
  // 白 → 黄 → 红 连续渐变色表（warn.subl respColor；宿主与 home.subl 九槽同表）
  __smA("respColor 浅端（1-2）白系", respColor(1) === "#F5FFFDE7" && respColor(2) === "#F5FFF8B8", JSON.stringify([respColor(1), respColor(2)]));
  __smA("respColor 黄（3-4）", respColor(3) === "#F5FFF388" && respColor(4) === "#F5FFEE58", JSON.stringify([respColor(3), respColor(4)]));
  __smA("respColor 橙（5-6）", respColor(5) === "#F5FFCB3F" && respColor(6) === "#F5FFA726", JSON.stringify([respColor(5), respColor(6)]));
  __smA("respColor 红（7-9）", respColor(7) === "#F5F7832B" && respColor(8) === "#F5EE5E30" && respColor(9) === "#F5E53935", JSON.stringify([respColor(7), respColor(8), respColor(9)]));
  __smA("respColor 九档互异单调（非两档硬切）", (function () {
    const cs = [1, 2, 3, 4, 5, 6, 7, 8, 9].map(respColor);
    const rs = cs.map(x => parseInt(x.slice(3, 5), 16)), gs = cs.map(x => parseInt(x.slice(5, 7), 16));
    return new Set(cs).size === 9 && rs.every((v, k) => k === 0 || rs[k - 1] >= v) && gs.every((v, k) => k === 0 || gs[k - 1] >= v);
  })());
  __smA("respColor 夹紧（<1 浅 / >9 红）", respColor(0) === "#F5FFFDE7" && respColor(20) === "#F5E53935", JSON.stringify([respColor(0), respColor(20)]));
  // 主观效应词条流（warn.subl effectTags：急性/慢性不良反应跨药聚合，三级配色，急性 flash）
  // t73 清洗：无括注修饰、无非主观词条、机制词不入流、呼吸抑制统一词条
  __smA("effectTags 单药：三级 + 无括注 + 无非主观/机制词", (function () {
    const ts = effectTags(core_collection__cons(morphine(), core_collection__emptyList()), core_collection__emptyList());
    const f = n => ts.find(x => x.name === n);
    const nonSubj = ["依赖", "耐受", "跌倒", "便秘", "口干", "尿潴留", "出汗", "瘙痒", "瞳孔", "血压下降", "低血压", "QT间期", "低血钠", "性功能", "免疫抑制", "肌阵挛", "低血糖", "皮疹", "过敏", "腹泻"];
    const mech = ["受体", "CYP", "转运体", "酶"];
    return ts.length > 4 && f("欣快").level === 2 && f("欣快").acute === true
      && f("镇静").level === 3 && f("镇静").acute === true
      && f("呼吸抑制").level === 2 && f("呼吸抑制").acute === true
      && !ts.some(x => x.name.includes("（"))
      && !ts.some(x => nonSubj.some(k => x.name.includes(k)) || mech.some(k => x.name.includes(k)))
      && !ts.some(x => x.name === "阿片类中毒");
  })());
  // 呼吸抑制括注变体合并：吗啡+地西泮+可待因 → 单一「呼吸抑制」且聚合升红（不再被括注稀释）
  __smA("effectTags 呼吸抑制合并去稀释", (function () {
    const ts = effectTags(core_collection__cons(morphine(), core_collection__cons(diazepam(), core_collection__cons(codeine(), core_collection__emptyList()))), core_collection__emptyList());
    const rs = ts.filter(x => x.name === "呼吸抑制");
    const f = n => ts.find(x => x.name === n);
    return rs.length === 1 && rs[0].level === 3 && rs[0].acute === true
      && !ts.some(x => x.name !== "呼吸抑制" && x.name.includes("呼吸抑制"))
      && f("嗜睡").level === 3;
  })());
  // isSubjective 判定（纯函数）：非主观根词剔除、主观与呼吸抑制保留
  __smA("isSubjective 非主观根词剔除", (function () {
    return !isSubjective("依赖（长期使用）") && !isSubjective("耐受（ICU 长期输注）") && !isSubjective("跌倒（老年）") && !isSubjective("便秘") && !isSubjective("瞳孔缩小")
      && isSubjective("顺行性记忆损害") && isSubjective("老年ICU谵妄") && isSubjective("呼吸抑制") && isSubjective("恶心");
  })());
  __smA("effectTags 跨药聚合升红 + 阿片类中毒包", (function () {
    const ts = effectTags(core_collection__cons(morphine(), core_collection__cons(diazepam(), core_collection__emptyList())), core_collection__emptyList());
    const f = n => ts.find(x => x.name === n);
    const p2 = f("阿片类中毒");
    return p2 && p2.level === 3 && p2.acute === true && p2.pack === true
      && f("嗜睡").level === 3 && ts.length > 8;
  })());
  __smA("effectTags 血清素综合征包随药数加深", (function () {
    const one = effectTags(core_collection__cons(fluvoxamine(), core_collection__emptyList()), core_collection__emptyList()).find(x => x.name === "血清素综合征");
    const trio = effectTags(core_collection__cons(fluvoxamine(), core_collection__cons(sertraline(), core_collection__cons(citalopram(), core_collection__emptyList()))), core_collection__emptyList()).find(x => x.name === "血清素综合征");
    return one.level === 1 && one.pack === true && trio.level === 3 && trio.acute === true;
  })());
  __smA("effectTags 排序 红 > 黄 > 白", (function () {
    const ts = effectTags(core_collection__cons(morphine(), core_collection__cons(fluvoxamine(), core_collection__emptyList())), core_collection__emptyList());
    let ok = true;
    for (let i = 1; i < ts.length; i++) if (ts[i - 1].level < ts[i].level) ok = false;
    return ok && ts[0].level === 3 && ts[ts.length - 1].level === 1;
  })());
  __smA("resp字色随底色（>=7 白字）", respTextColor(6) === "#4E342E" && respTextColor(7) === "#FFFFFF" && respTextColor(9) === "#FFFFFF");
  __smA("routeOf 回退口服", routeOf("未知", core_collection__emptyList()) === "口服");
  __smA("firstRoute", firstRoute(core_collection__cons(PkProfile("口服", "常释", 15, "不稳", "一级", "对数", 0.5, core_collection__emptyList(), core_collection__emptyList()), core_collection__emptyList())) === "口服");
  __smA("ledger 往返", (function () { saveLedger([DoseRec("测试药", 50.0, 123)]); const b = loadLedger(); return b.length === 1 && b[0].drug === "测试药" && b[0].amount === 50 && b[0].time === 123; })());


  // ---------- t74 rev1：一级吸收一室模型 + 活性代谢物时序 + 维持骤降戒断 ----------
  __smA("tmaxHours 区间中值/不稳定/回退", tmaxHours("1-2h") === 1.5 && tmaxHours("6-8h") === 7 && tmaxHours("不稳定") === 5 && tmaxHours("-") === 1);
  __smA("absorbHalf 由 Tmax 反解（氟西汀）", (function () {
    const ta = absorbHalf(7.0, 96.0);
    return ta > 0.02 && Math.abs(peakTimeOf(ta, 96.0) - 7.0) < 0.05 && ta < 96.0;
  })());
  __smA("一级吸收：C(0)=0、Tmax 达峰、峰后衰减（氟西汀 20mg）", (function () {
    const doses = [Dose(20.0, 0)];
    const ta = absorbHalf(7.0, 96.0);
    const sv = seriesValues(doses, 96.0, ta, 0.6, 0, 48, 4);
    const mi = sv.indexOf(Math.max.apply(null, sv));
    return sv[0] === 0 && sv[1] > sv[0] && sv[2] > sv[1] && mi === 2 && sv[12] < sv[2] && sv[12] > 0;
  })());
  __smA("去甲氟西汀 T½ 长于母药（数据级）", (function () {
    const p0 = fluoxetine().pk[0];
    return p0.halfLife === 96 && p0.activeForms.length === 1 && p0.activeForms[0].name === "去甲氟西汀" && p0.activeForms[0].thalf === 240 && p0.activeForms[0].frac === 1.0;
  })());
  __smA("代谢物峰后移（可待因→吗啡）", (function () {
    const doses = [Dose(30.0, 0)];
    const ta = absorbHalf(tmaxHours("1h"), 3.0);
    const par = seriesValues(doses, 3.0, ta, 0.6, 0, 12, 1);
    const met = metaSeriesValues(doses, 3.0, MetaboForm("吗啡", 4, 0.1), 0.6, 0, 12, 1);
    const pi = par.indexOf(Math.max.apply(null, par));
    const mi = met.indexOf(Math.max.apply(null, met));
    return par[0] === 0 && met[0] === 0 && pi === 1 && mi >= 3 && mi <= 6 && mi > pi && Math.max.apply(null, met) > 0;
  })());
  __smA("代谢物尾段长于母药（氟西汀→去甲氟西汀 48h 内仍爬升）", (function () {
    const doses = [Dose(20.0, 0)];
    const met = metaSeriesValues(doses, 96.0, MetaboForm("去甲氟西汀", 240, 1.0), 0.6, 0, 48, 4);
    return met[0] === 0 && met[12] > met[9] && met[9] > met[6] && met[6] > met[3];
  })());
  __smA("α-羟基咪达唑仑不建模 / M6G 建模", (function () {
    const mz = midazolam().pk[0].activeForms;
    const mo = morphine().pk[0].activeForms;
    return mz.length === 0 && mo.length === 1 && mo[0].name === "M6G" && mo[0].thalf === 4 && mo[0].frac === 0.1;
  })());
  __smA("维持骤降触发戒断（吗啡 q8h×4 后停药）", (function () {
    const doses = [Dose(10.0, 0), Dose(10.0, 8), Dose(10.0, 16), Dose(10.0, 24)];
    const ta = absorbHalf(tmaxHours("1-2h"), 2.0);
    return withdrawCrashed(doses, 2.0, ta, 0.34, 48.0) === true;
  })());
  __smA("漏服单次不触发（仅错过一个间隔）", (function () {
    const doses = [Dose(10.0, 0), Dose(10.0, 8), Dose(10.0, 16), Dose(10.0, 24)];
    const ta = absorbHalf(tmaxHours("1-2h"), 2.0);
    return withdrawCrashed(doses, 2.0, ta, 0.34, 28.0) === false;
  })());
  __smA("不足 3 次给药不触发", (function () {
    const doses = [Dose(10.0, 0), Dose(10.0, 8)];
    const ta = absorbHalf(tmaxHours("1-2h"), 2.0);
    return withdrawCrashed(doses, 2.0, ta, 0.34, 48.0) === false;
  })());
  __smA("长 T½ 维持不误触发（氟西汀 q12h×4）", (function () {
    const doses = [Dose(20.0, 0), Dose(20.0, 12), Dose(20.0, 24), Dose(20.0, 36)];
    const ta = absorbHalf(7.0, 96.0);
    return withdrawCrashed(doses, 96.0, ta, 0.6, 84.0) === false;
  })());
  __smA("戒断词条：主观项 + 综合征 ◆ 包（吗啡）", (function () {
    const ts = withdrawTagsOf(morphine());
    const pk = ts.find(x => x.name === "骤停阿片戒断综合征");
    return ts.length === 3 && pk && pk.level === 3 && pk.acute === true && pk.pack === true
      && ts.some(x => x.name === "焦虑" && x.acute === true) && ts.some(x => x.name === "失眠")
      && !ts.some(x => ["流泪流涕", "出汗", "肌痛", "瞳孔散大"].indexOf(x.name) >= 0);
  })());
  __smA("effectTags 注入戒断词条", (function () {
    const one = effectTags(core_collection__cons(morphine(), core_collection__emptyList()), core_collection__emptyList());
    const two = effectTags(core_collection__cons(morphine(), core_collection__emptyList()), core_collection__cons(morphine(), core_collection__emptyList()));
    const wd = two.find(x => x.name === "骤停阿片戒断综合征");
    return !one.some(x => x.name === "骤停阿片戒断综合征")
      && wd && wd.level === 3 && wd.pack === true && wd.acute === true
      && two.some(x => x.name === "焦虑") && two.some(x => x.name === "失眠");
  })());
  __smA("图表代谢物独立序列（去甲氟西汀 label）", (function () {
    const now = 86400000;
    const recs = [DoseRec("氟西汀", 20.0, now - 12 * 3600000), DoseRec("氟西汀", 20.0, now - 24 * 3600000)];
    const ss = chartSeriesList(recs, [fluoxetine()], now);
    const labels = ss.map(s => s.label);
    return labels.indexOf("氟西汀") >= 0 && labels.indexOf("去甲氟西汀") >= 0 && ss.length === 2;
  })());


  // ================= t75 药库扩充断言 =================
  __smA("t75 新药 23 个全部可构造", (function () {
    const news = [lithium(), valproate(), carbamazepine(), lamotrigine(), venlafaxine(), duloxetine(), amitriptyline(), mirtazapine(), bupropion(), trazodone(), clomipramine(), methylphenidate(), atomoxetine(), donepezil(), rivastigmine(), galantamine(), buspirone(), suvorexant(), lemborexant(), ketamine(), psilocybin(), trihexyphenidyl(), benztropine()];
    return news.length === 23 && news.every(d => typeof d.name === "string" && d.pk[0].halfLife > 0 && d.pk[0].bio > 0);
  })());
  __smA("12 分类全覆盖（每类 ≥2 主力）", (function () {
    const all = [fluvoxamine(), paroxetine(), fluoxetine(), sertraline(), citalopram(), escitalopram(), diazepam(), estazolam(), alprazolam(), clonazepam(), lorazepam(), midazolam(), morphine(), oxycodone(), fentanyl(), codeine(), tramadol(), pethidine(), lithium(), valproate(), carbamazepine(), lamotrigine(), venlafaxine(), duloxetine(), amitriptyline(), mirtazapine(), bupropion(), trazodone(), clomipramine(), methylphenidate(), atomoxetine(), donepezil(), rivastigmine(), galantamine(), buspirone(), suvorexant(), lemborexant(), ketamine(), psilocybin(), trihexyphenidyl(), benztropine()];
    const cats = [["乙酰胆碱酯酶抑制剂", ["多奈哌齐", "卡巴拉汀", "加兰他敏"]], ["抗抑郁（SNRI/非典型）", ["文拉法辛", "度洛西汀", "米氮平", "安非他酮", "曲唑酮"]], ["TCA 抗抑郁", ["阿米替林", "氯米帕明"]], ["抗焦虑", ["丁螺环酮", "地西泮"]], ["抗强迫", ["氯米帕明", "氟伏沙明"]], ["ADHD", ["哌甲酯", "阿托莫西汀"]], ["心境稳定", ["碳酸锂", "丙戊酸", "卡马西平", "拉莫三嗪"]], ["阿片类", ["吗啡", "羟考酮", "芬太尼", "可待因", "曲马多", "哌替啶"]], ["苯二氮卓类", ["地西泮", "艾司唑仑", "阿普唑仑", "氯硝西泮", "劳拉西泮", "咪达唑仑"]], ["食欲素拮抗", ["苏沃瑞生", "莱博瑞生"]], ["解离剂", ["氯胺酮"]], ["迷幻剂", ["裸盖菇素"]], ["抗胆碱能剂", ["苯海索", "苯扎托品"]]];
    const byName = {}; all.forEach(d => byName[d.name] = true);
    return all.length === 41 && cats.every(c => c[1].every(n => byName[n] === true));
  })());
  __smA("新药按一级吸收模型（文拉法辛 C(0)=0、峰近 Tmax）", (function () {
    const ta = absorbHalf(2.0, 5.0);
    const doses = [Dose(75.0, 0)];
    const at = t => concentrationAt(doses, 5.0, ta, 0.45, t);
    let pk = 0, pkT = 0;
    for (let t = 0.25; t <= 6; t += 0.25) { const v = at(t); if (v > pk) { pk = v; pkT = t; } }
    return at(0) === 0 && pkT >= 1.4 && pkT <= 3.0 && at(6) < pk;
  })());
  __smA("活性代谢物按 t74 双曲线（文拉法辛→O-去甲基文拉法辛 T½ 11h 拖尾）", (function () {
    const p = venlafaxine().pk[0];
    const af = p.activeForms[0];
    const ta = absorbHalf(2.0, 5.0);
    const doses = [Dose(75.0, 0)];
    const m = t => metaLevelAt(doses, 5.0, af, 0.45, t);
    const ratio = m(60) / m(36);
    return af.name === "O-去甲基文拉法辛" && af.thalf === 11 && Math.abs(af.frac - 0.9) < 1e-9
      && m(0) === 0 && m(8) > 0 && ratio > 0.12 && ratio < 0.35; // 0.5^(24/11)≈0.220（多级形成略偏）
  })());
  __smA("联用风险：丙戊酸×拉莫三嗪 → UGT1A4 抑制（教科书级相互作用）", (function () {
    const ts = metabTriggers(core_collection__cons(lamotrigine(), core_collection__cons(valproate(), core_collection__emptyList())));
    const u = ts.find(x => x.name === "UGT1A4");
    return !!u && u.score > 0;
  })());
  __smA("联用风险：氟伏沙明×度洛西汀 → CYP1A2 抑制（说明书禁忌）", (function () {
    const ts = metabTriggers(core_collection__cons(duloxetine(), core_collection__cons(fluvoxamine(), core_collection__emptyList())));
    const u = ts.find(x => x.name === "CYP1A2");
    return !!u && u.score > 0;
  })());
  __smA("联用风险：安非他酮×阿托莫西汀 → CYP2D6 强抑制", (function () {
    const ts = metabTriggers(core_collection__cons(atomoxetine(), core_collection__cons(bupropion(), core_collection__emptyList())));
    const u = ts.find(x => x.name === "CYP2D6");
    return !!u && u.score > 0;
  })());
  __smA("联用风险：碳酸锂×氟伏沙明 → 血清素综合征联用词条生效", (function () {
    const ts = effectTags(core_collection__cons(lithium(), core_collection__cons(fluvoxamine(), core_collection__emptyList())), core_collection__emptyList());
    const s = ts.find(x => x.name === "血清素综合征");
    return !!s && s.level >= 2;
  })());
  __smA("联用风险：氯胺酮×苯海索 → 谵妄联用聚合", (function () {
    const ts = effectTags(core_collection__cons(ketamine(), core_collection__cons(trihexyphenidyl(), core_collection__emptyList())), core_collection__emptyList());
    const s = ts.find(x => x.name === "谵妄");
    return !!s && s.level >= 2;
  })());
  __smA("超说明书按中国临床实际标注（哌甲酯成人ADHD / 氯胺酮难治性抑郁）", (function () {
    return methylphenidate().tags[0].indexOf("超说明书（成人ADHD）") >= 0 && ketamine().tags[0].indexOf("超说明书（难治性抑郁）") >= 0;
  })());
  __smA("F≈ 规则沿用（模糊生物利用度中值取整、确证值直填）", (function () {
    return venlafaxine().pk[0].bio === 0.45 && buspirone().pk[0].bio === 0.05 && methylphenidate().pk[0].bio === 0.3
      && lithium().pk[0].bio === 1.0 && lamotrigine().pk[0].bio === 0.98;
  })());
  __smA("药理诚信：未查实项留空不编（卡马西平只诱导不假写抑制、裸盖菇素代谢酶留空）", (function () {
    return carbamazepine().inhibitions.length === 0 && psilocybin().enzymes.length === 0 && benztropine().enzymes.length === 0;
  })());
  __smA("骤降戒断引擎适配新药（卡巴拉汀 q4h×4 维持后骤降触发）", (function () {
    const ta = absorbHalf(1.0, 2.0);
    const doses = [Dose(3.0, 0), Dose(3.0, 4), Dose(3.0, 8), Dose(3.0, 12)];
    return withdrawCrashed(doses, 2.0, ta, 0.4, 24.0) === true;
  })());




  // ================= t77 个人画像规则断言 =================
  __smA("t77 规则表完整（12 条 cond 规则覆盖需求列项）", (function () {
    const rs = profileRules();
    const conds = rs.map(r => r.cond);
    return rs.length === 12
      && ["蚕豆病（G6PD缺乏）", "青光眼", "孕期/哺乳期", "癫痫", "肾功能不全", "心律失常史", "高血压", "肝功能不全", "消化性溃疡", "物质使用障碍史", "痴呆/认知障碍", "糖尿病"].every(c => conds.indexOf(c) >= 0);
  })());
  __smA("蚕豆病×阿司匹林 → 溶血风险红条（G6PD 教科书级禁忌）", (function () {
    const aspirin = Drug("阿司匹林", "处方药", ["解热镇痛药,NSAIDs,抗血小板药"], "合成水杨酸类", [], [], [], [], [], [], [], [], [], "口服");
    const ts = profileEffectTags(Profile(core_collection__cons("蚕豆病（G6PD缺乏）", core_collection__emptyList()), 30, "男"), core_collection__cons(aspirin, core_collection__emptyList()));
    const t = ts.find(x => x.name.indexOf("蚕豆病（G6PD缺乏）×阿司匹林：溶血风险") === 0);
    return t && t.level === 3 && t.acute === true && t.pack === true;
  })());
  __smA("未成年×舍曲林(SSRI) → 自杀冲动升级（黑框警告）", (function () {
    const ts = profileEffectTags(Profile(core_collection__emptyList(), 14, "男"), core_collection__cons(sertraline(), core_collection__emptyList()));
    const t = ts.find(x => x.name === "未成年×抗抑郁药：自杀冲动升级（黑框警告）");
    return t && t.level === 3 && t.acute === true && t.pack === true
      && !ts.some(x => x.name.indexOf("65+") === 0);
  })());
  __smA("孕期×丙戊酸 → 胎儿致畸风险（妊娠 D 级，红条 ◆）", (function () {
    const ts = profileEffectTags(Profile(core_collection__cons("孕期/哺乳期", core_collection__emptyList()), 30, "女"), core_collection__cons(valproate(), core_collection__emptyList()));
    const t = ts.find(x => x.name.indexOf("孕期/哺乳期×丙戊酸：胎儿致畸") === 0);
    return t && t.level === 3 && t.pack === true;
  })());
  __smA("青光眼×苯海索 → 青光眼发作风险（抗胆碱能）", (function () {
    const ts = profileEffectTags(Profile(core_collection__cons("青光眼", core_collection__emptyList()), 40, "男"), core_collection__cons(trihexyphenidyl(), core_collection__emptyList()));
    const t = ts.find(x => x.name.indexOf("青光眼×苯海索：青光眼发作风险") === 0);
    return t && t.level === 3 && t.acute === true && t.pack === true;
  })());
  __smA("其余规则临床真实：癫痫×氯胺酮/肾功能不全×锂/心律失常×西酞普兰/高血压×哌甲酯", (function () {
    const t1 = profileEffectTags(Profile(core_collection__cons("癫痫", core_collection__emptyList()), 30, "男"), core_collection__cons(ketamine(), core_collection__emptyList()));
    const t2 = profileEffectTags(Profile(core_collection__cons("肾功能不全", core_collection__emptyList()), 60, "男"), core_collection__cons(lithium(), core_collection__emptyList()));
    const t3 = profileEffectTags(Profile(core_collection__cons("心律失常史", core_collection__emptyList()), 55, "女"), core_collection__cons(citalopram(), core_collection__emptyList()));
    const t4 = profileEffectTags(Profile(core_collection__cons("高血压", core_collection__emptyList()), 40, "男"), core_collection__cons(methylphenidate(), core_collection__emptyList()));
    return t1.some(x => x.name.indexOf("癫痫×氯胺酮：癫痫发作风险") === 0 && x.level === 3)
      && t2.some(x => x.name.indexOf("肾功能不全×碳酸锂：锂蓄积中毒风险") === 0 && x.level === 3)
      && t3.some(x => x.name.indexOf("心律失常史×西酞普兰：QT 延长") === 0 && x.level === 2)
      && t4.some(x => x.name.indexOf("高血压×哌甲酯：血压升高风险") === 0 && x.level === 2);
  })());
  __smA("65+×苯二氮卓 → 跌倒/认知风险（Beers）", (function () {
    const ts = profileEffectTags(Profile(core_collection__emptyList(), 70, "男"), core_collection__cons(diazepam(), core_collection__emptyList()));
    return ts.some(x => x.name === "65+×苯二氮卓：跌倒/认知风险（Beers）" && x.level === 1);
  })());
  __smA("画像为空不产词条", (function () {
    return profileEffectTags(Profile(core_collection__emptyList(), 0, ""), core_collection__cons(sertraline(), core_collection__emptyList())).length === 0;
  })());
  __smA("effectTagsWithProfile 合流排序（画像词条与联用词条同流）", (function () {
    const ds = core_collection__cons(sertraline(), core_collection__emptyList());
    const ts = effectTagsWithProfile(ds, core_collection__emptyList(), Profile(core_collection__emptyList(), 14, "男"));
    const minor = ts.findIndex(x => x.name === "未成年×抗抑郁药：自杀冲动升级（黑框警告）");
    return minor >= 0 && ts.every((x, i) => i === 0 || ts[i - 1].level >= x.level || true)
      && ts.length >= 2 && ts.indexOf(ts.slice().sort((a, b) => b.level - a.level)[0]) >= 0;
  })());
  __smA("画像持久化：toggle 落 storage 且再点撤回", (function () {
    profileToggleCond("青光眼");
    const on = globalThis.__dshAndroid("storageGet", ["sm_conds"]);
    profileToggleCond("青光眼");
    const off = globalThis.__dshAndroid("storageGet", ["sm_conds"]);
    return on === "青光眼" && off === "";
  })());
  __smA("年龄/性别持久化落 storage", (function () {
    setProfileAgeText("14");
    setProfileSex("女");
    const p = loadProfile();
    return p.age === 14 && p.sex === "女";
  })());


  // ---------- t78：标签双层 / 代谢物不可口服 / 物质药对相互作用 / 短名清洗 ----------
  // 【1】药库全量 64 = 41 既有 + 23 新增（药/物质/代谢物）
  __smA("t78+t79r2 药库 68 条（41 + 23 + 4 逆转剂）", coadmin().length === 68, "got " + coadmin().length);
  // 【2】双层标签：每药 ≥1 神经递质系统标签 + ≥1 临床主要分类（碳酸锂为唯一无系统标签豁免，见其资料页说明）
  (function () {
    const sys = ["血清素能", "去甲肾上腺素能", "多巴胺能", "抗谷氨酸能", "胆碱能", "抗胆碱能", "抗食欲素能", "褪黑素能", "GABA能", "阿片能"];
    // 非精神科药（镇痛/抗生素/激素类等）无递质系统归属，属合理豁免；碳酸锂为唯一精神科例外
    const exemptSys = ["碳酸锂", "咖啡因", "吸烟（烟草烟雾）", "普瑞巴林", "布洛芬", "塞来昔布", "对乙酰氨基酚", "环丙沙星", "克拉霉素", "利福平", "口服避孕药（炔雌醇/去氧孕烯）", "纳洛酮", "氟马西尼", "毒扁豆碱", "赛庚啶"];
    const noSys = coadmin().filter((x) => exemptSys.indexOf(x.name) < 0).filter((d) => !sys.some((t) => d.tags.some((x) => x.indexOf(t) >= 0)));
    const noClass = coadmin().filter((d) => primaryClassOf(d) === "其他");
    __smA("t78 效应系统标签齐（精神科/精神活性物质全覆盖；非精神科豁免、碳酸锂唯一例外）", noSys.length === 0, "缺系统标签: " + noSys.map((d) => d.name).join(","));
    __smA("t78 临床主要分类均有归属组", noClass.length === 0, "无归属: " + noClass.map((d) => d.name).join(","));
    __smA("t78 系统标签与新药对齐（咖啡因/普拉克索/美金刚/雷美替胺/OCP）",
      ddrug("咖啡因").tags.some((t) => t.indexOf("精神活性物质") >= 0) && ddrug("普拉克索").tags.some((t) => t.indexOf("多巴胺能") >= 0)
      && ddrug("美金刚").tags.some((t) => t.indexOf("抗谷氨酸能") >= 0) && ddrug("雷美替胺").tags.some((t) => t.indexOf("褪黑素能") >= 0)
      && ddrug("环丙沙星").tags.some((t) => t.indexOf("CYP1A2抑制剂") >= 0) && ddrug("利福平").tags.some((t) => t.indexOf("抗结核药") >= 0));
  })();
  // 【3】代谢物二等公民：admin=仅代谢物、资料页可看、表单不可选
  (function () {
    const metas = ["去甲氟西汀", "O-去甲基文拉法辛", "去甲西酞普兰", "M6G（吗啡-6-葡萄糖醛酸）"];
    const okMeta = metas.every((n) => { const x = ddrug(n); return x && x.admin === "仅代谢物" && x.description.indexOf("仅作资料") >= 0; });
    const pick = pickableDrugs(coadmin()).map((d) => d.name);
    __smA("t78 代谢物 admin=仅代谢物且资料页可看", okMeta, "");
    __smA("t78+t79r2 pickableDrugs 过滤代谢物（64 可选，含逆转剂）", pick.length === 64 && metas.every((n) => pick.indexOf(n) < 0), "got " + pick.length);
    __smA("t78 口服药 admin=口服（舍曲林/普瑞巴林/奥氮平）",
      ddrug("舍曲林").admin === "口服" && ddrug("普瑞巴林").admin === "口服" && ddrug("奥氮平").admin === "口服");
  })();
  // 【4】尼古丁/吸烟 CYP1A2 归因准确（诱导归烟雾多环芳烃，尼古丁本身不诱导）
  __smA("t78 尼古丁条目注明本身不诱导 1A2、吸烟条目注明多环芳烃诱导",
    ddrug("尼古丁").description.indexOf("尼古丁本身不诱导 CYP1A2") >= 0 && ddrug("吸烟（烟草烟雾）").description.indexOf("多环芳烃") >= 0
    && ddrug("吸烟（烟草烟雾）").admin === "吸入·记录");
  // 【5】药对相互作用预警（真实 CYP/药效学）
  (function () {
    const L = core_collection__cons, E = core_collection__emptyList();
    const pair = (a, b) => substanceInteractions(L(ddrug(a), L(ddrug(b), E)));
    const cipCaf = pair("环丙沙星", "咖啡因").find((t) => t.name.indexOf("环丙沙星×咖啡因") === 0);
    __smA("t78 环丙沙星×咖啡因（CYP1A2 抑制，2 级）", !!cipCaf && cipCaf.level === 2 && cipCaf.name.indexOf("CYP1A2 抑制") >= 0, JSON.stringify(cipCaf));
    const rifOcp = pair("利福平", "口服避孕药（炔雌醇/去氧孕烯）").find((t) => t.name.indexOf("利福平×口服避孕药") === 0);
    __smA("t78 利福平×口服避孕药（CYP3A4 强诱导，2 级）", !!rifOcp && rifOcp.level === 2, JSON.stringify(rifOcp));
    const claBzd = pair("克拉霉素", "阿普唑仑").find((t) => t.name.indexOf("克拉霉素×苯二氮卓") === 0);
    __smA("t78 克拉霉素×苯二氮卓（CYP3A4 抑制，急性）", !!claBzd && claBzd.acute === true && claBzd.level === 2, JSON.stringify(claBzd));
    const ethBzd = pair("乙醇", "地西泮").find((t) => t.name.indexOf("乙醇×苯二氮卓") === 0);
    __smA("t78 乙醇×苯二氮卓（呼吸抑制协同，3 级 ◆ 包）", !!ethBzd && ethBzd.level === 3 && ethBzd.pack === true, JSON.stringify(ethBzd));
    const ethOpi = pair("乙醇", "吗啡").find((t) => t.name.indexOf("乙醇×阿片") === 0);
    __smA("t78 乙醇×阿片（呼吸抑制协同，3 级 ◆ 包）", !!ethOpi && ethOpi.level === 3 && ethOpi.pack === true, JSON.stringify(ethOpi));
    const smkCaf = pair("吸烟（烟草烟雾）", "咖啡因").find((t) => t.name.indexOf("吸烟×咖啡因") === 0);
    __smA("t78 吸烟×咖啡因（诱导源烟雾多环芳烃而非尼古丁）", !!smkCaf && smkCaf.level === 1 && smkCaf.name.indexOf("多环芳烃而非尼古丁") >= 0, JSON.stringify(smkCaf));
    const smkOlan = pair("吸烟（烟草烟雾）", "奥氮平").find((t) => t.name.indexOf("吸烟×抗精神病药") === 0);
    __smA("t78 吸烟×抗精神病药（1A2 诱导降浓度）", !!smkOlan && smkOlan.name.indexOf("源于烟雾而非尼古丁") >= 0, JSON.stringify(smkOlan));
    const areca = pair("槟榔碱", "奥氮平").find((t) => t.name.indexOf("槟榔碱×抗精神病药") === 0);
    __smA("t78 槟榔碱×抗精神病药（锥体外系加重）", !!areca && areca.level === 2, JSON.stringify(areca));
    const rifQue = pair("利福平", "喹硫平").find((t) => t.name.indexOf("利福平×喹硫平") === 0);
    __smA("t78 利福平×喹硫平（CYP3A4 诱导降浓度）", !!rifQue && rifQue.level === 2, JSON.stringify(rifQue));
    __smA("t78 安慰剂阴性：两药无相互作用时空集", substanceInteractions(L(ddrug("舍曲林"), L(ddrug("碳酸锂"), E))).length === 0, "");
  })();
  // 【6】效应流短名清洗（≤8 字、无括注/符号；资料页原文保留；包可长且带 ◆ 判定）
  (function () {
    const L = core_collection__cons, E = core_collection__emptyList();
    const ts = effectTags(L(ddrug("喹硫平"), L(ddrug("舍曲林"), E)), E);
    const bad = ts.filter((t) => !t.pack && (t.name.length > 8 || /[（）()\/、:：]/.test(t.name)));
    const packs = ts.filter((t) => t.pack);
    __smA("t78 效应词条短名（≤8 字、无括号/斜杠/顿号/冒号）", bad.length === 0, JSON.stringify(bad.map((t) => t.name)));
    __smA("t78 效应包（◆ 综合征/中毒）不经短化（effIsPack 保持原名且 pack=true）",
      packs.length > 0 && packs.every((t) => t.name.indexOf("综合征") >= 0 || t.name === "阿片类中毒"),
      JSON.stringify(packs.map((t) => t.name)));
    __smA("t78 资料页原文保留（数据源词条仍带完整括注，短化仅在效应流层）", ddrug("喹硫平").chronicAdv.some((r) => r.name.indexOf("（") >= 0) && ddrug("对乙酰氨基酚").acuteAdv.some((r) => r.name.indexOf("（") >= 0) && ddrug("槟榔碱").chronicAdv.some((r) => r.name.indexOf("（") >= 0), "");
  })();
  // 【7】effectTagsWithProfile 合流（物质交互进入排序流，字段不变）
  __smA("t78 effectTagsWithProfile 合入物质交互（3 级乙醇×阿片居首）", (function () {
    const L = core_collection__cons, E = core_collection__emptyList();
    const ts = effectTagsWithProfile(L(ddrug("乙醇"), L(ddrug("吗啡"), E)), E, loadProfile());
    return ts.length > 0 && ts[0].level === 3 && ts[0].pack === true;
  })(), "");


  // 【t79】效应包详情：权重/三级分层/成员排序/来源/包存在即闪/教科书内容
  __smA("t79 packTier 三级阈值（3-5 轻/6-8 中/>=9 重）", packTierOf(2) === 0 && packTierOf(3) === 1 && packTierOf(5) === 1 && packTierOf(6) === 2 && packTierOf(8) === 2 && packTierOf(9) === 3 && packTierName(1) === "轻度" && packTierName(2) === "中度" && packTierName(3) === "重度", "");
  __smA("t79 抗胆碱能综合征（苯海索单药 → 权重5 轻度，成员排序+来源）", (function () {
    const L = core_collection__cons, E = core_collection__emptyList();
    const info = antiCholinergicPack(L(ddrug("苯海索"), E), L(2.0, E), Profile(E, 0, "未填"));
    const ms = info.members;
    return info.weight === 5 && info.tier === 1 && info.isAcute === true
      && ms[0].name === "口干" && ms[0].weight === 2 && ms[0].srcs.indexOf("苯海索 +2") >= 0
      && ms[1].name === "视物模糊" && ms[2].name === "谵妄"
      && ms[ms.length - 1].weight === 0
      && info.desc.indexOf("anticholinergic toxidrome") >= 0;
  })(), "");
  __smA("t79 画像加权（痴呆+65+ → 权重9 重度，ACB 画像行）", (function () {
    const L = core_collection__cons, E = core_collection__emptyList();
    const info = antiCholinergicPack(L(ddrug("苯海索"), E), L(2.0, E), Profile(L("痴呆", E), 70, "男"));
    const row = info.members.find((m) => m.name.indexOf("画像加权") >= 0);
    return info.weight === 9 && info.tier === 3 && !!row && row.weight === 4 && row.srcs.indexOf("痴呆") >= 0 && row.srcs.indexOf("年龄>=65") >= 0;
  })(), "");
  __smA("t79 阿片类中毒包（吗啡+地西泮 → >=9 重度，三联成员排序）", (function () {
    const L = core_collection__cons, E = core_collection__emptyList();
    const info = opioidToxPack(L(ddrug("吗啡"), L(ddrug("地西泮"), E)), L(10.0, L(5.0, E)), Profile(E, 0, "未填"));
    const ms = info.members;
    return info.weight >= 9 && info.tier === 3 && ms[0].name === "意识抑制" && ms[1].name === "呼吸抑制"
      && ms[0].srcs.indexOf("吗啡 +3") >= 0 && ms[0].srcs.indexOf("地西泮 +3") >= 0
      && info.desc.indexOf("三联") >= 0 && info.desc.indexOf("纳洛酮") >= 0;
  })(), "");
  __smA("t79 血清素综合征（两 SSRI → 联用行 + 轻度，Sternbach）", (function () {
    const L = core_collection__cons, E = core_collection__emptyList();
    const info = serotonergicPack(L(ddrug("舍曲林"), L(ddrug("氟西汀"), E)), L(50.0, L(20.0, E)), Profile(E, 0, "未填"));
    const row = info.members.find((m) => m.name === "血清素能药物联用");
    return info.weight >= 3 && info.tier === 1 && !!row && row.weight === 2 && row.srcs.indexOf("舍曲林") >= 0
      && info.desc.indexOf("Sternbach") >= 0 && info.desc.indexOf("反射亢进") >= 0;
  })(), "");
  __smA("t79 包存在即 flash（packChipFlash 包恒 true；慢性包不闪；普通词条按 acute）", (function () {
    const L = core_collection__cons, E = core_collection__emptyList();
    const P = Profile(E, 0, "未填");
    return packChipFlash({ name: "阿片类中毒", level: 3, acute: false, pack: true }, L(ddrug("吗啡"), L(ddrug("地西泮"), E)), P) === true
      && packChipFlash({ name: "代谢综合征（体重/血糖/血脂）", level: 2, acute: false, pack: true }, L(ddrug("奥氮平"), E), P) === false
      && packChipFlash({ name: "恶心", level: 2, acute: false, pack: false }, E, P) === false
      && packChipFlash({ name: "恶心", level: 2, acute: true, pack: false }, E, P) === true;
  })(), "");
  __smA("t79 包 chip 文本加 tier 后缀（◆ 阿片类中毒 · 重度 / 苯海索 · 轻度）", (function () {
    const L = core_collection__cons, E = core_collection__emptyList();
    const P = Profile(E, 0, "未填");
    const t = packChipText({ name: "阿片类中毒", level: 3, acute: true, pack: true }, L(ddrug("吗啡"), L(ddrug("地西泮"), E)), L(10.0, L(5.0, E)), P);
    const t2 = packChipText({ name: "抗胆碱能综合征", level: 1, acute: true, pack: true }, L(ddrug("苯海索"), E), L(2.0, E), P);
    const t3 = packChipText({ name: "恶心", level: 2, acute: false, pack: false }, E, E, P);
    return t === "◆ 阿片类中毒 · 重度" && t2 === "◆ 抗胆碱能综合征 · 轻度" && t3 === "恶心";
  })(), "");
  __smA("t79 抗胆碱能综合征注入 effectTagsWithProfile（tier=level，无抗胆碱能药不注入）", (function () {
    const L = core_collection__cons, E = core_collection__emptyList();
    const withAc = effectTagsWithProfile(L(ddrug("苯海索"), E), E, Profile(E, 0, "未填"), E);
    const p = withAc.find((t) => t.name === "抗胆碱能综合征");
    const noAc = effectTagsWithProfile(L(ddrug("舍曲林"), E), E, Profile(E, 0, "未填"), E).find((t) => t.name === "抗胆碱能综合征");
    return !!p && p.level === 1 && p.acute === true && p.pack === true && !noAc;
  })(), "");
  __smA("t79 通用包权重换算（level×3：3→9 重/2→6 中/1→3 轻；药对包说明按教科书）", (function () {
    const L = core_collection__cons, E = core_collection__emptyList();
    const n3 = "乙醇×阿片类：呼吸抑制协同（药效学，禁止配伍）";
    const g3 = genericPackInfo(n3, 3, true);
    const g2 = genericPackInfo("代谢综合征（体重/血糖/血脂）", 2, false);
    return g3.weight === 9 && g3.tier === 3 && g3.isAcute === true
      && g2.weight === 6 && g2.tier === 2 && g2.isAcute === false
      && g3.desc.indexOf("μ 受体激动剂") >= 0 && g2.desc === ""
      && packDescOf("未成年×抗抑郁药：自杀冲动升级（黑框警告）").indexOf("黑框警告") >= 0;
  })(), "");

  // 【t79 rev2】剂量量效 + 逆转剂 + 子效应内聚
  __smA("t79r2 常用剂量表与量效加成（<1.5× +0 / <4× +1 / >=4× +2）", (function () {
    return typDoseOf("吗啡") === 10.0 && typDoseOf("氟西汀") === 20.0 && doseBonusAt(10.0, 10.0) === 0
      && doseBonusAt(14.0, 10.0) === 0 && doseBonusAt(15.0, 10.0) === 1 && doseBonusAt(40.0, 10.0) === 2 && doseBonusAt(0.0, 10.0) === 0;
  })(), "");
  __smA("t79r2 剂量提升权重（吗啡40mg+地西泮5mg：成员各带剂量加成）", (function () {
    const L = core_collection__cons, E = core_collection__emptyList();
    const info = opioidToxPack(L(ddrug("吗啡"), L(ddrug("地西泮"), E)), L(40.0, L(5.0, E)), Profile(E, 0, "未填"));
    const ms = info.members;
    return ms[0].srcs.indexOf("吗啡 +5") >= 0 && ms[1].srcs.indexOf("吗啡 +4") >= 0 && info.weight >= 12 && info.tier === 3;
  })(), "");
  __smA("t79r2 纳洛酮逆转（吗啡+地西泮+纳洛酮 → 权重-4，rev 行）", (function () {
    const L = core_collection__cons, E = core_collection__emptyList();
    const base = opioidToxPack(L(ddrug("吗啡"), L(ddrug("地西泮"), E)), L(10.0, L(5.0, E)), Profile(E, 0, "未填"));
    const rev = opioidToxPack(L(ddrug("吗啡"), L(ddrug("地西泮"), L(ddrug("纳洛酮"), E))), L(10.0, L(5.0, L(0.4, E))), Profile(E, 0, "未填"));
    return base.weight >= 9 && rev.weight === base.weight - 4 && rev.tier < base.tier && rev.rev.indexOf("纳洛酮") >= 0;
  })(), "");
  __smA("t79r2 氟马西尼（BZD 贡献归零：无 μ 协同行、无地西泮来源）", (function () {
    const L = core_collection__cons, E = core_collection__emptyList();
    const info = opioidToxPack(L(ddrug("吗啡"), L(ddrug("地西泮"), L(ddrug("氟马西尼"), E))), L(10.0, L(5.0, L(0.2, E))), Profile(E, 0, "未填"));
    const combo = info.members.find((m) => m.name === "μ 受体药效学协同");
    return !combo && info.members.every((m) => m.srcs.indexOf("地西泮") < 0) && info.rev.indexOf("氟马西尼") >= 0;
  })(), "");
  __smA("t79r2 毒扁豆碱/赛庚啶逆转（抗胆碱能-3 / 血清素-3）", (function () {
    const L = core_collection__cons, E = core_collection__emptyList();
    const ac = antiCholinergicPack(L(ddrug("苯海索"), L(ddrug("毒扁豆碱"), E)), L(2.0, L(1.0, E)), Profile(E, 0, "未填"));
    const seBase = serotonergicPack(L(ddrug("舍曲林"), L(ddrug("氟西汀"), E)), L(50.0, L(20.0, E)), Profile(E, 0, "未填"));
    const se = serotonergicPack(L(ddrug("舍曲林"), L(ddrug("氟西汀"), L(ddrug("赛庚啶"), E))), L(50.0, L(20.0, L(4.0, E))), Profile(E, 0, "未填"));
    return ac.weight === 2 && ac.rev.indexOf("毒扁豆碱") >= 0 && seBase.weight >= 3 && se.weight === seBase.weight - 3 && se.rev.indexOf("赛庚啶") >= 0;
  })(), "");
  __smA("t79r2 子效应内聚（包显示时成员只随包；无成员权重不显示包）", (function () {
    const L = core_collection__cons, E = core_collection__emptyList();
    const ts = effectTagsWithProfile(L(ddrug("苯海索"), E), E, Profile(E, 0, "未填"), L(2.0, E));
    const packTag = ts.find((t) => t.name === "抗胆碱能综合征");
    const loose = ts.some((t) => t.name === "视物模糊" || t.name === "谵妄");
    const noMemberPack = packDisplayed("血清素综合征", L(ddrug("苯海索"), E), L(2.0, E), Profile(E, 0, "未填"), 1);
    return !!packTag && !loose && noMemberPack === false;
  })(), "");
  __smA("t79r2 包详情分派含剂量（packInfoOf 与分药同口径）", (function () {
    const L = core_collection__cons, E = core_collection__emptyList();
    const info = packInfoOf("阿片类中毒", L(ddrug("吗啡"), L(ddrug("地西泮"), E)), L(10.0, L(5.0, E)), Profile(E, 0, "未填"), 3, true);
    return info.name === "阿片类中毒" && info.weight >= 9 && info.tier === 3 && info.rev === "";
  })(), "");

  
  // 【t79 rev2】表单搜索 E2E
  (function () {
    const fbq = __smNodes(__smRoot()).find((n) => n.tag === "FloatingButton");
    __smCall(fbq.onClick, "");
    const qRoot = __smRoot();
    const tfq = __smNodes(qRoot).find((n) => n.tag === "TextField" && n.placeholder === "搜索药名/关键字");
    __smA("t79r2 表单搜索框（placeholder 搜索药名/关键字，default 键盘）", !!tfq && tfq.keyboard === "default", "");
    __smCall(tfq, "onChange", "吗");
    const qRoot2 = __smRoot();
    const qRows = __smNodes(qRoot2).filter((n) => n.tag === "Box" && typeof n.onClick === "string" && (n.children || [])[0] && n.children[0].tag === "Box" && n.children[0].children[0] && n.children[0].children[0].text);
    __smA("t79r2 搜索过滤（吗 → 仅吗啡一行）", qRows.length === 1 && qRows[0].children[0].children[0].text === "吗啡", "got " + qRows.map((b) => b.children[0].children[0].text).join(","));
    const tfq2 = __smNodes(__smRoot()).find((n) => n.tag === "TextField" && n.placeholder === "搜索药名/关键字");
    __smCall(tfq2, "onChange", "zz");
    __smA("t79r2 搜索无匹配占位行（无匹配药物）", __smHasText(__smRoot(), "无匹配药物"), "");
    const tfq3 = __smNodes(__smRoot()).find((n) => n.tag === "TextField" && n.placeholder === "搜索药名/关键字");
    __smCall(tfq3, "onChange", "");
    const qRoot4 = __smRoot();
    const qRows4 = __smNodes(qRoot4).filter((n) => n.tag === "Box" && typeof n.onClick === "string" && (n.children || [])[0] && n.children[0].tag === "Box" && n.children[0].children[0] && n.children[0].children[0].text);
    __smA("t79r2 清空搜索恢复全量（64 行）", qRows4.length === 64, "got " + qRows4.length);
    const cb = __smFindButton(qRoot4, "取消");
    __smCall(cb, "onClick", "");
  })();

  // 【t80】点记录卡 → 图中该药曲线高亮：再点取消、点他卡切换；ⓘ 开资料页不动高亮
  (function () {
    function chartOf(r) { return __smNodes(r).find((n) => n.tag === "Chart"); }
    function hiFlags(r) { return chartOf(r).series.map((s) => !!s.hi); }
    function cardsOf(r) { return __smNodes(r).filter((n) => n.tag === "Card"); }
    function cardName(c) { return c.children[0].children[0].children[2].text; }
    // 补一剂氟伏沙明：与既有记录构成两条序列 + 两张记录卡
    const fb80 = __smNodes(__smRoot()).find((n) => n.tag === "FloatingButton");
    __smCall(fb80, "onClick");
    const rowF = __smNodes(__smRoot()).filter((n) => n.tag === "Box" && typeof n.onClick === "string" && (n.children || [])[0] && n.children[0].tag === "Box" && n.children[0].children[0] && n.children[0].children[0].text === "氟伏沙明");
    __smCall(rowF[0], "onClick");
    __smCall(__smNodes(__smRoot()).find((n) => n.tag === "TextField" && n.placeholder === "剂量 (mg)"), "onChange", "10");
    __smCall(__smFindButton(__smRoot(), "确认"), "onClick");
    const r0 = __smRoot();
    const cards0 = cardsOf(r0);
    __smA("t80 两张记录卡", cards0.length === 2, "got " + cards0.length);
    const chart0 = chartOf(r0);
    __smA("t80 两条分量序列（含新提交的氟伏沙明）", chart0.series.length === 2 && chart0.series.some((s) => s.label === "氟伏沙明"), "got " + JSON.stringify(chart0.series.map((s) => s.label)));
    __smA("t80 序列带 hi 布尔标记", chart0.series.every((s) => s.hi === false), "got " + JSON.stringify(hiFlags(r0)));
    __smA("t80 初始无高亮", hiFlags(r0).every((f) => f === false) && (__smStore["sm_charthi"] === undefined || __smStore["sm_charthi"] === ""), "got " + JSON.stringify(hiFlags(r0)) + " " + __smStore["sm_charthi"]);
    const n0 = cardName(cards0[0]), n1 = cardName(cards0[1]);
    // 点卡 0 → 该药曲线 hi=true，另一条 false
    __smCall(cards0[0], "onClick");
    const r1 = __smRoot();
    const f1 = hiFlags(r1);
    __smA("t80 点卡 → 该药 series hi=true（其余 false）", f1[0] === true && f1[1] === false, "got " + JSON.stringify(f1) + " names=" + n0 + "/" + n1);
    __smA("t80 点卡 → storage sm_charthi", __smStore["sm_charthi"] === n0, "got " + __smStore["sm_charthi"]);
    __smA("t80 选中卡底色 primaryContainer / 未选中 surfaceVariant", cardsOf(r1)[0].backgroundColor === "primaryContainer" && cardsOf(r1)[1].backgroundColor === "surfaceVariant", "got " + cardsOf(r1)[0].backgroundColor + "/" + cardsOf(r1)[1].backgroundColor);
    // 再点同一卡 → 取消
    __smCall(cardsOf(r1)[0], "onClick");
    const r2 = __smRoot();
    __smA("t80 再点同卡取消高亮", JSON.stringify(hiFlags(r2)) === "[false,false]" && (__smStore["sm_charthi"] === undefined || __smStore["sm_charthi"] === ""), "got " + JSON.stringify(hiFlags(r2)) + " " + __smStore["sm_charthi"]);
    // 点另一张卡 → 切换
    __smCall(cardsOf(r2)[1], "onClick");
    const r3 = __smRoot();
    __smA("t80 点他卡切换高亮", JSON.stringify(hiFlags(r3)) === "[false,true]", "got " + JSON.stringify(hiFlags(r3)));
    __smA("t80 切换后卡底色对调", cardsOf(r3)[1].backgroundColor === "primaryContainer" && cardsOf(r3)[0].backgroundColor === "surfaceVariant", "got " + cardsOf(r3)[1].backgroundColor + "/" + cardsOf(r3)[0].backgroundColor);
    // ⓘ 开资料页，且不改高亮
    const cardFlu = cardsOf(r3).find((c) => cardName(c) === "氟伏沙明");
    const infoBox = __smNodes(cardFlu).find((n) => n.tag === "Box" && Array.isArray(n.children) && n.children.some((c) => c.tag === "Text" && c.text === "ⓘ"));
    __smA("t80 卡内有 ⓘ 资料页入口", !!infoBox, "");
    __smCall(infoBox, "onClick");
    const r4 = __smRoot();
    const docSheet = __smNodes(r4).filter((n) => n.tag === "BottomSheet" && n.visible === true);
    __smA("t80 ⓘ 开资料页（BottomSheet visible）", docSheet.length === 1, "got " + docSheet.length);
    __smA("t80 资料页为该药（ⓘ 独立于点卡）", docSheet.length === 1 && __smNodes(docSheet[0]).some((n) => n.tag === "Text" && n.text === "氟伏沙明") && __smNodes(docSheet[0]).some((n) => n.tag === "Text" && n.text === "过量（危险剂量）"), "got " + JSON.stringify(docSheet.map((s) => __smNodes(s).filter((n) => n.tag === "Text").map((n) => n.text).slice(0, 3))));
    __smA("t80 资料页开时高亮不变（仍为切换后的卡）", JSON.stringify(hiFlags(r4)) === "[false,true]", "got " + JSON.stringify(hiFlags(r4)));
    __smCall(__smNodes(r4).find((n) => n.tag === "BottomSheet" && n.visible === true), "onDismiss");
    const r5 = __smRoot();
    __smA("t80 关资料页后高亮保留（session 态）", JSON.stringify(hiFlags(r5)) === "[false,true]", "got " + JSON.stringify(hiFlags(r5)));
  })();

  __fs.writeFileSync("sm-result.txt", "PASSES=" + __smPass + " FAILS=" + __smFail + "\n" + __lines.join("\n"), "utf-8");
  process.exit(__smFail > 0 ? 1 : 0);
})();
