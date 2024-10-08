package com.wuc.designpattern.actual_combat.strategy.rule_strategy

import com.wuc.designpattern.actual_combat.strategy.BaseRule
import com.wuc.designpattern.actual_combat.strategy.JobCheck
import com.wuc.designpattern.actual_combat.strategy.RuleResult

class COVIDRule(private val callback: () -> Unit) : BaseRule<JobCheck> {

    override fun execute(rule: JobCheck): RuleResult {

        return if (rule.hasCovidTest) {
            RuleResult(true)
        } else {
            callback()
            RuleResult(false, "你没有新冠检测报告")
        }
    }

}