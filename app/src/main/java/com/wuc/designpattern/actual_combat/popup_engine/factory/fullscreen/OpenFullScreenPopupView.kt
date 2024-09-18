package com.wuc.designpattern.actual_combat.popup_engine.factory.fullscreen

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.lxj.xpopup.impl.FullScreenPopupView
import com.wuc.designpattern.actual_combat.popup_engine.factory.IPopupController

/**
 * @author: wuc
 * @date: 2024/9/13
 * @desc: 全屏弹窗
 */
@SuppressLint("ViewConstructor")
class OpenFullScreenPopupView<VB : ViewBinding>(
    context: Context,
    private val viewBinding: ((LayoutInflater) -> VB)?,
    private val onCreateListener: ((VB, IPopupController) -> Unit)?,
    private val onDismissListener: (() -> Unit)?,
) : FullScreenPopupView(context) {
    //弹窗控制器-常见的功能
    private val closeControl = IPopupController { dismiss() }
    lateinit var mBinding: VB

    override fun addInnerContent() {
        if (viewBinding != null) {
            mBinding = viewBinding.invoke(LayoutInflater.from(context))
            fullPopupContainer.addView(mBinding.root)
        } else {
            super.addInnerContent()
        }
    }

    override fun onCreate() {
        super.onCreate()
        onCreateListener?.invoke(mBinding, closeControl)
    }

    override fun onDismiss() {
        super.onDismiss()
        onDismissListener?.invoke()
    }
}