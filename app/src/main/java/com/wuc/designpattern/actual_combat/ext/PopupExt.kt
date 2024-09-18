package com.wuc.designpattern.actual_combat.ext

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupAnimation
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.PopupAnimationStrategyFactory
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.PopupAnimationType
import com.wuc.designpattern.actual_combat.popup_engine.factory.IPopupController
import com.wuc.designpattern.actual_combat.popup_engine.factory.PopupType
import com.wuc.designpattern.actual_combat.popup_engine.factory.PopupViewCreatorFactory

/**
 * @author: wuc
 * @date: 2024/9/13
 * @desc: 设计模式实战 ，实现一个弹窗引擎封装: https://juejin.cn/post/7370993837303971878#heading-3
 */

/**
 * 示例：
 * showPopup(
 *     PopupType.FULL_SCREEN,
 *     PopopBottomCardBinding::inflate,
 *     onCreateListener = { binding, control ->
 *         binding.tvClearAll.click {
 *             toast("点击清理")
 *             control.dismiss()
 *         }
 *     }
 * )
 */
fun <VB : ViewBinding> FragmentActivity.showPopup(
    popupType: PopupType,  //必须指定类型
    viewBinding: (LayoutInflater) -> VB,
    popupAnimationType: PopupAnimationType? = null,  //动画类型
    targetView: View? = null, //如果是Attach则一定要指定在哪个View的基础上弹窗
    width: Int = 0,
    height: Int = 0,   //宽高一般不需要额外设置，自动适配布局的宽高
    maxWidth: Int = 0,  //如果想要指定宽高，可以自定义宽高
    maxHeight: Int = 0,
    offsetX: Int = 0,   //弹框在 X,Y 方向的偏移值
    offsetY: Int = 0,
    isCenterHorizontal: Boolean = false,  //布局是否水平居中
    onCreateListener: ((VB, IPopupController) -> Unit)? = null,   // 创建的回调
    onDismissListener: (() -> Unit)? = null,     // 消失的回调
) {
    // 根据类型转换对应的PopupView继承
    val creator = PopupViewCreatorFactory.getCreator<VB>(
        popupType = popupType,
        context = this,
        width = width,
        height = height,
        maxWidth = maxWidth,
        maxHeight = maxHeight,
        onCreateListener = onCreateListener,
        onDismissListener = onDismissListener,
    )
    val popupView = creator.create(viewBinding)
    val builder = XPopup.Builder(this)
        // 是否有半透明的背景
        .hasShadowBg(popupType == PopupType.BOTTOM || popupType == PopupType.CENTER)  //默认底部弹窗和中间弹窗展示灰色背景遮罩
        .isDestroyOnDismiss(true)  //消失的时候是否释放资源
        .offsetX(offsetX) // 弹窗在x方向的偏移量
        .offsetY(offsetY) // 弹窗在y方向的偏移量
        //是否和目标水平居中，比如：默认情况下Attach弹窗依靠着目标的左边或者右边，
        //如果isCenterHorizontal为true，则与目标水平居中对齐
        .isCenterHorizontal(isCenterHorizontal)

    if (targetView != null) {
        // 依附于所点击的View，内部会自动判断在上方或者下方显示
        builder.atView(targetView)
    }

    if (popupType == PopupType.KEYBOARD) {
        builder.autoOpenSoftInput(true) //是否弹窗显示的同时打开输入法，只在包含输入框的弹窗内才有效，默认为false
            .moveUpToKeyboard(true) // 软键盘弹出时，弹窗是否移动到软键盘上面，默认为true
    }
    // 转换动画效果，是否需要添加动画
    if (transformAnimationType(popupAnimationType) != null) {
        builder.popupAnimation(transformAnimationType(popupAnimationType))
    }

    builder.asCustom(popupView)
        .show()
}

// 获取动画类型
fun transformAnimationType(animationType: PopupAnimationType?): PopupAnimation? {
    val strategy = PopupAnimationStrategyFactory.getStrategy(animationType)
    return strategy?.getAnimation()
}