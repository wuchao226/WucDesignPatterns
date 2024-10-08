package com.wuc.designpattern.actual_combat.strategy

/**
 * @author: wuc
 * @date: 2024/9/30
 * @desc: 策略接口
 */
interface BaseRule<T> {
    fun execute(rule: T): RuleResult
}