plugins {
    id("com.android.library")
}

// 使用自定义插件
apply<ModuleGradlePlugin>()

android {
    namespace = "com.wuc.profile_api"
}