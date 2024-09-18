package com.wuc.designpattern.actual_combat.popup_engine.factory

/**
 * @author: wuc
 * @date: 2024/9/13
 * @desc: 弹窗类型
 */
enum class PopupType {
    BOTTOM,         // 底部弹窗
    KEYBOARD,       // 键盘弹窗
    CENTER,         // 居中弹窗
    FULL_SCREEN,    // 全屏弹窗
    PART_SCREEN,    // 局部弹窗
    ATTACH          // 附加弹窗
}