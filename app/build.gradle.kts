plugins {
    id("com.android.application")
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
}