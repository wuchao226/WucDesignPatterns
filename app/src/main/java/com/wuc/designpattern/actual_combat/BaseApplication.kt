package com.wuc.designpattern.actual_combat

import android.app.Application
import com.wuc.designpattern.actual_combat.observe_network_change.NetWorkMonitorManager
import com.wuc.designpattern.actual_combat.utils.NetWorkUtil

/**
 * @author: wuc
 * @date: 2024/9/5
 * @desc: 基类的Application
 */
open class BaseApplication: Application() {

    companion object {
        //此变量会在网络监听中被动态赋值
        lateinit var networkType: NetWorkUtil.NetworkType
        //检查当前是否有网络
        fun checkHasNet(): Boolean {
            return networkType!= NetWorkUtil.NetworkType.NETWORK_NO && networkType!= NetWorkUtil.NetworkType.NETWORK_UNKNOWN
        }
    }
    override fun onCreate() {
        super.onCreate()
        //获取到全局的网络状态
        networkType = NetWorkUtil.getNetworkType(this@BaseApplication.applicationContext)
        //网络监听
        NetWorkMonitorManager.init(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            // 应用进入后台时解除网络监听
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        // 当系统内存紧张时解除网络监听
    }

    /**
     * 在真实设备上通常不会被调用，因此依赖它来解除网络监听器是不安全的
     */
    override fun onTerminate() {
        super.onTerminate()
        //网络监听解除
    }
}