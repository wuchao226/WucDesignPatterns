package com.wuc.designpattern.actual_combat

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.wuc.designpattern.actual_combat.observe_network_change.NetWorkMonitorManager
import com.wuc.designpattern.actual_combat.utils.NetWorkUtil

/**
 * @author: wuc
 * @date: 2024/9/9
 * @desc:最底层的Activity,不带MVP和MVVM,一般不用这个
 */
abstract class AbsActivity : AppCompatActivity(), NetWorkMonitorManager.NetworkConnectionChangedListener {
    private val TAG: String
        get() = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (needRegisterNetworkChangeObserver()) {
            NetWorkMonitorManager.registerObserver(this@AbsActivity)
        }
    }

    /**
     * 是否需要注册监听网络变换
     */
    protected open fun needRegisterNetworkChangeObserver(): Boolean {
        return false
    }

    private val networkObserver = object : DefaultLifecycleObserver {
        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
            Log.d("NetWorkActivity", "$TAG lifecycle>> onStart ${needRegisterNetworkChangeObserver()}")
            if (needRegisterNetworkChangeObserver()) {
                NetWorkMonitorManager.registerObserver(this@AbsActivity)
            }
        }

        override fun onStop(owner: LifecycleOwner) {
            super.onStop(owner)
            Log.d("NetWorkActivity", "$TAG lifecycle>> onStop ${needRegisterNetworkChangeObserver()}")
        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            Log.d("NetWorkActivity", "$TAG lifecycle>> onDestroy")
            if (needRegisterNetworkChangeObserver()) {
                NetWorkMonitorManager.unregisterObserver(this@AbsActivity)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (needRegisterNetworkChangeObserver()) {
            NetWorkMonitorManager.unregisterObserver(this@AbsActivity)
        }
    }
}