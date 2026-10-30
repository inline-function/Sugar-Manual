package functor.sublimation.android

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import functor.sublimation.android.gen.flash
import functor.sublimation.android.gen.SublSeries
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.json.JSONObject

/**
 * 逻辑 ↔ 布局 的桥（android doc/Compose代码生成契约.md §4）。
 *
 * 分工：subl 的 `@compose` 视图树在**编译期**展开成 Kotlin Compose 函数，函数体（可变状态、
 * 校验、持久化）仍编译成 JS 跑在 WebView 里。两边只交换**数据**，不交换视图：
 *
 *   - JS → KT（读）：编译器在 `@compose` 函数执行末尾发送 `pushState("<模块>.<函数>", {变量: 值})`；
 *     本类按槽位 `<模块>.<函数>.<变量>` 缓存 JSON，并把解析后的值写进 Compose 的 [MutableState]，
 *     从而触发真重组（不需要任何「JSON 树 → View」运行时映射）。
 *   - KT → JS（写）：交互只调用 JS 里已注册的回调槽位 `__dshFn_<key>`（协议 v2 的回调实例键）。
 *
 * 线程：`pushState` 从 WebView 的 JavascriptInterface 线程进来，统一 post 到主线程再改状态。
 */
object DshComposeHost {
    private const val tag = "Sublimation.Compose"

    /** 槽位 → Compose 状态（`status[key].value` 是解析后的 Kotlin 值）。 */
    private val status = LinkedHashMap<String, MutableState<Any?>>()

    /** 槽位 → JSON 值 → Kotlin 值（生成器的 stateOf 注册）。 */
    private val parsers = LinkedHashMap<String, (Any?) -> Any?>()

    @Volatile
    private var web: android.webkit.WebView? = null

    @Volatile
    private var poster: ((() -> Unit) -> Unit)? = null

    fun attach(webView: android.webkit.WebView, uiPost: (() -> Unit) -> Unit) {
        web = webView
        poster = uiPost
    }

    private fun ui(f: () -> Unit) {
        val p = poster
        if (p != null) p(f) else f()
    }

    /**
     * Compose 侧读一个状态槽：首次组合注册（用 [default] 渲染，等 JS 推送后替换），之后的推送
     * 经 [pushState] 写进同一个 [MutableState]，组合自动重跑。
     *
     * @param key     槽位键（`<模块>.<函数>.<变量名>`）
     * @param default 未推送前的缺省值（= 语言侧字段默认值，编译期已知）
     * @param parse   JSON 值 → Kotlin 值（生成器按值映射表 §4.1 填）
     */
    @Composable
    fun <T : Any> stateOf(key: String, default: T, parse: (JSONObject) -> T): MutableState<T> {
        val holder = remember(key) {
            @Suppress("UNCHECKED_CAST")
            val existing = status[key] as? MutableState<T>
            if (existing != null) existing else {
                parsers[key] = { raw: Any? -> parse(JSONObject().put("v", raw)) }
                val st = mutableStateOf(default)
                status[key] = st as MutableState<Any?>
                st
            }
        }
        return holder
    }

