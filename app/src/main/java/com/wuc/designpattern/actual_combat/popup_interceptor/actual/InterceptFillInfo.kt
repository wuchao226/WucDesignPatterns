package com.wuc.designpattern.actual_combat.popup_interceptor.actual

import android.util.Log
import com.wuc.baselib.view.FangIOSDialog
import com.wuc.designpattern.actual_combat.popup_interceptor.BaseInterceptImpl
import com.wuc.designpattern.actual_combat.popup_interceptor.InterceptChain

/**
 * @author: wuc
 * @date: 2024/9/23
 * @desc: 完善信息的拦截
 */
class InterceptFillInfo(private val bean: JobInterceptBean) : BaseInterceptImpl() {
    override fun intercept(chain: InterceptChain) {
        super.intercept(chain)
        if (!bean.isFillInfo) {
            //拦截
            //跳转新页面
            Log.d("intercept", "完善信息的拦截......")
            showDialogTips(chain)
        } else {
            //放行- 转交给下一个拦截器
            chain.process()
        }
    }
    private fun showDialogTips(chain: InterceptChain) {
        FangIOSDialog(chain.activity).apply {
            setTitle("完善信息")
            setMessage("你没有完善信息，你要去完善信息")
            setNegativeButton("跳过"){
                dismiss()
                chain.process()
            }
            setPositiveButton("去完善"){
                Log.d("intercept", "去完善信息......")
            }
            show()
        }
    }
}