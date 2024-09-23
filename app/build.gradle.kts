plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

// 使用自定义插件
apply<DefaultGradlePlugin>()

android {
    namespace = "com.wuc.designpattern"

    defaultConfig {
        applicationId = ProjectConfig.applicationId
    }
}

dependencies {
    //依赖子组件
    implementation(project(":cpt_profile"))
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity:1.9.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}