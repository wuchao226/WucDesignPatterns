package com.wuc.designpattern.actual_combat.base.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding

/**
 * @author: wuc
 * @date: 2024/9/9
 * @desc: ViewBinding Activity基类
 */
abstract class BaseViewBindingActivity<VB : ViewBinding> : AbsActivity() {

    private var _binding: VB? = null
    protected val mBinding: VB
        get() = requireNotNull(_binding) { "ViewBinding 对象为空，视图可能尚未创建或已销毁" }

    override fun setContentLayout() {
        _binding = createViewBinding()
        setContentView(mBinding.root)
    }

    /**
     * 示例：
     * override fun createViewBinding(): ActivityMainBinding {
     *     return ActivityMainBinding.inflate(layoutInflater)
     * }
     */
    protected abstract fun createViewBinding(): VB
    override fun getLayoutResId(): Int = 0
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}