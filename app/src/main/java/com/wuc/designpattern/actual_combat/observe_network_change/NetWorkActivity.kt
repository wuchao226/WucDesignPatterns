package com.wuc.designpattern.actual_combat.observe_network_change

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.wuc.designpattern.R
import com.wuc.baselib.base.BaseApplication
import com.wuc.baselib.base.activity.BaseViewBindingReflectActivity
import com.wuc.baselib.utils.NetWorkUtil
import com.wuc.designpattern.databinding.ActivityNetWorkBinding

class NetWorkActivity : BaseViewBindingReflectActivity<ActivityNetWorkBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        mBinding.txtNet.text = "checkHasNet = ${BaseApplication.checkHasNet()}, networkType = ${BaseApplication.networkType}"
    }

    override fun needRegisterNetworkChangeObserver(): Boolean {
        return true
    }

    @SuppressLint("SetTextI18n")
    override fun onNetworkConnectionChanged(isConnected: Boolean, networkType: NetWorkUtil.NetworkType) {
        Log.d(
            "NetWorkActivity",
            "onNetworkConnectionChanged, isConnected = $isConnected, networkType = ${networkType.name}"
        )
        Toast.makeText(this, "网络状态发生了变化：当前网络类型：$networkType", Toast.LENGTH_SHORT).show()
        mBinding.txtNet.text = "isConnected = $isConnected, networkType = ${networkType.name}"
    }
}