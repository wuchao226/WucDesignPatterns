package com.wuc.designpattern.actual_combat.popup_interceptor

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * @author: wuc
 * @date: 2024/9/23
 * @desc: 拦截链管理类(具体的逻辑)
 */
class PopupInterceptChain private constructor(
    // 弹窗的时候可能需要Activity/Fragment环境
    val activity: FragmentActivity? = null,
    val fragment: Fragment? = null,
    private var popupInterceptors: MutableList<PopupInterceptor>?
) {
    companion object {
        @JvmStatic
        fun create(count: Int = 0): Builder {
            return Builder(count)
        }
    }

    private var index: Int = 0
    // 执行拦截器
    fun process() {
        popupInterceptors ?: return
        when(index) {
            // index在interceptors的有效索引范围内
            in popupInterceptors!!.indices -> {
                // 获取当前索引index对应的拦截器
                val interceptor = popupInterceptors!![index]
                index++
                // 调用当前拦截器的intercept方法，并传入当前InterceptChain实例
                interceptor.intercept(this)
            }
            // 当index等于interceptors的大小时，表示所有拦截器已经被执行过，清空拦截器列表
            popupInterceptors!!.size -> {
                clearAllInterceptors()
            }
        }
    }

    private fun clearAllInterceptors() {
        popupInterceptors?.clear()
        popupInterceptors = null
    }

    open class Builder(val count: Int = 0) {
        private val popupInterceptors by lazy(LazyThreadSafetyMode.NONE) { ArrayList<PopupInterceptor>(count) }
        private var activity: FragmentActivity? = null
        private var fragment: Fragment? = null

        // 添加拦截器
        fun addInterceptor(popupInterceptor: PopupInterceptor): Builder {
            if (!popupInterceptors.contains(popupInterceptor)) {
                popupInterceptors.add(popupInterceptor)
            }
            return this
        }

        // 关联Activity
        fun attach(activity: FragmentActivity): Builder {
            this.activity = activity
            return this
        }

        // 关联Fragment
        fun attach(fragment: Fragment): Builder {
            this.fragment = fragment
            return this
        }

        fun build(): PopupInterceptChain {
            return PopupInterceptChain(activity, fragment, popupInterceptors)
        }
    }
}