    /** JS 推送一个 `@compose` 函数的局部状态（`{变量名: 值}`）。 */
    @android.webkit.JavascriptInterface
    fun pushState(fn: String, stateJson: String) {
        val obj = try { JSONObject(stateJson) } catch (t: Throwable) {
            Log.w(tag, "pushState 解析失败 " + fn + ": " + t.message); return
        }
        val keys = obj.keys()
        while (keys.hasNext()) {
            val name = keys.next()
            val key = fn + "." + name
            val raw = obj.opt(name)
            ui { applySlot(key, raw) }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun applySlot(key: String, raw: Any?) {
        val holder = status[key]
        val parse = parsers[key]
        if (holder == null || parse == null) {
            Log.d(tag, "pushState 未注册槽位: " + key); return
        }
        val v = try { parse(raw) } catch (t: Throwable) {
            Log.w(tag, "槽位解析失败 " + key + ": " + t.message); return
        }
        if (holder.value != v) holder.value = v
    }

    /** 交互 → JS 回调槽位（`__dshFn_<key>`，协议 v2 的回调实例键）。 */
    fun callJs(key: String, argsJson: String = "[]") {
        val w = web ?: run { Log.w(tag, "callJs 无 WebView: " + key); return }
        w.post {
            w.evaluateJavascript(
                "try{ var f = globalThis[\"__dshFn_" + key + "\"];" +
                    " if (typeof f === \"function\") f.apply(null, " + argsJson + ");" +
                    " else console.log(\"missing cb " + key + "\"); }catch(e){ console.log(e); }",
                null)
        }
    }


    /** 选药行序 → 药名（与 home.subl 表单行 pickDrugAt(0..9) 一一对应）。 */
    private val drugNames = listOf("氟伏沙明", "帕罗西汀", "氟西汀", "舍曲林", "西酞普兰", "艾司西酞普兰", "地西泮", "艾司唑仑", "阿普唑仑", "氯硝西泮", "劳拉西泮", "咪达唑仑", "吗啡", "羟考酮", "芬太尼", "可待因", "曲马多", "哌替啶", "碳酸锂", "丙戊酸", "卡马西平", "拉莫三嗪", "文拉法辛", "度洛西汀", "阿米替林", "米氮平", "安非他酮", "曲唑酮", "氯米帕明", "哌甲酯", "阿托莫西汀", "多奈哌齐", "卡巴拉汀", "加兰他敏", "丁螺环酮", "苏沃瑞生", "莱博瑞生", "氯胺酮", "裸盖菇素", "苯海索", "苯扎托品")
    /** per-drug 资料页文本（由 41 个 .subl 药物资料生成；与 main.subl 资料页同源，F≈=估算值）。 */
    private fun drugDocText(name: String): String = when (name) {
        "氟伏沙明" -> "处方药\n标签：选择性血清素再摄取抑制剂、SSRIs、五朵金花、抗抑郁药、抗强迫药、抗焦虑药、助眠药\n\n氟伏沙明是一种选择性血清素再摄取抑制剂类抗抑郁药，主要用于治疗抑郁症和强迫症。它通过抑制中枢神经突触对血清素的再摄取，提高突触间隙递质浓度，从而发挥抗抑郁、抗焦虑作用。常见不良反应为恶心、呕吐等胃肠道症状，通常服药两周后减轻。\n\n【药效】强效 · 血清素转运体抑制；极弱 · 正肾素转运体抑制；强效 · 激动 σ1 受体；极弱 · α1-肾上腺素能拮抗\n【酶抑制】强效 · CYP1A2；强效 · CYP2C19；弱效 · CYP2C9；弱效 · CYP3A4\n【急性期疗效】强效 · 镇静\n【维持期疗效】极强 · 抗强迫；强效 · 抗抑郁；中效 · 抗焦虑\n【急性不良反应】罕见 · 血清素综合征"
        "帕罗西汀" -> "处方药\n标签：SSRIs、抗抑郁药\n\n演示用最小资料（预警引擎联用示例），非完整药物资料。\n\n【药效】强效 · 血清素转运体抑制\n【酶抑制】极强 · CYP2D6\n【维持期疗效】强效 · 抗抑郁\n【急性不良反应】常见 · 恶心"
        "氟西汀" -> "处方药\n标签：选择性血清素再摄取抑制剂、SSRIs、五朵金花、抗抑郁药、抗强迫药、抗焦虑药、抗贪食药\n\n氟西汀是选择性血清素再摄取抑制剂类抗抑郁药，用于抑郁症、强迫症、神经性贪食症与经前烦躁障碍。口服吸收良好，经 CYP2D6 代谢为活性产物去甲氟西汀；母药半衰期长（慢性给药后约 4-6 天）、去甲氟西汀约 7-15 天，是「五朵金花」中消除最慢者，漏服后血药波动小、停药综合征发生率低，但清除受肝功能与合并用药影响显著。氟西汀是 CYP2D6 强抑制剂，可使经 2D6 活化的前体药（可待因、曲马多）镇痛效应下降，并升高三环类抗抑郁药、部分抗精神病药血药浓度。\n\n【药效】强效 · 血清素转运体抑制；极弱 · 正肾素转运体抑制；极弱 · 多巴胺转运体抑制\n【酶抑制】极强 · CYP2D6；中效 · CYP2C19；弱效 · CYP3A4；弱效 · CYP2C9\n【急性期疗效】弱效 · 激活（早期）\n【维持期疗效】极强 · 抗抑郁；强效 · 抗强迫；强效 · 抗焦虑；强效 · 抗贪食\n【急性不良反应】罕见 · 血清素综合征"
        "舍曲林" -> "处方药\n标签：选择性血清素再摄取抑制剂、SSRIs、五朵金花、抗抑郁药、抗强迫药、抗焦虑药、抗创伤药\n\n舍曲林是萘胺类选择性血清素再摄取抑制剂，用于抑郁症、强迫症、惊恐障碍、社交焦虑障碍与创伤后应激障碍（其中创伤后应激与经前烦躁在中国部分属【超说明书】用法）。对心血管安全性相对较好，可用于冠心病及心梗后抑郁患者。经 CYP2D6（次要）、CYP2C19、CYP3A4 与 CYP2B6 代谢，活性代谢物去甲舍曲林活性很低；自身仅为弱 CYP2D6 抑制剂，对经 2D6 活化的前体药影响弱于帕罗西汀与氟西汀；腹泻等胃肠道反应在 SSRI 中较突出。\n\n【药效】强效 · 血清素转运体抑制；极弱 · 正肾素转运体抑制；极弱 · 多巴胺转运体抑制\n【酶抑制】弱效 · CYP2D6；极弱 · CYP2B6\n【急性期疗效】弱效 · 激活（早期）\n【维持期疗效】极强 · 抗强迫；强效 · 抗抑郁；强效 · 抗焦虑；强效 · 抗创伤\n【急性不良反应】罕见 · 血清素综合征"
        "西酞普兰" -> "处方药\n标签：选择性血清素再摄取抑制剂、SSRIs、五朵金花、抗抑郁药、抗焦虑药\n\n西酞普兰是酞酐类 SSRI 消旋体，5-HT 再摄取抑制活性集中于左旋异构体（即艾司西酞普兰）。用于抑郁症与各类焦虑障碍。对 CYP 酶抑制极弱、药物相互作用少，适合多药联用与老年患者；但剂量依赖性 QT 间期延长明确，成人最大日剂量 40 mg，【超说明书】高剂量须评估电解质与心电监测。经 CYP3A4 与 CYP2C19（次要 CYP2D6）代谢为弱活性去甲西酞普兰。\n\n【药效】强效 · 血清素转运体抑制（左旋体）；极弱 · 正肾素转运体抑制\n【酶抑制】极弱 · CYP2D6\n【急性期疗效】弱效 · 镇静\n【维持期疗效】极强 · 抗抑郁；强效 · 抗焦虑\n【急性不良反应】罕见 · 血清素综合征"
        "艾司西酞普兰" -> "处方药\n标签：选择性血清素再摄取抑制剂、SSRIs、五朵金花、抗抑郁药、抗焦虑药\n\n艾司西酞普兰是西酞普兰的左旋 S-构型异构体，5-HT 再摄取抑制活性集中、对转运体选择性更高，起效与耐受优于消旋体；用于抑郁症、广泛性焦虑障碍、惊恐障碍与社交焦虑障碍。经 CYP3A4、CYP2C19 与 CYP2D6 代谢，对 CYP2D6 抑制极弱、相互作用少；剂量依赖性 QT 间期延长风险弱于消旋体，但高剂量仍须监测。\n\n【药效】强效 · 血清素转运体抑制；极弱 · 正肾素转运体抑制\n【酶抑制】极弱 · CYP2D6\n【急性期疗效】弱效 · 镇静\n【维持期疗效】极强 · 抗抑郁；强效 · 抗焦虑；强效 · 抗惊恐\n【急性不良反应】罕见 · 血清素综合征"
        "劳拉西泮" -> "处方药\n标签：苯二氮卓类、抗焦虑药、镇静药、催眠药、抗癫痫药（持续状态）、超说明书（失眠与化疗止吐辅助）\n\n劳拉西泮是中效 1,4-苯二氮卓类，用于焦虑症、焦虑性失眠、癫痫持续状态（静脉）、化疗相关恶心呕吐辅助与术前镇静。与多数苯二氮卓不同，其主要经 UGT2B7/2B15 葡萄糖醛酸化直接清除、不经 CYP450 氧化，故与 CYP 抑制剂（氟伏沙明、克拉霉素等）相互作用少，适合肝功能不全或多药联用场景；高龄与合用阿片/乙醇时呼吸抑制风险增加（黑框警告），长期使用产生依赖。\n\n【药效】强效 · GABA-A受体苯二氮卓位点变构调节\n【急性期疗效】强效 · 抗焦虑（持续状态）；强效 · 镇静（起效较快）\n【维持期疗效】强效 · 抗焦虑；强效 · 焦虑性失眠；中效 · 抗癫痫（持续状态首选静注）；中效 · 化疗止吐辅助（超说明书）\n【急性不良反应】少见 · 呼吸抑制；常见 · 嗜睡；常见 · 头晕"
        "咪达唑仑" -> "处方药\n标签：苯二氮卓类、催眠药、镇静药、麻醉辅助药、超说明书（儿童口服术前镇静）\n\n咪达唑仑是短效咪唑并苯二氮卓类，起效快（静脉 1-3 分钟）、半衰期短，用于麻醉诱导与维持、ICU 镇静、术前镇静与癫痫持续状态。口服首过代谢显著（生物利用度约 44%），中国常以注射剂【超说明书】口服用于儿童术前镇静。主要经 CYP3A4 代谢，与强 CYP3A4 抑制剂（克拉霉素、伏立康唑、利托那韦）合用可致深度镇静与呼吸抑制；氟伏沙明（1A2/3A4 抑制）亦可升高其血药浓度。与阿片类合用呼吸抑制风险显著（FDA 黑框警告）。\n\n【药效】强效 · GABA-A受体苯二氮卓位点变构调节；强效 · 催眠（起效快）\n【急性期疗效】强效 · 催眠（快起效）；强效 · 镇静（术前）\n【维持期疗效】强效 · ICU镇静；强效 · 麻醉诱导；强效 · 癫痫持续状态（静注）；中效 · 儿童术前镇静（口服，超说明书）\n【急性不良反应】少见 · 呼吸抑制；常见 · 嗜睡"
        "地西泮" -> "处方药\n标签：苯二氮卓类、抗焦虑药、催眠药、抗癫痫药、镇静药、肌肉松弛药\n\n地西泮是长效 1,4-苯二氮卓类，增强 GABA-A 受体介导的抑制性传导。用于焦虑症、酒精戒断综合征、肌肉痉挛、麻醉前给药与癫痫持续状态（静脉注射）。口服吸收快而接近完全，血浆蛋白结合率高（约 98%），经 CYP3A4 与 CYP2C19 氧化为活性代谢物去甲地西泮（半衰期 40-100 小时），反复给药后蓄积明显、次日宿醉感重。与阿片类镇痛药或乙醇合用可产生严重呼吸抑制乃至致死（FDA 黑框警告），长期使用产生耐受、依赖与戒断综合征。\n\n【药效】强效 · GABA-A受体苯二氮卓位点变构调节；强效 · 边缘系统抑制（抗焦虑）；中效 · 脊髓反射抑制（肌肉松弛）\n【急性期疗效】强效 · 镇静；强效 · 抗焦虑（快速起效）\n【维持期疗效】强效 · 抗焦虑；强效 · 抗癫痫（持续状态首选静注）；强效 · 酒精戒断；中效 · 肌肉松弛\n【急性不良反应】少见 · 呼吸抑制；常见 · 嗜睡；常见 · 头晕"
        "艾司唑仑" -> "处方药\n标签：苯二氮卓类、催眠药、镇静药、助眠药\n\n艾司唑仑是三唑并苯二氮卓类短-中效催眠药（中国临床常用），用于失眠的入睡困难与夜间易醒。口服吸收较快，主要经 CYP3A4 代谢（与 CYP3A4 抑制剂如氟伏沙明、克拉霉素、伊曲康唑合用血药浓度升高；与诱导剂如利福平、卡马西平合用疗效下降）；活性代谢物少、蓄积弱于地西泮。与阿片类或乙醇合用可致严重呼吸抑制（黑框警告），高龄与慢阻肺患者更敏感。\n\n【药效】强效 · GABA-A受体苯二氮卓位点变构调节\n【急性期疗效】强效 · 催眠（入睡）；强效 · 镇静\n【维持期疗效】强效 · 入睡困难；强效 · 睡眠维持\n【急性不良反应】少见 · 呼吸抑制；常见 · 次日残余嗜睡；常见 · 头晕"
        "阿普唑仑" -> "处方药\n标签：苯二氮卓类、抗焦虑药、抗惊恐药、镇静药、超说明书（抑郁伴焦虑）\n\n阿普唑仑是三唑并苯二氮卓类中效抗焦虑药，用于广泛性焦虑障碍与惊恐障碍；中国常见【超说明书】用法包括抑郁伴焦虑状态与经前综合征。口服后 1-2 小时达峰、起效快，适合急性焦虑发作；经 CYP3A4 代谢（α-羟化）为弱活性产物。与 CYP3A4/2D6 抑制剂（氟伏沙明、氟西汀、帕罗西汀、克拉霉素）合用血药浓度升高、过度镇静风险增加；与阿片类或乙醇合用可致严重呼吸抑制（黑框警告）。短效苯二氮卓骤停戒断症状较重，宜递减停药。\n\n【药效】强效 · GABA-A受体苯二氮卓位点变构调节\n【急性期疗效】强效 · 抗焦虑（快速起效）；强效 · 镇静\n【维持期疗效】强效 · 抗焦虑；强效 · 抗惊恐；中效 · 催眠（焦虑性失眠）\n【急性不良反应】少见 · 呼吸抑制；常见 · 嗜睡；常见 · 头晕"
        "氯硝西泮" -> "处方药\n标签：苯二氮卓类、抗焦虑药、抗癫痫药、助眠药、超说明书（躁狂辅助/惊恐/静坐不能）\n\n氯硝西泮是长效硝基苯二氮卓类，中国说明书适应证为癫痫（失神发作、肌阵挛发作）与焦虑状态。常用【超说明书】场景包括双相障碍躁狂或混合发作的辅助镇静、惊恐障碍、抗精神病药所致静坐不能与快动眼睡眠行为障碍。口服吸收良好，半衰期长（约 20-60 小时），日间残余效应与蓄积明显；经 CYP3A4 代谢。与阿片类或乙醇合用可致严重呼吸抑制（黑框警告）；长期使用产生依赖，骤停可诱发癫痫反跳。\n\n【药效】强效 · GABA-A受体苯二氮卓位点变构调节；强效 · 提高癫痫发作阈\n【急性期疗效】强效 · 镇静；强效 · 抗焦虑\n【维持期疗效】强效 · 抗癫痫；强效 · 抗焦虑；强效 · 抗惊恐（超说明书）；强效 · 躁狂辅助镇静（超说明书）\n【急性不良反应】少见 · 呼吸抑制；常见 · 嗜睡；常见 · 头晕"
        "吗啡" -> "处方药\n标签：阿片类、强阿片镇痛药、麻醉性镇痛药、麻醉药品（管制）\n\n吗啡是天然阿片生物碱（罂粟）、μ/κ 阿片受体完全激动剂，重度疼痛（癌痛、急性锐痛、急性肺水肿伴呼吸困难）的金标准。口服因高首过代谢生物利用度低（约 34%）；主要经 UGT2B7 葡萄糖醛酸化为活性代谢物 M6G（强 μ 激动，蓄积可致延迟呼吸抑制）与 M3G（可能致神经毒性）。与苯二氮卓类、乙醇或其他中枢抑制剂联用可致致命呼吸抑制（FDA 黑框警告）；长期使用产生耐受、生理依赖与便秘（持续不耐受）。严格按麻醉药品管理。\n\n【药效】强效 · μ-阿片受体激动；中效 · κ-阿片受体激动；强效 · 脊髓与中枢痛传导抑制\n【急性期疗效】强效 · 镇痛（急性锐痛）；强效 · 镇静；中效 · 急性肺水肿呼吸困难缓解\n【维持期疗效】强效 · 镇痛（癌痛）；强效 · 镇痛（慢性重度疼痛）\n【急性不良反应】常见 · 呼吸抑制；十分常见 · 镇静；常见 · 恶心呕吐；常见 · 欣快"
        "羟考酮" -> "处方药\n标签：阿片类、强阿片镇痛药、半合成阿片、麻醉药品（管制）\n\n羟考酮是半合成蒂巴因衍生物、μ/κ 阿片受体激动剂，用于中重度急慢性疼痛与癌痛，常与对乙酰氨基酚或纳洛酮组成复方（中国主要为缓释片与复方制剂）。口服生物利用度较吗啡高（60-87%）；主要经 CYP3A4 N-去甲基化为无活性的去甲羟考酮，次要经 CYP2D6 O-去甲基化为活性低的羟吗啡酮，故与 CYP3A4 抑制剂（克拉霉素、伏立康唑、氟伏沙明）合用血药浓度明显升高；与 CYP2D6 强抑制剂（帕罗西汀、氟西汀）合用镇痛效应下降。与苯二氮卓类合用呼吸抑制风险（黑框警告）。\n\n【药效】强效 · μ-阿片受体激动；中效 · κ-阿片受体激动\n【急性期疗效】强效 · 镇痛（急性中重度）；中效 · 镇静\n【维持期疗效】强效 · 镇痛（癌痛）；强效 · 镇痛（慢性疼痛）\n【急性不良反应】常见 · 呼吸抑制；十分常见 · 镇静；常见 · 恶心呕吐；常见 · 头晕；常见 · 欣快"
        "芬太尼" -> "处方药\n标签：阿片类、强阿片镇痛药、合成苯基哌啶类、麻醉药品（管制）、透皮给药\n\n芬太尼是合成苯基哌啶类 μ 阿片受体强激动剂（效价约为吗啡 75-100 倍），用于麻醉辅助（静脉）、重度癌痛（透皮贴片）与穿透性疼痛（粘膜制剂）。透皮贴片 12.5-100 μg/h、每 72 小时更换，达稳态需 12-24 小时，撤贴后表观半衰期约 17 小时；剂量按 72 小时累计释放量（mg）估算。主要经 CYP3A4 快速失活，与 CYP3A4 抑制剂（克拉霉素、伏立康唑、利托那韦）合用血药浓度可数倍升高致死；纳洛酮拮抗需重复给药（脂库再分布）。与苯二氮卓类合用呼吸抑制风险（FDA 黑框警告）。按麻醉药品严格管理。\n\n【药效】极强效 · μ-阿片受体激动；强效 · 镇痛（效价高）\n【急性期疗效】极强效 · 镇痛（急性重度）；强效 · 麻醉辅助（静脉）\n【维持期疗效】极强效 · 镇痛（癌痛）；强效 · 镇痛（慢性重度疼痛）\n【急性不良反应】常见 · 呼吸抑制；十分常见 · 镇静；常见 · 恶心呕吐；常见 · 欣快"
        "可待因" -> "处方药\n标签：阿片类、弱阿片镇痛药、镇咳药、前体药、麻醉药品（管制）\n\n可待因是天然阿片生物碱、弱 μ 阿片受体激动剂，本身活性低，镇痛效应主要依赖 CYP2D6 将约 5-10% 转化为吗啡。用于轻中度疼痛与干咳（中国含可待因镇咳复方曾广泛使用，现严格管控）。与 CYP2D6 强抑制剂（帕罗西汀、氟西汀）合用可几乎消除其镇痛效应（联用即警示）；与苯二氮卓类、乙醇合用呼吸抑制风险增加；CYP2D6 慢代谢者镇痛无效。哺乳期妇女禁用（转化为吗啡可致婴儿呼吸抑制）。\n\n【药效】弱效 · μ-阿片受体激动（前体，需 CYP2D6 转化吗啡）；中效 · 咳嗽中枢抑制\n【急性期疗效】中效 · 镇痛（轻中度）；强效 · 镇咳（干咳）\n【维持期疗效】中效 · 镇痛（轻中度疼痛）；中效 · 镇咳（限无痰干咳）\n【急性不良反应】少见 · 呼吸抑制；常见 · 嗜睡；常见 · 恶心呕吐；常见 · 欣快"
        "曲马多" -> "处方药\n标签：中枢性镇痛药、非典型阿片、SNRI样作用、前体药（M1）、管制（二类精神药品）、超说明书（不宁腿综合征/夜间下肢痛）\n\n曲马多是人工合成的中枢性镇痛药，结构属 4-苯基哌啶类（与可待因/吗啡结构相关但临床归类为非典型阿片）：消旋体的 (+)-异构体弱激动 μ 受体并抑制血清素再摄取，(-)-异构体抑制正肾素再摄取，镇痛由母药与活性代谢物 O-去甲基曲马多（M1，CYP2D6 生成，μ 受体亲和力更高）共同介导。用于中度疼痛；中国常见【超说明书】用于不宁腿综合征与夜间下肢痛。与 CYP2D6 抑制剂（帕罗西汀、氟西汀）合用镇痛下降并增加血清素能毒性；与 SSRI/MAOI/三环类合用可致血清素综合征；与苯二氮卓类合用呼吸抑制风险增加；过量癫痫风险高。\n\n【药效】弱效 · μ-阿片受体激动（母药）；强效 · μ-阿片受体激动（M1）；中效 · 血清素再摄取抑制；强效 · 正肾素再摄取抑制\n【急性期疗效】中效 · 镇痛（中度）；弱效 · 镇静\n【维持期疗效】强效 · 镇痛（中度疼痛）；中效 · 不宁腿综合征（超说明书）；中效 · 夜间下肢痛（超说明书）\n【急性不良反应】常见 · 恶心呕吐；常见 · 头晕；少见 · 呼吸抑制；常见 · 欣快"
        "哌替啶" -> "处方药\n标签：阿片类、弱阿片镇痛药、合成苯基哌啶类、麻醉药品（管制）、超说明书（术后寒战治疗）\n\n哌替啶（度冷丁）是合成苯基哌啶类 μ 阿片受体激动剂，曾广泛用于急性疼痛与分娩镇痛，现因代谢物毒性被多数指南限制使用。主要经 CYP3A4 N-去甲基化生成去甲哌替啶（半衰期 15-30 小时），后者无镇痛活性但致神经兴奋（焦虑、肌阵挛、癫痫），重复给药与肾功能不全者蓄积风险高。与单胺氧化酶抑制剂合用可致命（兴奋、高热、抽搐），与 SSRI（氟伏沙明、舍曲林等）合用血清素综合征风险；与苯二氮卓类合用呼吸抑制。产科与术后寒战治疗为中国常见【超说明书】场景。按麻醉药品管理。\n\n【药效】强效 · μ-阿片受体激动；弱效 · 阿托品样作用（解痉）\n【急性期疗效】强效 · 镇痛（急性）；弱效 · 寒战治疗（麻醉后，超说明书）\n【维持期疗效】中效 · 镇痛（急性疼痛，少用）；弱效 · 分娩镇痛（现已少用）\n【急性不良反应】常见 · 呼吸抑制；常见 · 恶心呕吐；常见 · 头晕；常见 · 欣快"
        "碳酸锂" -> "处方药\n标签：心境稳定剂，锂盐，抗躁狂药\n\n碳酸锂是经典心境稳定剂，用于双相障碍躁狂发作与维持治疗，并降低自杀风险。口服几乎完全吸收、不经肝脏代谢、全部经肾排泄（近端小管重吸收与钠竞争），治疗窗窄（0.6-1.2 mmol/L）。与 SSRI 联用可增加血清素综合征风险并可能升高锂浓度；与 NSAIDs、ACE 抑制剂、噻嗪类利尿剂联用升高锂血药浓度；快速减量可诱发反跳躁狂。\n\n【药效】极强 · 抑制第二信使系统（肌醇通路）；强效 · 五羟色胺能增强；中效 · 多巴胺能活动调节\n【酶抑制】无显著抑制（本库引擎仅建模 CYP/UGT 抑制；诱导性相互作用见药物描述）\n【急性期疗效】中效 · 急性躁狂（数日起效）\n【维持期疗效】极强 · 心境稳定（躁狂预防）；强效 · 自杀风险降低；中效 · 增效抗抑郁\n【急性不良反应】常见 · 手抖；常见 · 口渴；常见 · 恶心；少见 · 血清素综合征\n【慢性不良反应】常见 · 手抖；常见 · 烦渴多尿；少见 · 甲状腺功能减退；少见 · 肾功能下降；罕见 · 锂中毒\n【药代】口服常释 · T½ 24h · Tmax 1-2h · F=1（说明书值）"
        "丙戊酸" -> "处方药\n标签：心境稳定剂，抗癫痫药，广谱抗痫\n\n丙戊酸是广谱抗癫痫与心境稳定剂，用于双相障碍、癫痫与偏头痛预防。主要经 CYP2C9/2C19 与葡萄糖醛酸化代谢。为 CYP2C9 强抑制剂并抑制 UGT 葡萄糖醛酸化——显著升高拉莫三嗪血药浓度（联用时拉莫三嗪需减量半、加倍缓慢加量，否则皮疹/SJS 风险显著升高）；竞争血浆蛋白结合。\n\n【药效】强效 · 电压门控钠通道阻断；强效 · GABA能增强；中效 · T型钙通道阻断\n【酶抑制】极强 · CYP2C9；中效 · CYP2C19；强效 · UGT1A4\n【急性期疗效】中效 · 急性躁狂（数日起效）\n【维持期疗效】极强 · 心境稳定；强效 · 抗癫痫；中效 · 偏头痛预防\n【急性不良反应】十分常见 · 恶心；常见 · 嗜睡；常见 · 震颤\n【慢性不良反应】常见 · 体重增加；常见 · 震颤；常见 · 脱发；少见 · 肝毒性；少见 · 血小板减少；少见 · 高氨血症\n【药代】口服常释 · T½ 12h · Tmax 1-4h · F=0.9（说明书值）"
        "卡马西平" -> "处方药\n标签：心境稳定剂，抗癫痫药，钠通道阻滞剂\n\n卡马西平是钠通道阻滞类心境稳定剂与抗癫痫药，用于躁狂、癫痫与三叉神经痛。是强效 CYP3A4/2C9/1A2 及 UGT 诱导剂（并有自身诱导），降低口服避孕药、奥氮平、部分抗抑郁药等经其代谢药物的血药浓度；与锂盐联用神经毒性风险增加；HLA-B*1502 阳性者 SJS/TEN 风险显著升高（中国人群建议筛查）。慢加量起始。\n\n【药效】强效 · 电压门控钠通道阻断；中效 · T型钙通道阻断；中效 · 腺苷受体调节\n【酶抑制】无显著抑制（本库引擎仅建模 CYP/UGT 抑制；诱导性相互作用见药物描述）\n【急性期疗效】中效 · 急性躁狂（缓慢起效）\n【维持期疗效】极强 · 心境稳定（躁狂）；强效 · 抗癫痫；中效 · 三叉神经痛\n【急性不良反应】十分常见 · 头晕；常见 · 嗜睡；常见 · 恶心\n【慢性不良反应】常见 · 头晕；常见 · 嗜睡；少见 · 低血钠；少见 · 皮疹；少见 · 白细胞减少\n【药代】口服常释 · T½ 12h · Tmax 4-8h · F=0.8（F≈ 估算）"
        "拉莫三嗪" -> "处方药\n标签：心境稳定剂，抗癫痫药，双相抑郁维持\n\n拉莫三嗪是钠通道阻滞类抗癫痫/心境稳定剂，主要用于双相障碍抑郁发作维持期预防。不依赖 CYP450，主要经 UGT1A4 葡萄糖醛酸化代谢——丙戊酸抑制该途径使其血药浓度约升高 2 倍（联用须减量半、加倍缓慢加量）；与含雌激素避孕药合用浓度下降。皮疹需缓慢加量观察（含 SJS/TEN 风险）。\n\n【药效】强效 · 电压门控钠通道阻断；中效 · 抑制谷氨酸释放；中效 · P型钙通道阻断\n【酶抑制】无显著抑制（本库引擎仅建模 CYP/UGT 抑制；诱导性相互作用见药物描述）\n【急性期疗效】缓效 · 双相抑郁维持\n【维持期疗效】极强 · 双相抑郁维持；强效 · 抗癫痫；强效 · 躁狂预防\n【急性不良反应】常见 · 头晕；常见 · 头痛；常见 · 嗜睡\n【慢性不良反应】常见 · 失眠；少见 · 皮疹；少见 · 视力模糊；少见 · 震颤\n【药代】口服常释 · T½ 25h · Tmax 1.4-4.8h · F=0.98（说明书值）"
        "文拉法辛" -> "处方药\n标签：抗抑郁药，SNRIs，血清素去甲肾上腺素再摄取抑制剂\n\n文拉法辛是 SNRI 类抗抑郁药，用于抑郁症、焦虑障碍与神经病理性疼痛。经 CYP2D6 代谢为活性产物 O-去甲基文拉法辛（ODV），母药 T½ 约 5h 而 ODV 约 11h，联用或停药时代谢物尾段拉长浓度曲线（本库代谢物双曲线即演示该时序）。MAOI 禁忌联用；与 SSRI 联用增加血清素综合征风险；高剂量升高血压。\n\n【药效】强效 · 血清素转运体抑制；中效 · 正肾素转运体抑制；极弱 · 多巴胺转运体抑制\n【酶抑制】弱效 · CYP2D6\n【急性期疗效】中效 · 抗抑郁起效\n【维持期疗效】极强 · 抗抑郁；强效 · 抗焦虑；中效 · 镇痛（神经病理性）\n【急性不良反应】常见 · 恶心；常见 · 失眠；少见 · 血清素综合征\n【慢性不良反应】常见 · 恶心；常见 · 头痛；常见 · 失眠；常见 · 性功能障碍；少见 · 血压升高；少见 · 低血钠\n【药代】口服常释 · T½ 5h · Tmax 1.5-2.5h · F=0.45（F≈ 估算）"
        "度洛西汀" -> "处方药\n标签：抗抑郁药，SNRIs，镇痛药\n\n度洛西汀是 SNRI 类抗抑郁药，用于抑郁症、广泛性焦虑、神经病理性疼痛与纤维肌痛。经 CYP2D6 与 CYP1A2 代谢——CYP1A2 强抑制剂（如 Fluvoxamine 氟伏沙明）为其联用禁忌，CYP2D6 强抑制剂（如 Bupropion 安非他酮、Fluoxetine 氟西汀）显著升高其浓度；为 CYP2D6 中等抑制剂。MAOI 禁忌联用。\n\n【药效】强效 · 血清素转运体抑制；强效 · 正肾素转运体抑制\n【酶抑制】中效 · CYP2D6\n【急性期疗效】中效 · 抗抑郁起效\n【维持期疗效】极强 · 抗抑郁；强效 · 抗焦虑；强效 · 镇痛（神经病理性与纤维肌痛）\n【急性不良反应】常见 · 恶心；常见 · 口干；少见 · 血清素综合征\n【慢性不良反应】常见 · 恶心；常见 · 嗜睡；常见 · 失眠；常见 · 性功能障碍；少见 · 血压升高；少见 · 低血钠；少见 · 肝损伤\n【药代】口服常释 · T½ 12h · Tmax 4-6h · F=0.5（F≈ 估算）"
        "阿米替林" -> "处方药\n标签：抗抑郁药，TCA，三环类抗抑郁药\n\n阿米替林是三环类抗抑郁药，用于抑郁症、焦虑与慢性神经病理性疼痛（小剂量）。经 CYP2D6 等多酶 N-去甲基化为活性代谢物去甲替林。有明显的抗胆碱能、抗组胺与 α1 阻断效应（口干、便秘、嗜睡、体位性低血压），过量致死性高（QRS 增宽/心脏毒性）；CYP2D6 抑制剂（本库如安非他酮、氟西汀）升高其浓度。\n\n【药效】极强 · 血清素与正肾素再摄取抑制；中效 · 毒蕈碱受体阻断；中效 · 组胺H1受体阻断；中效 · α1受体阻断\n【酶抑制】中效 · CYP2D6\n【急性期疗效】强效 · 镇静催眠\n【维持期疗效】极强 · 抗抑郁；强效 · 抗焦虑；中效 · 镇痛（神经病理性疼痛）\n【急性不良反应】常见 · 嗜睡；常见 · 口干；常见 · 便秘；少见 · 抗胆碱能谵妄\n【慢性不良反应】十分常见 · 嗜睡；常见 · 口干；常见 · 便秘；常见 · 体重增加；少见 · QT间期延长；少见 · 体位性低血压；少见 · 谵妄\n【药代】口服常释 · T½ 19h · Tmax 2-5h · F=0.45（F≈ 估算）"
        "米氮平" -> "处方药\n标签：抗抑郁药，非典型抗抑郁药，去甲肾上腺素能特异性血清素能抗抑郁药\n\n米氮平是去甲肾上腺素能/特异性血清素能抗抑郁药（NaSSA），经突触前 α2 自受体阻断增强单胺释放，并阻断 5-HT2/3 与 H1 受体——镇静、刺激食欲、体重增加、性功能障碍少。经 CYP1A2/2D6/3A4 代谢；酶抑制剂（如氟伏沙明经 1A2）升高其浓度。\n\n【药效】强效 · α2自身受体阻断；强效 · 5-HT2受体阻断；强效 · 5-HT3受体阻断；强效 · 组胺H1受体阻断\n【酶抑制】弱效 · CYP3A4；弱效 · CYP2D6\n【急性期疗效】强效 · 镇静催眠\n【维持期疗效】极强 · 抗抑郁；强效 · 抗焦虑；中效 · 改善食欲\n【急性不良反应】十分常见 · 嗜睡；常见 · 食欲增加；常见 · 体重增加\n【慢性不良反应】十分常见 · 嗜睡；常见 · 体重增加；常见 · 食欲增加；少见 · 血脂升高\n【药代】口服常释 · T½ 30h · Tmax 1-3h · F=0.5（F≈ 估算）"
        "安非他酮" -> "处方药\n标签：抗抑郁药，NDRI，戒烟辅助药\n\n安非他酮是去甲肾上腺素-多巴胺再摄取抑制剂（NDRI），用于抑郁症、戒烟辅助与成人 ADHD（国内常见超说明书）。经 CYP2B6 代谢为活性羟基安非他酮等（T½ 与母药接近，本库不建模）；是 CYP2D6 强抑制剂，显著升高经 2D6 代谢药物（本库如度洛西汀、阿托莫西汀、TCA）浓度。降低癫痫发作阈值（厌食症/癫痫史禁用）；MAOI 禁忌联用。\n\n【药效】强效 · 多巴胺与正肾素再摄取抑制\n【酶抑制】强效 · CYP2D6\n【急性期疗效】中效 · 激活\n【维持期疗效】极强 · 抗抑郁；强效 · 戒烟（辅助）；中效 · 成人ADHD（常见超说明书）\n【急性不良反应】十分常见 · 失眠；常见 · 焦虑；常见 · 震颤；少见 · 癫痫发作\n【慢性不良反应】常见 · 失眠；常见 · 焦虑；常见 · 口干；少见 · 体重下降\n【药代】口服常释 · T½ 21h · Tmax 3h · F=0.7（F≈ 估算）"
        "曲唑酮" -> "处方药\n标签：抗抑郁药，SARI，血清素拮抗再摄取抑制剂，催眠药\n\n曲唑酮是血清素拮抗/再摄取抑制剂（SARI），小剂量广泛用于失眠（国内常见用法）。有 α1 阻断（体位性低血压）与 H1 阻断（镇静）；经 CYP3A4/2D6 代谢为 mCPP（致焦虑性代谢物）。罕见异常勃起（需急诊）；MAOI 禁忌联用；与降压药联用注意低血压。\n\n【药效】中效 · 血清素再摄取抑制；中效 · 5-HT2A受体阻断；中效 · α1受体阻断；强效 · 组胺H1受体阻断\n【酶抑制】弱效 · CYP2D6\n【急性期疗效】强效 · 镇静催眠\n【维持期疗效】强效 · 抗抑郁；强效 · 改善睡眠\n【急性不良反应】十分常见 · 嗜睡；常见 · 头晕；少见 · 体位性低血压\n【慢性不良反应】十分常见 · 嗜睡；常见 · 头晕；常见 · 口干；罕见 · 异常勃起\n【药代】口服常释 · T½ 7h · Tmax 1-2h · F=0.65（F≈ 估算）"
        "氯米帕明" -> "处方药\n标签：抗强迫药，TCA，三环类抗抑郁药\n\n氯米帕明是三环类抗抑郁药、抗强迫治疗金标准（强迫症一线）。经 CYP2D6 N-去甲基化为活性代谢物去甲氯米帕明（T½ 报告不一、接近或长于母药，本库不建双曲线）。抗胆碱能与心脏毒性与 TCA 类同；CYP2D6 强抑制剂升高其浓度；MAOI 禁忌联用。\n\n【药效】极强 · 血清素再摄取抑制；中效 · 毒蕈碱受体阻断；中效 · 组胺H1受体阻断\n【酶抑制】强效 · CYP2D6；中效 · CYP2C19\n【急性期疗效】中效 · 镇静\n【维持期疗效】极强 · 抗强迫；强效 · 抗抑郁；强效 · 抗焦虑；强效 · 惊恐障碍\n【急性不良反应】十分常见 · 嗜睡；常见 · 口干；常见 · 便秘；少见 · 抗胆碱能谵妄\n【慢性不良反应】十分常见 · 嗜睡；常见 · 口干；常见 · 便秘；常见 · 性功能障碍；少见 · QT间期延长；少见 · 体重增加\n【药代】口服常释 · T½ 26h · Tmax 2-6h · F=0.5（F≈ 估算）"
        "哌甲酯" -> "精神药品（一类）\n标签：抗多动药，ADHD治疗药，中枢兴奋剂，超说明书（成人ADHD）\n\n哌甲酯是哌啶类中枢兴奋剂，一线治疗 ADHD 与发作性睡病；管制精神药品（一类）。成人 ADHD 在国内常见超说明书使用。主要经羧酸酯酶（CES1）去酯化代谢（无明显 CYP450 参与）。与 MAOI 禁忌联用；与食欲素拮抗剂等催眠药药理上相互拮抗（兴奋 vs 促眠）；升高血压心率。\n\n【药效】强效 · 多巴胺与正肾素再摄取抑制（中枢）\n【酶抑制】无显著抑制（本库引擎仅建模 CYP/UGT 抑制；诱导性相互作用见药物描述）\n【急性期疗效】强效 · 改善注意（快起效）\n【维持期疗效】极强 · 改善ADHD注意缺陷；强效 · 控制多动冲动；中效 · 改善发作性睡病日间嗜睡\n【急性不良反应】常见 · 食欲下降；常见 · 失眠；少见 · 抽动加重\n【慢性不良反应】常见 · 食欲下降；常见 · 失眠；常见 · 体重下降；少见 · 儿童生长减速；少见 · 心率血压升高\n【药代】口服常释 · T½ 3h · Tmax 2h · F=0.3（F≈ 估算）"
        "阿托莫西汀" -> "处方药\n标签：抗多动药，ADHD治疗药，去甲肾上腺素再摄取抑制剂\n\n阿托莫西汀是非兴奋剂类 ADHD 一线药（正肾素再摄取抑制，选择性作用于前额叶）。主要经 CYP2D6 代谢——CYP2D6 强抑制剂（本库如氟西汀、安非他酮）显著升高其浓度，弱代谢者 T½ 延长约 4 倍。起效需数周；罕见肝损伤。\n\n【药效】强效 · 正肾素再摄取抑制（前额叶）\n【酶抑制】弱效 · CYP2D6\n【急性期疗效】缓效 · 改善注意（数周起效）\n【维持期疗效】极强 · 改善ADHD注意缺陷；强效 · 控制多动冲动\n【急性不良反应】常见 · 食欲下降；常见 · 上腹痛；常见 · 嗜睡\n【慢性不良反应】常见 · 食欲下降；常见 · 体重下降；少见 · 心率血压升高；少见 · 性功能障碍；罕见 · 肝损伤\n【药代】口服常释 · T½ 5h · Tmax 1-2h · F=0.7（F≈ 估算）"
        "多奈哌齐" -> "处方药\n标签：乙酰胆碱酯酶抑制剂，抗痴呆药，阿尔茨海默病药\n\n多奈哌齐是中枢可逆性乙酰胆碱酯酶抑制剂，用于阿尔茨海默病与轻度认知损害（国内常见超说明书）。口服生物利用度约 100%（说明书），T½ 长（约 70h）可每日一次给药；经 CYP2D6/3A4 代谢，CYP2D6 强抑制剂（本库如氟西汀、安非他酮）升高其浓度。与抗胆碱能药（本库如苯海索）药理上相互拮抗。\n\n【药效】极强 · 乙酰胆碱酯酶抑制（中枢）\n【酶抑制】弱效 · CYP2D6\n【急性期疗效】缓效 · 改善认知（数周-数月）\n【维持期疗效】强效 · 改善阿尔茨海默病认知；中效 · 改善伴发行为症状\n【急性不良反应】常见 · 恶心；常见 · 腹泻；常见 · 失眠\n【慢性不良反应】常见 · 恶心；常见 · 腹泻；少见 · 心动过缓；少见 · 恶梦；少见 · 肌肉痉挛\n【药代】口服常释 · T½ 70h · Tmax 3-4h · F=1（说明书值）"
        "卡巴拉汀" -> "处方药\n标签：乙酰胆碱酯酶抑制剂，抗痴呆药，阿尔茨海默病药，帕金森病痴呆药\n\n卡巴拉汀是伪不可逆性胆碱酯酶抑制剂（同时抑制乙酰与丁酰胆碱酯酶），用于阿尔茨海默病与帕金森病痴呆。口服生物利用度低（约 40%，首过被酯酶水解；透皮贴剂可规避）；T½ 极短（约 1.5h）但有靶酶再占据效应，一日两次/透皮给药。\n\n【药效】极强 · 乙酰胆碱酯酶抑制；中效 · 丁酰胆碱酯酶抑制\n【酶抑制】无显著抑制（本库引擎仅建模 CYP/UGT 抑制；诱导性相互作用见药物描述）\n【急性期疗效】缓效 · 改善认知\n【维持期疗效】强效 · 改善阿尔茨海默病认知；中效 · 帕金森病痴呆认知\n【急性不良反应】十分常见 · 恶心；常见 · 呕吐；常见 · 腹泻\n【慢性不良反应】十分常见 · 恶心；常见 · 呕吐；常见 · 腹泻；少见 · 体重下降；少见 · 震颤\n【药代】口服常释 · T½ 2h · Tmax 1h · F=0.4（F≈ 估算）"
        "加兰他敏" -> "处方药\n标签：乙酰胆碱酯酶抑制剂，抗痴呆药，阿尔茨海默病药\n\n加兰他敏是可逆性乙酰胆碱酯酶抑制剂兼烟碱受体变构调节，用于阿尔茨海默病（轻中度）。口服生物利用度约 90%，经 CYP2D6/3A4 代谢（弱 2D6 抑制剂），CYP2D6 弱代谢者浓度升高。\n\n【药效】极强 · 乙酰胆碱酯酶抑制；弱效 · 烟碱受体变构调节\n【酶抑制】弱效 · CYP2D6\n【急性期疗效】缓效 · 改善认知\n【维持期疗效】强效 · 改善阿尔茨海默病认知\n【急性不良反应】常见 · 恶心；常见 · 呕吐；常见 · 腹泻\n【慢性不良反应】常见 · 恶心；常见 · 呕吐；常见 · 腹泻；少见 · 体重下降；少见 · 头晕\n【药代】口服常释 · T½ 7h · Tmax 1h · F=0.9（说明书值）"
        "丁螺环酮" -> "处方药\n标签：抗焦虑药，5-HT1A部分激动剂，非苯二氮卓类\n\n丁螺环酮是非苯二氮卓类抗焦虑药（5-HT1A 受体部分激动），用于广泛性焦虑障碍。无依赖性、无停药综合征、不影响呼吸（与苯二氮卓/阿片类的安全性优势）。口服首过代谢极大（生物利用度约 5%）；起效慢（2-4 周）；CYP3A4 强抑制剂（如氟伏沙明）升高、诱导剂（如卡马西平）降低其浓度；MAOI 禁忌联用。\n\n【药效】中效 · 5-HT1A受体部分激动\n【酶抑制】无显著抑制（本库引擎仅建模 CYP/UGT 抑制；诱导性相互作用见药物描述）\n【急性期疗效】缓效 · 抗焦虑（2-4周起效）\n【维持期疗效】强效 · 抗广泛性焦虑\n【急性不良反应】常见 · 头晕；常见 · 恶心；常见 · 头痛\n【慢性不良反应】常见 · 头晕；少见 · 烦躁不安\n【药代】口服常释 · T½ 2h · Tmax 1h · F=0.05（F≈ 估算）"
        "苏沃瑞生" -> "处方药\n标签：食欲素受体拮抗剂，催眠药，失眠治疗药\n\n苏沃瑞生是双食欲素受体（OX1/OX2）拮抗剂，治疗入睡与睡眠维持困难的失眠。经 CYP3A4 代谢；CYP3A4 抑制剂（本库如氟伏沙明）升高其浓度。可致次日残余嗜睡、睡眠麻痹与入睡前幻觉；与中枢兴奋剂（本库如哌甲酯）药理上相互拮抗；管制类（美国 IV 类）。\n\n【药效】强效 · 食欲素OX1与OX2受体拮抗\n【酶抑制】无显著抑制（本库引擎仅建模 CYP/UGT 抑制；诱导性相互作用见药物描述）\n【急性期疗效】强效 · 缩短入睡时间\n【维持期疗效】极强 · 治疗失眠（入睡与睡眠维持）\n【急性不良反应】常见 · 次日残余嗜睡；少见 · 睡眠麻痹；少见 · 入睡前幻觉\n【慢性不良反应】常见 · 嗜睡；少见 · 睡眠麻痹；少见 · 复杂睡眠行为\n【药代】口服常释 · T½ 12h · Tmax 2h · F=0.7（F≈ 估算）"
        "莱博瑞生" -> "处方药\n标签：食欲素受体拮抗剂，催眠药，失眠治疗药\n\n莱博瑞生是双食欲素受体拮抗剂，治疗失眠（入睡与睡眠维持），对睡眠维持的结构改善证据较强。经 CYP3A4 代谢；T½ 约 17h（次日残余风险个体化评估）；与中枢兴奋剂药理拮抗。\n\n【药效】强效 · 食欲素OX1与OX2受体拮抗\n【酶抑制】无显著抑制（本库引擎仅建模 CYP/UGT 抑制；诱导性相互作用见药物描述）\n【急性期疗效】强效 · 缩短入睡时间\n【维持期疗效】极强 · 治疗失眠（入睡与睡眠维持）\n【急性不良反应】常见 · 次日残余嗜睡；少见 · 睡眠麻痹；少见 · 入睡前幻觉\n【慢性不良反应】常见 · 嗜睡；少见 · 睡眠麻痹\n【药代】口服常释 · T½ 17h · Tmax 1-3h · F=0.65（F≈ 估算）"
        "氯胺酮" -> "精神药品（一类）\n标签：解离麻醉药，NMDA受体拮抗剂，镇痛药，超说明书（难治性抑郁）\n\n氯胺酮是解离性静脉麻醉药（NMDA 拮抗），亚麻醉剂量用于难治性疼痛与难治性抑郁（国内常见超说明书静脉/口服 off-label；鼻喷艾司氯胺酮已获批准用于难治性抑郁）。口服首过代谢大（F 约 20%），经 CYP3A4/2B6 生成活性弱代谢物去甲氯胺酮。苏醒期精神症状（分离/谵妄）常见；与抗胆碱能药（本库如苯海索）联用易致谵妄（本库联用演示）；升高血压与颅内压；管制精神药品（一类）。\n\n【药效】强效 · NMDA受体拮抗；中效 · 阿片μ受体作用；中效 · 电压门控钠通道阻断\n【酶抑制】无显著抑制（本库引擎仅建模 CYP/UGT 抑制；诱导性相互作用见药物描述）\n【急性期疗效】强效 · 麻醉诱导（静脉，秒级）\n【维持期疗效】强效 · 难治性抑郁（常见超说明书）；强效 · 镇痛（亚麻醉）；中效 · 慢性疼痛管理\n【急性不良反应】常见 · 分离感；常见 · 幻觉；常见 · 谵妄；少见 · 血压升高\n【慢性不良反应】常见 · 分离感；少见 · 认知损害；少见 · 膀胱间质炎（长期使用）\n【药代】口服常释 · T½ 2h · Tmax 0.5-1h · F=0.2（F≈ 估算）"
        "裸盖菇素" -> "研究性物质\n标签：迷幻药，5-HT2A受体激动剂，研究性治疗药\n\n裸盖菇素是经典迷幻药（5-HT2A 激动），口服后快速去磷酸化为活性代谢物裸盖菇新碱（psilocin，T½ 约 2h）。在研究性语境用于难治性抑郁与终末期焦虑（国内未批准临床使用）。SSRI 长期联用降低其迷幻效应（受体脱敏）；MAOI 联用升压风险；急性期需陪伴监测（恐慌反应/急性精神病样反应）。\n\n【药效】强效 · 5-HT2A受体激动；中效 · 5-HT1A受体激动\n【酶抑制】无显著抑制（本库引擎仅建模 CYP/UGT 抑制；诱导性相互作用见药物描述）\n【急性期疗效】强效 · 迷幻效应（30分钟内起效）\n【维持期疗效】强效 · 抑郁（研究性辅助治疗）；中效 · 焦虑（终末期，研究性）\n【急性不良反应】常见 · 幻觉；常见 · 焦虑；常见 · 恶心\n【慢性不良反应】少见 · 持久知觉障碍；少见 · 闪回\n【药代】口服常释 · T½ 1h · Tmax 1-2h · F=0.5（F≈ 估算）"
        "苯海索" -> "处方药\n标签：抗胆碱能药，抗帕金森药，抗锥体外系反应药\n\n苯海索（安坦）是中枢抗胆碱能药，用于帕金森病（轻症）与抗精神病药所致锥体外系反应（国内常用）。老年患者中长期使用显著增加谵妄与认知下降风险；与 AChE 抑制剂（本库如多奈哌齐）药理上相互拮抗；与解离麻醉药（本库如氯胺酮）联用易致谵妄（本库联用演示）。外周抗胆碱能副作用经典（口干/便秘/视物模糊）。\n\n【药效】极强 · 毒蕈碱受体阻断（中枢）；中效 · 改善多巴胺与乙酰胆碱平衡\n【酶抑制】无显著抑制（本库引擎仅建模 CYP/UGT 抑制；诱导性相互作用见药物描述）\n【急性期疗效】中效 · 控制震颤\n【维持期疗效】强效 · 帕金森病（轻症）；强效 · 抗锥体外系反应；中效 · 肌张力障碍\n【急性不良反应】常见 · 口干；常见 · 视物模糊；常见 · 便秘\n【慢性不良反应】常见 · 口干；常见 · 便秘；常见 · 认知下降；少见 · 谵妄；少见 · 幻觉\n【药代】口服常释 · T½ 4h · Tmax 1-3h · F=0.7（F≈ 估算）"
        "苯扎托品" -> "处方药\n标签：抗胆碱能药，抗帕金森药，抗锥体外系反应药\n\n苯扎托品是中枢抗胆碱能药（兼弱多巴胺再摄取抑制），用于帕金森病与抗精神病药所致锥体外系反应。与苯海索同类同风险：老年谵妄/认知下降、与 AChE 抑制剂药理拮抗、与解离麻醉药联用易致谵妄；停药反跳肌强直。\n\n【药效】极强 · 毒蕈碱受体阻断（中枢）；弱效 · 多巴胺再摄取抑制\n【酶抑制】无显著抑制（本库引擎仅建模 CYP/UGT 抑制；诱导性相互作用见药物描述）\n【急性期疗效】中效 · 控制震颤\n【维持期疗效】强效 · 帕金森病；强效 · 抗锥体外系反应\n【急性不良反应】常见 · 口干；常见 · 视物模糊；常见 · 便秘\n【慢性不良反应】常见 · 口干；常见 · 便秘；常见 · 认知下降；少见 · 谵妄；少见 · 幻觉\n【药代】口服常释 · T½ 8h · Tmax 1-2h · F=0.5（F≈ 估算）"
        else -> "（未知药物）"
    }

    private val drugSsri = setOf("氟伏沙明", "帕罗西汀", "舍曲林", "氟西汀", "西酞普兰", "艾司西酞普兰")
    private val drugBzd = setOf("地西泮", "艾司唑仑", "阿普唑仑", "氯硝西泮", "劳拉西泮", "咪达唑仑")
    private val drugOpioid = mapOf("吗啡" to 0xFF795548.toInt(), "羟考酮" to 0xFFE64A19.toInt(), "芬太尼" to 0xFF6A1B9A.toInt(), "可待因" to 0xFF8D6E63.toInt(), "曲马多" to 0xFF00897B.toInt(), "哌替啶" to 0xFFAD1457.toInt())

    // ---------- 契约 §4 交互态（发射器直发 Kotlin 侧状态；JS 主逻辑仍跑 WebView，两端 storage 同源） ----------
    // act 路由名 = subl 侧函数名（toggleForm/cancelForm/pickDrugAt/setDose/commitDose/……），
    // 由 ComposeEmitter 的 onClickRoute 从 `unchecked f(args)` lambda 抽出。
    private val formOpen = mutableStateOf(false)
    private val selectedDrug = mutableStateOf(-1)
    private val doseTextState = mutableStateOf("")
    private val doseErrState = mutableStateOf("")
    private val settingsOpen = mutableStateOf(false)
    private val drugListOpen = mutableStateOf(false)
    private val docNameState = mutableStateOf("")
    // 服药记录列表（最近 10 条：name_k/dose_k/time_k/bar_k 槽位 + hasRec(k) 显隐）
    private data class HostRec(val name: String, val amount: String, val timeMs: Long)
    private val recListState = mutableStateOf(ArrayList<HostRec>())
    @Volatile private var appCtx: android.content.Context? = null

    // ---------- 主观效应词条流（与 warn.subl effectTags 同引擎：急性/慢性不良反应跨药聚合） ----------
    // 词条（呈现契约）：name 短文字 / level 1-3（白→黄→红）/ acute 急性→flash / pack 综合征包（◆）。
    data class EffectTag(val name: String, val level: Int, val acute: Boolean, val pack: Boolean)
    private data class AdvEnt(val name: String, val level: Int, val acute: Boolean)
    /** 41 药急性/慢性不良反应频率表（与 app 各药物的 subl 数据同源；由 t75 扩充，脚本生成勿手改）。 */
    private val advTable: Map<String, List<AdvEnt>> = mapOf(
        "氟伏沙明" to listOf(AdvEnt("血清素综合征", 1, true), AdvEnt("恶心", 3, false), AdvEnt("嗜睡", 2, false), AdvEnt("失眠", 2, false), AdvEnt("厌食", 2, false), AdvEnt("头晕", 2, false), AdvEnt("震颤", 2, false), AdvEnt("自杀冲动", 1, false)),
        "帕罗西汀" to listOf(AdvEnt("恶心", 2, true), AdvEnt("嗜睡", 1, false), AdvEnt("失眠", 2, false)),
        "氟西汀" to listOf(AdvEnt("血清素综合征", 1, true), AdvEnt("恶心", 3, false), AdvEnt("失眠", 2, false), AdvEnt("头痛", 2, false), AdvEnt("厌食", 2, false), AdvEnt("早期焦虑", 1, false), AdvEnt("年轻成人自杀冲动", 1, false), AdvEnt("躁狂转换", 1, false)),
        "舍曲林" to listOf(AdvEnt("血清素综合征", 1, true), AdvEnt("恶心", 3, false), AdvEnt("失眠", 2, false), AdvEnt("嗜睡", 2, false), AdvEnt("头晕", 2, false), AdvEnt("震颤", 1, false), AdvEnt("自杀冲动", 1, false)),
        "西酞普兰" to listOf(AdvEnt("血清素综合征", 1, true), AdvEnt("恶心", 3, false), AdvEnt("嗜睡", 2, false), AdvEnt("失眠", 2, false), AdvEnt("头晕", 2, false), AdvEnt("自杀冲动", 1, false)),
        "艾司西酞普兰" to listOf(AdvEnt("血清素综合征", 1, true), AdvEnt("恶心", 3, false), AdvEnt("嗜睡", 2, false), AdvEnt("失眠", 2, false), AdvEnt("头晕", 2, false), AdvEnt("自杀冲动", 1, false)),
        "地西泮" to listOf(AdvEnt("呼吸抑制", 1, true), AdvEnt("嗜睡", 2, true), AdvEnt("头晕", 2, true), AdvEnt("嗜睡", 3, false), AdvEnt("头晕", 2, false), AdvEnt("疲劳", 2, false), AdvEnt("共济失调", 2, false), AdvEnt("顺行性记忆损害", 2, false), AdvEnt("呼吸抑制", 1, false), AdvEnt("反常兴奋", 1, false)),
        "艾司唑仑" to listOf(AdvEnt("呼吸抑制", 1, true), AdvEnt("次日残余嗜睡", 2, true), AdvEnt("头晕", 2, true), AdvEnt("嗜睡", 2, false), AdvEnt("头晕", 2, false), AdvEnt("顺行性记忆损害", 2, false), AdvEnt("呼吸抑制", 1, false), AdvEnt("幻觉", 1, false)),
        "阿普唑仑" to listOf(AdvEnt("呼吸抑制", 1, true), AdvEnt("嗜睡", 2, true), AdvEnt("头晕", 2, true), AdvEnt("嗜睡", 2, false), AdvEnt("头晕", 2, false), AdvEnt("疲劳", 2, false), AdvEnt("共济失调", 2, false), AdvEnt("顺行性记忆损害", 2, false), AdvEnt("呼吸抑制", 1, false), AdvEnt("反常兴奋", 1, false)),
        "氯硝西泮" to listOf(AdvEnt("呼吸抑制", 1, true), AdvEnt("嗜睡", 2, true), AdvEnt("头晕", 2, true), AdvEnt("嗜睡", 3, false), AdvEnt("头晕", 2, false), AdvEnt("共济失调", 2, false), AdvEnt("顺行性记忆损害", 2, false), AdvEnt("疲劳", 2, false), AdvEnt("呼吸抑制", 1, false), AdvEnt("儿童行为脱抑制", 1, false)),
        "劳拉西泮" to listOf(AdvEnt("呼吸抑制", 1, true), AdvEnt("嗜睡", 2, true), AdvEnt("头晕", 2, true), AdvEnt("嗜睡", 3, false), AdvEnt("头晕", 2, false), AdvEnt("乏力", 2, false), AdvEnt("共济失调", 2, false), AdvEnt("顺行性记忆损害", 2, false), AdvEnt("呼吸抑制", 1, false), AdvEnt("脑损伤者矛盾攻击性", 1, false)),
        "咪达唑仑" to listOf(AdvEnt("呼吸抑制", 1, true), AdvEnt("嗜睡", 2, true), AdvEnt("嗜睡", 3, false), AdvEnt("头晕", 2, false), AdvEnt("顺行性记忆损害", 2, false), AdvEnt("呼吸抑制", 1, false), AdvEnt("老年ICU谵妄", 1, false)),
        "吗啡" to listOf(AdvEnt("呼吸抑制", 2, true), AdvEnt("镇静", 3, true), AdvEnt("恶心呕吐", 2, true), AdvEnt("欣快", 2, true), AdvEnt("嗜睡", 3, false), AdvEnt("恶心", 2, false), AdvEnt("呕吐", 2, false), AdvEnt("呼吸抑制", 2, false)),
        "羟考酮" to listOf(AdvEnt("呼吸抑制", 2, true), AdvEnt("镇静", 3, true), AdvEnt("恶心呕吐", 2, true), AdvEnt("头晕", 2, true), AdvEnt("欣快", 2, true), AdvEnt("嗜睡", 3, false), AdvEnt("恶心", 2, false), AdvEnt("呕吐", 2, false), AdvEnt("呼吸抑制", 2, false), AdvEnt("头晕", 2, false)),
        "芬太尼" to listOf(AdvEnt("呼吸抑制", 2, true), AdvEnt("镇静", 3, true), AdvEnt("恶心呕吐", 2, true), AdvEnt("欣快", 2, true), AdvEnt("嗜睡", 3, false), AdvEnt("呼吸抑制", 2, false), AdvEnt("恶心", 2, false), AdvEnt("呕吐", 2, false)),
        "可待因" to listOf(AdvEnt("呼吸抑制", 1, true), AdvEnt("嗜睡", 2, true), AdvEnt("恶心呕吐", 2, true), AdvEnt("欣快", 2, true), AdvEnt("嗜睡", 2, false), AdvEnt("恶心", 2, false), AdvEnt("呼吸抑制", 1, false)),
        "曲马多" to listOf(AdvEnt("恶心呕吐", 2, true), AdvEnt("头晕", 2, true), AdvEnt("呼吸抑制", 1, true), AdvEnt("欣快", 2, true), AdvEnt("恶心", 3, false), AdvEnt("头晕", 2, false), AdvEnt("嗜睡", 2, false), AdvEnt("呼吸抑制", 1, false)),
        "哌替啶" to listOf(AdvEnt("呼吸抑制", 2, true), AdvEnt("恶心呕吐", 2, true), AdvEnt("头晕", 2, true), AdvEnt("欣快", 2, true), AdvEnt("恶心", 3, false), AdvEnt("嗜睡", 2, false), AdvEnt("呼吸抑制", 2, false), AdvEnt("血清素综合征", 1, false)),
        "碳酸锂" to listOf(AdvEnt("手抖", 3, true), AdvEnt("口渴", 3, true), AdvEnt("恶心", 3, true), AdvEnt("血清素综合征", 2, true), AdvEnt("手抖", 3, false), AdvEnt("烦渴多尿", 3, false), AdvEnt("甲状腺功能减退", 2, false), AdvEnt("肾功能下降", 2, false), AdvEnt("锂中毒", 1, false)),
        "丙戊酸" to listOf(AdvEnt("恶心", 4, true), AdvEnt("嗜睡", 3, true), AdvEnt("震颤", 3, true), AdvEnt("体重增加", 3, false), AdvEnt("震颤", 3, false), AdvEnt("脱发", 3, false), AdvEnt("肝毒性", 2, false), AdvEnt("血小板减少", 2, false), AdvEnt("高氨血症", 2, false)),
        "卡马西平" to listOf(AdvEnt("头晕", 4, true), AdvEnt("嗜睡", 3, true), AdvEnt("恶心", 3, true), AdvEnt("头晕", 3, false), AdvEnt("嗜睡", 3, false), AdvEnt("低血钠", 2, false), AdvEnt("皮疹", 2, false), AdvEnt("白细胞减少", 2, false)),
        "拉莫三嗪" to listOf(AdvEnt("头晕", 3, true), AdvEnt("头痛", 3, true), AdvEnt("嗜睡", 3, true), AdvEnt("失眠", 3, false), AdvEnt("皮疹", 2, false), AdvEnt("视力模糊", 2, false), AdvEnt("震颤", 2, false)),
        "文拉法辛" to listOf(AdvEnt("恶心", 3, true), AdvEnt("失眠", 3, true), AdvEnt("血清素综合征", 2, true), AdvEnt("恶心", 3, false), AdvEnt("头痛", 3, false), AdvEnt("失眠", 3, false), AdvEnt("性功能障碍", 3, false), AdvEnt("血压升高", 2, false), AdvEnt("低血钠", 2, false)),
        "度洛西汀" to listOf(AdvEnt("恶心", 3, true), AdvEnt("口干", 3, true), AdvEnt("血清素综合征", 2, true), AdvEnt("恶心", 3, false), AdvEnt("嗜睡", 3, false), AdvEnt("失眠", 3, false), AdvEnt("性功能障碍", 3, false), AdvEnt("血压升高", 2, false), AdvEnt("低血钠", 2, false), AdvEnt("肝损伤", 2, false)),
        "阿米替林" to listOf(AdvEnt("嗜睡", 3, true), AdvEnt("口干", 3, true), AdvEnt("便秘", 3, true), AdvEnt("抗胆碱能谵妄", 2, true), AdvEnt("嗜睡", 4, false), AdvEnt("口干", 3, false), AdvEnt("便秘", 3, false), AdvEnt("体重增加", 3, false), AdvEnt("QT间期延长", 2, false), AdvEnt("体位性低血压", 2, false), AdvEnt("谵妄", 2, false)),
        "米氮平" to listOf(AdvEnt("嗜睡", 4, true), AdvEnt("食欲增加", 3, true), AdvEnt("体重增加", 3, true), AdvEnt("嗜睡", 4, false), AdvEnt("体重增加", 3, false), AdvEnt("食欲增加", 3, false), AdvEnt("血脂升高", 2, false)),
        "安非他酮" to listOf(AdvEnt("失眠", 4, true), AdvEnt("焦虑", 3, true), AdvEnt("震颤", 3, true), AdvEnt("癫痫发作", 2, true), AdvEnt("失眠", 3, false), AdvEnt("焦虑", 3, false), AdvEnt("口干", 3, false), AdvEnt("体重下降", 2, false)),
        "曲唑酮" to listOf(AdvEnt("嗜睡", 4, true), AdvEnt("头晕", 3, true), AdvEnt("体位性低血压", 2, true), AdvEnt("嗜睡", 4, false), AdvEnt("头晕", 3, false), AdvEnt("口干", 3, false), AdvEnt("异常勃起", 1, false)),
        "氯米帕明" to listOf(AdvEnt("嗜睡", 4, true), AdvEnt("口干", 3, true), AdvEnt("便秘", 3, true), AdvEnt("抗胆碱能谵妄", 2, true), AdvEnt("嗜睡", 4, false), AdvEnt("口干", 3, false), AdvEnt("便秘", 3, false), AdvEnt("性功能障碍", 3, false), AdvEnt("QT间期延长", 2, false), AdvEnt("体重增加", 2, false)),
        "哌甲酯" to listOf(AdvEnt("食欲下降", 3, true), AdvEnt("失眠", 3, true), AdvEnt("抽动加重", 2, true), AdvEnt("食欲下降", 3, false), AdvEnt("失眠", 3, false), AdvEnt("体重下降", 3, false), AdvEnt("儿童生长减速", 2, false), AdvEnt("心率血压升高", 2, false)),
        "阿托莫西汀" to listOf(AdvEnt("食欲下降", 3, true), AdvEnt("上腹痛", 3, true), AdvEnt("嗜睡", 3, true), AdvEnt("食欲下降", 3, false), AdvEnt("体重下降", 3, false), AdvEnt("心率血压升高", 2, false), AdvEnt("性功能障碍", 2, false), AdvEnt("肝损伤", 1, false)),
        "多奈哌齐" to listOf(AdvEnt("恶心", 3, true), AdvEnt("腹泻", 3, true), AdvEnt("失眠", 3, true), AdvEnt("恶心", 3, false), AdvEnt("腹泻", 3, false), AdvEnt("心动过缓", 2, false), AdvEnt("恶梦", 2, false), AdvEnt("肌肉痉挛", 2, false)),
        "卡巴拉汀" to listOf(AdvEnt("恶心", 4, true), AdvEnt("呕吐", 3, true), AdvEnt("腹泻", 3, true), AdvEnt("恶心", 4, false), AdvEnt("呕吐", 3, false), AdvEnt("腹泻", 3, false), AdvEnt("体重下降", 2, false), AdvEnt("震颤", 2, false)),
        "加兰他敏" to listOf(AdvEnt("恶心", 3, true), AdvEnt("呕吐", 3, true), AdvEnt("腹泻", 3, true), AdvEnt("恶心", 3, false), AdvEnt("呕吐", 3, false), AdvEnt("腹泻", 3, false), AdvEnt("体重下降", 2, false), AdvEnt("头晕", 2, false)),
        "丁螺环酮" to listOf(AdvEnt("头晕", 3, true), AdvEnt("恶心", 3, true), AdvEnt("头痛", 3, true), AdvEnt("头晕", 3, false), AdvEnt("烦躁不安", 2, false)),
        "苏沃瑞生" to listOf(AdvEnt("次日残余嗜睡", 3, true), AdvEnt("睡眠麻痹", 2, true), AdvEnt("入睡前幻觉", 2, true), AdvEnt("嗜睡", 3, false), AdvEnt("睡眠麻痹", 2, false), AdvEnt("复杂睡眠行为", 2, false)),
        "莱博瑞生" to listOf(AdvEnt("次日残余嗜睡", 3, true), AdvEnt("睡眠麻痹", 2, true), AdvEnt("入睡前幻觉", 2, true), AdvEnt("嗜睡", 3, false), AdvEnt("睡眠麻痹", 2, false)),
        "氯胺酮" to listOf(AdvEnt("分离感", 3, true), AdvEnt("幻觉", 3, true), AdvEnt("谵妄", 3, true), AdvEnt("血压升高", 2, true), AdvEnt("分离感", 3, false), AdvEnt("认知损害", 2, false), AdvEnt("膀胱间质炎（长期使用）", 2, false)),
        "裸盖菇素" to listOf(AdvEnt("幻觉", 3, true), AdvEnt("焦虑", 3, true), AdvEnt("恶心", 3, true), AdvEnt("持久知觉障碍", 2, false), AdvEnt("闪回", 2, false)),
        "苯海索" to listOf(AdvEnt("口干", 3, true), AdvEnt("视物模糊", 3, true), AdvEnt("便秘", 3, true), AdvEnt("口干", 3, false), AdvEnt("便秘", 3, false), AdvEnt("认知下降", 3, false), AdvEnt("谵妄", 2, false), AdvEnt("幻觉", 2, false)),
        "苯扎托品" to listOf(AdvEnt("口干", 3, true), AdvEnt("视物模糊", 3, true), AdvEnt("便秘", 3, true), AdvEnt("口干", 3, false), AdvEnt("便秘", 3, false), AdvEnt("认知下降", 3, false), AdvEnt("谵妄", 2, false), AdvEnt("幻觉", 2, false))
    )
    private val serotonergic = setOf("氟伏沙明", "帕罗西汀", "氟西汀", "舍曲林", "西酞普兰", "艾司西酞普兰", "曲马多", "哌替啶")
    /** 非主观效应根词（t73 清洗：与 warn.subl isSubjective 同表）——客观体征/生理/行为后果/长期状态剔除。 */
    private val nonSubjectiveRoots = listOf("依赖", "耐受", "跌倒", "便秘", "口干", "尿潴留", "出汗", "瘙痒", "瞳孔", "血压下降", "低血压", "QT间期", "心律失常", "低血钠", "性功能", "免疫抑制", "肌阵挛", "低血糖", "皮疹", "过敏反应", "腹泻", "癫痫", "胸壁强直", "去甲哌替啶")
    private fun isSubjective(name: String): Boolean = !nonSubjectiveRoots.any { name.contains(it) }
    /** 按当前台账药品计算效应词条流（频率基准级 + 跨药来源数 +1/来源、上限 3；级降序→急性→包）。 */
    fun effectTags(): List<EffectTag> {
        val names = recListState.value.map { it.name }.toSet()
        // name -> (药品下标, 条目) —— 仅主观效应（呼吸抑制为用户特令保留项）
        val byName = HashMap<String, MutableList<Pair<Int, AdvEnt>>>()
        names.forEachIndexed { idx, n ->
            advTable[n]?.filter { isSubjective(it.name) }?.forEach { e -> byName.getOrPut(e.name) { ArrayList() }.add(idx to e) }
        }
        val tags = ArrayList<EffectTag>()
        byName.forEach { (name, list) ->
            val base = list.maxOf { it.second.level }
            val drugs = list.map { it.first }.distinct().size
            val level = if (base + drugs >= 4) 3 else base + drugs - 1
            tags.add(EffectTag(name, level, list.any { it.second.acute }, name == "血清素综合征" || name == "阿片类中毒"))
        }
        if (drugOpioid.keys.any { it in names } && drugBzd.any { it in names }) {
            tags.add(EffectTag("阿片类中毒", 3, acute = true, pack = true))
        }
        return tags.sortedWith(
            compareByDescending<EffectTag> { it.level }.thenByDescending { it.acute }.thenByDescending { it.pack }
        )
    }
    fun attachEnv(ctx: android.content.Context) { appCtx = ctx; refreshRecList(); recomputeChart() }
    // ---------- 浓度图（与 JS chartSeriesList/PkEngine 同公式：C(t)=Σ D·F·0.5^(t/T½)；窗口 -12h..+36h 步长 1h，t=0=现在；t75 新药沿用 host 旧式峰值模型，与 JS 模型差异见 compose 同步说明） ----------
    private val chartSeriesState = mutableStateOf<List<SublSeries>>(emptyList())
    private val chartTotalState = mutableStateOf<List<Float>>(emptyList())
    private val chartLabelsState = mutableStateOf<List<String>>(emptyList())
    /** 图表三数据槽（发射器把 Chart 的 series/points/labels CallExpr 路由到此处）。 */
    fun chartSeries(): List<SublSeries> = chartSeriesState.value
    fun chartTotal(): List<Float> = chartTotalState.value
    fun chartLabels(): List<String> = chartLabelsState.value
    private fun drugParams(name: String): Pair<Int, Double> = when (name) {
        "氟伏沙明" -> 15 to 0.5      // T½ 15h · F=50%
        "帕罗西汀" -> 24 to 1.0      // T½ 24h · F=100%
        "氟西汀" -> 96 to 0.6       // T½ 96h（慢性给药母药）· F=60%
        "舍曲林" -> 26 to 0.44      // T½ 26h · F=44%
        "西酞普兰" -> 35 to 0.8     // T½ 35h · F=80%
        "艾司西酞普兰" -> 30 to 0.8 // T½ 30h · F=80%
        "地西泮" -> 36 to 0.9       // T½ 36h · F=90%
        "艾司唑仑" -> 17 to 1.0     // T½ 17h · F 未公开，按完全吸收建模（估算）
        "阿普唑仑" -> 11 to 0.9     // T½ 11h · F=90%
        "氯硝西泮" -> 30 to 0.9     // T½ 30h · F≈90%
        "劳拉西泮" -> 12 to 0.94    // T½ 12h · F≈94%
        "咪达唑仑" -> 3 to 0.44     // T½ 3h · F=44%
        "吗啡" -> 2 to 0.34         // T½ 2h · F=34%
        "羟考酮" -> 4 to 0.87       // T½ 4h · F≈87%
        "芬太尼" -> 17 to 0.92      // T½ 17h（透皮表观）· F≈92%
        "可待因" -> 3 to 0.6        // T½ 3h · F≈60%
        "曲马多" -> 6 to 0.66       // T½ 6h · F=66%
        "哌替啶" -> 4 to 0.5        // T½ 4h · F≈50%
        "碳酸锂" -> 24 to 1.0      // T½ 24h · F≈100%（口服近完全吸收）
        "丙戊酸" -> 12 to 0.9
        "卡马西平" -> 12 to 0.8     // F≈ 估算
        "拉莫三嗪" -> 25 to 0.98
        "文拉法辛" -> 5 to 0.45     // F≈ 估算
        "度洛西汀" -> 12 to 0.5     // F≈ 估算
        "阿米替林" -> 19 to 0.45     // F≈ 估算
        "米氮平" -> 30 to 0.5     // F≈ 估算
        "安非他酮" -> 21 to 0.7     // F≈ 估算
        "曲唑酮" -> 7 to 0.65     // F≈ 估算
        "氯米帕明" -> 26 to 0.5     // F≈ 估算
        "哌甲酯" -> 3 to 0.3     // F≈ 估算
        "阿托莫西汀" -> 5 to 0.7     // F≈ 估算
        "多奈哌齐" -> 70 to 1.0      // T½ 70h · F≈100%（说明书）
        "卡巴拉汀" -> 2 to 0.4     // F≈ 估算
        "加兰他敏" -> 7 to 0.9
        "丁螺环酮" -> 2 to 0.05     // F≈ 估算
        "苏沃瑞生" -> 12 to 0.7     // F≈ 估算
        "莱博瑞生" -> 17 to 0.65     // F≈ 估算
        "氯胺酮" -> 2 to 0.2     // F≈ 估算
        "裸盖菇素" -> 1 to 0.5     // F≈ 估算
        "苯海索" -> 4 to 0.7     // F≈ 估算
        "苯扎托品" -> 8 to 0.5     // F≈ 估算
        else -> 24 to 1.0           // 同 halfOfName/bioOfName 兜底
    }
    private val drugMood = setOf("碳酸锂", "丙戊酸", "卡马西平", "拉莫三嗪")
    private val drugAntidepress = setOf("文拉法辛", "度洛西汀", "阿米替林", "米氮平", "安非他酮", "曲唑酮", "氯米帕明")
    private val drugAdhd = setOf("哌甲酯", "阿托莫西汀")
    private val drugAchE = setOf("多奈哌齐", "卡巴拉汀", "加兰他敏")
    private val drugAnxiolytic = setOf("丁螺环酮")
    private val drugOrexin = setOf("苏沃瑞生", "莱博瑞生")
    private val drugDissoc = setOf("氯胺酮")
    private val drugPsyched = setOf("裸盖菇素")
    private val drugAntichol = setOf("苯海索", "苯扎托品")
    private fun drugColorOf(name: String): Int =
        if (drugSsri.contains(name)) 0xFFFFC107.toInt()
        else if (drugBzd.contains(name)) 0xFF1976D2.toInt()
        else drugOpioid[name] ?: when (name) {
            in drugMood -> 0xFF3949AB.toInt()
            in drugAntidepress -> 0xFFF9A825.toInt()
            in drugAdhd -> 0xFF43A047.toInt()
            in drugAchE -> 0xFF5E35B1.toInt()
            in drugAnxiolytic -> 0xFF1E88E5.toInt()
            in drugOrexin -> 0xFF546E7A.toInt()
            in drugDissoc -> 0xFFE91E63.toInt()
            in drugPsyched -> 0xFF7986CB.toInt()
            in drugAntichol -> 0xFF757575.toInt()
            else -> 0xFF00695C.toInt()
        }
    /** 读 ledger 重算全部序列（空台账→演示序列；与 chartSeriesList 同语义）。 */
    private fun recomputeChart() {
        val ctx = appCtx ?: return
        val now = System.currentTimeMillis()
        val recs = ArrayList<Triple<String, Double, Long>>()
        try {
            val arr = org.json.JSONArray(ctx.getSharedPreferences("dsh-storage", 0).getString("ledger", "[]") ?: "[]")
            for (i in 0 until arr.length()) {
                val o = arr.optJSONObject(i) ?: continue
                val amt = ((o.opt("amount") as? String) ?: o.opt("amount")?.toString() ?: "0").toDoubleOrNull() ?: 0.0
                recs.add(Triple(o.opt("drug") as? String ?: "氟伏沙明", amt,
                    (o.opt("time") as? String)?.toLongOrNull() ?: 0L))
            }
        } catch (e: Throwable) { Log.w(tag, "recomputeChart: " + e.message) }
        val names = recs.map { it.first }.toSet()
        val series = ArrayList<SublSeries>()
        if (recs.isEmpty()) {
            series.add(SublSeries("氟伏沙明", Color(0xFFFFC107),
                pkSeries(listOf(100.0 to -24L, 100.0 to 0L), 15, 0.5)))  // 演示：24h 前一剂 + 当前一剂
        } else {
            // recentDrugs：台账内首次出现序
            val seen = LinkedHashSet<String>()
            for (r in recs) seen.add(r.first)
            for (name in seen) {
                val (th, f) = drugParams(name)
                val doses = recs.filter { it.first == name }.map { it.second to (it.third - now) / 3600000L }
                series.add(SublSeries(name, Color(drugColorOf(name)), pkSeries(doses, th, f)))
            }
        }
        chartSeriesState.value = series
        chartTotalState.value = if (series.isEmpty()) emptyList()
            else List(series[0].points.size) { i -> series.sumOf { it.points[i].toDouble() }.toFloat() }
        chartLabelsState.value = (-12..36 step 12).map { "${it}h" }
    }
    /** -12h..+36h 每 1h 采样（t=0 为当前时间；供图表视口内裁剪绘制）；浓度取 1 位小数。 */
    private fun pkSeries(doses: List<Pair<Double, Long>>, tHalf: Int, bio: Double): List<Float> =
        (-12..36).map { t ->
            val c = doses.fold(0.0) { acc, d ->
                if (t >= d.second) acc + d.first * bio * Math.pow(0.5, (t - d.second).toDouble() / tHalf) else acc
            }
            Math.round(c * 10.0) / 10.0.toFloat()
        }
    /** 布尔态读：isFormOpen/isSettingsOpen/isDocOpen/isSel(k)。 */
    fun boolArg(name: String, vararg args: Any?): Boolean = when (name) {
        "isFormOpen" -> formOpen.value
        "isSettingsOpen" -> settingsOpen.value
        "isListOpen" -> drugListOpen.value
        "isDocOpen" -> docNameState.value.isNotEmpty()
        "hasRec" -> {
            val k = args.getOrNull(0) as? Int ?: 0
            k >= 0 && k < recListState.value.size && k < 10
        }
        "noRec" -> recListState.value.isEmpty()
        "isSel" -> (args.getOrNull(0) as? Int) == selectedDrug.value
        else -> { Log.w(tag, "boolArg 未知路由: " + name); false }
    }
    /** 字符串态读：curDose（剂量输入）、doseErr（校验错误）、呼吸抑制风险、记录列表槽位。 */
    fun strArg(name: String): String = when (name) {
        "curDose" -> doseTextState.value
        "doseErr" -> doseErrState.value
        "docName" -> docNameState.value
        "docBody" -> drugDocText(docNameState.value)
        else -> {
            // 记录卡槽位：name_k / dose_k / time_k
            val k = name.removePrefix("name").removePrefix("dose").removePrefix("time").toIntOrNull()
            if (k != null && k in 0 until 10 && k < recListState.value.size && (name.startsWith("name") || name.startsWith("dose") || name.startsWith("time"))) {
                val r = recListState.value[k]
                when {
                    name.startsWith("name") -> r.name
                    name.startsWith("dose") -> r.amount + " mg"
                    else -> if (r.timeMs > 0) {
                        val cal = java.util.Calendar.getInstance().apply { timeInMillis = r.timeMs }
                        "给药时间 今日 %02d:%02d · 口服".format(cal.get(java.util.Calendar.HOUR_OF_DAY), cal.get(java.util.Calendar.MINUTE))
                    } else "给药时间 · 口服"
                }
            } else { Log.w(tag, "strArg 未知路由: " + name); "" }
        }
    }
    /** TextField onValueChange → actText("setDose", v)。 */
    fun actText(name: String, v: String) {
        if (name == "setDose") {
            doseTextState.value = v
            if (doseErrState.value.isNotEmpty()) doseErrState.value = ""
        } else Log.w(tag, "actText 未知路由: " + name)
    }
    /** 交互动作（onClick → 宿主态机；与 main.subl 的 JS 逻辑同语义，storage 同源）。 */
    fun act(name: String, args: List<Any?> = emptyList()) {
        when (name) {
            "toggleForm" -> {
                formOpen.value = !formOpen.value
                if (formOpen.value) { selectedDrug.value = -1; doseTextState.value = ""; doseErrState.value = "" }
            }
            "cancelForm" -> {
                formOpen.value = false; selectedDrug.value = -1; doseTextState.value = ""; doseErrState.value = ""
            }
            "pickDrugAt" -> {
                selectedDrug.value = (args.getOrNull(0) as? Int) ?: 0
                doseErrState.value = ""
            }
            "commitDose" -> commitDose()
            "openSettingsDialog" -> settingsOpen.value = true
            "closeSettings" -> settingsOpen.value = false
            "openDrugList" -> drugListOpen.value = true
            "closeDrugList" -> drugListOpen.value = false
            "openDocFor" -> {
                val idx = args.getOrNull(0) as? Int
                docNameState.value = if (idx != null) drugNames.getOrNull(idx) ?: "" else (args.getOrNull(0) as? String) ?: ""
                drugListOpen.value = false
            }
            "closeDoc" -> docNameState.value = ""
            "clearAll" -> {
                putLedger("[]")
                recListState.value = ArrayList()
                recomputeChart()
                settingsOpen.value = false; toast("已清空记录")
            }
            else -> Log.w(tag, "act 未知路由: " + name)
        }
    }
    private fun commitDose() {
        val pick = selectedDrug.value
        val dt = doseTextState.value.trim()
        val amt = dt.toDoubleOrNull()
        when {
            pick < 0 -> { doseErrState.value = "请先选择药物"; toast("请先选择药物") }
            dt.isEmpty() || amt == null -> { doseErrState.value = "剂量必须为数字"; toast("剂量必须为数字") }
            amt <= 0.0 -> { doseErrState.value = "剂量必须大于 0"; toast("剂量必须大于 0") }
            amt > 10000.0 -> { doseErrState.value = "单次剂量不得超过 10000 mg"; toast("单次剂量不得超过 10000 mg") }
            else -> {
                val drug = drugNames.getOrElse(pick) { "氟伏沙明" }
                val time = System.currentTimeMillis().toString()
                val rec = "{\"drug\":" + org.json.JSONObject.quote(drug) +
                    ",\"amount\":" + org.json.JSONObject.quote(dt) +
                    ",\"time\":" + org.json.JSONObject.quote(time) + "}"
                val old = appCtx?.getSharedPreferences("dsh-storage", 0)?.getString("ledger", "[]") ?: "[]"
                val merged = if (old.trim() == "[]") "[$rec]" else "[$rec," + old.trim().trimStart('[').trimEnd(']') + "]"
                putLedger(merged)
                refreshRecList()
                recomputeChart()
                toast("已记录服药")
                formOpen.value = false; selectedDrug.value = -1; doseTextState.value = ""; doseErrState.value = ""
            }
        }
    }
    /** 记录列表同步：读 prefs "ledger" 全量（最近 10 条驱动 name_k/dose_k/time_k/bar_k 槽位）。 */
    private fun refreshRecList() {
        val ctx = appCtx ?: return
        try {
            recListState.value = readRecs(ctx.getSharedPreferences("dsh-storage", 0).getString("ledger", "[]") ?: "[]")
        } catch (e: Throwable) { Log.w(tag, "refreshRecList: " + e.message) }
    }
    private fun readRecs(json: String): ArrayList<HostRec> {
        val arr = org.json.JSONArray(json)
        val out = ArrayList<HostRec>()
        for (i in 0 until arr.length()) {
            val o = arr.optJSONObject(i) ?: continue
            out.add(HostRec(o.opt("drug") as? String ?: "",
                ((o.opt("amount") as? String) ?: o.opt("amount")?.toString() ?: "0"),
                (o.opt("time") as? String)?.toLongOrNull() ?: 0L))
        }
        return out
    }
    private fun putLedger(v: String) {
        appCtx?.getSharedPreferences("dsh-storage", 0)?.edit()?.putString("ledger", v)?.apply()
    }
    private fun toast(msg: String) {
        val ctx = appCtx
        if (ctx != null) ui { android.widget.Toast.makeText(ctx, msg, android.widget.Toast.LENGTH_SHORT).show() }
    }
    /** 调试：当前已注册槽位。 */
    fun slotKeys(): List<String> = status.keys.toList()
    /** 调试：某槽当前值。 */
    fun slotValue(key: String): Any? = status[key]?.value
    /** 预警标签流（@compose 同步脚本把 home 的 EFFECTTAGS_HOST 标记替换为本组合）：
     *  FlowRow 流式换行；三级配色 白（少见/罕见）→ 黄（常见）→ 红（十分常见/聚合加深）；
     *  急性词条 flash（gen.Modifier.flash，650ms 透明度循环），慢性不闪；综合征包加 ◆ 前缀。 */
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun Subl_EffectTags(modifier: Modifier = Modifier, tags: List<EffectTag>) {
        FlowRow(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            tags.forEach { tg ->
                val bg = when (tg.level) {
                    3 -> 0xF5D32F2F.toInt()   // 红：十分常见 / 聚合加深
                    2 -> 0xF5FFEE58.toInt()   // 黄：常见
                    else -> 0xF5ECEFF1.toInt()// 白：少见 / 罕见
                }
                val fg = if (tg.level == 3) 0xFFFFFFFF.toInt() else 0xFF4E342E.toInt()
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(bg))
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                        .then(if (tg.acute) Modifier.flash() else Modifier)
                ) {
                    Text(
                        text = (if (tg.pack) "◆ " else "") + tg.name,
                        color = Color(fg),
                        fontSize = 13.0.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
