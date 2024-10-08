package com.wuc.designpattern.actual_combat.strategy.rule_strategy

import com.wuc.designpattern.actual_combat.strategy.BaseRule
import com.wuc.designpattern.actual_combat.strategy.JobCheck
import com.wuc.designpattern.actual_combat.strategy.RuleResult

class FillProfileRule : BaseRule<JobCheck> {

    override fun execute(rule: JobCheck): RuleResult {

        return if (rule.isFillProfile) {
            RuleResult(true)
        } else {
            RuleResult(false, "请完善用户详细信息")
        }

    }

}