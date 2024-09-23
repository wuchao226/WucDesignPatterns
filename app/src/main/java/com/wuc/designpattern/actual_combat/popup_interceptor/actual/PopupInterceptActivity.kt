package com.wuc.designpattern.actual_combat.popup_interceptor.actual

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.wuc.baselib.base.activity.BaseViewBindingReflectActivity
import com.wuc.baselib.utils.CommUtils
import com.wuc.designpattern.R
import com.wuc.designpattern.actual_combat.popup_interceptor.PopupInterceptChain
import com.wuc.designpattern.databinding.ActivityPopupInterceptBinding

class PopupInterceptActivity : BaseViewBindingReflectActivity<ActivityPopupInterceptBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.btnPopupIntercept.setOnClickListener {
            showLoading()
            CommUtils.getHandler().postDelayed({
               dismissLoading()

                val bean = JobInterceptBean(true, false, false, false, true, true, true, true, true, true)

                createIntercept(bean)
            }, 1500)
        }
    }
    //创建拦截弹窗
    private fun createIntercept(bean: JobInterceptBean) {
        val chain = PopupInterceptChain.create(4)
            .attach(this@PopupInterceptActivity)
            .addInterceptor(InterceptNewMember(bean))
            .addInterceptor(InterceptFillInfo(bean))
            .addInterceptor(InterceptMemberApprove(bean))
            .addInterceptor(InterceptSkill(bean))
            .build()

        //开始执行
        chain.process()
    }
}