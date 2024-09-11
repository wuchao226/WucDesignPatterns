package com.wuc.designpattern.actual_combat.observe_network_change

import com.wuc.designpattern.actual_combat.utils.NetWorkUtil
import java.lang.reflect.Method

/**
 * @author: wuc
 * @date: 2024/9/5
 * @desc: 保存符合条件的网络监听注解的对象
 */
data class NetworkMethodState(
    //参数类型
    var type: Class<*>? = null,
    //网络类型
    var networkType: NetWorkUtil.NetworkType? = null,
    //方法对象
    var method: Method? = null
)
