package com.wuc.designpattern.actual_combat.popup_interceptor.actual

import android.util.Log
import com.wuc.baselib.view.FangIOSDialog
import com.wuc.designpattern.actual_combat.popup_interceptor.BaseInterceptImpl
import com.wuc.designpattern.actual_combat.popup_interceptor.InterceptChain
/**
 * @author: wuc
 * @date: 2024/9/23
 * @desc: 具体的拦截器一，新用户的拦截
 */
class InterceptNewMember(private val bean: JobInterceptBean): BaseInterceptImpl() {
    override fun intercept(chain: InterceptChain) {
        super.intercept(chain)
        if (bean.isNewMember) {
            //拦截
            //可以不弹窗，直接就暴力跳转新页面
            Log.d("intercept", "新用户的拦截......")
            showDialogTips(chain)
        } else {
            //放行- 转交给下一个拦截器
            chain.process()
        }
    }
    // 已经完成了培训-放行
    fun resetNewMember() {
        mChain?.process()
    }

    private fun showDialogTips(chain: InterceptChain) {
        FangIOSDialog(chain.activity).apply {
            setTitle("新用户培训")
            setMessage("你要去完成培训吗？")
            setNegativeButton("跳过") {
                dismiss()
                chain.process()
            }
            setPositiveButton("去培训") {
                Log.d("intercept", "去完成新用户培训......")
                resetNewMember()
            }
            show()
        }
    }
}