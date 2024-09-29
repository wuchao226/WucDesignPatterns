package com.wuc.baselib.log

import android.app.Application
import android.os.Environment
import android.util.Log
import com.google.gson.Gson
import com.wuc.baselib.log.printer.ConsolePrinter
import com.wuc.baselib.log.printer.FilePrinter
import com.wuc.baselib.log.printer.LogPrinter
import java.util.concurrent.TimeUnit

/**
 * @author: wuc
 * @date: 2024/9/27
 * @desc: Log 管理类
 */
/**
 * LogManager 是一个单例类，用于管理日志配置和日志打印器。
 * @param getLogConfig 日志配置
 * @param logPrinters 日志打印器数组
 */
class LogManager private constructor(val config: LogConfig, logPrinters: Array<out LogPrinter>) {
    companion object {
        @Volatile
        private var instance: LogManager? = null

        /**
         * 获取 LogManager 实例
         * @return LogManager 实例
         * @throws IllegalStateException 如果 LogManager 未初始化
         */
        @JvmStatic
        fun getInstance(): LogManager {
            return instance
                ?: throw IllegalStateException("请在 Application 中初始化【LogManager.init(config, printers)】")
        }

        /**
         * 初始化 LogManager
         */
        @JvmStatic
        fun init(application: Application) {
            // 根据设备的存储状态来确定日志文件的存储路径
            val logPath = application.applicationContext.run {
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                    // /storage/emulated/0/Android/data/com.wuc.designpattern/files/log
                    getExternalFilesDir("log")?.absolutePath
                } else {
                    // /data/user/0/com.wuc.designpattern/files/log/
                    filesDir.absolutePath + "/log/"
                } ?: (filesDir.absolutePath + "/log/")
            }
            init(
                object : LogConfig() {
                    override fun injectJsonParser(): JsonParser {
                        return JsonParser { src -> Gson().toJson(src) }
                    }

                    override fun includeThread(): Boolean = true

                    override fun enable(): Boolean = true

                    override fun stackTraceDepth(): Int = 5
                },
                ConsolePrinter(),
                FilePrinter.getInstance(logPath, TimeUnit.DAYS.toMillis(7))
            )
        }

        /**
         * 初始化 LogManager
         * @param config 日志配置
         * @param printers 可变参数的日志打印器
         */
        @JvmStatic
        fun init(config: LogConfig, vararg printers: LogPrinter) {
            instance ?: synchronized(this) {
                instance ?: LogManager(config, printers).also { instance = it }
            }
        }
    }

    private val logPrinters: MutableList<LogPrinter> = logPrinters.toMutableList()

    /**
     * 获取日志配置
     * @return 日志配置
     */
    fun getLogConfig(): LogConfig = config

    /**
     * 获取日志打印器列表
     * @return 日志打印器列表
     */
    fun getLogPrinters(): List<LogPrinter>? = logPrinters

    /**
     * 添加日志打印器
     * @param printer 日志打印器
     */
    fun addPrinter(printer: LogPrinter) {
        logPrinters.add(printer)
    }

    /**
     * 移除日志打印器
     * @param printer 日志打印器
     */
    fun removePrinter(printer: LogPrinter) {
        logPrinters.remove(printer)
    }
}