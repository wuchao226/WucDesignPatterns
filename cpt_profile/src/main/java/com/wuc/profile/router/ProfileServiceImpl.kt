package com.wuc.profile.router

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.wuc.profile_api.entity.TopArticleBean
import com.wuc.profile_api.router.IProfileService
import com.wuc.service.ARouterPath

/**
 * @author: wuc
 * @date: 2024/9/23
 * @desc:
 */
@Route(path = ARouterPath.PATH_SERVICE_PROFILE, name = "Profile模块路由服务")
class ProfileServiceImpl : IProfileService {

    //手动在DI注入，在路由中需要使用特殊的方式注入，不能直接@Inject自动注入
//    private lateinit var articleUserCase: ArticleUserCase

    override suspend fun fetchUserProfile(): List<TopArticleBean> {
//        return articleUserCase.invoke()
        return listOf()
    }

    override fun init(context: Context?) {
        if (context != null) {
//            articleUserCase = EntryPointAccessors.fromApplication(context, UserCaseDependencies::class.java).articleUserCase()
        }
    }
}