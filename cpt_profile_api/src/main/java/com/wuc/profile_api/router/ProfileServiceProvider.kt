package com.wuc.profile_api.router

import com.alibaba.android.arouter.launcher.ARouter

//ARouter 的组件服务提供
object ProfileServiceProvider {

    var profileService: IProfileService? = ARouter.getInstance().navigation(IProfileService::class.java)

}