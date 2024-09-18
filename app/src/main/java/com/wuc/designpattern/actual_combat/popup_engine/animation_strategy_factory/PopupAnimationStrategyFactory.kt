package com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory

import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.strategy.NoAnimationStrategy
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.strategy.ScaleAlphaFromCenterStrategy
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.strategy.ScaleAlphaFromLeftBottomStrategy
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.strategy.ScaleAlphaFromLeftTopStrategy
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.strategy.ScaleAlphaFromRightBottomStrategy
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.strategy.ScaleAlphaFromRightTopStrategy
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.strategy.TranslateAlphaFromBottomStrategy
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.strategy.TranslateAlphaFromLeftStrategy
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.strategy.TranslateAlphaFromRightStrategy
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.strategy.TranslateAlphaFromTopStrategy
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.strategy.TranslateFromBottomStrategy
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.strategy.TranslateFromLeftStrategy
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.strategy.TranslateFromRightStrategy
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.strategy.TranslateFromTopStrategy

/**
 * @author: wuc
 * @date: 2024/9/13
 * @desc: 动画策略工厂，为了方便创建具体的策略实例，实现了一个工厂类
 */
object PopupAnimationStrategyFactory {
    fun getStrategy(animationType: PopupAnimationType?): IPopupAnimationStrategy? {
        return when (animationType) {
            PopupAnimationType.ScaleAlphaFromCenter -> ScaleAlphaFromCenterStrategy()
            PopupAnimationType.ScaleAlphaFromLeftTop -> ScaleAlphaFromLeftTopStrategy()
            PopupAnimationType.ScaleAlphaFromRightTop -> ScaleAlphaFromRightTopStrategy()
            PopupAnimationType.ScaleAlphaFromLeftBottom -> ScaleAlphaFromLeftBottomStrategy()
            PopupAnimationType.ScaleAlphaFromRightBottom -> ScaleAlphaFromRightBottomStrategy()
            PopupAnimationType.TranslateAlphaFromLeft -> TranslateAlphaFromLeftStrategy()
            PopupAnimationType.TranslateAlphaFromRight -> TranslateAlphaFromRightStrategy()
            PopupAnimationType.TranslateAlphaFromTop -> TranslateAlphaFromTopStrategy()
            PopupAnimationType.TranslateAlphaFromBottom -> TranslateAlphaFromBottomStrategy()
            PopupAnimationType.TranslateFromLeft -> TranslateFromLeftStrategy()
            PopupAnimationType.TranslateFromRight -> TranslateFromRightStrategy()
            PopupAnimationType.TranslateFromTop -> TranslateFromTopStrategy()
            PopupAnimationType.TranslateFromBottom -> TranslateFromBottomStrategy()
            PopupAnimationType.NoAnimation -> NoAnimationStrategy()
            else -> null
        }
    }
}