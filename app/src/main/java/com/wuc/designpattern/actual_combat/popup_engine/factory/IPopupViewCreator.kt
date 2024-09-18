package com.wuc.designpattern.actual_combat.popup_engine.factory

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.lxj.xpopup.core.BasePopupView

/**
 * @author: wuc
 * @date: 2024/9/13
 * @desc: 工厂模式实现不同弹窗布局的创建
 */
interface IPopupViewCreator<VB : ViewBinding> {
    fun create(viewBinding: ((LayoutInflater) -> VB)?): BasePopupView
}