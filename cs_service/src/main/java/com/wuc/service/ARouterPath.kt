package com.wuc.service

/**
 * 常量-ARouter的路由参数
 * 加载注解上的路径
 * 注意：1.注解一定是在Kotlin文件上的 只有Kotlin的Kapt注解处理器
 * 2.一定按格式来 不同的模块加不同的group(第一个'/'中的内容为group)
 */
object ARouterPath {

    //登录页面 Page
    const val PATH_PAGE_AUTH_LOGIN = "/auth/page/login"

    //首页Main Page
    const val PATH_PAGE_MAIN = "/main/page"

    //Profile Page
    const val PATH_PAGE_PROFILE = "/profile/page"

    // ==============================  Service  ==============================

    //App模块路由服务Path
    const val PATH_SERVICE_APP = "/app/path/service"

    //Auth模块路由服务Path
    const val PATH_SERVICE_AUTH = "/auth/path/service"

    //Profile模块路由服务Path
    const val PATH_SERVICE_PROFILE = "/profile/path/service"

    //Service模块路由服务Path
    const val PATH_SERVICE_SERVER = "/service/path/service"
}