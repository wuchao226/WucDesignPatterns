package com.wuc.designpattern.actual_combat.observe_network_change

import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log

/**
 * @author: wuc
 * @date: 2024/9/9
 * @desc:
 */
class NetConnectCallback(private val onNetworkChanged: () -> Unit) : ConnectivityManager.NetworkCallback() {

    /**
     * 网络可用的回调连接成功
     */
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        Log.i(NetWorkMonitorManager.TAG, "Network Available")
        onNetworkChanged()
    }

    /**
     * 当网络已断开连接时调用
     * 网络不可用时调用和onAvailable成对出现
     */
    override fun onLost(network: Network) {
        super.onLost(network)
        Log.i(NetWorkMonitorManager.TAG, "Network Lost")
        onNetworkChanged()
    }

    /**
     * 当网络正在断开连接时调用
     */
    override fun onLosing(network: Network, maxMsToLive: Int) {
        super.onLosing(network, maxMsToLive)
        Log.i(NetWorkMonitorManager.TAG, "Network onLosing")
    }

    /**
     * 当网络状态修改但仍旧是可用状态时调用
     */
    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        Log.d(NetWorkMonitorManager.TAG,
            "Network 可正常访问网络 = ${networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)} " +
                    "& 数据连接 = ${networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)} " +
                    "& wifi连接= ${networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)}"
        )
        onNetworkChanged()
    }

    /**
     * 当网络连接超时或网络请求达不到可用要求时调用
     */
    override fun onUnavailable() {
        super.onUnavailable()
        Log.i(NetWorkMonitorManager.TAG, "Network onUnavailable")
    }

    /**
     * 当访问指定的网络被阻止或解除阻塞时调用
     */
    override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
        super.onBlockedStatusChanged(network, blocked)
        Log.i(NetWorkMonitorManager.TAG, "Network onBlockedStatusChanged, blocked = $blocked")
    }

    /**
     * 当网络连接的属性被修改时调用
     */
    override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties)
        Log.i(NetWorkMonitorManager.TAG, "Network onLinkPropertiesChanged")
    }
}