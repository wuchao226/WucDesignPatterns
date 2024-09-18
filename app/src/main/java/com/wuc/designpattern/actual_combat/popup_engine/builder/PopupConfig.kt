package com.wuc.designpattern.actual_combat.popup_engine.builder

import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.PopupAnimationType
import com.wuc.designpattern.actual_combat.popup_engine.factory.IPopupController
import com.wuc.designpattern.actual_combat.popup_engine.factory.PopupType

/**
 * @author: wuc
 * @date: 2024/9/13
 * @desc: 建造者模式创建入参 配置选项
 */
data class PopupConfig<VB : ViewBinding>(
    val popupType: PopupType,
    val viewBinding: (LayoutInflater) -> VB,
    val popupAnimationType: PopupAnimationType? = null,
    val targetView: View? = null,
    val width: Int = 0,
    val height: Int = 0,
    val maxWidth: Int = 0,
    val maxHeight: Int = 0,
    val offsetX: Int = 0,
    val offsetY: Int = 0,
    val isCenterHorizontal: Boolean = false,
    val onCreateListener: ((VB, IPopupController) -> Unit)? = null,
    val onDismissListener: (() -> Unit)? = null
)
