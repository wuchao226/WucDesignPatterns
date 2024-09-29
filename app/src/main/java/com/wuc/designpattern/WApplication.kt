package com.wuc.designpattern

import android.os.Environment
import com.wuc.baselib.base.BaseApplication
import com.wuc.baselib.log.LogManager

/**
 * @author: wuc
 * @date: 2024/9/29
 * @desc:
 */
class WApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        // 根据设备的存储状态来确定日志文件的存储路径
        val logPath = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            // 如果外部存储已挂载，则尝试获取外部存储中的 "log" 目录的绝对路径
            this.applicationContext.getExternalFilesDir("log")?.absolutePath
            // 如果获取失败，则使用内部存储中的 "log" 目录的绝对路径
                ?: (this.applicationContext.filesDir.absolutePath + "/log/")
        } else {
            // 如果外部存储未挂载，则直接使用内部存储中的 "log" 目录的绝对路径
            this.applicationContext.filesDir.absolutePath + "/log/"
        }
        LogManager.init(this)
        /*LogManager.init(
            object : LogConfig() {
                override fun injectJsonParser(): JsonParser {
                    return JsonParser { src -> Gson().toJson(src) }
                }

                override fun includeThread(): Boolean = true

                override fun enable(): Boolean = true
            },
            ConsolePrinter(),
            FilePrinter.getInstance(logPath, TimeUnit.DAYS.toMillis(7))
        )*/
    }
}