package com.wuc.designpattern.actual_combat.popup_engine.factory.part

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.lxj.xpopup.impl.PartShadowPopupView
import com.wuc.designpattern.actual_combat.popup_engine.factory.IPopupController

/**
 * @author: wuc
 * @date: 2024/9/13
 * @desc: 局部阴影弹窗
 */
@SuppressLint("ViewConstructor")
class OpenPartPopupView<VB : ViewBinding>(
    context: Context,
    private val viewBinding: ((LayoutInflater) -> VB)?,
    private val height: Int,
    private val maxHeight: Int,
    private val onCreateListener: ((VB, IPopupController) -> Unit)?,
    private val onDismissListener: (() -> Unit)?,
) : PartShadowPopupView(context) {
    //弹窗控制器-常见的功能
    private val closeControl = IPopupController { dismiss() }

    lateinit var mBinding: VB

    override fun addInnerContent() {
        if (viewBinding != null) {
            mBinding = viewBinding.invoke(LayoutInflater.from(context))
            attachPopupContainer.addView(mBinding.root)
        } else {
            super.addInnerContent()
        }
    }
    /**
     * 弹窗的高度，用来动态设定当前弹窗的高度，受getMaxHeight()限制
     *
     * @return
     */
    override fun getPopupHeight(): Int {
        return if (height != 0) height else super.getPopupHeight()
    }
    // 设置最大高度
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