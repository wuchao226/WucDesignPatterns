package com.wuc.designpattern.actual_combat.strategy.rule_strategy

import com.wuc.designpattern.actual_combat.strategy.BaseRule
import com.wuc.designpattern.actual_combat.strategy.JobCheck
import com.wuc.designpattern.actual_combat.strategy.RuleResult

class NationalityRule(private val requirNationalitys: List<String>) : BaseRule<JobCheck> {

    override fun execute(rule: JobCheck): RuleResult {

        return if (requirNationalitys.contains(rule.nationality)) {
            RuleResult(true)
        } else {
            RuleResult(false, "国籍不符合此工作")
        }

    }

}