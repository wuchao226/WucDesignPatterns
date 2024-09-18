package com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.strategy

import com.lxj.xpopup.enums.PopupAnimation
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.IPopupAnimationStrategy

/**
 * @author: wuc
 * @date: 2024/9/13
 * @desc: 平移动画，不带渐变
 */
class TranslateFromBottomStrategy : IPopupAnimationStrategy {
    override fun getAnimation(): PopupAnimation {
        return PopupAnimation.TranslateFromBottom
    }
}