package com.wuc.designpattern.actual_combat.popup_engine.factory.center

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.lxj.xpopup.core.CenterPopupView
import com.wuc.designpattern.actual_combat.popup_engine.factory.IPopupController

/**
 * @author: wuc
 * @date: 2024/9/13
 * @desc: 居中弹窗
 */
@SuppressLint("ViewConstructor")
class OpenCenterPopupView<VB : ViewBinding>(
    context: Context,
    private val viewBinding: ((LayoutInflater) -> VB)?,
    private val width: Int,
    private val height: Int,
    private val maxWidth: Int,
    private val maxHeight: Int,
    private val onCreateListener: ((VB, IPopupController) -> Unit)?,
    private val onDismissListener: (() -> Unit)?,
) : CenterPopupView(context) {
    //弹窗控制器-常见的功能
    private val closeControl = IPopupController { dismiss() }
    lateinit var mBinding: VB

    override fun addInnerContent() {
        if (viewBinding != null) {
            mBinding = viewBinding.invoke(LayoutInflater.from(context))
            centerPopupContainer.addView(mBinding.root)
        } else {
            super.addInnerContent()
        }
    }

    override fun getPopupWidth(): Int {
        return if (width != 0) width else super.getPopupWidth()
    }

    override fun getPopupHeight(): Int {
        return if (height != 0) height else super.getPopupHeight()
    }

    // 如果需要覆写 getMaxWidth 和 getMaxHeight，可以提供自定义的最大宽高
    override fun getMaxWidth(): Int {
        return if (maxWidth != 0) maxWidth else super.getMaxWidth()
    }

    override fun getMaxHeight(): Int {
        return if (maxHeight != 0) maxHeight else super.getMaxHeight()
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