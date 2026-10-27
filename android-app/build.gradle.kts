// 顶层构建：AGP 8.7.3 + Kotlin 2.1.20（Compose 编译器随 Kotlin 发布）。
// Kotlin 从 1.9.24 升到 2.1.20 才能用 org.jetbrains.kotlin.plugin.compose（与参考实现 Sugar Manual 同版本）。
plugins {
    id("com.android.application") version "8.7.3" apply false
    id("org.jetbrains.kotlin.android") version "2.1.20" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.20" apply false
}
