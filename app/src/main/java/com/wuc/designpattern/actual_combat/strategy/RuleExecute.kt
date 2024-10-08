package com.wuc.designpattern.actual_combat.strategy

/**
 * @desc: 策略的执行规则
 * 这个类用于执行一组规则，并根据规则的执行结果返回最终的结果。
 * 规则可以通过 AND 或 OR 逻辑进行组合。
 */
class RuleExecute<T>(val check: T, val hashMap: HashMap<String, MutableList<BaseRule<T>>>) {

    companion object {
        private const val AND = "&&"
        private const val OR = "||"

        /**
         * 创建一个 Builder 对象，用于构建 RuleExecute 实例。
         * @param check 需要检查的对象
         * @return Builder 实例
         */
        @JvmStatic
        fun <T> create(check: T): Builder<T> {
            return Builder(check)
        }
    }

    /**
     * Builder 类用于构建 RuleExecute 实例。
     * @param check 需要检查的对象
     */
    open class Builder<T>(val check: T) {
        private val hashMap: HashMap<String, MutableList<BaseRule<T>>> = HashMap()
        private var indexSuffix: Int = 0

        /**
         * 添加一组需要 AND 逻辑的规则。
         * @param ruleList 规则列表
         * @return Builder 实例
         */
        fun and(ruleList: MutableList<BaseRule<T>>): Builder<T> {
            val key = AND + (indexSuffix++).toString()
            hashMap[key] = ruleList
            return this
        }

        /**
         * 添加一组需要 OR 逻辑的规则。
         * @param ruleList 规则列表
         * @return Builder 实例
         */
        fun or(ruleList: MutableList<BaseRule<T>>): Builder<T> {
            val key = OR + (indexSuffix++).toString()
            hashMap[key] = ruleList
            return this
        }

        /**
         * 构建 RuleExecute 实例。
         * @return RuleExecute 实例
         */
        fun build(): RuleExecute<T> {
            return RuleExecute(check, hashMap)
        }
    }

    /**
     * 执行所有规则，并返回最终的结果。
     * @return RuleResult 规则执行结果
     */
    /**
     * 执行所有规则，并返回最终的结果。
     * 这个方法遍历了hashMap中的所有键值对，根据键的前两个字符来判断是AND还是OR逻辑。
     * 对于AND逻辑，如果任何一个规则失败，则立即返回失败结果。
     * 对于OR逻辑，如果任何一个规则成功，则立即返回成功结果。
     * 如果所有规则都执行完毕且没有返回失败结果，则返回成功结果。
     * @return RuleResult 规则执行结果
     */
    fun execute(): RuleResult {
        for ((key, ruleList) in hashMap) {
            // 根据键的前两个字符来判断是AND还是OR逻辑
            when (key.substring(0, 2)) {
                AND -> {
                    // 执行AND逻辑的规则列表
                    val result = and(check, ruleList)
                    // 如果任何一个规则失败，则立即返回失败结果
                    if (!result.success) {
                        return result
                    }
                }

                OR -> {
                    // 执行OR逻辑的规则列表
                    val result = or(check, ruleList)
                    // 如果任何一个规则成功，则立即返回成功结果
                    if (!result.success) {
                        return result
                    }
                }
            }
        }
        // 如果所有规则都执行完毕且没有返回失败结果，则返回成功结果
        return RuleResult(true)
    }

    /**
     * 执行 AND 逻辑的规则列表。
     * @param check 需要检查的对象
     * @param ruleList 规则列表
     * @return RuleResult 规则执行结果
     */
    private fun and(check: T, ruleList: MutableList<BaseRule<T>>): RuleResult {
        for (rule in ruleList) {
            val result = rule.execute(check)
            if (!result.success) {
                // 只要有一个规则失败，就返回失败结果
                return result
            }
        }
        // 所有规则都成功，返回成功结果
        return RuleResult(true)
    }

    /**
     * 执行 OR 逻辑的规则列表。
     * @param check 需要检查的对象
     * @param ruleList 规则列表
     * @return RuleResult 规则执行结果
     */
    private fun or(check: T, ruleList: MutableList<BaseRule<T>>): RuleResult {
        val stringBuilder = StringBuilder()
        for (rule in ruleList) {
            val result = rule.execute(check)
            if (result.success) {
                // 只要有一个规则成功，就返回成功结果
                return RuleResult(true)
            } else {
                stringBuilder.append(result.message)
            }
        }
        // 所有规则都失败，返回失败结果
        val ruleResult = RuleResult(false, stringBuilder.toString())
        stringBuilder.clear()
        return ruleResult
    }
}
