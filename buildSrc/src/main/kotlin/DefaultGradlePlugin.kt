import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

/**
 * @author: wuc
 * @date: 2024/9/20
 * @desc: 默认的配置实现，支持 library 和 application 级别，根据子组件的类型自动判断
 */
open class DefaultGradlePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        setProjectConfig(target)
        setConfigurations(target)
    }

    /**
     * 项目配置
     */
    private fun setProjectConfig(project: Project) {
        val isApplicationModule = project.plugins.hasPlugin("com.android.application")
        if (isApplicationModule) {
            // 处理 com.android.application 模块逻辑
            println("===> Handle Project Config by [com.android.application] Logic")
            setProjectConfigByApplication(project)
        } else {
            // 处理 com.android.library 模块逻辑
            println("===> Handle Project Config by [com.android.library] Logic")
            setProjectConfigByLibrary(project)
        }
    }

    /**
     * 指定依赖版本
     */
    private fun setConfigurations(project: Project) {
        // 配置ARouter的Kapt配置
        project.configureKapt()
        // project.configurations.all: 用于对项目的所有配置（configurations）应用相同的设置
        // 强制指定项目中某些依赖的版本，以确保所有模块使用相同的依赖版本，避免版本冲突和不一致的问题。
        project.configurations.all {
            // resolutionStrategy: 定义依赖解析策略
            // force: 强制指定某个依赖的版本
            resolutionStrategy.force("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
            resolutionStrategy.force("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.0")
            resolutionStrategy.force("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.0")
            resolutionStrategy.force("org.jetbrains.kotlin:kotlin-reflect:1.9.0")
            resolutionStrategy.force("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.7.1")
        }
    }

    /**
     * 设置 application 的相关配置
     */
    private fun setProjectConfigByApplication(project: Project) {
        //添加插件
        project.apply {
            plugin("kotlin-android")
            plugin("kotlin-kapt")
            plugin("org.jetbrains.kotlin.android")
        }
        project.application().apply {
            compileSdk = ProjectConfig.compileSdk
            defaultConfig {
                minSdk = ProjectConfig.minSdk
                targetSdk = ProjectConfig.targetSdk
                versionCode = ProjectConfig.versionCode
                versionName = ProjectConfig.versionName
                testInstrumentationRunner = ProjectConfig.testInstrumentationRunner
                vectorDrawables {
                    useSupportLibrary = true
                }
                ndk {
                    //常用构建目标 'x86_64','armeabi-v7a','arm64-v8a','x86'
                    abiFilters.addAll(arrayListOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
                }
                multiDexEnabled = true
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            // 设置 Kotlin JVM 目标版本
            kotlinOptions {
                jvmTarget = "17"
            }
            
            buildFeatures {
                buildConfig = true
                viewBinding = true
            }
            
            // 设置项目的打包选项，指定如何打包应用程序的资源
            packaging {
                // 配置资源的打包选项
                resources {
                    // excludes: 指定要排除的资源路径
                    // 排除位于 META-INF 目录下的 AL2.0 和 LGPL2.1 文件, 这些文件通常是与许可证相关的文件，通过排除它们可以避免打包过程中出现重复的许可证文件导致的冲突
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }

            // 定义项目的签名配置
            signingConfigs {
                // 创建一个名为 release 的签名配置
                create("release") {
                    // 签名密钥的别名
                    keyAlias = SigningConfigs.keyAlias
                    // 签名密钥的密码
                    keyPassword = SigningConfigs.keyPassword
                    // 指定签名密钥存储文件的路径。
                    storeFile = project.rootDir.resolve(SigningConfigs.storeFile)
                    // 签名密钥存储文件的密码
                    storePassword = SigningConfigs.storePassword
                    // 启用 V1 签名
                    enableV1Signing = true
                    // 启用 V2 签名
                    enableV2Signing = true
                    // 启用 V3 签名
                    enableV3Signing = true
                    // 启用 V4 签名
                    enableV4Signing = true
                }
            }

            buildTypes {
                release {
                    // 启用混淆
                    isMinifyEnabled = true
                    // 启用资源压缩
                    isShrinkResources = true
                    // 是否可调试
                    isDebuggable = false
                    // 是否打开jniDebuggable开关
                    isJniDebuggable = false
                    // 指定混淆规则文件
                    proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                    // 使用 release 签名配置
                    signingConfig = signingConfigs.getByName("release")
                }

                debug {
                    // 启用混淆
                    isMinifyEnabled = false
                    // 启用资源压缩
                    isShrinkResources = false
                    // 是否可调试
                    isDebuggable = false
                    // 是否打开jniDebuggable开关
                    isJniDebuggable = true
                }
            }
            
            // 默认 application 的依赖
            project.dependencies {
                router()
                test()
                appcompat()
                lifecycle()
                kotlin()
                widgetLayout()
                xpopup()
                //依赖 Service 服务
                //implementation(project(":cs-service"))
            }
        }
    }

    /**
     * 设置 library 的相关配置
     */
    private fun setProjectConfigByLibrary(project: Project) {
        // 添加插件
        project.apply {
            plugin("kotlin-android")
            plugin("kotlin-kapt")
            plugin("org.jetbrains.kotlin.android")
        }
        project.library().apply {
            compileSdk = ProjectConfig.compileSdk
            defaultConfig {
                minSdk = ProjectConfig.minSdk
                testInstrumentationRunner = ProjectConfig.testInstrumentationRunner
                vectorDrawables {
                    useSupportLibrary = true
                }
                ndk {
                    //常用构建目标 'x86_64','armeabi-v7a','arm64-v8a','x86'
                    abiFilters.addAll(arrayListOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
                }
                multiDexEnabled = true
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            // 设置 Kotlin JVM 目标版本
            kotlinOptions {
                jvmTarget = "17"
            }

            buildFeatures {
                buildConfig = true
                viewBinding = true
            }
            
            // 设置项目的打包选项，指定如何打包应用程序的资源
            packaging {
                // 配置资源的打包选项
                resources { 
                    // excludes: 指定要排除的资源路径
                    // 排除位于 META-INF 目录下的 AL2.0 和 LGPL2.1 文件, 这些文件通常是与许可证相关的文件，通过排除它们可以避免打包过程中出现重复的许可证文件导致的冲突
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }
            
            // 默认 library 的依赖
            project.dependencies {
                router()
                test()
                appcompat()
                lifecycle()
                kotlin()
                widgetLayout()

                if(isLibraryNeedService()) {
                    //依赖 Service 服务
                    //implementation(project(":cs-service"))
                }
            }
        }
    }

    /**
     * 扩展函数，用于获取 LibraryExtension 实例
     * 根据组件模块的类型给出不同的对象去配置
     * @return LibraryExtension 实例
     */
    private fun Project.library(): LibraryExtension {
        return extensions.getByType(LibraryExtension::class.java)
    }

    /**
     * 扩展函数，用于获取 BaseAppModuleExtension 实例
     * @return BaseAppModuleExtension 实例
     */
    private fun Project.application(): BaseAppModuleExtension {
        return extensions.getByType(BaseAppModuleExtension::class.java)
    }

    /**
     * 扩展函数，用于配置 Kotlin 选项
     * Application 级别 - 扩展函数来设置 KotlinOptions
     * @param action 配置动作
     */
    private fun BaseAppModuleExtension.kotlinOptions(action: KotlinJvmOptions.() -> Unit) {
        (this as ExtensionAware).extensions.configure("kotlinOptions", action)
    }

    /**
     * 扩展函数，用于配置 Kotlin 选项
     * Library 级别 - 扩展函数来设置 KotlinOptions
     * @param action 配置动作
     */
    private fun LibraryExtension.kotlinOptions(action: KotlinJvmOptions.() -> Unit) {
        (this as ExtensionAware).extensions.configure("kotlinOptions", action)
    }

    /**
     * 配置 Project 的 kapt
     */
    private fun Project.configureKapt() {
        this.extensions.findByType(KaptExtension::class.java)?.apply {
            arguments {
                arg("AROUTER_MODULE_NAME", name)
            }
        }
    }

    /**
     * 判断当前项目是否需要配置 service 依赖
     * Library模块是否需要依赖底层 Service 服务，一般子 Module 模块或者 Module-api 模块会依赖到
     */
    protected open fun isLibraryNeedService(): Boolean = false
}