package com.wuc.baselib.log

import com.wuc.baselib.log.format.StackTraceFormatter
import com.wuc.baselib.log.format.ThreadFormatter
import com.wuc.baselib.log.printer.LogPrinter

/**
 * @desc: Log 配置类
 */
abstract class LogConfig {
    companion object {
        // 日志格式化时，每一行显示的最大长度
        const val MAX_LEN = 512

        // 懒汉方式创建单例
        val stackTraceFormatter by lazy { StackTraceFormatter() }
        val threadFormatter by lazy { ThreadFormatter() }
    }

    /**
     * 通过配置 完成 JSON 序列化器的注入，默认没有任何实现
     *
     * @return JsonParser 默认Gson序列化器
     */
    open fun injectJsonParser(): JsonParser? = null

    /**
     * 全局 tag，不设置 tag 时使用默认实现
     *
     * @return String
     */
    open fun getGlobalTag(): String = "WLog"

    /**
     * 日志是否启用
     *
     * @return Boolean
     */
    open fun enable(): Boolean = true

    /**
     * 是否包含线程信息，默认 false
     *
     * @return Boolean
     */
    open fun includeThread(): Boolean = false

    /**
     * 栈日志里携带堆栈信息的深度
     *
     * @return int
     */
    open fun stackTraceDepth(): Int = 5

    /**
     * 日志打印器
     *
     * @return LogPrinter[]
     */
    open fun printers(): Array<LogPrinter>? = null

    /**
     * Log 日志序列化接口
     */
    fun interface JsonParser {
        fun toJson(src: Any): String
    }
}