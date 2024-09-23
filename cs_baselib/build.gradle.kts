plugins {
    id("com.android.library")
}

// 使用自定义插件
apply<DefaultGradlePlugin>()

android {
    namespace = "com.wuc.basiclib"
}

dependencies {
    //限制范围，使用引擎类封装使用
    retrofit()
    dataStore()
    imageSelector()
    xpopup()
    glide()
    implementation(VersionThirdPart.toaster)
    implementation(VersionThirdPart.xxPermission)

    //不限制范围，全局使用
    refresh()
    work()
    api(VersionThirdPart.banner)
    api(VersionThirdPart.liveEventBus)
    api(VersionThirdPart.lottie)
}