package com.wuc.designpattern.actual_combat.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.content.ContextCompat


/**
 * @author: wuc
 * @date: 2024/9/5
 * @desc: 网络工具类
 */
object NetWorkUtil {
    var num: Int = 0

    enum class NetworkType {
        NETWORK_WIFI,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN,  // 未知
        NETWORK_NO // 无网
    }

    /**
     * 打开网络设置界面
     *
     * 3.0以下打开设置界面
     */
    fun openWirelessSettings(context: Context) {
        if (Build.VERSION.SDK_INT > 10) {
            context.startActivity(
                Intent(Settings.ACTION_WIRELESS_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        } else {
            context.startActivity(
                Intent(Settings.ACTION_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    /**
     * 判断网络是否连接
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    fun isConnected(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    /**
     * 判断网络是否可用
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`
     *
     * @return `true`: 可用<br></br>`false`: 不可用
     */
    fun isAvailable(context: Context?): Boolean {
        return isConnected(context)
    }

    /**
     * 判断移动数据是否打开
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    fun getDataEnabled(context: Context): Boolean {
        return try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val getMobileDataEnabledMethod = tm.javaClass.getDeclaredMethod("getDataEnabled")
            getMobileDataEnabledMethod?.invoke(tm) as Boolean
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 判断网络是否是4G
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    fun is4G(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                && getMobileNetworkType(context) == TelephonyManager.NETWORK_TYPE_LTE
    }

    /**
     * 判断wifi是否打开
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>`
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    fun getWifiEnabled(context: Context): Boolean {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.isWifiEnabled
    }

    /**
     * 打开或关闭wifi
     *
     * 需添加权限 `<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>`
     *
     * @param enabled `true`: 打开<br></br>`false`: 关闭
     */
    fun setWifiEnabled(enabled: Boolean, context: Context) {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.isWifiEnabled = enabled
    }

    /**
     * 判断wifi是否连接状态
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`
     *
     * @return `true`: 连接<br></br>`false`: 未连接
     */
    fun isWifiConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    /**
     * 判断wifi数据是否可用
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>`
     * 需添加权限 `<uses-permission android:name="android.permission.INTERNET"/>`
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    fun isWifiAvailable(context: Context): Boolean {
        return getWifiEnabled(context) && isAvailable(context)
    }

    /**
     * 获取网络运营商名称
     *
     * @return 运营商名称
     */
    fun getNetworkOperatorName(context: Context): String? {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.networkOperatorName
    }

    /**
     * 获取当前网络类型
     *
     * @return 网络类型
     */
    fun getNetworkType(context: Context?): NetworkType {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return NetworkType.NETWORK_NO
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        if (networkCapabilities != null) {
            when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return NetworkType.NETWORK_WIFI
                }

                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    val networkType = getMobileNetworkType(context)
                    return when (networkType) {
                        TelephonyManager.NETWORK_TYPE_LTE -> NetworkType.NETWORK_4G
                        TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_HSPA,
                        TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA -> NetworkType.NETWORK_3G

                        TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE -> NetworkType.NETWORK_2G
                        else -> NetworkType.NETWORK_UNKNOWN
                    }
                }
            }
        }
        return NetworkType.NETWORK_NO
    }

    /**
     * 获取当前移动网络的类型
     */
    private fun getMobileNetworkType(context: Context): Int {
        // 检查权限是否被授予
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("NetWorkUtil", "Network permissions【Manifest.permission.READ_PHONE_STATE】 must be obtained")
            // 如果没有权限，返回一个默认值，或者可以提示用户申请权限
            return TelephonyManager.NETWORK_TYPE_UNKNOWN
        }
        return try {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            // Android 30及以上版本使用 getDataNetworkType
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                telephonyManager.dataNetworkType
            } else {
                // 低于Android 30版本使用 getNetworkType
                telephonyManager.networkType
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
            TelephonyManager.NETWORK_TYPE_UNKNOWN
        }
    }
}
