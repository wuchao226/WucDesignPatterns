package com.wuc.baselib.log.printer

import com.wuc.baselib.log.LogConfig

/**
 * @author: wuc
 * @date: 2024/9/24
 * @desc: 日志打印接口，基于该接口可以自定义日志打印方式
 */
interface LogPrinter {
    /**
     * @param config 日志配置
     * @param level 日志级别
     * @param tag tag
     * @param printStr 日志内容
     */
    fun print(config: LogConfig, level: Int, tag: String, printStr: String)
}