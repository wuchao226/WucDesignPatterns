package com.wuc.designpattern

import android.os.Bundle
import com.wuc.designpattern.actual_combat.base.activity.BaseViewBindingActivity
import com.wuc.designpattern.actual_combat.observe_network_change.NetWorkActivity
import com.wuc.designpattern.actual_combat.ext.openActivity
import com.wuc.designpattern.databinding.ActivityMainBinding

class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {

    override fun createViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.btnNet.setOnClickListener {
            openActivity<NetWorkActivity>(this@MainActivity)
        }
    }
}