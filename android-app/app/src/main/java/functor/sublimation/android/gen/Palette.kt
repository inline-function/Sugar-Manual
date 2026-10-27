package functor.sublimation.android.gen

import androidx.compose.ui.graphics.Color

/**
 * 由 SfA 生成的 subl 主题（android doc/Compose代码生成契约.md §5「主题色」）。
 *
 * 来源：标准库 `Theme.subl` 的色常量表 + 语言侧默认主题（薄荷绿，与参考实现 `Theme.kt` 一致）。
 * 生成器按色名直接引用 `Palette.primary` 等常量；**不再硬编码十六进制**——
 * 换主题只改这一处（或换 `Theme.subl` 后重编）。
 */
object Palette {
    // primary / onPrimary / primaryContainer / onPrimaryContainer
    val primary = Color(0xFF00695C)
    val onPrimary = Color(0xFFFFFFFF)
    val primaryContainer = Color(0xFFB2DFDB)
    val onPrimaryContainer = Color(0xFF00211E)
    // secondary / onSecondary / secondaryContainer
    val secondary = Color(0xFF4D6357)
    val onSecondary = Color(0xFFFFFFFF)
    val secondaryContainer = Color(0xFFCFE8D9)
    val onSecondaryContainer = Color(0xFF0A1F16)
    // surface / onSurface / surfaceVariant / onSurfaceVariant / outline / outlineVariant
    val surface = Color(0xFFFFFBFE)
    val onSurface = Color(0xFF1D1B20)
    val surfaceVariant = Color(0xFFE7E0EC)
    val onSurfaceVariant = Color(0xFF49454E)
    val outline = Color(0xFF79747E)
    val outlineVariant = Color(0xFFCAC4D0)
    // 语义色：error / warning / info
    val error = Color(0xFFB3261E)
    val onError = Color(0xFFFFFFFF)
    val errorContainer = Color(0x33D32F2F)   // 20% 红——#AARRGGBB 直通
    val warning = Color(0xFFE65100)
    val warningContainer = Color(0x33F9A825)
    val info = Color(0xFF2E7D32)
    val infoContainer = Color(0x334CAF50)
    // 药物类别色（参考 DrugPalettes：SSRI 金色）
    val ssri = Color(0xFFFFC107)
    val defaultBar = Color(0xFF00695C)

    /** 主题别名表：`Theme.subl` 的色名 → 常量。生成器按此表把 `color = "primary"` 翻成 `Palette.primary`。 */
    fun byName(name: String): Color? = when (name) {
        "primary" -> primary
        "onPrimary" -> onPrimary
        "primaryContainer" -> primaryContainer
        "onPrimaryContainer" -> onPrimaryContainer
        "secondary" -> secondary
        "onSecondary" -> onSecondary
        "secondaryContainer" -> secondaryContainer
        "onSecondaryContainer" -> onSecondaryContainer
        "surface" -> surface
        "onSurface" -> onSurface
        "surfaceVariant" -> surfaceVariant
        "onSurfaceVariant" -> onSurfaceVariant
        "outline" -> outline
        "outlineVariant" -> outlineVariant
        "error" -> error
        "onError" -> onError
        "errorContainer" -> errorContainer
        "warning" -> warning
        "warningContainer" -> warningContainer
        "info" -> info
        "infoContainer" -> infoContainer
        "ssri" -> ssri
        else -> null
    }
}

/** `"#AARRGGBB"` / `"#RRGGBB"` / 主题别名 → Color。生成器对**字面量**颜色在编译期折叠，这里只服务运行期动态色。 */
fun colorOf(spec: String): Color {
    if (spec.isEmpty()) return Color.Unspecified
    Palette.byName(spec)?.let { return it }
    val hex = spec.removePrefix("#")
    return when (hex.length) {
        8 -> Color(hex.toLong(16).toInt())
        6 -> Color(0xFF000000L.toInt() or hex.toInt(16))
        else -> Color.Unspecified
    }
}
