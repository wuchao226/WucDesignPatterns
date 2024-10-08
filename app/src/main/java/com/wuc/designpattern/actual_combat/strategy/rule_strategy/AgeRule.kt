package com.wuc.designpattern.actual_combat.strategy.rule_strategy

import com.wuc.designpattern.actual_combat.strategy.BaseRule
import com.wuc.designpattern.actual_combat.strategy.JobCheck
import com.wuc.designpattern.actual_combat.strategy.RuleResult

class AgeRule : BaseRule<JobCheck> {

    override fun execute(rule: JobCheck): RuleResult {

        return if (rule.age > 16 && rule.age < 50) {
            RuleResult(true)
        } else {
            RuleResult(false, "年龄不满足")
        }

    }

}