plugins {
    id("com.android.application")
}

// 使用自定义插件
apply<DefaultGradlePlugin>()

android {
    namespace = "com.wuc.designpattern"

    defaultConfig {
        applicationId = "com.wuc.designpattern"
    }
}

dependencies {
}