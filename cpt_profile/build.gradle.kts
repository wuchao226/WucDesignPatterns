plugins {
    id("com.android.library")
}

// 使用自定义插件
apply<ModuleGradlePlugin>()

android {
    namespace = "com.wuc.profile"
}

dependencies {

    //依赖到对应组件的Api模块
    api(project(":cpt_profile_api"))
}