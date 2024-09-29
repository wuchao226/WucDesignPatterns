package com.wuc.baselib.log

import android.util.Log
import androidx.annotation.IntDef


/**
 * @author: wuc
 * @date: 2024/9/24
 * @desc: Log 日志类型
 */
class LogType {
    companion object {
        const val V: Int = Log.VERBOSE
        const val D: Int = Log.DEBUG
        const val I: Int = Log.INFO
        const val E: Int = Log.ERROR
        const val W: Int = Log.WARN
        const val A: Int = Log.ASSERT
    }
    @IntDef(V, D, I, E, W, A)
    @Retention(AnnotationRetention.SOURCE)
    annotation class TYPE()
}