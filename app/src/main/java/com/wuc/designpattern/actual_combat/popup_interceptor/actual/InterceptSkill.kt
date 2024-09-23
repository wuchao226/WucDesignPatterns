package com.wuc.designpattern.actual_combat.popup_interceptor.actual

import android.util.Log
import com.wuc.baselib.view.FangIOSDialog
import com.wuc.designpattern.actual_combat.popup_interceptor.BaseInterceptImpl
import com.wuc.designpattern.actual_combat.popup_interceptor.InterceptChain

/**
 * @author: wuc
 * @date: 2024/9/23
 * @desc: 技能的拦截-使用弹窗判断
 */
class InterceptSkill(private val bean: JobInterceptBean): BaseInterceptImpl() {
    override fun intercept(chain: InterceptChain) {
        super.intercept(chain)
        if (bean.isNeedSkill) {
            //拦截
            //跳转新页面
            Log.d("intercept", "技能的拦截......")
            showDialogTips(chain)
        } else {
            //放行- 转交给下一个拦截器
            chain.process()
        }
    }
    private fun showDialogTips(chain: InterceptChain) {
        FangIOSDialog(chain.activity).apply {
            setTitle("你没有填写技能")
            setMessage("你要去填写技能吗？")
            setNegativeButton("跳过") {
                dismiss()
                chain.process()
            }
            setPositiveButton("去填写") {
                Log.d("intercept", "去填写技能......")
            }
            show()
        }
    }
}