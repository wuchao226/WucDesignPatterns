plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    // Kotlin Gradle 插件的依赖，确保项目能够编译和构建 Kotlin 代码
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    // Android 构建工具的依赖，它通常用于 Android 项目的构建过程
    implementation("com.android.tools.build:gradle:8.1.4")
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
}

// 应用于项目中的所有依赖配置
configurations.all {
    // 强制依赖解决策略，确保使用特定的版本。这可以避免项目中多个库使用不同版本的依赖，导致版本冲突
//    resolutionStrategy.force("org.jetbrains.kotlin:kotlin-stdlib:1.8.22")
//    resolutionStrategy.force("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
//    resolutionStrategy.force("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.0")
//    resolutionStrategy.force("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.0")
//    resolutionStrategy.force("org.jetbrains.kotlin:kotlin-reflect:1.9.0")
//    resolutionStrategy.force("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.7.1")
}