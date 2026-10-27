plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

// ===== 生成物开关（SfA @compose → Kotlin Compose；契约 android doc/Compose代码生成契约.md）=====
// generated        ：生成器（t57）产物 gen/GeneratedCompose.kt 是否存在（SublRoot()）
// generatedSample  ：是否把 gen/GeneratedComposeSample.kt 的样例首页挂为启动页（本任务验证用）
fun findOutDir(): String {
    val prop = (project.findProperty("subl.out") as String?)
    if (prop != null) return prop
    return "${rootProject.projectDir}/../../out"
}
fun hasGenerated(): Boolean = java.io.File(findOutDir(), "GeneratedCompose.kt").exists()
fun isSample(): Boolean =
    java.io.File("${projectDir}/src/main/java/functor/sublimation/android/gen/GeneratedComposeSample.kt").exists() &&
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
        buildConfigField("boolean", "SUBL_COMPOSE_SAMPLE", (hasGenerated() && isSample()).toString())
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
