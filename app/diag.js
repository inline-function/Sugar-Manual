"use strict";
(() => {

  const __smStore = {};
  const __smLayouts = [];
  globalThis.__dshAndroid = (kind, args) => { switch (kind) { case "layout": __smLayouts.push(args[0]); return null; case "storageGet": return ""; case "storagePut": __smStore[args[0]] = args[1]; return null; case "toast": return null; default: return null; } };
  const None = () => ({ tag: "None" });
  const Some = (a0) => ({ tag: "Some", 0: a0 });
  const __dshtoStr = (v) => {
    if (v === null || v === undefined) return String(v);
    if (Array.isArray(v)) return "[" + v.map(__dshtoStr).join(",") + "]";
    if (typeof v === "object" && Object.keys(v).length === 1 && "tag" in v) return String(v.tag);
    return String(v);
  };
  const __isa = (v, t) => {
    if (t === "Nat" || t === "Int" || t === "Rat") return typeof v === "number";
    if (t === "Str") return typeof v === "string";
    if (t === "Bool") return typeof v === "boolean";
    if (t === "Null") return v === null || (v && v.tag === "Null");
    if (t === "(fn)") return typeof v === "function";
    if (t === "List") return Array.isArray(v);
    return v !== null && typeof v === "object" && v.tag === t;
  };
  const __eq = (a, b) => {
    if (a === b) return true;
    if (typeof a !== "object" || typeof b !== "object" || a === null || b === null) return false;
    if (a.tag !== undefined && a.tag !== b.tag) return false;
    const ka = Object.keys(a), kb = Object.keys(b);
    if (ka.length !== kb.length) return false;
    return ka.every(k => __eq(a[k], b[k]));
  };
  const __dshAndroid = (typeof globalThis.__dshAndroid === 'function')
    ? globalThis.__dshAndroid
    : (kind, args) => {
        if (kind === "log") console.log(...args);
        return null;
      };
  function __dshViewTree(x){
    if (Array.isArray(x)) return x.map(__dshViewTree);
    if (x && typeof x === "object") {
      var o = {}; for (var k in x) o[k] = __dshViewTree(x[k]);
      var m = o["modifier"];
      if (Array.isArray(m)) { o["modifiers"] = m; delete o["modifier"]; }
      return o;
    }
    return x;
  }
  function __dshCb(base, fn){
    __dshCb.n = (__dshCb.n || 0) + 1;
    var k = base + "_" + __dshCb.n;
    globalThis["__dshFn_" + k] = fn;
    return k;
  }
  const AlertDialog = (visible = false, title = "", confirmText = "确定", dismissText = "取消", onConfirm = (_) => {
  return null;
}, onDismiss = (_) => {
  return null;
}, body = () => {
  return null;
}, children = "", recomposion = (_) => {}, __ord = 0) => ({ tag: "AlertDialog", visible: visible, title: title, confirmText: confirmText, dismissText: dismissText, onConfirm: onConfirm, onDismiss: onDismiss, body: body, children: children, recomposion: recomposion, __ord: __ord });
  function Alignment__Center() { return { tag: "Center" }; }
  const Center = Alignment__Center;
  function Alignment__TopStart() { return { tag: "TopStart" }; }
  const TopStart = Alignment__TopStart;
  function Alignment__BottomEnd() { return { tag: "BottomEnd" }; }
  const BottomEnd = Alignment__BottomEnd;
  function Alignment__CenterStart() { return { tag: "CenterStart" }; }
  const CenterStart = Alignment__CenterStart;
  function Alignment__CenterEnd() { return { tag: "CenterEnd" }; }
  const CenterEnd = Alignment__CenterEnd;
  function Alignment__Top() { return { tag: "Top" }; }
  const Top = Alignment__Top;
  function Alignment__Bottom() { return { tag: "Bottom" }; }
  const Bottom = Alignment__Bottom;
  const BottomSheet = (visible = false, onDismiss = (_) => {
  return null;
}, body = () => {
  return null;
}, children = "", recomposion = (_) => {}, __ord = 0) => ({ tag: "BottomSheet", visible: visible, onDismiss: onDismiss, body: body, children: children, recomposion: recomposion, __ord: __ord });
  const Box = (modifier = core_collection__emptyList(), contentAlignment = Center(), orientation = "vertical", backgroundColor = "", scrollable = false, spacing = 0, arrangement = "Start", wrap = false, onClick = (_) => {
  return null;
}, body = () => {
  return null;
}, children = "", recomposion = (_) => {}, __ord = 0) => ({ tag: "Box", modifier: modifier, contentAlignment: contentAlignment, orientation: orientation, backgroundColor: backgroundColor, scrollable: scrollable, spacing: spacing, arrangement: arrangement, wrap: wrap, onClick: onClick, body: body, children: children, recomposion: recomposion, __ord: __ord });
  const Button = (modifier = core_collection__emptyList(), label = "", style = "filled", enabled = true, onClick = (_) => {
  return null;
}, body = () => {
  return null;
}, children = "", recomposion = (_) => {}, __ord = 0) => ({ tag: "Button", modifier: modifier, label: label, style: style, enabled: enabled, onClick: onClick, body: body, children: children, recomposion: recomposion, __ord: __ord });
  const Card = (modifier = core_collection__emptyList(), cornerRadius = 12, elevation = 1, backgroundColor = "", onClick = (_) => {
  return null;
}, body = () => {
  return null;
}, children = "", recomposion = (_) => {}, __ord = 0) => ({ tag: "Card", modifier: modifier, cornerRadius: cornerRadius, elevation: elevation, backgroundColor: backgroundColor, onClick: onClick, body: body, children: children, recomposion: recomposion, __ord: __ord });
  const Series = (label, color, points) => ({ tag: "Series", label: label, color: color, points: points });
  const Chart = (kind = "line", series = core_collection__emptyList(), points = core_collection__emptyList(), labels = core_collection__emptyList(), color = "", legend = true, badge = "估算", modifier = core_collection__emptyList(), body = () => {
  return null;
}, children = "", recomposion = (_) => {}, __ord = 0) => ({ tag: "Chart", kind: kind, series: series, points: points, labels: labels, color: color, legend: legend, badge: badge, modifier: modifier, body: body, children: children, recomposion: recomposion, __ord: __ord });
  const Checkbox = (checked = false, text = "", onToggle = (b) => {
  return null;
}, modifier = core_collection__emptyList(), body = () => {
  return null;
}, children = "", recomposion = (_) => {}, __ord = 0) => ({ tag: "Checkbox", checked: checked, text: text, onToggle: onToggle, modifier: modifier, body: body, children: children, recomposion: recomposion, __ord: __ord });
  const FloatingButton = (label = "", color = "#6750A4", cornerRadius = 28, icon = "", text = "", onClick = (_) => {
  return null;
}, modifier = core_collection__emptyList(), body = () => {
  return null;
}, children = "", recomposion = (_) => {}, __ord = 0) => ({ tag: "FloatingButton", label: label, color: color, cornerRadius: cornerRadius, icon: icon, text: text, onClick: onClick, modifier: modifier, body: body, children: children, recomposion: recomposion, __ord: __ord });
  const Icon = (name = "add", size = 24, tint = "", modifier = core_collection__emptyList(), children = "", recomposion = (_) => {}, __ord = 0) => ({ tag: "Icon", name: name, size: size, tint: tint, modifier: modifier, children: children, recomposion: recomposion, __ord: __ord });
  const Image = (name = "", modifier = core_collection__emptyList(), body = () => {
  return null;
}, children = "", recomposion = (_) => {}, __ord = 0) => ({ tag: "Image", name: name, modifier: modifier, body: body, children: children, recomposion: recomposion, __ord: __ord });
  function MKind__Plain() { return { tag: "Plain" }; }
  const Plain = MKind__Plain;
  function MKind__FillMaxSize() { return { tag: "FillMaxSize" }; }
  const FillMaxSize = MKind__FillMaxSize;
  function MKind__WrapContent() { return { tag: "WrapContent" }; }
  const WrapContent = MKind__WrapContent;
  function MKind__Padding(a0) { return { tag: "Padding", 0: a0 }; }
  const Padding = MKind__Padding;
  function MKind__Width(a0) { return { tag: "Width", 0: a0 }; }
  const Width = MKind__Width;
  function MKind__Height(a0) { return { tag: "Height", 0: a0 }; }
  const Height = MKind__Height;
  function MKind__FillWidth() { return { tag: "FillWidth" }; }
  const FillWidth = MKind__FillWidth;
  function MKind__FractionWidth(a0) { return { tag: "FractionWidth", 0: a0 }; }
  const FractionWidth = MKind__FractionWidth;
  function MKind__FillHeight() { return { tag: "FillHeight" }; }
  const FillHeight = MKind__FillHeight;
  function MKind__FractionHeight(a0) { return { tag: "FractionHeight", 0: a0 }; }
  const FractionHeight = MKind__FractionHeight;
  function MKind__Padding4(a0, a1, a2, a3) { return { tag: "Padding4", 0: a0, 1: a1, 2: a2, 3: a3 }; }
  const Padding4 = MKind__Padding4;
  function MKind__PaddingH(a0) { return { tag: "PaddingH", 0: a0 }; }
  const PaddingH = MKind__PaddingH;
  function MKind__PaddingV(a0) { return { tag: "PaddingV", 0: a0 }; }
  const PaddingV = MKind__PaddingV;
  function MKind__Weight(a0) { return { tag: "Weight", 0: a0 }; }
  const Weight = MKind__Weight;
  function MKind__Background(a0) { return { tag: "Background", 0: a0 }; }
  const Background = MKind__Background;
  function MKind__Clip(a0) { return { tag: "Clip", 0: a0 }; }
  const Clip = MKind__Clip;
  function MKind__Alpha(a0) { return { tag: "Alpha", 0: a0 }; }
  const Alpha = MKind__Alpha;
  function MKind__Flash() { return { tag: "Flash" }; }
  const Flash = MKind__Flash;
  function one(x) {
    return [x, ...([])];
  }
  function chain(a, b) {
    return (() => {
  const __s0 = a;
  if (((Array.isArray(__s0) && __s0.length === 0))) {  return b; }
  if (((Array.isArray(__s0) && __s0.length >= 1))) { const h = __s0[0]; const t = __s0.slice(1);  return [h, ...(chain(t, b))]; }
   return b;
})();
  }
  function width$Nat(n) {
    return one(Width(n));
  }
  function width$List(m, n) {
    return chain(m, one(Width(n)));
  }
  function height$Nat(n) {
    return one(Height(n));
  }
  function height$List(m, n) {
    return chain(m, one(Height(n)));
  }
  function fillMaxSize$v0() {
    return one(FillMaxSize());
  }
  function fillMaxSize$List(m) {
    return chain(m, one(FillMaxSize()));
  }
  function wrapContent$v0() {
    return one(WrapContent());
  }
  function wrapContent$List(m) {
    return chain(m, one(WrapContent()));
  }
  function fillWidth$v0() {
    return one(FillWidth());
  }
  function fillWidth$List(m) {
    return chain(m, one(FillWidth()));
  }
  function fillHeight$v0() {
    return one(FillHeight());
  }
  function fillHeight$List(m) {
    return chain(m, one(FillHeight()));
  }
  function fractionWidth$Rat(f) {
    return one(FractionWidth(f));
  }
  function fractionWidth$List(m, f) {
    return chain(m, one(FractionWidth(f)));
  }
  function fractionHeight$Rat(f) {
    return one(FractionHeight(f));
  }
  function fractionHeight$List(m, f) {
    return chain(m, one(FractionHeight(f)));
  }
  function weight$Rat(w) {
    return one(Weight(w));
  }
  function weight$List(m, w) {
    return chain(m, one(Weight(w)));
  }
  function padding$Nat(n) {
    return one(Padding(n));
  }
  function padding$List(m, n) {
    return chain(m, one(Padding(n)));
  }
  function padding4$Nat(l, t, r, b) {
    return one(Padding4(l, t, r, b));
  }
  function padding4$List(m, l, t, r, b) {
    return chain(m, one(Padding4(l, t, r, b)));
  }
  function paddingHV$Nat(h, v) {
    return chain(one(PaddingH(h)), one(PaddingV(v)));
  }
  function paddingHV$List(m, h, v) {
    return chain(m, chain(one(PaddingH(h)), one(PaddingV(v))));
  }
  function paddingH$Nat(h) {
    return one(PaddingH(h));
  }
  function paddingH$List(m, h) {
    return chain(m, one(PaddingH(h)));
  }
  function paddingV$Nat(v) {
    return one(PaddingV(v));
  }
  function paddingV$List(m, v) {
    return chain(m, one(PaddingV(v)));
  }
  function background$Str(c) {
    return one(Background(c));
  }
  function background$List(m, c) {
    return chain(m, one(Background(c)));
  }
  function clip$Nat(n) {
    return one(Clip(n));
  }
  function clip$List(m, n) {
    return chain(m, one(Clip(n)));
  }
  function alpha$Rat(a) {
    return one(Alpha(a));
  }
  function alpha$List(m, a) {
    return chain(m, one(Alpha(a)));
  }
  function flash$v0() {
    return one(Flash());
  }
  function flash$List(m) {
    return chain(m, one(Flash()));
  }
  const Progress = (value = 0, indeterminate = false, modifier = core_collection__emptyList(), body = () => {
  return null;
}, children = "", recomposion = (_) => {}, __ord = 0) => ({ tag: "Progress", value: value, indeterminate: indeterminate, modifier: modifier, body: body, children: children, recomposion: recomposion, __ord: __ord });
  const Slider = (value = 0.0, from = 0.0, to = 1.0, enabled = true, onChange = (v) => {
  return null;
}, modifier = core_collection__emptyList(), body = () => {
  return null;
}, children = "", recomposion = (_) => {}, __ord = 0) => ({ tag: "Slider", value: value, from: from, to: to, enabled: enabled, onChange: onChange, modifier: modifier, body: body, children: children, recomposion: recomposion, __ord: __ord });
  const Switch = (checked = false, enabled = true, onToggle = (b) => {
  return null;
}, modifier = core_collection__emptyList(), body = () => {
  return null;
}, children = "", recomposion = (_) => {}, __ord = 0) => ({ tag: "Switch", checked: checked, enabled: enabled, onToggle: onToggle, modifier: modifier, body: body, children: children, recomposion: recomposion, __ord: __ord });
  const Text = (text = "", color = "", size = 0.0, bold = false, italic = false, maxLines = 0, flash = false, modifier = core_collection__emptyList(), children = "", recomposion = (_) => {}, __ord = 0) => ({ tag: "Text", text: text, color: color, size: size, bold: bold, italic: italic, maxLines: maxLines, flash: flash, modifier: modifier, children: children, recomposion: recomposion, __ord: __ord });
  const TextButton = (modifier = core_collection__emptyList(), label = "", enabled = true, onClick = (_) => {
  return null;
}, body = () => {
  return null;
}, children = "", recomposion = (_) => {}, __ord = 0) => ({ tag: "TextButton", modifier: modifier, label: label, enabled: enabled, onClick: onClick, body: body, children: children, recomposion: recomposion, __ord: __ord });
  const TextField = (placeholder = "", text = "", label = "", errText = "", keyboard = "text", onChange = (t) => {
  return null;
}, modifier = core_collection__emptyList(), body = () => {
  return null;
}, children = "", recomposion = (_) => {}, __ord = 0) => ({ tag: "TextField", placeholder: placeholder, text: text, label: label, errText: errText, keyboard: keyboard, onChange: onChange, modifier: modifier, body: body, children: children, recomposion: recomposion, __ord: __ord });
  function primary() {
    return "primary";
  }
  function onPrimary() {
    return "onPrimary";
  }
  function primaryContainer() {
    return "primaryContainer";
  }
  function onPrimaryContainer() {
    return "onPrimaryContainer";
  }
  function secondary() {
    return "secondary";
  }
  function onSecondary() {
    return "onSecondary";
  }
  function secondaryContainer() {
    return "secondaryContainer";
  }
  function onSecondaryContainer() {
    return "onSecondaryContainer";
  }
  function surface() {
    return "surface";
  }
  function onSurface() {
    return "onSurface";
  }
  function surfaceVariant() {
    return "surfaceVariant";
  }
  function onSurfaceVariant() {
    return "onSurfaceVariant";
  }
  function outline() {
    return "outline";
  }
  function outlineVariant() {
    return "outlineVariant";
  }
  function error() {
    return "error";
  }
  function onError() {
    return "onError";
  }
  function errorContainer() {
    return "errorContainer";
  }
  function warning() {
    return "warning";
  }
  function warningContainer() {
    return "warningContainer";
  }
  function info() {
    return "info";
  }
  function infoContainer() {
    return "infoContainer";
  }
  const TimelineView = (times = core_collection__emptyList(), labels = core_collection__emptyList(), orientation = "vertical", color = "", modifier = core_collection__emptyList(), body = () => {
  return null;
}, children = "", recomposion = (_) => {}, __ord = 0) => ({ tag: "TimelineView", times: times, labels: labels, orientation: orientation, color: color, modifier: modifier, body: body, children: children, recomposion: recomposion, __ord: __ord });
  const TopAppBar = (title = "", subtitle = "", color = "primary", body = () => {
  return null;
}, children = "", recomposion = (_) => {}, __ord = 0) => ({ tag: "TopAppBar", title: title, subtitle: subtitle, color: color, body: body, children: children, recomposion: recomposion, __ord: __ord });
  function alprazolam() {
    return Drug("阿普唑仑", "处方药", strList("苯二氮卓类,抗焦虑药,抗惊恐药,镇静药,超说明书（抑郁伴焦虑）"), "阿普唑仑是三唑并苯二氮卓类中效抗焦虑药，用于广泛性焦虑障碍与惊恐障碍；中国常见【超说明书】用法包括抑郁伴焦虑状态与经前综合征。口服后 1-2 小时达峰、起效快，适合急性焦虑发作；经 CYP3A4 代谢（α-羟化）为弱活性产物。与 CYP3A4/2D6 抑制剂（氟伏沙明、氟西汀、帕罗西汀、克拉霉素）合用血药浓度升高、过度镇静风险增加；与阿片类或乙醇合用可致严重呼吸抑制（黑框警告）。短效苯二氮卓骤停戒断症状较重，宜递减停药。", ratedList("强效|GABA-A受体苯二氮卓位点变构调节"), strList("CYP3A4"), core_collection__emptyList(), core_collection__cons(PkProfile("口服", "常释", 11, "1-2h", "一级（CYP3A4 氧化）", "对数", 0.9, core_collection__cons(Rated("极弱", "α-羟基阿普唑仑"), core_collection__emptyList())), core_collection__emptyList()), ratedList("强效|抗焦虑（快速起效）;强效|镇静"), ratedList("强效|抗焦虑;强效|抗惊恐;中效|催眠（焦虑性失眠）"), ratedList("少见|呼吸抑制;常见|嗜睡;常见|头晕"), ratedList("常见|嗜睡;常见|头晕;常见|疲劳;常见|共济失调;常见|记忆损害（顺行性）;常见|性功能障碍;常见|依赖（长期使用）;少见|呼吸抑制（与阿片/酒精合用）;少见|反常兴奋;少见|跌倒（老年）"), ratedList("常见|焦虑反跳;常见|失眠反跳;常见|震颤;常见|出汗;少见|癫痫发作（骤停）;罕见|谵妄（骤停高剂量）"), ratedList(">10mg|呼吸抑制"));
  }
  function citalopram() {
    return Drug("西酞普兰", "处方药", strList("选择性血清素再摄取抑制剂,SSRIs,五朵金花,抗抑郁药,抗焦虑药"), "西酞普兰是酞酐类 SSRI 消旋体，5-HT 再摄取抑制活性集中于左旋异构体（即艾司西酞普兰）。用于抑郁症与各类焦虑障碍。对 CYP 酶抑制极弱、药物相互作用少，适合多药联用与老年患者；但剂量依赖性 QT 间期延长明确，成人最大日剂量 40 mg，【超说明书】高剂量须评估电解质与心电监测。经 CYP3A4 与 CYP2C19（次要 CYP2D6）代谢为弱活性去甲西酞普兰。", ratedList("强效|血清素转运体抑制（左旋体）;极弱|正肾素转运体抑制"), strList("CYP3A4,CYP2C19,CYP2D6"), ratedList("极弱|CYP2D6可逆性抑制"), core_collection__cons(PkProfile("口服", "常释", 35, "4h", "一级", "对数", 0.8, core_collection__cons(Rated("极弱", "去甲西酞普兰"), core_collection__emptyList())), core_collection__emptyList()), ratedList("弱效|镇静"), ratedList("极强|抗抑郁;强效|抗焦虑"), ratedList("罕见|血清素综合征"), ratedList("十分常见|恶心;常见|嗜睡;常见|失眠;常见|出汗;常见|口干;常见|性功能障碍;常见|头晕;少见|QT间期延长（剂量依赖）;少见|低血钠症;少见|自杀冲动"), ratedList("常见|头晕;常见|失眠;常见|恶心;常见|焦虑;少见|电击样感觉"), ratedList(">400mg|心律失常（QT延长）;>600mg|血清素综合征"));
  }
  function clonazepam() {
    return Drug("氯硝西泮", "处方药", strList("苯二氮卓类,抗焦虑药,抗癫痫药,助眠药,超说明书（躁狂辅助/惊恐/静坐不能）"), "氯硝西泮是长效硝基苯二氮卓类，中国说明书适应证为癫痫（失神发作、肌阵挛发作）与焦虑状态。常用【超说明书】场景包括双相障碍躁狂或混合发作的辅助镇静、惊恐障碍、抗精神病药所致静坐不能与快动眼睡眠行为障碍。口服吸收良好，半衰期长（约 20-60 小时），日间残余效应与蓄积明显；经 CYP3A4 代谢。与阿片类或乙醇合用可致严重呼吸抑制（黑框警告）；长期使用产生依赖，骤停可诱发癫痫反跳。", ratedList("强效|GABA-A受体苯二氮卓位点变构调节;强效|提高癫痫发作阈"), strList("CYP3A4"), core_collection__emptyList(), core_collection__cons(PkProfile("口服", "常释", 30, "1-4h", "一级（CYP3A4 硝基还原）", "对数", 0.9, core_collection__emptyList()), core_collection__emptyList()), ratedList("强效|镇静;强效|抗焦虑"), ratedList("强效|抗癫痫;强效|抗焦虑;强效|抗惊恐（超说明书）;强效|躁狂辅助镇静（超说明书）"), ratedList("少见|呼吸抑制;常见|嗜睡;常见|头晕"), ratedList("十分常见|嗜睡;常见|头晕;常见|共济失调;常见|记忆损害（顺行性）;常见|疲劳;常见|依赖（长期使用）;少见|呼吸抑制（与阿片/酒精合用）;少见|行为脱抑制（儿童）;少见|跌倒（老年）"), ratedList("常见|癫痫反跳（骤停）;常见|焦虑反跳;常见|失眠;常见|震颤;少见|戒断性谵妄"), ratedList(">20mg|呼吸抑制"));
  }
  function diazepam() {
    return Drug("地西泮", "处方药", strList("苯二氮卓类,抗焦虑药,催眠药,抗癫痫药,镇静药,肌肉松弛药"), "地西泮是长效 1,4-苯二氮卓类，增强 GABA-A 受体介导的抑制性传导。用于焦虑症、酒精戒断综合征、肌肉痉挛、麻醉前给药与癫痫持续状态（静脉注射）。口服吸收快而接近完全，血浆蛋白结合率高（约 98%），经 CYP3A4 与 CYP2C19 氧化为活性代谢物去甲地西泮（半衰期 40-100 小时），反复给药后蓄积明显、次日宿醉感重。与阿片类镇痛药或乙醇合用可产生严重呼吸抑制乃至致死（FDA 黑框警告），长期使用产生耐受、依赖与戒断综合征。", ratedList("强效|GABA-A受体苯二氮卓位点变构调节;强效|边缘系统抑制（抗焦虑）;中效|脊髓反射抑制（肌肉松弛）"), strList("CYP3A4,CYP2C19"), core_collection__emptyList(), core_collection__cons(PkProfile("口服", "常释", 36, "1-1.5h", "一级（肝氧化代谢）", "对数", 0.9, core_collection__cons(Rated("强效", "去甲地西泮（活性，半衰期 40-100h）"), core_collection__emptyList())), core_collection__emptyList()), ratedList("强效|镇静;强效|抗焦虑（快速起效）"), ratedList("强效|抗焦虑;强效|抗癫痫（持续状态首选静注）;强效|酒精戒断;中效|肌肉松弛"), ratedList("少见|呼吸抑制;常见|嗜睡;常见|头晕"), ratedList("十分常见|嗜睡;常见|头晕;常见|疲劳;常见|共济失调;常见|记忆损害（顺行性）;常见|依赖（长期使用）;少见|呼吸抑制（与阿片/酒精合用）;少见|跌倒（老年）;少见|反常兴奋"), ratedList("常见|焦虑反跳;常见|失眠反跳;常见|震颤;常见|出汗;少见|癫痫发作（骤停高剂量）;少见|感知异常"), ratedList(">100mg|呼吸抑制"));
  }
  const Rated = (level, name) => ({ tag: "Rated", level: level, name: name });
  const PkProfile = (route, form, halfLife, tmax, clearance, curve, bio, metabolites) => ({ tag: "PkProfile", route: route, form: form, halfLife: halfLife, tmax: tmax, clearance: clearance, curve: curve, bio: bio, metabolites: metabolites });
  const Drug = (name, category, tags, description, effects, enzymes, inhibitions, pk, acuteTher, chronicTher, acuteAdv, chronicAdv, withdraw, overdose) => ({ tag: "Drug", name: name, category: category, tags: tags, description: description, effects: effects, enzymes: enzymes, inhibitions: inhibitions, pk: pk, acuteTher: acuteTher, chronicTher: chronicTher, acuteAdv: acuteAdv, chronicAdv: chronicAdv, withdraw: withdraw, overdose: overdose });
  function dq() {
    return "\"";
  }
  function jsonEsc(s) {
    return ((((s).split("\\")).join("\\\\")).split(dq())).join(("\"" + dq()));
  }
  function jsonLit(s) {
    return ((dq() + jsonEsc(s)) + dq());
  }
  function jsonField(name, value) {
    return (jsonLit(name) + (":" + value));
  }
  function joinStrs(sep, xs) {
    return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, xs, "", (acc, x) => (__eq(acc, "") ? (() => {
  return x;
})() : (() => {
  return (acc + (sep + x));
})()));
  }
  function joinFields(xs) {
    return (("{" + joinStrs(",", xs)) + "}");
  }
  function joinJsonElems(xs) {
    return (("[" + joinStrs(",", xs)) + "]");
  }
  function encRatedList(xs) {
    return joinJsonElems(dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, xs, (r) => encRated(r)));
  }
  function encRated(r) {
    return joinFields(core_collection__cons(jsonField("level", jsonLit(r.level)), core_collection__cons(jsonField("name", jsonLit(r.name)), core_collection__emptyList())));
  }
  function encStrList(xs) {
    return joinJsonElems(dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, xs, (t) => jsonLit(t)));
  }
  function encPkList(xs) {
    return joinJsonElems(dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, xs, (p) => encPk(p)));
  }
  function encPk(p) {
    return joinFields(core_collection__cons(jsonField("route", jsonLit(p.route)), core_collection__cons(jsonField("form", jsonLit(p.form)), core_collection__cons(jsonField("halfLife", jsonLit(__dshtoStr(p.halfLife))), core_collection__cons(jsonField("tmax", jsonLit(p.tmax)), core_collection__cons(jsonField("clearance", jsonLit(p.clearance)), core_collection__cons(jsonField("curve", jsonLit(p.curve)), core_collection__cons(jsonField("bio", __dshtoStr(p.bio)), core_collection__cons(jsonField("metabolites", encRatedList(p.metabolites)), core_collection__emptyList())))))))));
  }
  function encDrug(d) {
    return joinFields(core_collection__cons(jsonField("name", jsonLit(d.name)), core_collection__cons(jsonField("category", jsonLit(d.category)), core_collection__cons(jsonField("tags", encStrList(d.tags)), core_collection__cons(jsonField("description", jsonLit(d.description)), core_collection__cons(jsonField("effects", encRatedList(d.effects)), core_collection__cons(jsonField("enzymes", encStrList(d.enzymes)), core_collection__cons(jsonField("inhibitions", encRatedList(d.inhibitions)), core_collection__cons(jsonField("pk", encPkList(d.pk)), core_collection__cons(jsonField("acuteTher", encRatedList(d.acuteTher)), core_collection__cons(jsonField("chronicTher", encRatedList(d.chronicTher)), core_collection__cons(jsonField("acuteAdv", encRatedList(d.acuteAdv)), core_collection__cons(jsonField("chronicAdv", encRatedList(d.chronicAdv)), core_collection__cons(jsonField("withdraw", encRatedList(d.withdraw)), core_collection__cons(jsonField("overdose", encRatedList(d.overdose)), core_collection__emptyList())))))))))))))));
  }
  function encDrugArray(xs) {
    return joinJsonElems(dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, xs, (d) => encDrug(d)));
  }
  function jsonStrField(j, k) {
    return (() => {
  const __s1 = (() => { const __js2 = j; const __jv3 = (__js2 == null) ? undefined : __js2[k]; return (__jv3 === undefined ? None() : Some(__jv3)); })();
  if ((__s1.tag === "Some")) { const v = __s1[0];  return core_collection__getOrElse((() => { const __jv4 = v; return (typeof __jv4 === "string") ? Some(__jv4) : None(); })(), ""); }
  if ((__s1.tag === "None")) {  return ""; }
  return null;
})();
  }
  function jsonStrVal(j) {
    return core_collection__getOrElse((() => { const __jv5 = j; return (typeof __jv5 === "string") ? Some(__jv5) : None(); })(), "");
  }
  function jsonListField(j, k) {
    return (() => {
  const __s6 = (() => { const __js7 = j; const __jv8 = (__js7 == null) ? undefined : __js7[k]; return (__jv8 === undefined ? None() : Some(__jv8)); })();
  if ((__s6.tag === "Some")) { const v = __s6[0];  return core_collection__getOrElse((() => { const __jv9 = v; return Array.isArray(__jv9) ? Some(__jv9) : None(); })(), core_collection__emptyList()); }
  if ((__s6.tag === "None")) {  return core_collection__emptyList(); }
  return null;
})();
  }
  function jsonNatField(j, k) {
    return parseNatOr(jsonStrField(j, k), 0);
  }
  function jsonRatField(j, k) {
    return (() => {
  const __s10 = (() => { const __js11 = j; const __jv12 = (__js11 == null) ? undefined : __js11[k]; return (__jv12 === undefined ? None() : Some(__jv12)); })();
  if ((__s10.tag === "Some")) { const v = __s10[0];  return core_collection__getOrElse((() => { const __jv13 = v; return (typeof __jv13 === "number") ? Some(__jv13) : None(); })(), 0.0); }
  if ((__s10.tag === "None")) {  return 0.0; }
  return null;
})();
  }
  function parseNatOr(s, d) {
    return (() => {
  const __s14 = (() => { const __m15 = String(s).match(/^(0|[1-9][0-9]*)$/); return __m15 ? Some(Number(__m15[0])) : None(); })();
  if ((__s14.tag === "Some")) { const v = __s14[0];  return v; }
  if ((__s14.tag === "None")) {  return d; }
  return null;
})();
  }
  function decRated(j) {
    return Rated(jsonStrField(j, "level"), jsonStrField(j, "name"));
  }
  function decRatedList(j, k) {
    return dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, jsonListField(j, k), (o) => decRated(o));
  }
  function decStrList(j, k) {
    return dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, jsonListField(j, k), (o) => jsonStrVal(o));
  }
  function decPk(j) {
    return PkProfile(jsonStrField(j, "route"), jsonStrField(j, "form"), jsonNatField(j, "halfLife"), jsonStrField(j, "tmax"), jsonStrField(j, "clearance"), jsonStrField(j, "curve"), jsonRatField(j, "bio"), decRatedList(j, "metabolites"));
  }
  function decPkList(j, k) {
    return dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, jsonListField(j, k), (o) => decPk(o));
  }
  function decodeDrug(j) {
    return Drug(jsonStrField(j, "name"), jsonStrField(j, "category"), decStrList(j, "tags"), jsonStrField(j, "description"), decRatedList(j, "effects"), decStrList(j, "enzymes"), decRatedList(j, "inhibitions"), decPkList(j, "pk"), decRatedList(j, "acuteTher"), decRatedList(j, "chronicTher"), decRatedList(j, "acuteAdv"), decRatedList(j, "chronicAdv"), decRatedList(j, "withdraw"), decRatedList(j, "overdose"));
  }
  function drugsKey() {
    return "drugs";
  }
  function saveDrugs(xs) {
    const text = encDrugArray(xs);
    const ok = (() => {
  const __s16 = (() => { try { return Some(JSON.parse(text)); } catch (__e17) { return None(); } })();
  if ((__s16.tag === "Some")) { const j = __s16[0];  return true; }
  if ((__s16.tag === "None")) {  return false; }
  return null;
})();
    if (ok) {
      // unchecked
      const v = (() => {
  const __s18 = (() => { try { return Some(JSON.parse(text)); } catch (__e19) { return None(); } })();
  if ((__s18.tag === "Some")) { const j = __s18[0];  return JSON.stringify(j); }
  if ((__s18.tag === "None")) {  return text; }
  return null;
})();
      // unchecked
      __dshAndroid("storagePut", [drugsKey(), v]);
    }
    return ok;
  }
  function loadDrugs() {
    const s = __dshAndroid("storageGet", [drugsKey()]);
    return (() => {
  const __s20 = (() => { try { return Some(JSON.parse(s)); } catch (__e21) { return None(); } })();
  if ((__s20.tag === "Some")) { const j = __s20[0];  return (() => {
  const __s22 = (() => { const __jv23 = j; return Array.isArray(__jv23) ? Some(__jv23) : None(); })();
  if ((__s22.tag === "Some")) { const items = __s22[0];  return dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, items, (o) => decodeDrug(o)); }
  if ((__s22.tag === "None")) {  return core_collection__emptyList(); }
  return null;
})(); }
  if ((__s20.tag === "None")) {  return core_collection__emptyList(); }
  return null;
})();
  }
  function loadDrugNames() {
    return dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, loadDrugs(), (d) => d.name);
  }
  function escitalopram() {
    return Drug("艾司西酞普兰", "处方药", strList("选择性血清素再摄取抑制剂,SSRIs,五朵金花,抗抑郁药,抗焦虑药"), "艾司西酞普兰是西酞普兰的左旋 S-构型异构体，5-HT 再摄取抑制活性集中、对转运体选择性更高，起效与耐受优于消旋体；用于抑郁症、广泛性焦虑障碍、惊恐障碍与社交焦虑障碍。经 CYP3A4、CYP2C19 与 CYP2D6 代谢，对 CYP2D6 抑制极弱、相互作用少；剂量依赖性 QT 间期延长风险弱于消旋体，但高剂量仍须监测。", ratedList("强效|血清素转运体抑制;极弱|正肾素转运体抑制"), strList("CYP3A4,CYP2C19,CYP2D6"), ratedList("极弱|CYP2D6可逆性抑制"), core_collection__cons(PkProfile("口服", "常释", 30, "4h", "一级", "对数", 0.8, core_collection__cons(Rated("极弱", "去甲基艾司西酞普兰"), core_collection__emptyList())), core_collection__emptyList()), ratedList("弱效|镇静"), ratedList("极强|抗抑郁;强效|抗焦虑;强效|抗惊恐"), ratedList("罕见|血清素综合征"), ratedList("十分常见|恶心;常见|嗜睡;常见|失眠;常见|出汗;常见|性功能障碍;常见|口干;常见|头晕;少见|QT间期延长;少见|低血钠症;少见|自杀冲动"), ratedList("常见|头晕;常见|失眠;常见|恶心;少见|电击样感觉"), ratedList(">400mg|QT间期延长;>600mg|血清素综合征"));
  }
  function estazolam() {
    return Drug("艾司唑仑", "处方药", strList("苯二氮卓类,催眠药,镇静药,助眠药"), "艾司唑仑是三唑并苯二氮卓类短-中效催眠药（中国临床常用），用于失眠的入睡困难与夜间易醒。口服吸收较快，主要经 CYP3A4 代谢（与 CYP3A4 抑制剂如氟伏沙明、克拉霉素、伊曲康唑合用血药浓度升高；与诱导剂如利福平、卡马西平合用疗效下降）；活性代谢物少、蓄积弱于地西泮。与阿片类或乙醇合用可致严重呼吸抑制（黑框警告），高龄与慢阻肺患者更敏感。", ratedList("强效|GABA-A受体苯二氮卓位点变构调节"), strList("CYP3A4"), core_collection__emptyList(), core_collection__cons(PkProfile("口服", "常释", 17, "2-3h", "一级（CYP3A4 氧化）", "对数", 1.0, core_collection__emptyList()), core_collection__emptyList()), ratedList("强效|催眠（入睡）;强效|镇静"), ratedList("强效|入睡困难;强效|睡眠维持"), ratedList("少见|呼吸抑制;常见|嗜睡（次日残余）;常见|头晕"), ratedList("常见|嗜睡;常见|头晕;常见|口干;常见|记忆损害（顺行性）;少见|呼吸抑制（与阿片/酒精合用）;少见|依赖（长期使用）;少见|跌倒（老年）;罕见|幻觉"), ratedList("常见|反跳性失眠（骤停）;常见|焦虑反跳;少见|癫痫发作（骤停高剂量）"), ratedList(">8mg|呼吸抑制"));
  }
  function fluoxetine() {
    return Drug("氟西汀", "处方药", strList("选择性血清素再摄取抑制剂,SSRIs,五朵金花,抗抑郁药,抗强迫药,抗焦虑药,抗贪食药"), "氟西汀是选择性血清素再摄取抑制剂类抗抑郁药，用于抑郁症、强迫症、神经性贪食症与经前烦躁障碍。口服吸收良好，经 CYP2D6 代谢为活性产物去甲氟西汀；母药半衰期长（慢性给药后约 4-6 天）、去甲氟西汀约 7-15 天，是「五朵金花」中消除最慢者，漏服后血药波动小、停药综合征发生率低，但清除受肝功能与合并用药影响显著。氟西汀是 CYP2D6 强抑制剂，可使经 2D6 活化的前体药（可待因、曲马多）镇痛效应下降，并升高三环类抗抑郁药、部分抗精神病药血药浓度。", ratedList("强效|血清素转运体抑制;极弱|正肾素转运体抑制;极弱|多巴胺转运体抑制"), strList("CYP2D6"), ratedList("极强|CYP2D6可逆性抑制;中效|CYP2C19可逆性抑制;弱效|CYP3A4可逆性抑制;弱效|CYP2C9可逆性抑制"), core_collection__cons(PkProfile("口服", "常释", 96, "6-8h", "一级（慢）", "对数", 0.6, core_collection__cons(Rated("强效", "去甲氟西汀（半衰期约 7-15 天）"), core_collection__emptyList())), core_collection__emptyList()), ratedList("弱效|激活（早期）"), ratedList("极强|抗抑郁;强效|抗强迫;强效|抗焦虑;强效|抗贪食"), ratedList("罕见|血清素综合征"), ratedList("十分常见|恶心;常见|失眠;常见|头痛;常见|腹泻;常见|厌食;常见|出汗;常见|性功能障碍;少见|焦虑（早期）;少见|低血钠症;少见|皮疹;少见|自杀冲动（年轻成人）;罕见|躁狂转换"), ratedList("少见|停药综合征（代谢物缓释）;常见|恶心;少见|头晕"), ratedList(">1500mg|恶心与躁动"));
  }
  function ratedFrom(s) {
    const p = (s).split("|");
    return Rated(nthStr(p, 0), nthStr(p, 1));
  }
  function nthStr(xs, n) {
    return core_collection__getOrElse((() => { const __xs24 = xs; const __ix24 = n; return __ix24 >= 0 && __ix24 < __xs24.length ? Some(__xs24[__ix24]) : None(); })(), "");
  }
  function ratedList(text) {
    return dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, (text).split(";"), (e) => ratedFrom(e));
  }
  function strList(text) {
    return (text).split(",");
  }
  function fluvoxamine() {
    return Drug("氟伏沙明", "处方药", strList("选择性血清素再摄取抑制剂,SSRIs,五朵金花,抗抑郁药,抗强迫药,抗焦虑药,助眠药"), "氟伏沙明是一种选择性血清素再摄取抑制剂类抗抑郁药，主要用于治疗抑郁症和强迫症。它通过抑制中枢神经突触对血清素的再摄取，提高突触间隙递质浓度，从而发挥抗抑郁、抗焦虑作用。常见不良反应为恶心、呕吐等胃肠道症状，通常服药两周后减轻。", ratedList("强效|血清素转运体抑制;极弱|正肾素转运体抑制;强效|激动 σ1 受体;极弱|α1-肾上腺素能拮抗"), strList("CYP2D6,CYP1A2"), ratedList("强效|CYP1A2可逆性抑制;强效|CYP2C19可逆性抑制;弱效|CYP2C9可逆性抑制;弱效|CYP3A4可逆性抑制"), core_collection__cons(PkProfile("口服", "常释", 15, "不稳定", "一级", "对数", 0.5, core_collection__emptyList()), core_collection__emptyList()), ratedList("强效|镇静"), ratedList("极强|抗强迫;强效|抗抑郁;中效|抗焦虑"), ratedList("罕见|血清素综合征"), ratedList("十分常见|恶心;常见|嗜睡;常见|失眠;常见|腹泻;常见|厌食;常见|头晕;常见|出汗;常见|震颤;少见|自杀冲动;罕见|低血钠症"), ratedList("常见|头晕;常见|失眠;常见|激越;常见|焦虑;常见|易激惹;常见|情绪不稳;常见|恶心;常见|腹泻;少见|意识模糊"), ratedList(">1000mg|血清素综合征"));
  }
  const Home = () => ({ tag: "Home",  });
  function paroxetine() {
    return Drug("帕罗西汀", "处方药", strList("SSRIs,抗抑郁药"), "演示用最小资料（预警引擎联用示例），非完整药物资料。", ratedList("强效|血清素转运体抑制"), strList("CYP2D6"), ratedList("极强|CYP2D6"), core_collection__emptyList(), core_collection__emptyList(), ratedList("强效|抗抑郁"), ratedList("常见|恶心"), ratedList("少见|嗜睡;常见|失眠"), core_collection__emptyList(), core_collection__emptyList());
  }
  const DoseRec = (drug, amount, time) => ({ tag: "DoseRec", drug: drug, amount: amount, time: time });
  function msPerHour() {
    return 3600000;
  }
  function windowHours() {
    return 48;
  }
  function stepHours() {
    return 4;
  }
  function minusWindow(nowMs) {
    return (nowMs - (windowHours() * msPerHour()));
  }
  function ledgerDoses(recs, nowMs) {
    return dosesInWindow(recs, minusWindow(nowMs), nowMs);
  }
  function dosesInWindow(recs, t0, nowMs) {
    return (__eq((recs).length, 0) ? (() => {
  return demoDoses();
})() : (() => {
  return dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, (recs).filter((r) => inWindow(r, t0, nowMs)), (r) => Dose(r.amount, hourOf(r, t0)));
})());
  }
  function inWindow(r, t0, nowMs) {
    return ((r.time >= t0) && (r.time <= nowMs));
  }
  function hourOf(r, t0) {
    return ((r.time - t0) / msPerHour());
  }
  function recentDrugs(recs) {
    return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, recs, core_collection__emptyList(), (acc, r) => ((acc).includes(r.drug) ? (() => {
  return acc;
})() : (() => {
  return (acc).concat(core_collection__cons(r.drug, core_collection__emptyList()));
})()));
  }
  function recentDrug(recs) {
    return (() => {
  const __s25 = recs;
  if (((Array.isArray(__s25) && __s25.length === 0))) {  return "氟伏沙明"; }
  if (((Array.isArray(__s25) && __s25.length >= 1))) { const r = __s25[0];  return r.drug; }
  return null;
})();
  }
  function encDose(r) {
    return joinFields(core_collection__cons(jsonField("drug", jsonLit(r.drug)), core_collection__cons(jsonField("amount", __dshtoStr(r.amount)), core_collection__cons(jsonField("time", jsonLit(__dshtoStr(r.time))), core_collection__emptyList()))));
  }
  function encLedger(xs) {
    return joinJsonElems(dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, xs, (r) => encDose(r)));
  }
  function decDose(j) {
    return DoseRec(jsonStrField(j, "drug"), jsonRatField(j, "amount"), jsonNatField(j, "time"));
  }
  function ledgerKey() {
    return "ledger";
  }
  function saveLedger(xs) {
    const text = encLedger(xs);
    const ok = (() => {
  const __s26 = (() => { try { return Some(JSON.parse(text)); } catch (__e27) { return None(); } })();
  if ((__s26.tag === "Some")) { const j = __s26[0];  return true; }
  if ((__s26.tag === "None")) {  return false; }
  return null;
})();
    if (ok) {
      // unchecked
      const v = (() => {
  const __s28 = (() => { try { return Some(JSON.parse(text)); } catch (__e29) { return None(); } })();
  if ((__s28.tag === "Some")) { const j = __s28[0];  return JSON.stringify(j); }
  if ((__s28.tag === "None")) {  return text; }
  return null;
})();
      // unchecked
      __dshAndroid("storagePut", [ledgerKey(), v]);
    }
    return ok;
  }
  function loadLedger() {
    return (() => {
  const __s30 = (() => { try { return Some(JSON.parse(__dshAndroid("storageGet", [ledgerKey()]))); } catch (__e31) { return None(); } })();
  if ((__s30.tag === "Some")) { const j = __s30[0];  return dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, jsonToListOrEmpty(j), (o) => decDose(o)); }
  if ((__s30.tag === "None")) {  return core_collection__emptyList(); }
  return null;
})();
  }
  function jsonToListOrEmpty(j) {
    return core_collection__getOrElse((() => { const __jv32 = j; return Array.isArray(__jv32) ? Some(__jv32) : None(); })(), core_collection__emptyList());
  }
  function tagBg(level, overdose) {
    return (overdose ? (() => {
  return "#40D32F2F";
})() : (() => {
  return (() => {
  const __s33 = level;
  if ((__s33 === 3)) {  return "#33D32F2F"; }
  if ((__s33 === 2)) {  return "#33F9A825"; }
  if ((__s33 === 1)) {  return "#334CAF50"; }
   return "#FFFFFF";
})();
})());
  }
  function tagFg(level, overdose) {
    return (overdose ? (() => {
  return "#D32F2F";
})() : (() => {
  return (() => {
  const __s34 = level;
  if ((__s34 === 3)) {  return "#C62828"; }
  if ((__s34 === 2)) {  return "#E65100"; }
  if ((__s34 === 1)) {  return "#2E7D32"; }
   return "#49454E";
})();
})());
  }
  function drugColor(name) {
    return (__eq(name, "氟伏沙明") ? (() => {
  return "#FFC107";
})() : (() => {
  return (__eq(name, "帕罗西汀") ? (() => {
  return "#FFC107";
})() : (() => {
  return (__eq(name, "舍曲林") ? (() => {
  return "#FFC107";
})() : (() => {
  return (__eq(name, "氟西汀") ? (() => {
  return "#FFC107";
})() : (() => {
  return (__eq(name, "西酞普兰") ? (() => {
  return "#FFC107";
})() : (() => {
  return (__eq(name, "艾司西酞普兰") ? (() => {
  return "#FFC107";
})() : (() => {
  return (__eq(name, "地西泮") ? (() => {
  return "#1976D2";
})() : (() => {
  return (__eq(name, "艾司唑仑") ? (() => {
  return "#1976D2";
})() : (() => {
  return (__eq(name, "阿普唑仑") ? (() => {
  return "#1976D2";
})() : (() => {
  return (__eq(name, "氯硝西泮") ? (() => {
  return "#1976D2";
})() : (() => {
  return "#00695C";
})());
})());
})());
})());
})());
})());
})());
})());
})());
})());
  }
  function demoDoses() {
    return core_collection__cons(Dose(100.0, 0), core_collection__cons(Dose(100.0, 24), core_collection__emptyList()));
  }
  function coadmin() {
    return (loadDrugs()).concat(core_collection__cons(fluvoxamine(), core_collection__cons(paroxetine(), core_collection__cons(fluoxetine(), core_collection__cons(sertraline(), core_collection__cons(citalopram(), core_collection__cons(escitalopram(), core_collection__cons(diazepam(), core_collection__cons(estazolam(), core_collection__cons(alprazolam(), core_collection__cons(clonazepam(), core_collection__emptyList())))))))))));
  }
  function formKey() {
    return "sm_form";
  }
  function pickKey() {
    return "sm_pick";
  }
  function doseKey() {
    return "sm_dose";
  }
  function settingsKey() {
    return "sm_set";
  }
  function docKey() {
    return "sm_doc";
  }
  function errKey() {
    return "sm_err";
  }
  function doseErr() {
    return __dshAndroid("storageGet", [errKey()]);
  }
  function setDoseErr(e) {
    // unchecked
    __dshAndroid("storagePut", [errKey(), e]);
    return null;
  }
  function isFormOpen() {
    return __eq(__dshAndroid("storageGet", [formKey()]), "1");
  }
  function isSettingsOpen() {
    return __eq(__dshAndroid("storageGet", [settingsKey()]), "1");
  }
  function isDocOpen() {
    return __eq(__dshAndroid("storageGet", [docKey()]), "1");
  }
  function curPick() {
    return core_collection__getOrElse((() => { const __m35 = String(__dshAndroid("storageGet", [pickKey()])).match(/^(0|[1-9][0-9]*)$/); return __m35 ? Some(Number(__m35[0])) : None(); })(), 0);
  }
  function isPickSet() {
    return (() => {
  const __s36 = (() => { const __m37 = String(__dshAndroid("storageGet", [pickKey()])).match(/^(0|[1-9][0-9]*)$/); return __m37 ? Some(Number(__m37[0])) : None(); })();
  if ((__s36.tag === "Some")) {  return true; }
  if ((__s36.tag === "None")) {  return false; }
  return null;
})();
  }
  function curDose() {
    return __dshAndroid("storageGet", [doseKey()]);
  }
  function setFormOpen(o) {
    // unchecked
    __dshAndroid("storagePut", [formKey(), (o ? (() => {
  return "1";
})() : (() => {
  return "0";
})())]);
    return null;
  }
  function setPickUnset() {
    // unchecked
    __dshAndroid("storagePut", [pickKey(), ""]);
    return null;
  }
  function setPickAt(n) {
    // unchecked
    __dshAndroid("storagePut", [pickKey(), __dshtoStr(n)]);
    return null;
  }
  function setDose(t) {
    // unchecked
    __dshAndroid("storagePut", [doseKey(), t]);
    return null;
  }
  function setSettingsOpen(o) {
    // unchecked
    __dshAndroid("storagePut", [settingsKey(), (o ? (() => {
  return "1";
})() : (() => {
  return "0";
})())]);
    return null;
  }
  function setDocOpen(o) {
    // unchecked
    __dshAndroid("storagePut", [docKey(), (o ? (() => {
  return "1";
})() : (() => {
  return "0";
})())]);
    return null;
  }
  function refreshHome() {
    // unchecked
    (function(f){ return __dshAndroid("layout", [JSON.stringify(__dshViewTree(f(null)))]); })(content);
    return null;
  }
  async function main() {
    (globalThis.__dshLifecycle_onCreate = ((_) => {
  __dshAndroid("log", ["Sugar Manual 已加载（t59：v2 协议照 Compose 参考重写）", 2]);
}), __dshAndroid("registerLifecycle", ["onCreate"]));
    (globalThis.__dshLifecycle_onStart = ((_) => (function(f){ return __dshAndroid("layout", [JSON.stringify(__dshViewTree(f(null)))]); })(content)), __dshAndroid("registerLifecycle", ["onStart"]));
    return null;
  }
  function topBar() {
    return Box(background$List(height$List(fillWidth$v0(), 48), "primary"), CenterStart(), "horizontal", undefined, undefined, undefined, "SpaceBetween", undefined, undefined, undefined, [Box(paddingH$Nat(16), CenterStart(), "horizontal", undefined, undefined, undefined, undefined, undefined, undefined, undefined, [Text("语法糖手册", "onPrimary", 22, true, undefined, undefined, undefined, undefined, [], recomposion$Text$1, 1), Box(width$Nat(6), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, recomposion$Box$3, 3), Text("V1.0", "onPrimary", 11, undefined, undefined, undefined, undefined, undefined, [], recomposion$Text$2, 2)], recomposion$Box$2, 2), Box(paddingH$Nat(12), undefined, "horizontal", undefined, undefined, undefined, undefined, undefined, undefined, undefined, [TextButton(undefined, undefined, undefined, __dshCb("TextButton_onClick_1", ((_) => {
  openDocDialog();
})), undefined, [Icon("info", 22, "onPrimary", undefined, [], recomposion$Icon$1, 1)], recomposion$TextButton$1, 1), TextButton(undefined, undefined, undefined, __dshCb("TextButton_onClick_2", ((_) => {
  noNavigation();
})), undefined, [Icon("menu", 22, "onPrimary", undefined, [], recomposion$Icon$2, 2)], recomposion$TextButton$2, 2), TextButton(undefined, undefined, undefined, __dshCb("TextButton_onClick_3", ((_) => {
  openSettingsDialog();
})), undefined, [Icon("settings", 22, "onPrimary", undefined, [], recomposion$Icon$3, 3)], recomposion$TextButton$3, 3)], recomposion$Box$4, 4)], recomposion$Box$1, 1);
  }
  function noNavigation() {
    // unchecked
    __dshAndroid("toast", ["药物页：当前视图协议无多页导航", false]);
    null;
    return null;
  }
  function alertItems(adr) {
    return (itemsAt(3, adr.items)).concat((itemsAt(2, adr.items)).concat(itemsAt(1, adr.items)));
  }
  function itemsAt(l, ts) {
    return (ts).filter((t) => __eq(advLevel(t.score), l));
  }
  function isOverdoseName(name, drugs) {
    return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, drugs, false, (acc, d) => (containsRated(d.overdose, name) ? (() => {
  return true;
})() : (() => {
  return acc;
})()));
  }
  function alertStream(adr, drugs) {
    return (__eq((adr.items).length, 0) ? (() => {
  return Box(paddingHV$List(fillWidth$v0(), 24, 8), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, [Text("暂无预警", "onSurfaceVariant", 14, undefined, undefined, undefined, undefined, undefined, [], recomposion$Text$3, 3)], recomposion$Box$5, 5);
})() : (() => {
  return Box(paddingHV$List(fillWidth$v0(), 24, 8), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, [Box(fillWidth$v0(), undefined, undefined, undefined, undefined, 8, undefined, true, undefined, undefined, tagList(alertItems(adr), drugs), undefined, undefined)], recomposion$Box$6, 6);
})());
  }
  function tagList(ts, drugs) {
    return (() => {
  const __s38 = ts;
  if (((Array.isArray(__s38) && __s38.length === 0))) {  return core_collection__emptyList(); }
  if (((Array.isArray(__s38) && __s38.length >= 1))) { const t = __s38[0]; const rest = __s38.slice(1);  return core_collection__cons(tagCell(t, drugs), tagList(rest, drugs)); }
   return core_collection__emptyList();
})();
  }
  function tagCell(t, drugs) {
    return Box(paddingHV$List(background$List(clip$Nat(12), tagBg(advLevel(t.score), isOverdoseName(t.name, drugs))), 12, 5), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, [Text(t.name, tagFg(advLevel(t.score), isOverdoseName(t.name, drugs)), 14, true, undefined, undefined, isOverdoseName(t.name, drugs), undefined, [], recomposion$Text$4, 4)], recomposion$Box$7, 7);
  }
  const MedCard = (name, time, route, dose) => ({ tag: "MedCard", name: name, time: time, route: route, dose: dose });
  function routeOf(name, drugs) {
    return (() => {
  const __s39 = drugs;
  if (((Array.isArray(__s39) && __s39.length === 0))) {  return "口服"; }
  if (((Array.isArray(__s39) && __s39.length >= 1))) { const d = __s39[0]; const rest = __s39.slice(1);  return (__eq(d.name, name) ? (() => {
  return firstRoute(d.pk);
})() : (() => {
  return routeOf(name, rest);
})()); }
  return null;
})();
  }
  function firstRoute(pk) {
    return (() => {
  const __s40 = pk;
  if (((Array.isArray(__s40) && __s40.length >= 1))) { const p = __s40[0];  return p.route; }
  if (((Array.isArray(__s40) && __s40.length === 0))) {  return "口服"; }
  return null;
})();
  }
  function halfNat(pk) {
    return (() => {
  const __s41 = pk;
  if (((Array.isArray(__s41) && __s41.length >= 1))) { const p = __s41[0];  return p.halfLife; }
  if (((Array.isArray(__s41) && __s41.length === 0))) {  return 24; }
  return null;
})();
  }
  function bioRat(pk) {
    return (() => {
  const __s42 = pk;
  if (((Array.isArray(__s42) && __s42.length >= 1))) { const p = __s42[0];  return p.bio; }
  if (((Array.isArray(__s42) && __s42.length === 0))) {  return 1.0; }
  return null;
})();
  }
  function detailHalf(pk) {
    return __dshtoStr(halfNat(pk));
  }
  function detailBio(pk) {
    return __dshtoStr((bioRat(pk) * 100.0));
  }
  function drugDetail(d) {
    return ((((((d.category + " · T½ ") + detailHalf(d.pk)) + "h · F=") + detailBio(d.pk)) + "% · ") + firstRoute(d.pk));
  }
  function medRowOf(r, drugs) {
    // unchecked
    const t = __dshAndroid("fmtTime", [r.time]);
    return MedCard(r.drug, t, routeOf(r.drug, drugs), (__dshtoStr(r.amount) + " mg"));
  }
  function medRowsOf(recs, drugs) {
    return dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, recs, (r) => medRowOf(r, drugs));
  }
  function recordTitle() {
    return Box(padding4$List(fillWidth$v0(), 24, 16, 24, 8), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, [Text("服药记录", "onSurface", 16, true, undefined, undefined, undefined, undefined, [], recomposion$Text$5, 5)], recomposion$Box$8, 8);
  }
  function recordList(rows) {
    return (() => {
  const __s43 = rows;
  if (((Array.isArray(__s43) && __s43.length === 0))) {  return core_collection__cons(doseEmpty(), core_collection__emptyList()); }
  if (((Array.isArray(__s43) && __s43.length >= 1))) { const c = __s43[0]; const rest = __s43.slice(1);  return core_collection__cons(doseCard(c), recordList(rest)); }
   return core_collection__emptyList();
})();
  }
  function doseEmpty() {
    return Box(paddingV$List(paddingH$List(fillWidth$v0(), 24), 12), Center(), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, [Text("暂无服药记录", "onSurfaceVariant", 14, undefined, undefined, undefined, undefined, undefined, [], recomposion$Text$6, 6)], recomposion$Box$9, 9);
  }
  function doseCard(c) {
    return Card(paddingV$List(paddingH$List(fillWidth$v0(), 24), 6), 12, 0, "surfaceVariant", undefined, undefined, [Box(paddingHV$List(fillWidth$v0(), 16, 12), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, [Box(fillWidth$v0(), CenterStart(), "horizontal", undefined, undefined, undefined, undefined, undefined, undefined, undefined, [Box(background$List(height$List(width$Nat(4), 20), drugColor(c.name)), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, recomposion$Box$12, 12), Box(width$Nat(10), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, recomposion$Box$13, 13), Text(c.name, "onSurface", 16, true, undefined, undefined, undefined, weight$Rat(1.0), [], recomposion$Text$7, 7), Text(c.dose, "primary", 16, true, undefined, undefined, undefined, undefined, [], recomposion$Text$8, 8)], recomposion$Box$11, 11), Box(height$Nat(4), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, recomposion$Box$14, 14), Text((("给药时间 " + c.time) + (" · " + c.route)), "onSurfaceVariant", 12, undefined, undefined, undefined, undefined, undefined, [], recomposion$Text$9, 9)], recomposion$Box$10, 10)], recomposion$Card$1, 1);
  }
  function toDp(px, dpi) {
    return ((px * 160) / dpi);
  }
  function hourAhead(r, nowMs) {
    return ((r.time - nowMs) / msPerHour());
  }
  function dosesOfName(n, recs, nowMs) {
    return (() => {
  const __s44 = recs;
  if (((Array.isArray(__s44) && __s44.length === 0))) {  return core_collection__emptyList(); }
  if (((Array.isArray(__s44) && __s44.length >= 1))) { const r = __s44[0]; const rest = __s44.slice(1);  return (__eq(r.drug, n) ? (() => {
  return core_collection__cons(Dose(r.amount, hourAhead(r, nowMs)), dosesOfName(n, rest, nowMs));
})() : (() => {
  return dosesOfName(n, rest, nowMs);
})()); }
   return core_collection__emptyList();
})();
  }
  function halfOfName(n, drugs) {
    return (() => {
  const __s45 = drugs;
  if (((Array.isArray(__s45) && __s45.length === 0))) {  return 24; }
  if (((Array.isArray(__s45) && __s45.length >= 1))) { const d = __s45[0]; const rest = __s45.slice(1);  return (__eq(d.name, n) ? (() => {
  return halfNat(d.pk);
})() : (() => {
  return halfOfName(n, rest);
})()); }
   return 24;
})();
  }
  function bioOfName(n, drugs) {
    return (() => {
  const __s46 = drugs;
  if (((Array.isArray(__s46) && __s46.length === 0))) {  return 1.0; }
  if (((Array.isArray(__s46) && __s46.length >= 1))) { const d = __s46[0]; const rest = __s46.slice(1);  return (__eq(d.name, n) ? (() => {
  return bioRat(d.pk);
})() : (() => {
  return bioOfName(n, rest);
})()); }
   return 1.0;
})();
  }
  function seriesOfDrug(n, recs, drugs, nowMs) {
    return seriesValues(dosesOfName(n, recs, nowMs), halfOfName(n, drugs), bioOfName(n, drugs), 0, windowHours(), stepHours());
  }
  function seriesOfNames(names, recs, drugs, nowMs) {
    return (() => {
  const __s47 = names;
  if (((Array.isArray(__s47) && __s47.length === 0))) {  return core_collection__emptyList(); }
  if (((Array.isArray(__s47) && __s47.length >= 1))) { const n = __s47[0]; const rest = __s47.slice(1);  return core_collection__cons(Series(n, drugColor(n), seriesOfDrug(n, recs, drugs, nowMs)), seriesOfNames(rest, recs, drugs, nowMs)); }
   return core_collection__emptyList();
})();
  }
  function chartSeriesList(recs, drugs, nowMs) {
    return (__eq((recentDrugs(recs)).length, 0) ? (() => {
  return core_collection__cons(Series("氟伏沙明", "#FFC107", seriesValues(demoDoses(), halfNat(fluvoxamine().pk), bioRat(fluvoxamine().pk), 0, windowHours(), stepHours())), core_collection__emptyList());
})() : (() => {
  return seriesOfNames(recentDrugs(recs), recs, drugs, nowMs);
})());
  }
  function sumSeries(xs, ys) {
    return (() => {
  const __s48 = xs;
  if (((Array.isArray(__s48) && __s48.length === 0))) {  return ys; }
  if (((Array.isArray(__s48) && __s48.length >= 1))) { const a = __s48[0]; const rest = __s48.slice(1);  return (() => {
  const __s49 = ys;
  if (((Array.isArray(__s49) && __s49.length === 0))) {  return xs; }
  if (((Array.isArray(__s49) && __s49.length >= 1))) { const b = __s49[0]; const ysRest = __s49.slice(1);  return core_collection__cons((a + b), sumSeries(rest, ysRest)); }
   return xs;
})(); }
   return ys;
})();
  }
  function sumAllSeries(xss) {
    return (() => {
  const __s50 = xss;
  if (((Array.isArray(__s50) && __s50.length === 0))) {  return core_collection__emptyList(); }
  if (((Array.isArray(__s50) && __s50.length >= 1))) { const h = __s50[0]; const rest = __s50.slice(1);  return sumSeries(h, sumAllSeries(rest)); }
   return core_collection__emptyList();
})();
  }
  function pointsOf(xss) {
    return (() => {
  const __s51 = xss;
  if (((Array.isArray(__s51) && __s51.length === 0))) {  return core_collection__emptyList(); }
  if (((Array.isArray(__s51) && __s51.length >= 1))) { const s = __s51[0]; const rest = __s51.slice(1);  return core_collection__cons(s.points, pointsOf(rest)); }
   return core_collection__emptyList();
})();
  }
  function totalPoints(seriesXs) {
    return sumAllSeries(pointsOf(seriesXs));
  }
  function chartLabels() {
    return dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, core_collection__range(0, ((windowHours() / stepHours()) + 1)), (i) => tickLabel(i));
  }
  function tickLabel(i) {
    return (__eq((i % 3), 0) ? (() => {
  return __dshtoStr((i * stepHours()));
})() : (() => {
  return "";
})());
  }
  function toggleForm() {
    // unchecked
    const open = isFormOpen();
    if (open) {
      // unchecked
      setFormOpen(false);
    } else {
      // unchecked
      setFormOpen(true);
      // unchecked
      setPickUnset();
      // unchecked
      setDose("");
      // unchecked
      setDoseErr("");
    }
    // unchecked
    refreshHome();
    return null;
  }
  function pickDrugAt(k) {
    // unchecked
    setPickAt(k);
    // unchecked
    setDoseErr("");
    // unchecked
    refreshHome();
    return null;
  }
  function cancelForm() {
    // unchecked
    setFormOpen(false);
    // unchecked
    setPickUnset();
    // unchecked
    setDose("");
    // unchecked
    setDoseErr("");
    // unchecked
    refreshHome();
    return null;
  }
  function commitDose() {
    // unchecked
    const ok = isPickSet();
    // unchecked
    const dt = (curDose()).trim();
    if (__eq(ok, false)) {
      // unchecked
      setDoseErr("请先选择药物");
      // unchecked
      __dshAndroid("toast", ["请先选择药物", false]);
    } else {
      if (__eq(dt, "")) {
        // unchecked
        setDoseErr("剂量必须为数字");
        // unchecked
        __dshAndroid("toast", ["剂量必须为数字", false]);
      } else {
        if (__eq(isNumber(dt), false)) {
          // unchecked
          setDoseErr("剂量必须为数字");
          // unchecked
          __dshAndroid("toast", ["剂量必须为数字", false]);
        } else {
          const amt = core_real__ratOr(dt, 0.0);
          if ((amt <= 0.0)) {
            // unchecked
            setDoseErr("剂量必须大于 0");
            // unchecked
            __dshAndroid("toast", ["剂量必须大于 0", false]);
          } else {
            if ((amt > 10000.0)) {
              // unchecked
              setDoseErr("单次剂量不得超过 10000 mg");
              // unchecked
              __dshAndroid("toast", ["单次剂量不得超过 10000 mg", false]);
            } else {
              // unchecked
              const recs = loadLedger();
              // unchecked
              const nowMs = Date.now();
              // unchecked
              const drugs = coadmin();
              // unchecked
              const idx = curPick();
              const drug = drugAt(drugs, idx);
              const nr = core_collection__cons(DoseRec(drug, amt, nowMs), recs);
              // unchecked
              saveLedger(nr);
              // unchecked
              setFormOpen(false);
              // unchecked
              setPickUnset();
              // unchecked
              setDoseErr("");
              // unchecked
              setDose("");
              // unchecked
              __dshAndroid("toast", ["已记录服药，浓度曲线与记录已刷新", false]);
              // unchecked
              refreshHome();
            }
          }
        }
      }
    }
    return null;
  }
  function isNumber(s) {
    return (() => {
  const __s52 = (() => { const __v53 = Number(String(s)); return Number.isNaN(__v53) ? None() : Some(__v53); })();
  if ((__s52.tag === "Some")) {  return true; }
  if ((__s52.tag === "None")) {  return false; }
  return null;
})();
  }
  function drugAt(drugs, n) {
    return (() => {
  const __s54 = (() => { const __xs55 = drugs; const __ix55 = n; return __ix55 >= 0 && __ix55 < __xs55.length ? Some(__xs55[__ix55]) : None(); })();
  if ((__s54.tag === "Some")) { const d = __s54[0];  return d.name; }
  if ((__s54.tag === "None")) {  return "氟伏沙明"; }
  return null;
})();
  }
  function formPanel(open, drugs, idx, sel, dt, de) {
    return BottomSheet(open, __dshCb("BottomSheet_onDismiss_4", ((_) => {
  cancelForm();
})), undefined, [Box(padding4$List(fillWidth$v0(), 24, 0, 24, 24), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, [Text("＋服用药物", undefined, 22, true, undefined, undefined, undefined, undefined, [], recomposion$Text$10, 10), Box(height$Nat(4), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, recomposion$Box$16, 16), Text("选择药物并填写剂量（mg）", "onSurfaceVariant", 12, undefined, undefined, undefined, undefined, undefined, [], recomposion$Text$11, 11), Box(height$Nat(12), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, recomposion$Box$17, 17), Text("药物", "onSurfaceVariant", 14, undefined, undefined, undefined, undefined, undefined, [], recomposion$Text$12, 12), Box(height$Nat(6), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, recomposion$Box$18, 18), Box(height$List(fillWidth$v0(), 168), undefined, undefined, undefined, true, 6, undefined, undefined, undefined, undefined, drugRowsFrom(drugs, 0, idx, sel), undefined, undefined), Box(height$Nat(12), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, recomposion$Box$19, 19), TextField("剂量 (mg)", dt, "剂量 (mg)", de, "number", __dshCb("TextField_onChange_5", ((t) => {
  setDose(t);
})), undefined, undefined, [], recomposion$TextField$1, 1), Box(height$Nat(16), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, recomposion$Box$20, 20), Box(fillWidth$v0(), CenterStart(), "horizontal", undefined, undefined, undefined, "End", undefined, undefined, undefined, [TextButton(undefined, "取消", undefined, __dshCb("TextButton_onClick_6", ((_) => {
  cancelForm();
})), undefined, [], recomposion$TextButton$4, 4), Box(width$Nat(8), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, recomposion$Box$22, 22), Button(undefined, "确认", undefined, undefined, __dshCb("Button_onClick_7", ((_) => {
  commitDose();
})), undefined, [], recomposion$Button$1, 1)], recomposion$Box$21, 21)], recomposion$Box$15, 15)], recomposion$BottomSheet$1, 1);
  }
  function drugRowsFrom(drugs, k, idx, sel) {
    return (() => {
  const __s56 = drugs;
  if (((Array.isArray(__s56) && __s56.length === 0))) {  return core_collection__emptyList(); }
  if (((Array.isArray(__s56) && __s56.length >= 1))) { const d = __s56[0]; const rest = __s56.slice(1);  return core_collection__cons(drugRow(d, k, idx, sel), drugRowsFrom(rest, (k + 1), idx, sel)); }
   return core_collection__emptyList();
})();
  }
  function drugRow(d, k, idx, sel) {
    return Box(paddingHV$List(background$List(clip$List(fillWidth$v0(), 12), rowBg(k, idx, sel)), 12, 10), CenterStart(), "horizontal", undefined, undefined, undefined, undefined, undefined, __dshCb("Box_onClick_8", ((_) => {
  pickDrugAt(k);
})), undefined, [Box(weight$Rat(1.0), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, [Text(d.name, rowNameColor(k, idx, sel), 16, rowBold(k, idx, sel), undefined, undefined, undefined, undefined, [], recomposion$Text$13, 13), Text(drugDetail(d), "onSurfaceVariant", 12, undefined, undefined, undefined, undefined, undefined, [], recomposion$Text$14, 14)], recomposion$Box$24, 24), Text("✓", rowCheckColor(k, idx, sel), 16, true, undefined, undefined, undefined, undefined, [], recomposion$Text$15, 15)], recomposion$Box$23, 23);
  }
  function rowBg(k, idx, sel) {
    return ((__eq(sel, true) && __eq(k, idx)) ? (() => {
  return "primaryContainer";
})() : (() => {
  return "surfaceVariant";
})());
  }
  function rowNameColor(k, idx, sel) {
    return ((__eq(sel, true) && __eq(k, idx)) ? (() => {
  return "onPrimaryContainer";
})() : (() => {
  return "onSurface";
})());
  }
  function rowCheckColor(k, idx, sel) {
    return ((__eq(sel, true) && __eq(k, idx)) ? (() => {
  return "primary";
})() : (() => {
  return "#00000000";
})());
  }
  function rowBold(k, idx, sel) {
    return (__eq(sel, true) && __eq(k, idx));
  }
  function openDocDialog() {
    // unchecked
    setDocOpen(true);
    // unchecked
    refreshHome();
    return null;
  }
  function closeDoc() {
    // unchecked
    setDocOpen(false);
    // unchecked
    refreshHome();
    return null;
  }
  function openSettingsDialog() {
    // unchecked
    setSettingsOpen(true);
    // unchecked
    refreshHome();
    return null;
  }
  function closeSettings() {
    // unchecked
    setSettingsOpen(false);
    // unchecked
    refreshHome();
    return null;
  }
  function clearAll() {
    // unchecked
    saveLedger(core_collection__emptyList());
    // unchecked
    setSettingsOpen(false);
    // unchecked
    refreshHome();
    return null;
  }
  function settingsDialog(open) {
    return AlertDialog(open, "设置", "清空记录", "关闭", __dshCb("AlertDialog_onConfirm_9", ((_) => {
  clearAll();
})), __dshCb("AlertDialog_onDismiss_10", ((_) => {
  closeSettings();
})), undefined, [Text("语法糖手册 V1.0\nSfA（Sublimation for Android）视图协议 v2 渲染\n数据持久化：storage 键 ledger", "onSurfaceVariant", 12, undefined, undefined, undefined, undefined, undefined, [], recomposion$Text$16, 16)], recomposion$AlertDialog$1, 1);
  }
  function docDialog(open) {
    return AlertDialog(open, "文档", "", "关闭", undefined, __dshCb("AlertDialog_onDismiss_11", ((_) => {
  closeDoc();
})), undefined, [Text("糖手册：记录服药 → 估算血药浓度 → 联用预警。\n\n氟伏沙明（CYP2D6 底物）+ 帕罗西汀（CYP2D6 极强抑制）联用构成 CYP2D6 抑制-底物相互作用（不在首页预警），并共享恶心/失眠/嗜睡等不良反应。\n\n源码：github.com/inline-function/Sugar-Manual", "onSurfaceVariant", 12, undefined, undefined, undefined, undefined, undefined, [], recomposion$Text$17, 17)], recomposion$AlertDialog$2, 2);
  }
  function contentHeightDp(sh, dpi) {
    return (toDp(sh, dpi) - 64);
  }
  function chartHeightDp(sh, dpi) {
    return ((contentHeightDp(sh, dpi) * 45) / 100);
  }
  function lowerHeightDp(sh, dpi) {
    return (contentHeightDp(sh, dpi) - chartHeightDp(sh, dpi));
  }
  function lowerBox(adr, rows, drugs, sh, dpi) {
    return Box(height$List(fillWidth$v0(), lowerHeightDp(sh, dpi)), undefined, undefined, undefined, true, undefined, undefined, undefined, undefined, undefined, [alertStream(adr, drugs), recordTitle(), Box(fillWidth$v0(), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, recordList(rows), undefined, undefined), Box(height$Nat(96), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, recomposion$Box$26, 26)], recomposion$Box$25, 25);
  }
  function content() {
    // unchecked
    const open = isFormOpen();
    // unchecked
    const setOp = isSettingsOpen();
    // unchecked
    const docOp = isDocOpen();
    // unchecked
    const idx = curPick();
    // unchecked
    const sel = isPickSet();
    // unchecked
    const dt = curDose();
    // unchecked
    const de = doseErr();
    // unchecked
    let recs = loadLedger();
    // unchecked
    let nowMs = Date.now();
    // unchecked
    const drugs = coadmin();
    const adr = adrAlert(drugs);
    // unchecked
    const sh = JSON.parse(__dshAndroid("screenHeight", []));
    // unchecked
    const dpi = JSON.parse(__dshAndroid("screenDensity", []));
    const seriesXs = chartSeriesList(recs, drugs, nowMs);
    const total = totalPoints(seriesXs);
    let chart = Chart("line", seriesXs, total, chartLabels(), "primary", true, "估算", height$List(fillWidth$v0(), chartHeightDp(sh, dpi)), undefined, [], recomposion$Chart$1, 1);
    // unchecked
    const rows = medRowsOf(recs, drugs);
    let lower = lowerBox(adr, rows, drugs, sh, dpi);
    let sheet = formPanel(open, drugs, idx, sel, dt, de);
    let setDlg = settingsDialog(setOp);
    let docDlg = docDialog(docOp);
    return Box(fillMaxSize$v0(), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, [topBar(), chart, lower, FloatingButton(undefined, "primary", undefined, "add", "＋服用药物", __dshCb("FloatingButton_onClick_12", ((_) => {
  toggleForm();
})), undefined, undefined, [], recomposion$FloatingButton$1, 1), sheet, setDlg, docDlg], recomposion$Box$27, 27);
  }
  const Dose = (amount, time) => ({ tag: "Dose", amount: amount, time: time });
  function ln2() {
    return 0.6931471805599453;
  }
  function eliminationRate(tHalf) {
    return (ln2() / Number(tHalf));
  }
  function decay(tHalf, dt) {
    return Math.pow(0.5, (Number(dt) / Number(tHalf)));
  }
  function concentrationAt(doses, tHalf, bio, t) {
    return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, doses, 0.0, (acc, d) => ((t >= d.time) ? (() => {
  return (acc + ((d.amount * bio) * decay(tHalf, (t - d.time))));
})() : (() => {
  return acc;
})()));
  }
  function seriesValues(doses, tHalf, bio, from, to, step) {
    const n = ((to - from) / step);
    return dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, core_collection__range(0, (n + 1)), (i) => concentrationAt(doses, tHalf, bio, ((from + i) * step)));
  }
  function seriesLabels(from, to, step) {
    const n = ((to - from) / step);
    return dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, core_collection__range(0, (n + 1)), (i) => (__dshtoStr(((from + i) * step)) + "h"));
  }
  function doseEffect(d, ed50) {
    return ((d <= 0.0) ? (() => {
  return 0.0;
})() : (() => {
  return (d / (d + ed50));
})());
  }
  function sertraline() {
    return Drug("舍曲林", "处方药", strList("选择性血清素再摄取抑制剂,SSRIs,五朵金花,抗抑郁药,抗强迫药,抗焦虑药,抗创伤药"), "舍曲林是萘胺类选择性血清素再摄取抑制剂，用于抑郁症、强迫症、惊恐障碍、社交焦虑障碍与创伤后应激障碍（其中创伤后应激与经前烦躁在中国部分属【超说明书】用法）。对心血管安全性相对较好，可用于冠心病及心梗后抑郁患者。经 CYP2D6（次要）、CYP2C19、CYP3A4 与 CYP2B6 代谢，活性代谢物去甲舍曲林活性很低；自身仅为弱 CYP2D6 抑制剂，对经 2D6 活化的前体药影响弱于帕罗西汀与氟西汀；腹泻等胃肠道反应在 SSRI 中较突出。", ratedList("强效|血清素转运体抑制;极弱|正肾素转运体抑制;极弱|多巴胺转运体抑制"), strList("CYP2D6,CYP2C19,CYP3A4,CYP2B6"), ratedList("弱效|CYP2D6可逆性抑制;极弱|CYP2B6可逆性抑制"), core_collection__cons(PkProfile("口服", "常释", 26, "4.5-8.4h", "一级", "对数", 0.44, core_collection__cons(Rated("弱效", "去甲舍曲林"), core_collection__emptyList())), core_collection__emptyList()), ratedList("弱效|激活（早期）"), ratedList("极强|抗强迫;强效|抗抑郁;强效|抗焦虑;强效|抗创伤"), ratedList("罕见|血清素综合征"), ratedList("十分常见|恶心;常见|腹泻;常见|失眠;常见|嗜睡;常见|头晕;常见|口干;常见|出汗;常见|性功能障碍;少见|低血钠症;少见|震颤;罕见|自杀冲动"), ratedList("常见|头晕;常见|失眠;常见|易激惹;常见|恶心;少见|电击样感觉"), ratedList(">1000mg|血清素综合征"));
  }
  const Trigger = (name, score) => ({ tag: "Trigger", name: name, score: score });
  const Alert = (level, items) => ({ tag: "Alert", level: level, items: items });
  function levelName(level) {
    return (() => {
  const __s57 = level;
  if ((__s57 === 1)) {  return "提示"; }
  if ((__s57 === 2)) {  return "警告"; }
  if ((__s57 === 3)) {  return "危险"; }
   return "无";
})();
  }
  function adrWeight(level) {
    return (() => {
  const __s58 = level;
  if ((__s58 === "罕见")) {  return 1; }
  if ((__s58 === "少见")) {  return 2; }
  if ((__s58 === "常见")) {  return 3; }
  if ((__s58 === "十分常见")) {  return 5; }
   return 0;
})();
  }
  function inhibWeight(level) {
    return (() => {
  const __s59 = level;
  if ((__s59 === "弱效")) {  return 1; }
  if ((__s59 === "中效")) {  return 2; }
  if ((__s59 === "强效")) {  return 3; }
  if ((__s59 === "极强")) {  return 5; }
   return 0;
})();
  }
  function advLevel(score) {
    return ((score >= 7) ? (() => {
  return 3;
})() : (() => {
  return ((score >= 5) ? (() => {
  return 2;
})() : (() => {
  return ((score >= 3) ? (() => {
  return 1;
})() : (() => {
  return 0;
})());
})());
})());
  }
  function metabLevel(score) {
    return ((score >= 5) ? (() => {
  return 3;
})() : (() => {
  return ((score >= 3) ? (() => {
  return 2;
})() : (() => {
  return ((score >= 1) ? (() => {
  return 1;
})() : (() => {
  return 0;
})());
})());
})());
  }
  const Score = (name, total, count) => ({ tag: "Score", name: name, total: total, count: count });
  function aggAdr(drugs) {
    return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, drugs, core_collection__emptyList(), (acc, d) => aggSection(aggSection(acc, d.acuteAdv), d.chronicAdv));
  }
  function aggSection(acc, xs) {
    return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, xs, acc, (a, e) => addScore(a, e.name, adrWeight(e.level)));
  }
  function addScore(acc, name, w) {
    return (() => {
  const __s60 = acc;
  if (((Array.isArray(__s60) && __s60.length === 0))) {  return core_collection__cons(Score(name, w, 1), core_collection__emptyList()); }
  if (((Array.isArray(__s60) && __s60.length >= 1))) { const s = __s60[0]; const rest = __s60.slice(1);  return (__eq(s.name, name) ? (() => {
  return core_collection__cons(Score(name, (s.total + w), (s.count + 1)), rest);
})() : (() => {
  return core_collection__cons(s, addScore(rest, name, w));
})()); }
   return acc;
})();
  }
  function adrAlert(drugs) {
    const ts = adrTriggers(drugs);
    return Alert(maxLevelAdv(ts), ts);
  }
  function adrTriggers(drugs) {
    return dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, (aggAdr(drugs)).filter((s) => (s.count >= 2)), (s) => Trigger(s.name, s.total));
  }
  function maxLevelAdv(ts) {
    return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, ts, 0, (acc, t) => ((advLevel(t.score) > acc) ? (() => {
  return advLevel(t.score);
})() : (() => {
  return acc;
})()));
  }
  function allEnzymes(drugs) {
    return core_collection__distinct(joinEnzymeLists(dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, drugs, (d) => (inhibNames(d.inhibitions)).concat(d.enzymes))));
  }
  function inhibNames(xs) {
    return dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, xs, (r) => r.name);
  }
  function joinEnzymeLists(xss) {
    return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, xss, core_collection__emptyList(), (acc, ys) => (acc).concat(ys));
  }
  function relatedCount(drugs, e) {
    return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, drugs, 0, (acc, d) => (relatesTo(d, e) ? (() => {
  return (acc + 1);
})() : (() => {
  return acc;
})()));
  }
  function relatesTo(d, e) {
    return ((d.enzymes).includes(e) ? (() => {
  return true;
})() : (() => {
  return containsRated(d.inhibitions, e);
})());
  }
  function containsRated(xs, e) {
    return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, xs, false, (acc, r) => (__eq(r.name, e) ? (() => {
  return true;
})() : (() => {
  return acc;
})()));
  }
  function inhibTotal(drugs, e) {
    return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, drugs, 0, (acc, d) => (acc + inhibWeightOf(d.inhibitions, e)));
  }
  function inhibWeightOf(xs, e) {
    return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, xs, 0, (acc, r) => (__eq(r.name, e) ? (() => {
  return (acc + inhibWeight(r.level));
})() : (() => {
  return acc;
})()));
  }
  function metabAlert(drugs) {
    const ts = metabTriggers(drugs);
    return Alert(maxLevelMetab(ts), ts);
  }
  function metabTriggers(drugs) {
    return dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, (metabScores(drugs)).filter((s) => ((s.count >= 2) ? (() => {
  return (s.total > 0);
})() : (() => {
  return false;
})())), (s) => Trigger(s.name, s.total));
  }
  function metabScores(drugs) {
    return dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, allEnzymes(drugs), (e) => Score(e, inhibTotal(drugs, e), relatedCount(drugs, e)));
  }
  function maxLevelMetab(ts) {
    return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, ts, 0, (acc, t) => ((metabLevel(t.score) > acc) ? (() => {
  return metabLevel(t.score);
})() : (() => {
  return acc;
})()));
  }
  function maxAlertLevel(a, b) {
    return ((b.level > a.level) ? (() => {
  return b.level;
})() : (() => {
  return a.level;
})());
  }
  function recomposion$Box$1(v) {
    return __dshAndroid("androidRecompose", ["Box", 1, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$2(v) {
    return __dshAndroid("androidRecompose", ["Box", 2, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Text$1(v) {
    return __dshAndroid("androidRecompose", ["Text", 1, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$3(v) {
    return __dshAndroid("androidRecompose", ["Box", 3, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Text$2(v) {
    return __dshAndroid("androidRecompose", ["Text", 2, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$4(v) {
    return __dshAndroid("androidRecompose", ["Box", 4, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$TextButton$1(v) {
    return __dshAndroid("androidRecompose", ["TextButton", 1, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Icon$1(v) {
    return __dshAndroid("androidRecompose", ["Icon", 1, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$TextButton$2(v) {
    return __dshAndroid("androidRecompose", ["TextButton", 2, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Icon$2(v) {
    return __dshAndroid("androidRecompose", ["Icon", 2, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$TextButton$3(v) {
    return __dshAndroid("androidRecompose", ["TextButton", 3, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Icon$3(v) {
    return __dshAndroid("androidRecompose", ["Icon", 3, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$5(v) {
    return __dshAndroid("androidRecompose", ["Box", 5, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Text$3(v) {
    return __dshAndroid("androidRecompose", ["Text", 3, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$6(v) {
    return __dshAndroid("androidRecompose", ["Box", 6, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$7(v) {
    return __dshAndroid("androidRecompose", ["Box", 7, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$8(v) {
    return __dshAndroid("androidRecompose", ["Box", 8, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Text$4(v) {
    return __dshAndroid("androidRecompose", ["Text", 4, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$9(v) {
    return __dshAndroid("androidRecompose", ["Box", 9, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Text$5(v) {
    return __dshAndroid("androidRecompose", ["Text", 5, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$10(v) {
    return __dshAndroid("androidRecompose", ["Box", 10, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Text$6(v) {
    return __dshAndroid("androidRecompose", ["Text", 6, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Card$1(v) {
    return __dshAndroid("androidRecompose", ["Card", 1, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$11(v) {
    return __dshAndroid("androidRecompose", ["Box", 11, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$12(v) {
    return __dshAndroid("androidRecompose", ["Box", 12, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$13(v) {
    return __dshAndroid("androidRecompose", ["Box", 13, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$14(v) {
    return __dshAndroid("androidRecompose", ["Box", 14, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Text$7(v) {
    return __dshAndroid("androidRecompose", ["Text", 7, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Text$8(v) {
    return __dshAndroid("androidRecompose", ["Text", 8, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$15(v) {
    return __dshAndroid("androidRecompose", ["Box", 15, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Text$9(v) {
    return __dshAndroid("androidRecompose", ["Text", 9, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$BottomSheet$1(v) {
    return __dshAndroid("androidRecompose", ["BottomSheet", 1, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$16(v) {
    return __dshAndroid("androidRecompose", ["Box", 16, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Text$10(v) {
    return __dshAndroid("androidRecompose", ["Text", 10, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$17(v) {
    return __dshAndroid("androidRecompose", ["Box", 17, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Text$11(v) {
    return __dshAndroid("androidRecompose", ["Text", 11, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$18(v) {
    return __dshAndroid("androidRecompose", ["Box", 18, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Text$12(v) {
    return __dshAndroid("androidRecompose", ["Text", 12, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$19(v) {
    return __dshAndroid("androidRecompose", ["Box", 19, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$20(v) {
    return __dshAndroid("androidRecompose", ["Box", 20, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$21(v) {
    return __dshAndroid("androidRecompose", ["Box", 21, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$TextField$1(v) {
    return __dshAndroid("androidRecompose", ["TextField", 1, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$22(v) {
    return __dshAndroid("androidRecompose", ["Box", 22, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$23(v) {
    return __dshAndroid("androidRecompose", ["Box", 23, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$TextButton$4(v) {
    return __dshAndroid("androidRecompose", ["TextButton", 4, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$24(v) {
    return __dshAndroid("androidRecompose", ["Box", 24, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Button$1(v) {
    return __dshAndroid("androidRecompose", ["Button", 1, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$25(v) {
    return __dshAndroid("androidRecompose", ["Box", 25, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$26(v) {
    return __dshAndroid("androidRecompose", ["Box", 26, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Text$13(v) {
    return __dshAndroid("androidRecompose", ["Text", 13, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Text$14(v) {
    return __dshAndroid("androidRecompose", ["Text", 14, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Text$15(v) {
    return __dshAndroid("androidRecompose", ["Text", 15, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$AlertDialog$1(v) {
    return __dshAndroid("androidRecompose", ["AlertDialog", 1, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Text$16(v) {
    return __dshAndroid("androidRecompose", ["Text", 16, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$AlertDialog$2(v) {
    return __dshAndroid("androidRecompose", ["AlertDialog", 2, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Text$17(v) {
    return __dshAndroid("androidRecompose", ["Text", 17, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$27(v) {
    return __dshAndroid("androidRecompose", ["Box", 27, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$28(v) {
    return __dshAndroid("androidRecompose", ["Box", 28, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$29(v) {
    return __dshAndroid("androidRecompose", ["Box", 29, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Chart$1(v) {
    return __dshAndroid("androidRecompose", ["Chart", 1, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$Box$30(v) {
    return __dshAndroid("androidRecompose", ["Box", 30, JSON.stringify(__dshViewTree(v))]);
  }
  function recomposion$FloatingButton$1(v) {
    return __dshAndroid("androidRecompose", ["FloatingButton", 1, JSON.stringify(__dshViewTree(v))]);
  }
  const dict_core_collection_Functor_List = {
    map: (d, fa, f) => (() => {
  const __s61 = fa;
  if (((Array.isArray(__s61) && __s61.length === 0))) {  return []; }
  if (((Array.isArray(__s61) && __s61.length >= 1))) { const h = __s61[0]; const t = __s61.slice(1);  return [f(h), ...(dict_core_collection_Functor_List.map(dict_core_collection_Functor_List, t, f))]; }
  return null;
})()
  };
  const dict_core_collection_Foldable_List = {
    fold: (d, fa, init, f) => (() => {
  const __s62 = fa;
  if (((Array.isArray(__s62) && __s62.length === 0))) {  return init; }
  if (((Array.isArray(__s62) && __s62.length >= 1))) { const h = __s62[0]; const t = __s62.slice(1);  return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, t, f(init, h), f); }
  return null;
})()
  };
  const dict_core_collection_Monad_List = {
    pure: (d, x) => [x, ...([])],
    flatMap: (d, fa, f) => (() => {
  const __s63 = fa;
  if (((Array.isArray(__s63) && __s63.length === 0))) {  return []; }
  if (((Array.isArray(__s63) && __s63.length >= 1))) { const h = __s63[0]; const t = __s63.slice(1);  return (f(h)).concat(dict_core_collection_Monad_List.flatMap(dict_core_collection_Monad_List, t, f)); }
  return null;
})()
  };
  function core_collection__emptyList() {
    return [];
  }
  function core_collection__cons(x, xs) {
    return [x, ...(xs)];
  }
  function core_collection__listOf(items) {
    return items;
  }
  function core_collection__range(a, b) {
    if ((a < b)) {
      // unchecked
      const rest = core_collection__range((a + 1), b);
      return [a, ...(rest)];
    }
    return [];
  }
  function core_collection__size$List(xs) {
    return (() => {
  const __s64 = xs;
  if (((Array.isArray(__s64) && __s64.length === 0))) {  return 0; }
  if (((Array.isArray(__s64) && __s64.length >= 1))) { const t = __s64.slice(1);  return ((t).length + 1); }
  return null;
})();
  }
  function core_collection__isEmpty$List(xs) {
    return (() => {
  const __s65 = xs;
  if (((Array.isArray(__s65) && __s65.length === 0))) {  return true; }
  if (((Array.isArray(__s65) && __s65.length >= 1))) {  return false; }
  return null;
})();
  }
  function core_collection__head(xs) {
    return (() => {
  const __s66 = xs;
  if (((Array.isArray(__s66) && __s66.length === 0))) {  return None(); }
  if (((Array.isArray(__s66) && __s66.length >= 1))) { const h = __s66[0];  return Some(h); }
  return null;
})();
  }
  function core_collection__last(xs) {
    return (() => {
  const __s67 = xs;
  if (((Array.isArray(__s67) && __s67.length === 0))) {  return None(); }
  if (((Array.isArray(__s67) && __s67.length >= 1)) && ((Array.isArray(__s67.slice(1)) && __s67.slice(1).length === 0))) { const h = __s67[0];  return Some(h); }
  if (((Array.isArray(__s67) && __s67.length >= 1))) { const t = __s67.slice(1);  return (() => { const __xs68 = t; return __xs68.length ? Some(__xs68[__xs68.length - 1]) : None(); })(); }
  return null;
})();
  }
  function core_collection__elementAt(xs, i) {
    return (() => {
  const __s69 = xs;
  if (((Array.isArray(__s69) && __s69.length === 0))) {  return None(); }
  if (((Array.isArray(__s69) && __s69.length >= 1))) { const h = __s69[0]; const t = __s69.slice(1);  return (__eq(i, 0) ? (() => {
  return Some(h);
})() : (() => {
  return (() => { const __xs70 = t; const __ix70 = (i - 1); return __ix70 >= 0 && __ix70 < __xs70.length ? Some(__xs70[__ix70]) : None(); })();
})()); }
  return null;
})();
  }
  function core_collection__get$List(xs, i) {
    return (() => {
  const __s71 = (() => { const __xs72 = xs; const __ix72 = i; return __ix72 >= 0 && __ix72 < __xs72.length ? Some(__xs72[__ix72]) : None(); })();
  if ((__s71.tag === "Some")) { const v = __s71[0];  return v; }
  if ((__s71.tag === "None")) {  return (() => { throw new Error('panic: unreachable') })(); }
  return null;
})();
  }
  function core_collection__firstOr(xs, d) {
    return core_collection__getOrElse((() => { const __xs73 = xs; return __xs73.length ? Some(__xs73[0]) : None(); })(), d);
  }
  function core_collection__contains$List(xs, x) {
    return (() => {
  const __s74 = xs;
  if (((Array.isArray(__s74) && __s74.length === 0))) {  return false; }
  if (((Array.isArray(__s74) && __s74.length >= 1))) { const h = __s74[0]; const t = __s74.slice(1);  return (__eq(h, x) ? (() => {
  return true;
})() : (() => {
  return (t).includes(x);
})()); }
  return null;
})();
  }
  function core_collection__indexOf(xs, x) {
    return (() => {
  const __s75 = xs;
  if (((Array.isArray(__s75) && __s75.length === 0))) {  return None(); }
  if (((Array.isArray(__s75) && __s75.length >= 1))) { const h = __s75[0]; const t = __s75.slice(1);  return (__eq(h, x) ? (() => {
  return Some(0);
})() : (() => {
  return (() => {
  const __s76 = (() => { const __ix77 = (t).indexOf(x); return __ix77 === -1 ? None() : Some(__ix77); })();
  if ((__s76.tag === "Some")) { const n = __s76[0];  return Some((n + 1)); }
  if ((__s76.tag === "None")) {  return None(); }
  return null;
})();
})()); }
  return null;
})();
  }
  function core_collection__count$List(xs, p) {
    return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, xs, 0, (acc, x) => (p(x) ? (() => {
  return (acc + 1);
})() : (() => {
  return acc;
})()));
  }
  function core_collection__any(xs, p) {
    return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, xs, false, (acc, x) => (acc || p(x)));
  }
  function core_collection__all(xs, p) {
    return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, xs, true, (acc, x) => (acc && p(x)));
  }
  function core_collection__find(xs, p) {
    return (() => {
  const __s78 = xs;
  if (((Array.isArray(__s78) && __s78.length === 0))) {  return None(); }
  if (((Array.isArray(__s78) && __s78.length >= 1))) { const h = __s78[0]; const t = __s78.slice(1);  return (p(h) ? (() => {
  return Some(h);
})() : (() => {
  return core_collection__find(t, p);
})()); }
  return null;
})();
  }
  function core_collection__forEach$List(xs, f) {
    return (() => {
  const __s79 = xs;
  if (((Array.isArray(__s79) && __s79.length === 0))) {  return null; }
  if (((Array.isArray(__s79) && __s79.length >= 1))) { const h = __s79[0]; const t = __s79.slice(1);  return (() => {
  f(h);
  return (t).forEach(__x80 => (f)(__x80));
})(); }
  return null;
})();
  }
  function core_collection__append(xs, ys) {
    return (() => {
  const __s81 = xs;
  if (((Array.isArray(__s81) && __s81.length === 0))) {  return ys; }
  if (((Array.isArray(__s81) && __s81.length >= 1))) { const h = __s81[0]; const t = __s81.slice(1);  return [h, ...((t).concat(ys))]; }
  return null;
})();
  }
  function core_collection__take(xs, n) {
    return (() => {
  const __s82 = xs;
  if (((Array.isArray(__s82) && __s82.length === 0))) {  return []; }
  if (((Array.isArray(__s82) && __s82.length >= 1))) { const h = __s82[0]; const t = __s82.slice(1);  return (__eq(n, 0) ? (() => {
  return [];
})() : (() => {
  const r = (t).slice(0, (n - 1));
  return [h, ...(r)];
})()); }
  return null;
})();
  }
  function core_collection__drop(xs, n) {
    return (() => {
  const __s83 = xs;
  if (((Array.isArray(__s83) && __s83.length === 0))) {  return []; }
  if (((Array.isArray(__s83) && __s83.length >= 1))) { const t = __s83.slice(1);  return (__eq(n, 0) ? (() => {
  return xs;
})() : (() => {
  const r = (t).slice((n - 1));
  return r;
})()); }
  return null;
})();
  }
  function core_collection__slice(xs, from, to) {
    return (() => {
  const __s84 = xs;
  if (((Array.isArray(__s84) && __s84.length === 0))) {  return []; }
  if (((Array.isArray(__s84) && __s84.length >= 1))) { const h = __s84[0]; const t = __s84.slice(1);  return ((to <= from) ? (() => {
  return [];
})() : (() => {
  return (__eq(from, 0) ? (() => {
  const r = (t).slice(0, (to - 1));
  return [h, ...(r)];
})() : (() => {
  return (t).slice((from - 1), (to - 1));
})());
})()); }
  return null;
})();
  }
  function core_collection__filter$List(xs, p) {
    return (() => {
  const __s85 = xs;
  if (((Array.isArray(__s85) && __s85.length === 0))) {  return []; }
  if (((Array.isArray(__s85) && __s85.length >= 1))) { const h = __s85[0]; const t = __s85.slice(1);  return (p(h) ? (() => {
  return [h, ...((t).filter(p))];
})() : (() => {
  return (t).filter(p);
})()); }
  return null;
})();
  }
  function core_collection__distinct(xs) {
    return (() => {
  const __s86 = xs;
  if (((Array.isArray(__s86) && __s86.length === 0))) {  return []; }
  if (((Array.isArray(__s86) && __s86.length >= 1))) { const h = __s86[0]; const t = __s86.slice(1);  return ((t).includes(h) ? (() => {
  return core_collection__distinct(t);
})() : (() => {
  return [h, ...(core_collection__distinct(t))];
})()); }
  return null;
})();
  }
  function core_collection__sortBy(xs, f) {
    return (() => {
  const __s87 = xs;
  if (((Array.isArray(__s87) && __s87.length === 0))) {  return []; }
  if (((Array.isArray(__s87) && __s87.length >= 1))) { const h = __s87[0]; const t = __s87.slice(1);  return core_collection__insertSort(h, (t).slice().sort((__a88, __b88) => (f)(__a88, __b88) ? -1 : 1), f); }
  return null;
})();
  }
  function core_collection__insertSort(x, xs, f) {
    return (() => {
  const __s89 = xs;
  if (((Array.isArray(__s89) && __s89.length === 0))) {  return [x, ...([])]; }
  if (((Array.isArray(__s89) && __s89.length >= 1))) { const h = __s89[0]; const t = __s89.slice(1);  return (f(x, h) ? (() => {
  return [x, ...([h, ...(t)])];
})() : (() => {
  return [h, ...(core_collection__insertSort(x, t, f))];
})()); }
  return null;
})();
  }
  function core_collection__reverse(xs) {
    return (() => {
  const __s90 = xs;
  if (((Array.isArray(__s90) && __s90.length === 0))) {  return []; }
  if (((Array.isArray(__s90) && __s90.length >= 1))) { const h = __s90[0]; const t = __s90.slice(1);  return ((t).slice().reverse()).concat([h, ...([])]); }
  return null;
})();
  }
  function core_collection__clear(xs) {
    return [];
  }
  function core_collection__sum(xs) {
    return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, xs, 0, (acc, x) => (acc + x));
  }
  function core_collection__maxNat(xs) {
    return (() => {
  const __s91 = xs;
  if (((Array.isArray(__s91) && __s91.length === 0))) {  return None(); }
  if (((Array.isArray(__s91) && __s91.length >= 1))) { const h = __s91[0]; const t = __s91.slice(1);  return (() => {
  const __s92 = t;
  if (((Array.isArray(__s92) && __s92.length === 0))) {  return Some(h); }
  if (((Array.isArray(__s92) && __s92.length >= 1))) {  return (() => {
  const __s93 = core_collection__maxNat(t);
  if ((__s93.tag === "Some")) { const v = __s93[0];  return ((h > v) ? (() => {
  return Some(h);
})() : (() => {
  return Some(v);
})()); }
  if ((__s93.tag === "None")) {  return Some(h); }
  return null;
})(); }
  return null;
})(); }
  return null;
})();
  }
  function core_collection__minNat(xs) {
    return (() => {
  const __s94 = xs;
  if (((Array.isArray(__s94) && __s94.length === 0))) {  return None(); }
  if (((Array.isArray(__s94) && __s94.length >= 1))) { const h = __s94[0]; const t = __s94.slice(1);  return (() => {
  const __s95 = t;
  if (((Array.isArray(__s95) && __s95.length === 0))) {  return Some(h); }
  if (((Array.isArray(__s95) && __s95.length >= 1))) {  return (() => {
  const __s96 = core_collection__minNat(t);
  if ((__s96.tag === "Some")) { const v = __s96[0];  return ((h < v) ? (() => {
  return Some(h);
})() : (() => {
  return Some(v);
})()); }
  if ((__s96.tag === "None")) {  return Some(h); }
  return null;
})(); }
  return null;
})(); }
  return null;
})();
  }
  function core_collection__joinToString(xs, sep) {
    return dict_core_collection_Foldable_List.fold(dict_core_collection_Foldable_List, xs, "", (acc, x) => (__eq(acc, "") ? (() => {
  return __dshtoStr(x);
})() : (() => {
  return (acc + (sep + __dshtoStr(x)));
})()));
  }
  function core_collection__zip(xs, ys) {
    return (() => {
  const __s97 = xs;
  if (((Array.isArray(__s97) && __s97.length === 0))) {  return []; }
  if (((Array.isArray(__s97) && __s97.length >= 1))) { const h = __s97[0]; const t = __s97.slice(1);  return (() => {
  const __s98 = ys;
  if (((Array.isArray(__s98) && __s98.length === 0))) {  return []; }
  if (((Array.isArray(__s98) && __s98.length >= 1))) { const h2 = __s98[0]; const t2 = __s98.slice(1);  return [[h, h2], ...(core_collection__zip(t, t2))]; }
  return null;
})(); }
  return null;
})();
  }
  function core_collection__toSet(xs) {
    return (() => {
  const __s99 = xs;
  if (((Array.isArray(__s99) && __s99.length === 0))) {  return core_collection__SetNil(); }
  if (((Array.isArray(__s99) && __s99.length >= 1))) { const h = __s99[0]; const t = __s99.slice(1);  return core_collection__add(core_collection__toSet(t), h); }
  return null;
})();
  }
  function core_collection__toMap(xs) {
    return (() => {
  const __s100 = xs;
  if (((Array.isArray(__s100) && __s100.length === 0))) {  return core_collection__MapNil(); }
  if (((Array.isArray(__s100) && __s100.length >= 1))) { const pr = __s100[0]; const t = __s100.slice(1);  return (() => {
  const __s101 = pr;
  if (((Array.isArray(__s101) && __s101.length === 2))) { const k = __s101[0]; const v = __s101[1];  return core_collection__put(core_collection__toMap(t), k, v); }
  return null;
})(); }
  return null;
})();
  }
  function core_collection__Map__MapNil() { return { tag: "MapNil" }; }
  const core_collection__MapNil = core_collection__Map__MapNil;
  function core_collection__Map__MapCons(a0, a1, a2) { return { tag: "MapCons", 0: a0, 1: a1, 2: a2 }; }
  const core_collection__MapCons = core_collection__Map__MapCons;
  function core_collection__mapEmpty() {
    return core_collection__MapNil();
  }
  function core_collection__size$Map(m) {
    return (() => {
  const __s102 = m;
  if ((__s102.tag === "MapNil")) {  return 0; }
  if ((__s102.tag === "MapCons")) { const t = __s102[2];  return (core_collection__size$Map(t) + 1); }
  return null;
})();
  }
  function core_collection__isEmpty$Map(m) {
    return (() => {
  const __s103 = m;
  if ((__s103.tag === "MapNil")) {  return true; }
  if ((__s103.tag === "MapCons")) {  return false; }
  return null;
})();
  }
  function core_collection__get$Map(m, k) {
    return (() => {
  const __s104 = m;
  if ((__s104.tag === "MapNil")) {  return None(); }
  if ((__s104.tag === "MapCons")) { const k0 = __s104[0]; const v0 = __s104[1]; const t = __s104[2];  return (__eq(k0, k) ? (() => {
  return Some(v0);
})() : (() => {
  return core_collection__get$Map(t, k);
})()); }
  return null;
})();
  }
  function core_collection__containsKey(m, k) {
    return (() => {
  const __s105 = m;
  if ((__s105.tag === "MapNil")) {  return false; }
  if ((__s105.tag === "MapCons")) { const k0 = __s105[0]; const t = __s105[2];  return (__eq(k0, k) ? (() => {
  return true;
})() : (() => {
  return core_collection__containsKey(t, k);
})()); }
  return null;
})();
  }
  function core_collection__keys(m) {
    return (() => {
  const __s106 = m;
  if ((__s106.tag === "MapNil")) {  return []; }
  if ((__s106.tag === "MapCons")) { const k0 = __s106[0]; const t = __s106[2];  return [k0, ...(core_collection__keys(t))]; }
  return null;
})();
  }
  function core_collection__values(m) {
    return (() => {
  const __s107 = m;
  if ((__s107.tag === "MapNil")) {  return []; }
  if ((__s107.tag === "MapCons")) { const v0 = __s107[1]; const t = __s107[2];  return [v0, ...(core_collection__values(t))]; }
  return null;
})();
  }
  function core_collection__put(m, k, v) {
    return core_collection__MapCons(k, v, core_collection__remove$Map(m, k));
  }
  function core_collection__remove$Map(m, k) {
    return (() => {
  const __s108 = m;
  if ((__s108.tag === "MapNil")) {  return core_collection__MapNil(); }
  if ((__s108.tag === "MapCons")) { const k0 = __s108[0]; const v0 = __s108[1]; const t = __s108[2];  return (__eq(k0, k) ? (() => {
  return t;
})() : (() => {
  return core_collection__MapCons(k0, v0, core_collection__remove$Map(t, k));
})()); }
  return null;
})();
  }
  function core_collection__filter$Map(m, p) {
    return (() => {
  const __s109 = m;
  if ((__s109.tag === "MapNil")) {  return core_collection__MapNil(); }
  if ((__s109.tag === "MapCons")) { const k0 = __s109[0]; const v0 = __s109[1]; const t = __s109[2];  return (p(v0) ? (() => {
  return core_collection__MapCons(k0, v0, core_collection__filter$Map(t, p));
})() : (() => {
  return core_collection__filter$Map(t, p);
})()); }
  return null;
})();
  }
  function core_collection__mapValues(m, f) {
    return (() => {
  const __s110 = m;
  if ((__s110.tag === "MapNil")) {  return core_collection__MapNil(); }
  if ((__s110.tag === "MapCons")) { const k0 = __s110[0]; const v0 = __s110[1]; const t = __s110[2];  return core_collection__MapCons(k0, f(v0), core_collection__mapValues(t, f)); }
  return null;
})();
  }
  function core_collection__mapKeys(m, f) {
    return (() => {
  const __s111 = m;
  if ((__s111.tag === "MapNil")) {  return core_collection__MapNil(); }
  if ((__s111.tag === "MapCons")) { const k0 = __s111[0]; const v0 = __s111[1]; const t = __s111[2];  return core_collection__put(core_collection__mapKeys(t, f), f(k0), v0); }
  return null;
})();
  }
  function core_collection__merge(m1, m2) {
    return (() => {
  const __s112 = m2;
  if ((__s112.tag === "MapNil")) {  return m1; }
  if ((__s112.tag === "MapCons")) { const k0 = __s112[0]; const v0 = __s112[1]; const t = __s112[2];  return core_collection__merge(core_collection__put(m1, k0, v0), t); }
  return null;
})();
  }
  function core_collection__toList$Map(m) {
    return core_collection__values(m);
  }
  function core_collection__Optional__Some(a0) { return { tag: "Some", 0: a0 }; }
  const core_collection__Some = core_collection__Optional__Some;
  function core_collection__Optional__None() { return { tag: "None" }; }
  const core_collection__None = core_collection__Optional__None;
  const dict_core_collection_Functor_Optional = {
    map: (d, fa, f) => (() => {
  const __s113 = fa;
  if ((__s113.tag === "Some")) { const v = __s113[0];  return Some(f(v)); }
  if ((__s113.tag === "None")) {  return None(); }
  return null;
})()
  };
  const dict_core_collection_Foldable_Optional = {
    fold: (d, fa, init, f) => (() => {
  const __s114 = fa;
  if ((__s114.tag === "Some")) { const v = __s114[0];  return f(init, v); }
  if ((__s114.tag === "None")) {  return init; }
  return null;
})()
  };
  const dict_core_collection_Monad_Optional = {
    pure: (d, x) => Some(x),
    flatMap: (d, fa, f) => (() => {
  const __s115 = fa;
  if ((__s115.tag === "Some")) { const v = __s115[0];  return f(v); }
  if ((__s115.tag === "None")) {  return None(); }
  return null;
})()
  };
  function core_collection__isSome(o) {
    return (() => {
  const __s116 = o;
  if ((__s116.tag === "Some")) {  return true; }
  if ((__s116.tag === "None")) {  return false; }
  return null;
})();
  }
  function core_collection__isNone(o) {
    return (() => {
  const __s117 = o;
  if ((__s117.tag === "Some")) {  return false; }
  if ((__s117.tag === "None")) {  return true; }
  return null;
})();
  }
  function core_collection__getOrElse(o, d) {
    return (() => {
  const __s118 = o;
  if ((__s118.tag === "Some")) { const v = __s118[0];  return v; }
  if ((__s118.tag === "None")) {  return d; }
  return null;
})();
  }
  function core_collection__orElse(o, d) {
    return (() => {
  const __s119 = o;
  if ((__s119.tag === "Some")) {  return o; }
  if ((__s119.tag === "None")) {  return d; }
  return null;
})();
  }
  function core_collection__filter$Optional(o, p) {
    return (() => {
  const __s120 = o;
  if ((__s120.tag === "Some")) { const v = __s120[0];  return (p(v) ? (() => {
  return Some(v);
})() : (() => {
  return None();
})()); }
  if ((__s120.tag === "None")) {  return None(); }
  return null;
})();
  }
  function core_collection__toResult(o, e) {
    return (() => {
  const __s121 = o;
  if ((__s121.tag === "Some")) { const v = __s121[0];  return core_collection__Ok(v); }
  if ((__s121.tag === "None")) {  return core_collection__Err(e); }
  return null;
})();
  }
  function core_collection__toList$Optional(o) {
    return (() => {
  const __s122 = o;
  if ((__s122.tag === "Some")) { const v = __s122[0];  return [v, ...([])]; }
  if ((__s122.tag === "None")) {  return []; }
  return null;
})();
  }
  function core_collection__Result__Ok(a0) { return { tag: "Ok", 0: a0 }; }
  const core_collection__Ok = core_collection__Result__Ok;
  function core_collection__Result__Err(a0) { return { tag: "Err", 0: a0 }; }
  const core_collection__Err = core_collection__Result__Err;
  function core_collection__isOk(r) {
    return (() => {
  const __s123 = r;
  if ((__s123.tag === "Ok")) {  return true; }
  if ((__s123.tag === "Err")) {  return false; }
  return null;
})();
  }
  function core_collection__isErr(r) {
    return (() => {
  const __s124 = r;
  if ((__s124.tag === "Ok")) {  return false; }
  if ((__s124.tag === "Err")) {  return true; }
  return null;
})();
  }
  function core_collection__unwrapOr(r, d) {
    return (() => {
  const __s125 = r;
  if ((__s125.tag === "Ok")) { const v = __s125[0];  return v; }
  if ((__s125.tag === "Err")) {  return d; }
  return null;
})();
  }
  function core_collection__mapOk(r, f) {
    return (() => {
  const __s126 = r;
  if ((__s126.tag === "Ok")) { const v = __s126[0];  return core_collection__Ok(f(v)); }
  if ((__s126.tag === "Err")) { const e = __s126[0];  return core_collection__Err(e); }
  return null;
})();
  }
  function core_collection__mapErr(r, f) {
    return (() => {
  const __s127 = r;
  if ((__s127.tag === "Ok")) { const v = __s127[0];  return core_collection__Ok(v); }
  if ((__s127.tag === "Err")) { const e = __s127[0];  return core_collection__Err(f(e)); }
  return null;
})();
  }
  function core_collection__flatMapOk(r, f) {
    return (() => {
  const __s128 = r;
  if ((__s128.tag === "Ok")) { const v = __s128[0];  return f(v); }
  if ((__s128.tag === "Err")) { const e = __s128[0];  return core_collection__Err(e); }
  return null;
})();
  }
  function core_collection__toList$Result(r) {
    return (() => {
  const __s129 = r;
  if ((__s129.tag === "Ok")) { const v = __s129[0];  return [v, ...([])]; }
  if ((__s129.tag === "Err")) {  return []; }
  return null;
})();
  }
  function core_collection__Set__SetNil() { return { tag: "SetNil" }; }
  const core_collection__SetNil = core_collection__Set__SetNil;
  function core_collection__Set__SetCons(a0, a1) { return { tag: "SetCons", 0: a0, 1: a1 }; }
  const core_collection__SetCons = core_collection__Set__SetCons;
  const dict_core_collection_Functor_Set = {
    map: (d, fa, f) => (() => {
  const __s130 = fa;
  if ((__s130.tag === "SetNil")) {  return core_collection__SetNil(); }
  if ((__s130.tag === "SetCons")) { const h = __s130[0]; const t = __s130[1];  return core_collection__add(dict_core_collection_Functor_Set.map(dict_core_collection_Functor_Set, t, f), f(h)); }
  return null;
})()
  };
  const dict_core_collection_Foldable_Set = {
    fold: (d, fa, init, f) => (() => {
  const __s131 = fa;
  if ((__s131.tag === "SetNil")) {  return init; }
  if ((__s131.tag === "SetCons")) { const h = __s131[0]; const t = __s131[1];  return dict_core_collection_Foldable_Set.fold(dict_core_collection_Foldable_Set, t, f(init, h), f); }
  return null;
})()
  };
  function core_collection__setEmpty() {
    return core_collection__SetNil();
  }
  function core_collection__setOf(items) {
    return core_collection__SetNil();
  }
  function core_collection__add(s, x) {
    return (core_collection__contains$Set(s, x) ? (() => {
  return s;
})() : (() => {
  return core_collection__SetCons(x, s);
})());
  }
  function core_collection__remove$Set(s, x) {
    return (() => {
  const __s132 = s;
  if ((__s132.tag === "SetNil")) {  return core_collection__SetNil(); }
  if ((__s132.tag === "SetCons")) { const h = __s132[0]; const t = __s132[1];  return (__eq(h, x) ? (() => {
  return t;
})() : (() => {
  return core_collection__SetCons(h, core_collection__remove$Set(t, x));
})()); }
  return null;
})();
  }
  function core_collection__size$Set(s) {
    return (() => {
  const __s133 = s;
  if ((__s133.tag === "SetNil")) {  return 0; }
  if ((__s133.tag === "SetCons")) { const t = __s133[1];  return (core_collection__size$Set(t) + 1); }
  return null;
})();
  }
  function core_collection__isEmpty$Set(s) {
    return (() => {
  const __s134 = s;
  if ((__s134.tag === "SetNil")) {  return true; }
  if ((__s134.tag === "SetCons")) {  return false; }
  return null;
})();
  }
  function core_collection__contains$Set(s, x) {
    return (() => {
  const __s135 = s;
  if ((__s135.tag === "SetNil")) {  return false; }
  if ((__s135.tag === "SetCons")) { const h = __s135[0]; const t = __s135[1];  return (__eq(h, x) ? (() => {
  return true;
})() : (() => {
  return core_collection__contains$Set(t, x);
})()); }
  return null;
})();
  }
  function core_collection__count$Set(s, p) {
    return dict_core_collection_Foldable_Set.fold(dict_core_collection_Foldable_Set, s, 0, (acc, x) => (p(x) ? (() => {
  return (acc + 1);
})() : (() => {
  return acc;
})()));
  }
  function core_collection__forEach$Set(s, f) {
    return (() => {
  const __s136 = s;
  if ((__s136.tag === "SetNil")) {  return null; }
  if ((__s136.tag === "SetCons")) { const h = __s136[0]; const t = __s136[1];  return (() => {
  f(h);
  return core_collection__forEach$Set(t, f);
})(); }
  return null;
})();
  }
  function core_collection__union(s1, s2) {
    return (() => {
  const __s137 = s1;
  if ((__s137.tag === "SetNil")) {  return s2; }
  if ((__s137.tag === "SetCons")) { const h = __s137[0]; const t = __s137[1];  return (core_collection__contains$Set(s2, h) ? (() => {
  return core_collection__union(t, s2);
})() : (() => {
  return core_collection__SetCons(h, core_collection__union(t, s2));
})()); }
  return null;
})();
  }
  function core_collection__intersect(s1, s2) {
    return (() => {
  const __s138 = s1;
  if ((__s138.tag === "SetNil")) {  return core_collection__SetNil(); }
  if ((__s138.tag === "SetCons")) { const h = __s138[0]; const t = __s138[1];  return (core_collection__contains$Set(s2, h) ? (() => {
  return core_collection__SetCons(h, core_collection__intersect(t, s2));
})() : (() => {
  return core_collection__intersect(t, s2);
})()); }
  return null;
})();
  }
  function core_collection__filter$Set(s, p) {
    return (() => {
  const __s139 = s;
  if ((__s139.tag === "SetNil")) {  return core_collection__SetNil(); }
  if ((__s139.tag === "SetCons")) { const h = __s139[0]; const t = __s139[1];  return (p(h) ? (() => {
  return core_collection__SetCons(h, core_collection__filter$Set(t, p));
})() : (() => {
  return core_collection__filter$Set(t, p);
})()); }
  return null;
})();
  }
  function core_collection__flatMap(s, f) {
    return (() => {
  const __s140 = s;
  if ((__s140.tag === "SetNil")) {  return core_collection__SetNil(); }
  if ((__s140.tag === "SetCons")) { const h = __s140[0]; const t = __s140[1];  return core_collection__union(f(h), core_collection__flatMap(t, f)); }
  return null;
})();
  }
  function core_collection__toList$Set(s) {
    return (() => {
  const __s141 = s;
  if ((__s141.tag === "SetNil")) {  return []; }
  if ((__s141.tag === "SetCons")) { const h = __s141[0]; const t = __s141[1];  return [h, ...(core_collection__toList$Set(t))]; }
  return null;
})();
  }
  const dict_core_collection_Tuple_EmptyTuple = {
    size: (d, __self) => 0,
    isEmpty: (d, __self) => true
  };
  const dict_core_collection_Tuple_SingleTuple = {
    size: (d, __self) => 1,
    isEmpty: (d, __self) => false
  };
  const dict_core_collection_Tuple__a__b_ = {
    size: (d, __self) => 2,
    isEmpty: (d, __self) => false
  };
  const dict_core_collection_Tuple__a__b__c_ = {
    size: (d, __self) => 3,
    isEmpty: (d, __self) => false
  };
  const dict_core_collection_Tuple__a__b__c__d_ = {
    size: (d, __self) => 4,
    isEmpty: (d, __self) => false
  };
  const dict_core_collection_Tuple__a__b__c__d__e_ = {
    size: (d, __self) => 5,
    isEmpty: (d, __self) => false
  };
  const dict_core_collection_Tuple__a__b__c__d__e__f_ = {
    size: (d, __self) => 6,
    isEmpty: (d, __self) => false
  };
  const dict_core_collection_Tuple__a__b__c__d__e__f__g_ = {
    size: (d, __self) => 7,
    isEmpty: (d, __self) => false
  };
  const dict_core_collection_Tuple__a__b__c__d__e__f__g__h_ = {
    size: (d, __self) => 8,
    isEmpty: (d, __self) => false
  };
  const dict_core_collection_Tuple__a__b__c__d__e__f__g__h__i_ = {
    size: (d, __self) => 9,
    isEmpty: (d, __self) => false
  };
  const dict_core_collection_Tuple__a__b__c__d__e__f__g__h__i__j_ = {
    size: (d, __self) => 10,
    isEmpty: (d, __self) => false
  };
  function core_encoding__base64Encode(s) {
    return (() => { throw new Error('panic: unreachable') })();
  }
  function core_encoding__base64Decode(s) {
    return (() => { throw new Error('panic: unreachable') })();
  }
  function core_encoding__hexEncode(s) {
    return (() => { throw new Error('panic: unreachable') })();
  }
  function core_encoding__hexDecode(s) {
    return (() => { throw new Error('panic: unreachable') })();
  }
  function core_math__sqrt(x) {
    return Math.pow(x, 0.5);
  }
  function core_math__gcd(a, b) {
    return (__eq(b, 0) ? (() => {
  return a;
})() : (() => {
  const r = core_math__gcd(b, (a % b));
  return r;
})());
  }
  function core_math__lcm(a, b) {
    return (__eq(b, 0) ? (() => {
  return 0;
})() : (() => {
  const r = core_math__gcd(a, b);
  return (a * (b / r));
})());
  }
  function core_math__isEven(n) {
    return __eq((n % 2), 0);
  }
  function core_math__isOdd(n) {
    return (!__eq((n % 2), 0));
  }
  function core_math__sq(n) {
    return (n * n);
  }
  function core_math__cube(n) {
    return ((n * n) * n);
  }
  function core_math__summation(a, b) {
    return (core_collection__range(a, b)).reduce((a, b) => a + b, 0);
  }
  async function core_net__httpGet(url) {
    return (() => { throw new Error('panic: unreachable') })();
  }
  async function core_net__httpPost(url, body) {
    return (() => { throw new Error('panic: unreachable') })();
  }
  async function core_net__socketConnect(host, port) {
    return (() => { throw new Error('panic: unreachable') })();
  }
  const dict_core_number_Num_Rat = {
  };
  function core_random__randNat(n) {
    return Math.floor((Math.random() * Number(n)));
  }
  function core_real__ratOr(s, fallback) {
    return (() => {
  const __s142 = (() => { const __v143 = Number(String(s)); return Number.isNaN(__v143) ? None() : Some(__v143); })();
  if ((__s142.tag === "Some")) { const r = __s142[0];  return r; }
  if ((__s142.tag === "None")) {  return fallback; }
  return null;
})();
  }
  function core_regex__regexMatch(s, pattern) {
    return (() => { throw new Error('panic: unreachable') })();
  }
  function core_regex__regexFind(s, pattern) {
    return (() => { throw new Error('panic: unreachable') })();
  }
  function core_regex__regexSplit(s, pattern) {
    return (() => { throw new Error('panic: unreachable') })();
  }
  function core_regex__regexReplace(s, pattern, repl) {
    return (() => { throw new Error('panic: unreachable') })();
  }
  function core_time__diffMs(a, b) {
    return (b - a);
  }main();
globalThis.__dshLifecycle_onStart(null);

  main();
  globalThis.__dshLifecycle_onStart(null);
  const walk = (n, out) => { if (!n) return out; out.push(n); (n.children || []).forEach(c => walk(c, out)); return out; };
  const all = (r) => walk(JSON.parse(r), []);
  const fab = all(__smLayouts[0]).find(n => n.tag === "FloatingButton");
  globalThis["__dshFn_" + fab.onClick](null);
  const r1 = all(__smLayouts[__smLayouts.length - 1]);
  const rows = r1.filter(n => n.tag === "Box" && typeof n.onClick === "string" && (n.children||[])[0] && n.children[0].tag === "Box" && (n.children[0].children||[])[0] && n.children[0].children[0].tag === "Text" && n.children[0].children[1] && n.children[0].children[1].tag === "Text");
  console.log("rows:", rows.length);
  for (const b of rows) console.log(JSON.stringify({ name: b.children[0].children[0].text, bold: b.children[0].children[0].bold, checked: (b.children || []).some(c => c.tag === "Text" && c.text.indexOf("✓") === 0), childTags: (b.children||[]).map(c => c.tag).join(",") }));

})();
