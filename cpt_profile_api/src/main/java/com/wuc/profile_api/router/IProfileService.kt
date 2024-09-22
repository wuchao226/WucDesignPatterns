package com.wuc.profile_api.router

import com.alibaba.android.arouter.facade.template.IProvider
import com.wuc.profile_api.entity.TopArticleBean

interface IProfileService : IProvider {

    suspend fun fetchUserProfile(): List<TopArticleBean>
}
