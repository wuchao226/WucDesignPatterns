package com.wuc.designpattern.actual_combat.observe_network_change

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.wuc.designpattern.actual_combat.utils.NetWorkUtil

/**
 * @author: wuc
 * @date: 2024/9/9
 * @desc:
 */
class NetConnectCallback(private val context: Context, private val onNetworkChanged: (NetWorkUtil.NetworkType) -> Unit) : ConnectivityManager.NetworkCallback() {

    /**
     * 网络可用的回调连接成功
     */
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        Log.i(NetWorkMonitorManager.TAG, "Network Available")
        onNetworkChanged(NetWorkUtil.getNetworkType(context))
    }

    /**
     * 当网络已断开连接时调用
     * 网络不可用时调用和onAvailable成对出现
     */
    override fun onLost(network: Network) {
        super.onLost(network)
        Log.i(NetWorkMonitorManager.TAG, "Network Lost 网络已断开连接")
        //此处遇到问题，开启开发者选项“始终开启移动数据网络”时，在开启数据时打开wifi，最终呈现是网络断开，但是能用NetworkCapabilities检测到wifi
        //查看文档发现onLost()方法是在有`network`断开而并非所有`network`都断开时，即有任意网络连接断开时即触发。
        //onAvailable()方法是在有新的`network`可用时触发。
        //经检测当wifi和移动网络同时连接时，先连接wifi，触发onAvailable()方法，但移动网络要过30s左右才断开，所以会触发onLost()方法。

        //另有开启开发者选项“始终开启移动数据网络”时，数据WiFi双开时关闭WiFi后先调用`onAvailable()`方法后调用`onLost()`方法，且`NetworkCapabilities`为null的情况。
        //结果发现出现此Bug的原因是关闭WiFi时刚好是之前讲的连接了WiFi后和断开移动网络前，所以只会调用`onLost()`方法。

        //此处整理一下开启“始终开启移动数据网络”与否的双开网络变化情况。
        //开启时：开着数据开WiFi时：先连接WiFi（但测试是数据），过约30s后断开数据。 双开关闭WiFi时：依据关WiFi的时机而定，在前述的30s内会无网络，30s之后马上切换至数据。
        //关闭时：开着数据开WiFi时：先连接WiFi，再马上关闭数据。 双开关闭WiFi时：先关闭WiFi，再连接数据。
//        if (connMgr.getNetworkCapabilities(connMgr.getActiveNetwork()) == null) sendConnInfo(CONN_UNAVAILABLE);
        if (!NetWorkUtil.isAvailable(context)) {
            Log.i(NetWorkMonitorManager.TAG, "Network Lost 网络已断开连接，完全没有网络，通知网络观察者")
            onNetworkChanged(NetWorkUtil.NetworkType.NETWORK_NO)
        } else {
            Log.i(NetWorkMonitorManager.TAG, "Network Lost 网络已断开连接，部分网络可用，通知网络观察者")
            onNetworkChanged(NetWorkUtil.getNetworkType(context))
        }
    }

    /**
     * 当网络正在断开连接时调用
     */
    override fun onLosing(network: Network, maxMsToLive: Int) {
        super.onLosing(network, maxMsToLive)
        Log.i(NetWorkMonitorManager.TAG, "Network onLosing 网络正在断开")
    }

    /**
     * 当网络状态修改但仍旧是可用状态时调用
     */
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        val isWifi = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        val isCellular = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        val isValidated = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

        Log.d(NetWorkMonitorManager.TAG,
            "Network onCapabilitiesChanged 可正常访问网络 = $isValidated " +
                    "& 数据连接 = $isCellular " +
                    "& wifi连接= $isWifi"
        )
        if (isWifi && !isValidated) {
            // Wi-Fi 状态更新不准确，进一步处理
            Log.d(NetWorkMonitorManager.TAG, "Wi-Fi 可能未连接到互联网")
        } else if (isCellular && !isValidated) {
            // 移动数据状态更新不准确，进一步处理
            Log.d(NetWorkMonitorManager.TAG, "移动数据可能未连接到互联网")
        }
        onNetworkChanged(NetWorkUtil.getNetworkType(context))
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