  // ===== 桌面驱动：触发生命周期 =====
  main();
  globalThis.__dshLifecycle_onCreate(null);
  globalThis.__dshLifecycle_onStart(null);

  const __fs = require("fs");
  const __lines = [];
  let __smPass = 0, __smFail = 0;
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
  __smA("根 8 区（表单/设置/药库名单/资料页四弹层存根）", root0.children.length === 8, JSON.stringify(root0.children.map((c) => c.tag)));
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
  __smA("药库名单 18 行", listRows.length === 18, "got " + listRows.length + " " + listRows.map((b) => b.children[2].children[0].text).join(","));
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
  __smA("表单副标题", __smHasText(root1, "选择药物并填写剂量（mg）"));
  __smA("表单药物标签", __smHasText(root1, "药物"));
  __smA("空台账提示消失", !__smHasText(root1, "尚无服药记录"));
  const listHolder = __smNodes(root1).find((n) => n.tag === "Box" && n.scrollable === true && (n.modifiers || []).some((m) => m.tag === "Height" && m["0"] === 168));
  __smA("药物列表定高 168", !!listHolder, "missing Height(168) holder");
  __smA("药物列表可滚", listHolder && listHolder.scrollable === true);
  const rowBtns = __smNodes(root1).filter((n) => n.tag === "Box" && typeof n.onClick === "string" && (n.children || [])[0] && n.children[0].tag === "Box" && (n.children[0].children || [])[0] && n.children[0].children[0].tag === "Text" && n.children[0].children[1] && n.children[0].children[1].tag === "Text");
  function __smRowName(b) { return b.children[0].children[0]; }
  function __smRowSub(b) { return b.children[0].children[1]; }
  function __smRowChecked(b) { return (b.children || []).some((c) => c.tag === "Text" && c.text.indexOf("✓") === 0 && c.color !== "#00000000"); } // v2：✓ 常驻，未选为透明 #00000000
  __smA("药物行 18 行", rowBtns.length === 18, "got " + rowBtns.map((b) => __smRowName(b).text).join(","));
  __smA("行 0 氟伏沙明未选", rowBtns.some((b) => __smRowName(b).text === "氟伏沙明" && __smRowName(b).bold === false), "got " + JSON.stringify(rowBtns.map((b) => __smRowName(b).text)));
  __smA("行 1 帕罗西汀", rowBtns.some((b) => __smRowName(b).text === "帕罗西汀"));
  __smA("行副标题：氟伏沙明", rowBtns.some((b) => __smRowSub(b).text === "处方药 · T½ 15h · F=50% · 口服"), "got " + JSON.stringify(rowBtns.map((b) => __smRowSub(b).text)));
  __smA("行副标题：帕罗西汀", rowBtns.some((b) => __smRowSub(b).text === "处方药 · T½ 24h · F=100% · 口服"));
  __smA("默认未选药", rowBtns.every((b) => __smRowName(b).bold === false && !__smRowChecked(b)));
  const tf = __smNodes(root1).find((n) => n.tag === "TextField");
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
  const tf2 = __smNodes(__smRoot()).find((n) => n.tag === "TextField");
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
  __smA("曲线末点=2×F×0.5^(48÷T½)", Math.abs(chart3.points[12] - 0.5) < 1e-3, "got " + chart3.points[12]);

  // ---------- 取消 ----------
  const fab3 = __smNodes(root3).find((n) => n.tag === "FloatingButton");
  __smCall(fab3, "onClick");
  const root4 = __smRoot();
  __smA("再开表单", __smHasText(root4, "选择药物并填写剂量（mg）"));
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
  const tfY = __smNodes(__smRoot()).find((n) => n.tag === "TextField");
  __smCall(tfY, "onChange", "10");
  __smCall(__smFindButton(__smRoot(), "确认"), "onClick");
  const rootM = __smRoot();
  const respTagM = __smNodes(rootM).filter((n) => n.tag === "Text" && typeof n.text === "string" && n.text.indexOf("呼吸抑制") === 0);
  __smA("单类吗啡=警告不红", respTagM.length === 1 && respTagM[0].text.indexOf("剂量依赖") >= 0 && respTagM[0].color === "#E65100" && respTagM[0].flash !== true, "got " + JSON.stringify(respTagM.map((n) => [n.text, n.color])));
  __smA("单类无红闪(flash)", !__smNodes(rootM).some((n) => n.tag === "Text" && n.flash === true));
  const fab5 = __smNodes(__smRoot()).find((n) => n.tag === "FloatingButton");
  __smCall(fab5, "onClick");
  const rowsZ = __smNodes(__smRoot()).filter((n) => n.tag === "Box" && typeof n.onClick === "string" && (n.children || [])[0] && n.children[0].tag === "Box" && (n.children[0].children || [])[0] && n.children[0].children[0].tag === "Text" && (n.children[0].children || [])[1] && n.children[0].children[1].tag === "Text");
  __smCall(rowsZ.find((b) => b.children[0].children[0].text === "地西泮"), "onClick");
  const tfZ = __smNodes(__smRoot()).find((n) => n.tag === "TextField");
  __smCall(tfZ, "onChange", "5");
  __smCall(__smFindButton(__smRoot(), "确认"), "onClick");
  const rootD = __smRoot();
  const respTagD = __smNodes(rootD).filter((n) => n.tag === "Text" && typeof n.text === "string" && n.text.indexOf("呼吸抑制") === 0);
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
    const ts = effectTags(core_collection__cons(morphine(), core_collection__emptyList()));
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
    const ts = effectTags(core_collection__cons(morphine(), core_collection__cons(diazepam(), core_collection__cons(codeine(), core_collection__emptyList()))));
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
    const ts = effectTags(core_collection__cons(morphine(), core_collection__cons(diazepam(), core_collection__emptyList())));
    const f = n => ts.find(x => x.name === n);
    const p2 = f("阿片类中毒");
    return p2 && p2.level === 3 && p2.acute === true && p2.pack === true
      && f("嗜睡").level === 3 && ts.length > 8;
  })());
  __smA("effectTags 血清素综合征包随药数加深", (function () {
    const one = effectTags(core_collection__cons(fluvoxamine(), core_collection__emptyList())).find(x => x.name === "血清素综合征");
    const trio = effectTags(core_collection__cons(fluvoxamine(), core_collection__cons(sertraline(), core_collection__cons(citalopram(), core_collection__emptyList())))).find(x => x.name === "血清素综合征");
    return one.level === 1 && one.pack === true && trio.level === 3 && trio.acute === true;
  })());
  __smA("effectTags 排序 红 > 黄 > 白", (function () {
    const ts = effectTags(core_collection__cons(morphine(), core_collection__cons(fluvoxamine(), core_collection__emptyList())));
    let ok = true;
    for (let i = 1; i < ts.length; i++) if (ts[i - 1].level < ts[i].level) ok = false;
    return ok && ts[0].level === 3 && ts[ts.length - 1].level === 1;
  })());
  __smA("resp字色随底色（>=7 白字）", respTextColor(6) === "#4E342E" && respTextColor(7) === "#FFFFFF" && respTextColor(9) === "#FFFFFF");
  __smA("routeOf 回退口服", routeOf("未知", core_collection__emptyList()) === "口服");
  __smA("firstRoute", firstRoute(core_collection__cons(PkProfile("口服", "常释", 15, "不稳", "一级", "对数", 0.5, core_collection__emptyList()), core_collection__emptyList())) === "口服");
  __smA("ledger 往返", (function () { saveLedger([DoseRec("测试药", 50.0, 123)]); const b = loadLedger(); return b.length === 1 && b[0].drug === "测试药" && b[0].amount === 50 && b[0].time === 123; })());

  __fs.writeFileSync("sm-result.txt", "PASSES=" + __smPass + " FAILS=" + __smFail + "\n" + __lines.join("\n"), "utf-8");
  process.exit(__smFail > 0 ? 1 : 0);
})();
