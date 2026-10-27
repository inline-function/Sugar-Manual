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
  function __smWalk(node, out) { out.push(node); if (Array.isArray(node.children)) node.children.forEach((c) => __smWalk(c, out)); return out; }
  function __smNodes(root) { return __smWalk(root, []); }
  function __smRoot(i) { return JSON.parse(__smLayouts[i === undefined ? __smLayouts.length - 1 : i]); }
  function __smBtnTexts(root) {
    return __smNodes(root).filter((n) => n.tag === "Button" && Array.isArray(n.children))
      .map((n) => (n.children[0] && n.children[0].text) || "");
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
    return __smNodes(root).filter((n) => n.tag === "Button" && Array.isArray(n.children))
      .find((n) => (n.children[0] && String(n.children[0].text) || "").indexOf(part) >= 0);
  }

  // ---------- 首屏（空台账） ----------
  __smA("首次 layout", __smLayouts.length === 1);
  const root0 = __smRoot(0);
  __smA("根 4 区", root0.children.length === 4, JSON.stringify(root0.children.map((c) => c.tag)));
  const topNode = root0.children[0];
  __smA("顶栏 Box", topNode.tag === "Box", "got " + topNode.tag);
  __smA("顶栏横向", topNode.orientation === "horizontal");
  __smA("顶栏薄荷绿底", topNode.backgroundColor === "#B2DFDB", "got " + topNode.backgroundColor);
  __smA("顶栏标题", __smHasText(root0, "语法糖手册"));
  __smA("顶栏副标题 V1.0", __smNodes(root0).some((n) => n.tag === "Text" && n.text.indexOf("V1.0") >= 0));
  __smA("顶栏三按钮", ["文档", "药物", "设置"].every((t) => __smBtnTexts(root0).includes(t)), "got " + JSON.stringify(__smBtnTexts(root0)));
  const chartNode = __smNodes(root0).find((n) => n.tag === "Chart");
  __smA("Chart 存在", !!chartNode);
  __smA("Chart line", chartNode.kind === "line");
  __smA("Chart 主色薄荷绿", chartNode.color === "#00695C", "got " + chartNode.color);
  __smA("Chart 填充上半区 Height(1080)", chartNode.modifier && chartNode.modifier.tag === "Height" && chartNode.modifier["0"] === 1080, "got " + JSON.stringify(chartNode.modifier));
  __smA("Chart 13 点", chartNode.points.length === 13, "got " + chartNode.points.length);
  const texts0 = __smNodes(root0).filter((n) => n.tag === "Text").map((n) => n.text);
  __smA("预警无区标题", !texts0.includes("相互作用预警"));
  __smA("预警无组标题", !texts0.some((s) => String(s).indexOf("危险") === 0 || String(s).indexOf("警告") === 0));
  __smA("标签名不带权重", !texts0.some((s) => /^\S+ \d+/.test(String(s))), "got " + JSON.stringify(texts0.slice(0, 12)));
  __smA("危险标签 恶心", texts0.includes("恶心"));
  const tagBoxes = __smNodes(root0).filter((n) => n.tag === "Box" && n.backgroundColor && ["#F6D5D5", "#FEEED3", "#DBEFDC"].includes(n.backgroundColor));
  __smA("三级标签底色存在", tagBoxes.length >= 2, "got " + tagBoxes.map((b) => b.backgroundColor).join(","));
  const dangerTag = tagBoxes.find((b) => b.backgroundColor === "#F6D5D5");
  __smA("危险标签含恶心", dangerTag && dangerTag.children.some((c) => c.text === "恶心" && c.color === "#C62828"), "got " + JSON.stringify(dangerTag && dangerTag.children.map((c) => c.text)));
  __smA("标签横向流", __smNodes(root0).some((n) => n.tag === "Box" && n.orientation === "horizontal" && n.scrollable === true));
  __smA("记录区标题", texts0.includes("服药记录"));
  __smA("空台账占位", texts0.includes("暂无服药记录"));
  __smA("底部 96dp 留白", __smNodes(root0).some((n) => n.tag === "Box" && n.modifier && n.modifier.tag === "Height" && n.modifier["0"] === 96));
  const fab0 = __smNodes(root0).find((n) => n.tag === "FloatingButton");
  __smA("FAB 存在", !!fab0);
  __smA("FAB 文案", fab0.children[0].text === "＋服用药物", "got " + fab0.children[0].text);
  const drugPageBtn = __smFindButton(root0, "药物");
  __smA("药物按钮存在", !!drugPageBtn);
  __smCall(drugPageBtn, "onClick");
  __smA("药物按钮 toast", __smLastToast === "药物页：当前视图协议无多页导航", "got " + __smLastToast);

  // ---------- FAB 开表单 ----------
  const before = __smLayouts.length;
  __smA("FAB 回调执行", __smCall(fab0, "onClick"));
  const root1 = __smRoot();
  __smA("FAB 后新 layout", __smLayouts.length === before + 1, "got " + __smLayouts.length + " vs " + (before + 1));
  __smA("表单标题 ＋服用药物", __smHasText(root1, "＋服用药物"));
  __smA("表单副标题", __smHasText(root1, "选择药物并填写剂量（mg）"));
  __smA("表单药物标签", __smHasText(root1, "药物"));
  __smA("空台账提示消失", !__smHasText(root1, "尚无服药记录"));
  const listHolder = __smNodes(root1).find((n) => n.tag === "Box" && n.modifier && n.modifier.tag === "Height" && n.modifier["0"] === 168);
  __smA("药物列表定高 168", !!listHolder, "missing Height(168) holder");
  __smA("药物列表可滚", listHolder && listHolder.scrollable === true);
  const rowBtns = __smNodes(root1).filter((n) => n.tag === "Button" && Array.isArray(n.children) && n.children[0] && String(n.children[0].text).indexOf("\n") >= 0);
  __smA("药物行 2 行", rowBtns.length === 2, "got " + rowBtns.map((b) => b.children[0].text.split("\n")[0]).join(","));
  __smA("行 0 氟伏沙明未选", rowBtns.some((b) => b.children[0].text.indexOf("氟伏沙明") === 0 && b.children[0].bold === false), "got " + JSON.stringify(rowBtns.map((b) => b.children[0].text)));
  __smA("行 1 帕罗西汀", rowBtns.some((b) => b.children[0].text.indexOf("帕罗西汀") === 0));
  __smA("行副标题：氟伏沙明", rowBtns.some((b) => b.children[0].text.indexOf("\n处方药 · T½ 15h · F=50% · 口服") >= 0), "got " + JSON.stringify(rowBtns.map((b) => b.children[0].text)));
  __smA("行副标题：帕罗西汀", rowBtns.some((b) => b.children[0].text.indexOf("\n处方药 · T½ 24h · F=100% · 口服") >= 0), "got " + JSON.stringify(rowBtns.map((b) => b.children[0].text)));
  __smA("默认未选药", rowBtns.every((b) => b.children[0].bold === false && b.children[0].text.charAt(0) !== "✓"));
  const tf = __smNodes(root1).find((n) => n.tag === "TextField");
  __smA("TextField 存在", !!tf);
  __smA("TextField 占位符", tf.placeholder === "剂量 (mg)", "got " + tf.placeholder);
  __smA("TextField 数字键盘", tf.keyboard === "number");
  __smA("TextField 草稿空", tf.text === "");
  __smA("确认/取消按钮", __smBtnTexts(root1).includes("确认") && __smBtnTexts(root1).includes("取消"));

  // ---------- 四级校验 ----------
  const cBtn = __smFindButton(root1, "确认");
  const lenA = __smLayouts.length;
  __smCall(cBtn, "onClick");
  __smA("未选药不刷新", __smLayouts.length === lenA);
  __smA("未选药 toast", __smLastToast === "请先选择药物", "got " + __smLastToast);
  __smA("表单保持开", __smHasText(__smRoot(), "＋服用药物"));

  const row1Btn = __smFindButton(__smRoot(), "帕罗西汀");
  __smA("选药回调执行", __smCall(row1Btn, "onClick"));
  const root2 = __smRoot();
  const selBtn = __smFindButton(root2, "帕罗西汀");
  __smA("选中 ✓ 前缀", selBtn.children[0].text.indexOf("✓ 帕罗西汀") === 0, "got " + selBtn.children[0].text);
  __smA("选中加粗", selBtn.children[0].bold === true);
  const unselBtn = __smFindButton(root2, "氟伏沙明");
  __smA("未选无 ✓", unselBtn.children[0].text.indexOf("✓") === -1 && unselBtn.children[0].bold === false);

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
  __smA("表单已关", !__smHasText(root3, "选择药物并填写剂量（mg）"));
  __smA("FAB 复原", __smNodes(root3).find((n) => n.tag === "FloatingButton").children[0].text === "＋服用药物");
  const ledger = JSON.parse(__smStore["ledger"]);
  __smA("台账 1 条", ledger.length === 1, "got " + __smStore["ledger"]);
  __smA("台账药名", ledger[0].drug === "帕罗西汀");
  __smA("台账剂量 2", ledger[0].amount === 2, "got " + ledger[0].amount);
  __smA("台账时间合理", Math.abs(ledger[0].time - tBefore) < 60000);
  const cardBg = __smNodes(root3).find((n) => n.tag === "Box" && n.backgroundColor === "#E7E0EC");
  __smA("卡片 surfaceVariant 底", !!cardBg);
  __smA("卡片全宽 1032", cardBg.modifier && cardBg.modifier.tag === "Width" && cardBg.modifier["0"] === 1032, "got " + JSON.stringify(cardBg.modifier));
  const barBox = __smNodes(cardBg).find((n) => n.tag === "Box" && n.backgroundColor === "#FFC107");
  __smA("药名竖条金色", !!barBox, "no #FFC107 bar");
  __smA("竖条宽 4", barBox.modifier && barBox.modifier.tag === "Width" && barBox.modifier["0"] === 4, "got " + JSON.stringify(barBox.modifier));
  __smA("竖条后有 Text 撑高", barBox.children.some((c) => c.tag === "Text"));
  const cardTexts = __smNodes(cardBg).filter((n) => n.tag === "Text").map((n) => n.text);
  __smA("卡药名", cardTexts.includes("帕罗西汀"), "got " + JSON.stringify(cardTexts));
  __smA("卡剂量", cardTexts.includes("2 mg"));
  __smA("卡给药时间/方式", cardTexts.some((s) => String(s).indexOf("给药时间 ") === 0 && String(s).indexOf(" · 口服") >= 0), "got " + JSON.stringify(cardTexts));
  __smA("记录区标题仍在", __smHasText(root3, "服药记录"));
  const chart3 = __smNodes(root3).find((n) => n.tag === "Chart");
  __smA("曲线 13 点", chart3.points.length === 13);
  __smA("曲线末点=剂量×F", Math.abs(chart3.points[12] - 2 * trackBio()) < 1e-6, "got " + chart3.points[12]);

  // ---------- 取消 ----------
  const fab3 = __smNodes(root3).find((n) => n.tag === "FloatingButton");
  __smCall(fab3, "onClick");
  const root4 = __smRoot();
  __smA("再开表单", __smHasText(root4, "选择药物并填写剂量（mg）"));
  __smA("再开默认未选药", __smNodes(root4).filter((n) => n.tag === "Button" && n.children[0] && String(n.children[0].text).indexOf("\n") >= 0).every((b) => b.children[0].bold === false));
  const cancelBtn = __smFindButton(root4, "取消");
  __smCall(cancelBtn, "onClick");
  const root5 = __smRoot();
  __smA("取消后表单关", !__smHasText(root5, "选择药物并填写剂量（mg）"));
  __smA("取消台账不变", JSON.parse(__smStore["ledger"]).length === 1);
  __smA("取消草稿复位", __smStore["sm_dose"] === "");
  __smA("取消未选复位", __smStore["sm_pick"] === "");

  // ---------- 纯函数层 ----------
  __smA("tagBg 三级", tagBg(3) === "#F6D5D5" && tagBg(2) === "#FEEED3" && tagBg(1) === "#DBEFDC");
  __smA("tagFg 三级", tagFg(3) === "#C62828" && tagFg(2) === "#E65100" && tagFg(1) === "#2E7D32");
  __smA("drugColor SSRI 金", drugColor("氟伏沙明") === "#FFC107" && drugColor("帕罗西汀") === "#FFC107" && drugColor("舍曲林") === "#FFC107");
  __smA("drugColor 回退绿", drugColor("未知药") === "#00695C");
  __smA("cardWidth", cardWidth(1080) === 1032);
  __smA("drugDetail", drugDetail(fluvoxamine()) === "处方药 · T½ 15h · F=50% · 口服", "got " + drugDetail(fluvoxamine()));
  __smA("rowText 选中", rowText(fluvoxamine(), 0, 0, true).indexOf("✓ 氟伏沙明\n") === 0);
  __smA("rowText 未选", rowText(fluvoxamine(), 0, 1, true).indexOf("氟伏沙明\n") === 0);
  __smA("rowBold", rowBold(1, 1, true) === true && rowBold(1, 1, false) === false && rowBold(0, 1, true) === false);
  __smA("itemsAt 过滤", itemsAt(3, [Trigger("a", 8), Trigger("b", 5), Trigger("c", 2)]).length === 1, "got " + JSON.stringify(itemsAt(3, [Trigger("a", 8), Trigger("b", 5), Trigger("c", 2)])));
  __smA("alertItems 降序混排", (function () { const ts = alertItems(Alert(3, [Trigger("恶", 8), Trigger("失", 6), Trigger("小", 4)])); return ts.length === 3 && ts[0].score === 8 && ts[2].score === 4; })());
  __smA("routeOf 回退口服", routeOf("未知", core_collection__emptyList()) === "口服");
  __smA("firstRoute", firstRoute(core_collection__cons(PkProfile("口服", "常释", 15, "不稳", "一级", "对数", 0.5, core_collection__emptyList()), core_collection__emptyList())) === "口服");
  __smA("ledger 往返", (function () { saveLedger([DoseRec("测试药", 50.0, 123)]); const b = loadLedger(); return b.length === 1 && b[0].drug === "测试药" && b[0].amount === 50 && b[0].time === 123; })());

  __fs.writeFileSync("sm-result.txt", "PASSES=" + __smPass + " FAILS=" + __smFail + "\n" + __lines.join("\n"), "utf-8");
  process.exit(__smFail > 0 ? 1 : 0);
})();
