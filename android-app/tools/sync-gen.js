/**
 * sync-gen.js —— Sublimation for Android：out/*.kt（@compose 发射器产物）→ gen/ 宿主侧合并。
 * 家规：gen/ 由 home.subl→处理器→out/Home.kt 生成，但发射器无法表达宿主组合与抽屉策略，
 * 故本脚本把下列「宿主补丁」应用后再落盘（每次 runcompile.bat 之后执行一次）：
 *   1) Home.kt: EFFECTTAGS_HOST / PACKSHEET_HOST 两处标记 → 宿主 Subl_EffectTags / Subl_PackSheet 组合；
 *   2) Home.kt: strArg("profName")/strArg("sexName") 按出现次序注入下标（宿主按 key+i 取值）；
 *   3) Home.kt: 5 处 ModalBottomSheet(…, sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true))
 *      → 宿主 DshSheet（自绘抽屉：点遮罩/系统返回退出、无下滑手势——用户要求 t79 rev2）；
 *      服药表单抽屉（cancelForm）额外注入 sheetFraction = 2f/3f（用户要求：抽屉上沿拉到屏幕顶部下方 1/3 处）；
 *   3.5) Home.kt: 10 处 hasRec(k) if 块 → 宿主 Subl_RecCard(k)（药名左色块条=该药曲线色；长按=删记录确认框）；
 *   4) PickerRowsA/B/C.kt、LibRowsA/B/C/D.kt、GeneratedCompose.kt 逐字复制。
 * 用法：node android-app/tools/sync-gen.js
 */
const fs = require("fs");
const path = require("path");
const root = path.join(__dirname, "..", "..");
const outDir = path.join(root, "out");
const genDir = path.join(__dirname, "..", "app", "src", "main", "java", "functor", "sublimation", "android", "gen");

let src = fs.readFileSync(path.join(outDir, "Home.kt"), "utf8");
src = src.replace(/\r\n/g, "\n");

// 1) 标记 → 宿主组合
const EFFECT_OLD = 'androidx.compose.material3.Text(text = "EFFECTTAGS_HOST", modifier = Modifier, fontSize = 1.0.sp)';
const EFFECT_NEW = 'functor.sublimation.android.DshComposeHost.Subl_EffectTags(tags = functor.sublimation.android.DshComposeHost.effectTags(), modifier = Modifier)';
const PACK_OLD = 'androidx.compose.material3.Text(text = "PACKSHEET_HOST", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 1.0.sp)';
const PACK_NEW = 'functor.sublimation.android.DshComposeHost.Subl_PackSheet(modifier = Modifier)';
if (!src.includes(EFFECT_OLD) || !src.includes(PACK_OLD)) throw new Error("sync-gen: 标记未找到（请重跑 runcompile 后再同步）");
src = src.replace(EFFECT_OLD, EFFECT_NEW).replace(PACK_OLD, PACK_NEW);

// 2) 下标注入（strArg("profName") → strArg("profName", 0..)，按出现次序）
for (const name of ["profName", "sexName"]) {
  const lit = 'strArg("' + name + '")';
  if (!src.includes(lit)) continue;
  const parts = src.split(lit);
  let out = parts[0];
  for (let i = 1; i < parts.length; i++) out += 'strArg("' + name + '", ' + (i - 1) + ')' + parts[i];
  src = out;
}

// 3) ModalBottomSheet → DshSheet（5 处，onDismissRequest→onDismiss，去 sheetState）
// 服用药物表单抽屉单独注入 sheetFraction = 2f/3f（用户要求：抽屉上沿拉到屏幕顶部下方 1/3 处，
// 即抽屉高 = 2/3 屏高下限；内容更高时 DshSheet 的 heightIn 仍自然撑开）
for (const route of ["closeSettings", "closeDrugList", "closeDoc", "closePackSheet"]) {
  const mbsCall = 'androidx.compose.material3.ModalBottomSheet(onDismissRequest = { functor.sublimation.android.DshComposeHost.act("' + route + '") }, sheetState = androidx.compose.material3.rememberModalBottomSheetState(skipPartiallyExpanded = true))';
  const sheetCall = 'functor.sublimation.android.DshComposeHost.DshSheet(onDismiss = { functor.sublimation.android.DshComposeHost.act("' + route + '") })';
  if (src.split(mbsCall).length - 1 !== 1) throw new Error("sync-gen: ModalBottomSheet 调用未唯一匹配: " + route);
  src = src.split(mbsCall).join(sheetCall);
}
{
  const route = "cancelForm";
  const mbsCall = 'androidx.compose.material3.ModalBottomSheet(onDismissRequest = { functor.sublimation.android.DshComposeHost.act("' + route + '") }, sheetState = androidx.compose.material3.rememberModalBottomSheetState(skipPartiallyExpanded = true))';
  const sheetCall = 'functor.sublimation.android.DshComposeHost.DshSheet(onDismiss = { functor.sublimation.android.DshComposeHost.act("' + route + '") }, sheetFraction = 2f / 3f)';
  if (src.split(mbsCall).length - 1 !== 1) throw new Error("sync-gen: ModalBottomSheet 调用未唯一匹配: " + route);
  src = src.split(mbsCall).join(sheetCall);
}
if (src.includes("ModalBottomSheet")) throw new Error("sync-gen: 仍有未替换的 ModalBottomSheet");

// 3.5) 记录卡整卡宿主化（t82）：hasRec(k) 的 if 块 → DshComposeHost.Subl_RecCard(k)
//   药名左色块条与该药曲线同色（drugColorOf：五朵金花金色、BZD 蓝、阿片棕橙……）；
//   点卡 = 高亮曲线（toggleChartHi 不变），长按 = 弹删除确认框（askDeleteRec）。
//   替换保留 if 头，故重复执行幂等（块内容已是单行 Subl_RecCard 时原样写回）。
{
  for (let k = 0; k < 10; k++) {
    const tagIf = 'if (functor.sublimation.android.DshComposeHost.boolArg("hasRec", ' + k + ')) {';
    const at = src.indexOf(tagIf);
    if (at < 0) throw new Error("sync-gen: hasRec 卡 " + k + " 未找到");
    let depth = 0, i = at + tagIf.length - 1;   // i 指向 if 的 '{'
    for (; i < src.length; i++) {
      const ch = src[i];
      if (ch === "{") depth++;
      else if (ch === "}") { depth--; if (depth === 0) break; }
    }
    if (depth !== 0) throw new Error("sync-gen: hasRec 卡 " + k + " 括号不配对");
    const ind = "                    ";
    const replacement = tagIf + "\n" + ind + "functor.sublimation.android.DshComposeHost.Subl_RecCard(" + k + ")\n" + ind + "}";
    src = src.slice(0, at) + replacement + src.slice(i + 1);
  }
}

fs.writeFileSync(path.join(genDir, "Home.kt"), src);

// 4) 逐字复制
for (const f of ["PickerRowsA.kt", "PickerRowsB.kt", "PickerRowsC.kt", "LibRowsA.kt", "LibRowsB.kt", "LibRowsC.kt", "LibRowsD.kt", "GeneratedCompose.kt"]) {
  fs.copyFileSync(path.join(outDir, f), path.join(genDir, f));
}
console.log("sync-gen: Home.kt 已打补丁并落盘，复制 8 个逐字文件");
