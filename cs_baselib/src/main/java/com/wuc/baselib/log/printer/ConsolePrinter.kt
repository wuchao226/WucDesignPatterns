package com.wuc.baselib.log.printer

import android.util.Log
import com.wuc.baselib.log.LogConfig
import com.wuc.baselib.log.LogConfig.Companion.MAX_LEN
import kotlin.math.log

/**
 * @author: wuc
 * @date: 2024/9/26
 * @desc: 控制台打印器
 */
/**
 * ConsolePrinter 类实现了 LogPrinter 接口，用于将日志打印到控制台。
 */
class ConsolePrinter : LogPrinter {
    /**
     * 打印日志的方法。
     * @param config 日志配置
     * @param level 日志级别
     * @param tag 日志标签
     * @param printStr 要打印的日志字符串
     */
    override fun print(config: LogConfig, level: Int, tag: String, printStr: String) {
        val len = printStr.length
        // 计算日志的行数，MAX_LEN 表示每一行显示的最大长度
        val countOfSub = len / MAX_LEN
        if (countOfSub > 0) {
            // 如果日志长度超过一行，使用 StringBuilder 拼接日志
            val logSb = StringBuilder()
            var index = 0
            for (i in 0 until countOfSub) {
                // 将日志按 MAX_LEN 分割并拼接
                logSb.append(printStr.substring(index, index + MAX_LEN))
                index += MAX_LEN
            }
            if (index != len) {
                // 拼接剩余的日志内容
                logSb.append(printStr.substring(index, len))
            }
            // 打印拼接后的日志
            Log.println(level, tag, logSb.toString())
        } else {
            // 如果日志长度不超过一行，直接打印
            Log.println(level, tag, printStr)
        }
    }
}