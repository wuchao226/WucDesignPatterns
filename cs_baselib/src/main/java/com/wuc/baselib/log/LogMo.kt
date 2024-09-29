package com.wuc.baselib.log

import java.text.SimpleDateFormat
import java.util.Locale

/**
 * @author: wuc
 * @date: 2024/9/25
 * @desc: Log 的日志 model
 */
class LogMo(
    // 日志 发生时间
    val timeMillis: Long,
    // 日志级别 V/E/W/I/D/A
    val level: Int,
    // 日志 TAG
    val tag: String,
    // 日志内容
    val log: String,
) {
    companion object {
        private val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
    }
    fun flattenLog(): String {
        return getFlattened() + "\n" + log
    }

    fun getFlattened(): String {
        return format(timeMillis) + "|" + level + "|" + tag + "|:"
    }
    fun format(timeMillis: Long): String {
        return sdf.format(timeMillis)
    }
}