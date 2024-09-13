package com.wuc.designpattern.actual_combat.observe_network_change

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.wuc.designpattern.actual_combat.BaseApplication
import com.wuc.designpattern.actual_combat.utils.NetWorkUtil

/**
 * @author: wuc
 * @date: 2024/9/5
 * @desc: 监听网络变换, 需要在Application中注册 NetworkMonitorManager.init()
 * https://juejin.cn/post/7107833324059492383
 */
object NetWorkMonitorManager {
    const val TAG = "NetworkMonitorManager"
    private var mConnectivityManager: ConnectivityManager? = null

    // Observer的通知集合对象，本质上是接口回调
    private val observers: MutableList<NetworkConnectionChangedListener> = mutableListOf()
    private var lastTimeMillis: Long = 0L
    private var lastType: NetWorkUtil.NetworkType = NetWorkUtil.NetworkType.NETWORK_UNKNOWN

    // 默认网络防抖时间
    private const val DEFAULT_JITTER_TIME: Long = 1500L
    private var mJitterTime: Long = DEFAULT_JITTER_TIME

    private var mApplication: Application? = null
    private val mainHandler: Handler = Handler(Looper.getMainLooper())

    /**
     * 初始化
     * @param application 上下文
     * @param jitterTime 设置抖动时间
     */
    fun init(application: Application, jitterTime: Long = DEFAULT_JITTER_TIME) {
        mApplication = application
        mJitterTime = jitterTime.coerceAtLeast(0L)
        initMonitor(application)
    }

    private fun initMonitor(application: Application) {
        mConnectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager?

        val networkCallback = NetConnectCallback(application.applicationContext) {
            postNetworkState(it)
        }

        val networkReceiver = NetConnectReceiver {
            postNetworkState(it)
        }
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                // 如果Android版本等于7.0(API 24)或以上
                // 监听“系统默认网络”，所以可以实现网络状态与类型的判断，但都存在重复回调的情况，所以要做过滤处理，以及“系统默认网络”切换到普通网络时会有偶现短暂“无网络”状态，需要做延迟处理。
                mConnectivityManager?.registerDefaultNetworkCallback(networkCallback)
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                // 监听所有网络变换的，监听范围广，但无法得知当前“系统默认网络”是什么，可以实现判断网络状态，但无法判断网络类型
                mConnectivityManager?.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
            }

            else -> {
                // 广播监听, 监听“系统默认网络”，所以可以实现网络状态与类型的判断，但都存在重复回调的情况，所以要做过滤处理，以及“系统默认网络”切换到普通网络时会有偶现短暂“无网络”状态，需要做延迟处理。
                val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                application.registerReceiver(networkReceiver, intentFilter)
            }
        }
    }

    private fun postNetworkState(networkType: NetWorkUtil.NetworkType) {
        val currentTimeMillis = System.currentTimeMillis()
        Log.w(TAG, "postNetworkState>> 网络状态networkType = [$networkType], lastType = [$lastType]")
        Log.w(TAG, "postNetworkState>> 是否应通知网络变化 ${shouldNotifyNetworkChange(networkType, currentTimeMillis)}")
        if (shouldNotifyNetworkChange(networkType, currentTimeMillis)) {
            doNotifyObserver(networkType)
        } else {
            // 防抖动，如果两次相同的网络状态发生在[jitterTime/1000]秒之内，那么只会触发一次
            Log.w(TAG, "postNetworkState>> 防抖动，两次相同的网络状态[$networkType]发生在[${mJitterTime.toFloat()/1000}]秒之内，那么只会触发一次")
        }
        // 重新赋值最后一次的网络类型和时间戳
        lastType = networkType
        lastTimeMillis = currentTimeMillis
    }

    /**
     * 判断是否应通知网络变化，防止抖动
     */
    private fun shouldNotifyNetworkChange(networkType: NetWorkUtil.NetworkType, currentTimeMillis: Long): Boolean {
        return if (lastType == networkType) {
            currentTimeMillis - lastTimeMillis > mJitterTime
        } else {
            true
        }
    }

    /**
     * 具体去执行通知
     */
    private fun doNotifyObserver(networkType: NetWorkUtil.NetworkType) {
        // 收到变换网络的通知就通过遍历集合去循环回调接口
        notifyObservers(networkType)
        //赋值Application全局的类型
        BaseApplication.networkType = networkType
    }

    /**
     * 通知所有观察者Observer网络状态变化
     */
    private fun notifyObservers(networkType: NetWorkUtil.NetworkType) {
        mainHandler.post {
            val isConnected =
                networkType != NetWorkUtil.NetworkType.NETWORK_NO && networkType != NetWorkUtil.NetworkType.NETWORK_UNKNOWN
            for (observer in synchronized(this) { observers.toList() }) {
                observer.onNetworkConnectionChanged(isConnected, networkType)
            }
        }
    }

    /**
     * 注册网络变化Observer
     */
    fun registerObserver(observer: NetworkConnectionChangedListener?) {
        observer?.let {
            synchronized(this) {
                if (!observers.contains(it)) {
                    observers.add(it)
                }
            }
        }
    }

    /**
     * 取消网络变化Observer的注册
     */
    fun unregisterObserver(observer: NetworkConnectionChangedListener?) {
        observer?.let {
            synchronized(this) {
                observers.remove(it)
            }
        }
    }

    /**
     * 观察者模式的通知接口
     * 通过这个接口回调出去
     */
    interface NetworkConnectionChangedListener {
        fun onNetworkConnectionChanged(isConnected: Boolean, networkType: NetWorkUtil.NetworkType) {}
    }
}