package com.wuc.designpattern.actual_combat.popup_interceptor.actual

/**
 * @author: wuc
 * @date: 2024/9/23
 * @desc: 后台返回的工作校验对象, 具体到工作申请判断类
 */
data class JobInterceptBean(
    var isNewMember: Boolean = false,
    var isFillInfo: Boolean = false,
    var isMemberApprove: Boolean = false,
    var isNOCVUpload: Boolean = false,
    var isNeedDepost: Boolean = false,
    var isNeedFace: Boolean = false,
    var isUnderCompany: Boolean = false,
    var isNeedWhatApp: Boolean = false,
    var isNeedBankInfo: Boolean = false,
    var isNeedSkill: Boolean = false
)
