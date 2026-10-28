plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

import java.io.File

// ===== 生成物开关（SfA @compose → Kotlin Compose；契约 android doc/Compose代码生成契约.md）=====
// generated        ：编译期 @compose 通路产物是否就位——发射器把 <Name>.kt 与聚合
//                     GeneratedCompose.kt（含 SublRoot() 分派）写进契约位置 <ns>/gen/
// generatedSample  ：是否把 gen/GeneratedComposeSample.kt 的样例首页挂为启动页（可与 generated 独立）
fun findOutDir(): String {
    val prop = (project.findProperty("subl.out") as String?)
    if (prop != null) return prop
    return "${projectDir}/src/main/java/functor/sublimation/android/gen"
}
fun hasGenerated(): Boolean = File(findOutDir(), "GeneratedCompose.kt").isFile
fun isSample(): Boolean =
    File("${projectDir}/src/main/java/functor/sublimation/android/gen/GeneratedComposeSample.kt").isFile &&
    (project.findProperty("subl.sample") as String?) != "false"

android {
    namespace = "functor.sublimation.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "functor.sublimation.android"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        buildConfigField("boolean", "SUBL_COMPOSE_GENERATED", hasGenerated().toString())
        buildConfigField("boolean", "SUBL_COMPOSE_SAMPLE", isSample().toString())
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    // 布局层（@compose 生成物）：Compose BOM 2024.12.01 → Compose 1.7.6 / Material3 1.3.1
    implementation(platform("androidx.compose:compose-bom:2024.12.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
    debugImplementation("androidx.compose.ui:ui-tooling")
}
