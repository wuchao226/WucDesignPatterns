package com.wuc.designpattern.actual_combat.strategy.rule_strategy

import com.wuc.designpattern.actual_combat.strategy.BaseRule
import com.wuc.designpattern.actual_combat.strategy.JobCheck
import com.wuc.designpattern.actual_combat.strategy.RuleResult

class GenderRule(private val requirGender: Int) : BaseRule<JobCheck> {

    override fun execute(rule: JobCheck): RuleResult {

        return if (requirGender == 2) {
            RuleResult(true)
        } else {

            RuleResult(
                rule.gender == requirGender,
                if (rule.gender == requirGender) "" else "性别不符合此工作"
            )

        }

    }

}