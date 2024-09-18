package com.wuc.designpattern.actual_combat.popup_engine.factory.part

import android.content.Context
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.lxj.xpopup.core.BasePopupView
import com.wuc.designpattern.actual_combat.popup_engine.factory.IPopupController
import com.wuc.designpattern.actual_combat.popup_engine.factory.IPopupViewCreator

/**
 * @author: wuc
 * @date: 2024/9/13
 * @desc:
 */
class PartPopupViewCreator<VB : ViewBinding>(
    private val context: Context,
    private val height: Int,
    private val maxHeight: Int,
    private val onCreateListener: ((VB, IPopupController) -> Unit)?,
    private val onDismissListener: (() -> Unit)?,
) : IPopupViewCreator<VB> {
    override fun create(viewBinding: ((LayoutInflater) -> VB)?): BasePopupView {
        return OpenPartPopupView(
            context = context,
            viewBinding = viewBinding,
            height = height,
            maxHeight = maxHeight,
            onCreateListener = onCreateListener,
            onDismissListener = onDismissListener,
        )
    }
}