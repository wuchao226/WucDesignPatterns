package com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory

import com.lxj.xpopup.enums.PopupAnimation

/**
 * @author: wuc
 * @date: 2024/9/13
 * @desc: 为每种动画类型定义一个策略接口
 */
interface IPopupAnimationStrategy {
    fun getAnimation(): PopupAnimation?
}