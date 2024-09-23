package com.wuc.designpattern.actual_combat.popup_interceptor.actual

import com.wuc.baselib.ext.toast
import com.wuc.baselib.view.FangIOSDialog
import com.wuc.designpattern.actual_combat.popup_interceptor.BaseInterceptImpl
import com.wuc.designpattern.actual_combat.popup_interceptor.InterceptChain

/**
 * @author: wuc
 * @date: 2024/9/23
 * @desc: 用户状态使用弹窗判断
 */
class InterceptMemberApprove(private val bean: JobInterceptBean): BaseInterceptImpl() {
    override fun intercept(chain: InterceptChain) {
        super.intercept(chain)
        if (!bean.isMemberApprove) {
            //拦截
            showDialogTips(chain)
        } else {
            //放行- 转交给下一个拦截器
            chain.process()
        }
    }
    private fun showDialogTips(chain: InterceptChain) {
        FangIOSDialog(chain.activity).apply {
            setTitle("状态不对")
            setMessage("你用户状态不对，联系管理员吗？")
            setNegativeButton("跳过") {
                dismiss()
                chain.process()
            }
            setPositiveButton("联系") {
                dismiss()
                toast("去拨打电话，当你状态对了才能继续")
            }
            show()
        }
    }
}