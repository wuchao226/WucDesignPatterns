package com.wuc.designpattern

import android.app.ProgressDialog.show
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import com.wuc.baselib.base.activity.BaseViewBindingActivity
import com.wuc.baselib.ext.openActivity
import com.wuc.baselib.ext.toast
import com.wuc.baselib.log.WLogUtils
import com.wuc.baselib.utils.AppExit
import com.wuc.baselib.utils.CommUtils
import com.wuc.baselib.view.FangIOSDialog
import com.wuc.basiclib.R
import com.wuc.designpattern.actual_combat.observe_network_change.NetWorkActivity
import com.wuc.designpattern.actual_combat.popup_engine.PopupEngineActivity
import com.wuc.designpattern.actual_combat.popup_interceptor.actual.PopupInterceptActivity
import com.wuc.designpattern.actual_combat.strategy.JobCheck
import com.wuc.designpattern.actual_combat.strategy.RuleExecute
import com.wuc.designpattern.actual_combat.strategy.rule_strategy.AgeRule
import com.wuc.designpattern.actual_combat.strategy.rule_strategy.COVIDRule
import com.wuc.designpattern.actual_combat.strategy.rule_strategy.FillProfileRule
import com.wuc.designpattern.actual_combat.strategy.rule_strategy.GenderRule
import com.wuc.designpattern.actual_combat.strategy.rule_strategy.LanguageRule
import com.wuc.designpattern.actual_combat.strategy.rule_strategy.NationalityRule
import com.wuc.designpattern.actual_combat.strategy.rule_strategy.VisaRule
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
        mBinding.btnStrategy.setOnClickListener {
            strategyTest()
        }
    }
    // 策略模式实战 and or
    fun strategyTest() {
        val jobCheck = JobCheck(
            "S9876543A", "Singapore", "Singapore", 20F,
            3.5F, false, false,
            "Chinese", 1, "25", true,
            false
        )

        jobCheck.hasCovidTest = true
        val covidRule = COVIDRule {
            //如果不满足新冠，首先就被排除了
            //如果没有新冠 - 弹窗提示他
            FangIOSDialog(this).apply {
                setTitle("你没有新冠表单")
                setMessage("老哥你快去做核酸吧,老哥你快去做核酸吧,老哥你快去做核酸吧")
                setPositiveButton("好的") {
                    dismiss()
                }
                setNegativeButton("就不去") {
                    dismiss()
                }
                show()
            }

        }  //false
        val ageRule = AgeRule()  //true
        val fillProfileRule = FillProfileRule()  //true
        val genderRule = GenderRule(0)  //true
        val languageRule = LanguageRule(listOf("Chinese", "English"))   //true
        val nationalityRule = NationalityRule(listOf("China", "Malaysia"))   //true
        val visaRule = VisaRule()   //true

        //构建规则执行器
        val result = RuleExecute.create(jobCheck)
            .and(mutableListOf(covidRule))
            .or(mutableListOf(nationalityRule, visaRule))
            .and(mutableListOf(ageRule, fillProfileRule, genderRule, languageRule))
            .build()
            .execute()

        WLogUtils.w("执行规则器的结果：$result")
        toast("执行规则器的结果：$result")
    }

    override fun onBackPressed() {
        AppExit.onBackPressed(this, tipCallback = {
            toast(getString(R.string.base_app_exit_one_more_press))
        }, exitCallback = {})
    }
}