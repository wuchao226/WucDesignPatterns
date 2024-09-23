package com.wuc.designpattern.actual_combat.popup_interceptor

/**
  * @author: wuc
  * @date: 2024/9/23
  * @desc: 拦截器接口
  */
interface PopupInterceptor {
    fun intercept(chain: PopupInterceptChain)
}