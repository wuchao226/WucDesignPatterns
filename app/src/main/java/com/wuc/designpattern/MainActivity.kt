package com.wuc.designpattern

import android.content.Intent
import android.os.Bundle
import com.wuc.designpattern.actual_combat.BaseVBActivity
import com.wuc.designpattern.actual_combat.observe_network_change.NetWorkActivity
import com.wuc.designpattern.actual_combat.openActivity
import com.wuc.designpattern.actual_combat.utils.NetWorkUtil
import com.wuc.designpattern.databinding.ActivityMainBinding

class MainActivity : BaseVBActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnNet.setOnClickListener {
            openActivity<NetWorkActivity>(this@MainActivity)
        }
    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}