package com.wuc.designpattern.actual_combat.strategy.rule_strategy

import com.wuc.designpattern.actual_combat.strategy.BaseRule
import com.wuc.designpattern.actual_combat.strategy.JobCheck
import com.wuc.designpattern.actual_combat.strategy.RuleResult

class VisaRule : BaseRule<JobCheck> {

    override fun execute(rule: JobCheck): RuleResult {

        return if (rule.visa.lowercase() == "Singapore".lowercase()) {
            RuleResult(true)
        } else {
            RuleResult(false, "签证不满足工作需求")
        }


    }

}