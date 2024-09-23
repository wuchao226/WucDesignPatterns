package com.wuc.baselib.network_intercept

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import com.wuc.baselib.utils.NetWorkUtil

/**
 * @author: wuc
 * @date: 2024/9/5
 * @desc: 监听网络变换的广播
 *
 */
class NetConnectReceiver(private val onNetworkChanged: (NetWorkUtil.NetworkType) -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
            Log.d(NetWorkMonitorManager.TAG, "Network state changed")
            onNetworkChanged(NetWorkUtil.getNetworkType(context))
        }
    }
}