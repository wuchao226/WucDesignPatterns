package com.wuc.designpattern.actual_combat.popup_engine.factory

import android.content.Context
import androidx.viewbinding.ViewBinding
import com.wuc.designpattern.actual_combat.popup_engine.factory.attach.AttachPopupViewCreator
import com.wuc.designpattern.actual_combat.popup_engine.factory.bottom.BottomPopupViewCreator
import com.wuc.designpattern.actual_combat.popup_engine.factory.center.CenterPopupViewCreator
import com.wuc.designpattern.actual_combat.popup_engine.factory.fullscreen.FullScreenPopupViewCreator
import com.wuc.designpattern.actual_combat.popup_engine.factory.part.PartPopupViewCreator

/**
 * @author: wuc
 * @date: 2024/9/13
 * @desc: 布局实例工厂来提供对应的布局
 */
object PopupViewCreatorFactory {
    fun <VB : ViewBinding> getCreator(
        popupType: PopupType,
        context: Context,
        width: Int = 0,
        height: Int = 0,
        maxWidth: Int = 0,
        maxHeight: Int = 0,
        onCreateListener: ((VB, IPopupController) -> Unit)? = null,
        onDismissListener: (() -> Unit)? = null,
    ): IPopupViewCreator<VB> {
        return when (popupType) {
            PopupType.BOTTOM -> BottomPopupViewCreator(context, width, height, maxWidth, maxHeight, onCreateListener, onDismissListener)
            PopupType.KEYBOARD -> BottomPopupViewCreator(context, width, height, maxWidth, maxHeight, onCreateListener, onDismissListener)
            PopupType.CENTER -> CenterPopupViewCreator(context, width, height, maxWidth, maxHeight, onCreateListener, onDismissListener)
            PopupType.FULL_SCREEN -> FullScreenPopupViewCreator(context, onCreateListener, onDismissListener)
            PopupType.PART_SCREEN -> PartPopupViewCreator(context, height, maxHeight, onCreateListener, onDismissListener)
            PopupType.ATTACH -> AttachPopupViewCreator(context, width, height, maxWidth, maxHeight, onCreateListener, onDismissListener)
        }
    }
}