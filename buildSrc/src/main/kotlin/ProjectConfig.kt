/**
 * @author: wuc
 * @date: 2024/9/19
 * @desc: 项目编译配置与AppId配置
 */

object ProjectConfig {
    const val minSdk = 21
    const val compileSdk = 34
    const val targetSdk = 34

    const val versionCode = 1
    const val versionName = "1.0.0"

    const val applicationId = "com.wuc.designpattern"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

/**
 * 签名文件信息配置
 */
object SigningConfigs {
    //密钥文件路径
    const val storeFile = "key/design_pattern.jks"

    //密钥密码
    const val storePassword = "123456"

    //密钥别名
    const val keyAlias = "design_pattern"

    //别名密码
    const val keyPassword = "123456"
}