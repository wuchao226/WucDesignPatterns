package com.wuc.designpattern

import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import com.wuc.baselib.base.activity.BaseViewBindingActivity
import com.wuc.baselib.ext.openActivity
import com.wuc.baselib.ext.toast
import com.wuc.baselib.log.WLogUtils
import com.wuc.baselib.utils.AppExit
import com.wuc.baselib.utils.CommUtils
import com.wuc.basiclib.R
import com.wuc.designpattern.actual_combat.observe_network_change.NetWorkActivity
import com.wuc.designpattern.actual_combat.popup_engine.PopupEngineActivity
import com.wuc.designpattern.actual_combat.popup_interceptor.actual.PopupInterceptActivity
import com.wuc.designpattern.databinding.ActivityMainBinding

class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {

    override fun createViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.btnNet.setOnClickListener {
            openActivity<NetWorkActivity>(this@MainActivity)
        }
        mBinding.btnPopup.setOnClickListener {
            openActivity<PopupEngineActivity>(this@MainActivity)
        }
        mBinding.btnPopupIntercept.setOnClickListener {
            openActivity<PopupInterceptActivity>(this@MainActivity)
        }
        mBinding.btnLog.setOnClickListener {
            WLogUtils.e("log test-------------------")
        }
    }

    override fun onBackPressed() {
        AppExit.onBackPressed(this, tipCallback = {
            toast(getString(R.string.base_app_exit_one_more_press))
        }, exitCallback = {})
    }
}