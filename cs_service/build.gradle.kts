plugins {
    id("com.android.library")
}

// 使用自定义插件
apply<DefaultGradlePlugin>()

android {
    namespace = "com.wuc.service"
}

dependencies {
    api(project(":cs_baselib"))
}