package com.wuc.designpattern.actual_combat.strategy

/**
 * @author: wuc
 * @date: 2024/9/30
 * @desc: 校验对象，一般由服务器返回
 */
data class JobCheck(
    //人物要求
    val nric: String,
    val visa: String,
    val nationality: String,
    val age: Float,
    val workingYear: Float,
    val isFillProfile: Boolean,

    var hasCovidTest: Boolean,

    //工作要求
    val language: String,
    val gender: Int,
    val deposit: String,
    val isSelfProvide: Boolean,
    val isTrained: Boolean,
)
