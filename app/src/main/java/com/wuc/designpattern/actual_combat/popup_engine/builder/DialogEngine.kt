package com.wuc.designpattern.actual_combat.popup_engine.builder

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.lxj.xpopup.XPopup
import com.wuc.designpattern.actual_combat.ext.transformAnimationType
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.PopupAnimationType
import com.wuc.designpattern.actual_combat.popup_engine.factory.IPopupController
import com.wuc.designpattern.actual_combat.popup_engine.factory.PopupType
import com.wuc.designpattern.actual_combat.popup_engine.factory.PopupViewCreatorFactory

/**
 * @author: wuc
 * @date: 2024/9/14
 * @desc: 设置构建者模式
 * 建造者模式可以非常适合用于进一步提升弹窗创建的灵活性和可读性。
 * 建造者模式能让你构建一个复杂对象的多个版本（例如不同配置的弹窗），而无需创建多个构造器，这样代码会更加清晰
 *
 * 示例：
 *   DialogEngine<PopopBottomCardBinding>(mActivity)
 *       .setPopupType(PopupType.BOTTOM)
 *       .setViewBinding(PopopBottomCardBinding::inflate)
 *       .setOnCreateListener { binding, control ->
 *           binding.tvClearAll.click {
 *               toast("点击清理")
 *               control.dismiss()
 *           }
 *       }
 *       .show()
 */
class DialogEngine<VB : ViewBinding>(private val activity: FragmentActivity) {
    private var config = PopupConfig<VB>(
        popupType = PopupType.CENTER,
        viewBinding = { throw IllegalArgumentException("ViewBinding must be set.") },
    )

    // 默认居中弹窗，需要指定类型
    fun setPopupType(popupType: PopupType) = apply {
        config = config.copy(popupType = popupType)
    }

    //一定要设置布局
    fun setViewBinding(viewBinding: (LayoutInflater) -> VB) = apply {
        config = config.copy(viewBinding = viewBinding)
    }

    //选填弹窗动画方式
    fun setPopupAnimationType(popupAnimationType: PopupAnimationType?) =
        apply { config = config.copy(popupAnimationType = popupAnimationType) }

    //依附布局必须指定
    fun setTargetView(targetView: View?) = apply { config = config.copy(targetView = targetView) }


    //其他选填项
    // 宽度
    fun setWidth(width: Int) = apply { config = config.copy(width = width) }

    // 高度
    fun setHeight(height: Int) = apply { config = config.copy(height = height) }

    // 设置最大宽度
    fun setMaxWidth(maxWidth: Int) = apply { config = config.copy(maxWidth = maxWidth) }

    // 设置最大高度
    fun setMaxHeight(maxHeight: Int) = apply { config = config.copy(maxHeight = maxHeight) }

    // 弹窗在x方向的偏移量
    fun setOffsetX(offsetX: Int) = apply { config = config.copy(offsetX = offsetX) }

    // 弹窗在y方向的偏移量
    fun setOffsetY(offsetY: Int) = apply { config = config.copy(offsetY = offsetY) }

    //是否和目标水平居中，比如：默认情况下Attach弹窗依靠着目标的左边或者右边，
    //如果isCenterHorizontal为true，则与目标水平居中对齐
    fun isCenterHorizontal(isCenterHorizontal: Boolean) =
        apply { config = config.copy(isCenterHorizontal = isCenterHorizontal) }

    // 创建的回调
    fun setOnCreateListener(listener: (VB, IPopupController) -> Unit) =
        apply { config = config.copy(onCreateListener = listener) }

    // 消失的回调
    fun setOnDismissListener(listener: () -> Unit) = apply { config = config.copy(onDismissListener = listener) }


    fun show() {
        // 根据类型转换对应的PopupView继承
        val creator = PopupViewCreatorFactory.getCreator<VB>(
            popupType = config.popupType,
            context = activity,
            width = config.width,
            height = config.height,
            maxWidth = config.maxWidth,
            maxHeight = config.maxHeight,
            onCreateListener = config.onCreateListener,
            onDismissListener = config.onDismissListener,
        )

        val popupView = creator.create(config.viewBinding)
        val builder = XPopup.Builder(activity)
            // 是否有半透明的背景
            .hasShadowBg(config.popupType == PopupType.BOTTOM || config.popupType == PopupType.CENTER)  //默认底部弹窗和中间弹窗展示灰色背景遮罩
            .isDestroyOnDismiss(true)  //消失的时候是否释放资源
            .offsetX(config.offsetY) // 弹窗在x方向的偏移量
            .offsetY(config.offsetY) // 弹窗在y方向的偏移量
            //是否和目标水平居中，比如：默认情况下Attach弹窗依靠着目标的左边或者右边，
            //如果isCenterHorizontal为true，则与目标水平居中对齐
            .isCenterHorizontal(config.isCenterHorizontal)

        if (config.targetView != null) {
            // 依附于所点击的View，内部会自动判断在上方或者下方显示
            builder.atView(config.targetView)
        }

        if (config.popupType == PopupType.KEYBOARD) {
            builder.autoOpenSoftInput(true) //是否弹窗显示的同时打开输入法，只在包含输入框的弹窗内才有效，默认为false
                .moveUpToKeyboard(true) // 软键盘弹出时，弹窗是否移动到软键盘上面，默认为true
        }
        // 转换动画效果，是否需要添加动画
        if (transformAnimationType(config.popupAnimationType) != null) {
            builder.popupAnimation(transformAnimationType(config.popupAnimationType))
        }

        builder.asCustom(popupView)
            .show()
    }
}