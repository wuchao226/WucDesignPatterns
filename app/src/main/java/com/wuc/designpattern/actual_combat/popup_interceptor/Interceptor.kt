package com.wuc.designpattern.actual_combat.popup_interceptor

/**
  * @author: wuc
  * @date: 2024/9/23
  * @desc: 拦截器接口
  */
interface Interceptor {
    fun intercept(chain: InterceptChain)
}