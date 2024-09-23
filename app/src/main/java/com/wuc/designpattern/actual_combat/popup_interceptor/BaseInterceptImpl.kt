package com.wuc.designpattern.actual_combat.popup_interceptor
import androidx.annotation.CallSuper
/**
  * @author: wuc
  * @date: 2024/9/23
  * @desc: 拦截器的基类
  */
abstract class BaseInterceptImpl: PopupInterceptor {
    protected var mChain : PopupInterceptChain? = null
    @CallSuper
    override fun intercept(chain: PopupInterceptChain) {
        // 具体的拦截逻辑
        mChain = chain
    }
}