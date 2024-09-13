package com.wuc.designpattern.actual_combat.base.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.wuc.designpattern.actual_combat.base.fragment.BaseViewBindingReflectFragment

/**
 * @author: wuc
 * @date: 2024/9/13
 * @desc:基于 ViewBinding 和 ViewModel Activity 基类
 */
abstract class BaseVBVMReflectActivity<VB : ViewBinding, VM : ViewModel> : BaseViewBindingReflectActivity<VB>() {

    protected lateinit var mViewModel: VM

    /**
     * 通过 lazy 初始化 ViewModel，子类可以覆盖此函数实现特定的 ViewModelProvider 逻辑
     * 子类可以通过覆盖该方法来自定义 ViewModel 的提供方式（如使用 ViewModelProvider(requireActivity()) 来共享 ViewModel）
     */
    protected open val viewModelProvider: () -> VM = {
        ViewModelProvider(this)[getViewModelClass()]
    }

    /**
     * 子类需要提供 ViewModel 的具体类型
     * 示例：
     * override fun getViewModelClass(): Class<ExampleViewModel> = ExampleViewModel::class.java
     */
    protected abstract fun getViewModelClass(): Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        mViewModel = viewModelProvider()
        super.onCreate(savedInstanceState)
    }
}