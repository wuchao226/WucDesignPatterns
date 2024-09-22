import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * @author: wuc
 * @date: 2024/9/20
 * @desc: 给 DependencyHandler 默认添加扩展方法用于代码中添加依赖
 */
fun DependencyHandler.api(dependency: Any) {
    add("api", dependency)
}

fun DependencyHandler.implementation(dependency: Any) {
    add("implementation", dependency)
}

fun DependencyHandler.testImplementation(dependency: Any) {
    add("testImplementation", dependency)
}

fun DependencyHandler.androidTestImplementation(dependency: Any) {
    add("androidTestImplementation", dependency)
}

fun DependencyHandler.debugImplementation(dependency: Any) {
    add("debugImplementation", dependency)
}

fun DependencyHandler.kapt(dependency: Any) {
    add("kapt", dependency)
}