package com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory

/**
 * @author: wuc
 * @date: 2024/9/13
 * @desc: 动画类型
 */
enum class PopupAnimationType {
    ScaleAlphaFromCenter,
    ScaleAlphaFromLeftTop,
    ScaleAlphaFromRightTop,
    ScaleAlphaFromLeftBottom,
    ScaleAlphaFromRightBottom,
    TranslateAlphaFromLeft,
    TranslateAlphaFromRight,
    TranslateAlphaFromTop,
    TranslateAlphaFromBottom,
    TranslateFromLeft,
    TranslateFromRight,
    TranslateFromTop,
    TranslateFromBottom,
    ScrollAlphaFromLeft,
    ScrollAlphaFromLeftTop,
    ScrollAlphaFromTop,
    ScrollAlphaFromRightTop,
    ScrollAlphaFromRight,
    ScrollAlphaFromRightBottom,
    ScrollAlphaFromBottom,
    ScrollAlphaFromLeftBottom,
    NoAnimation;
}