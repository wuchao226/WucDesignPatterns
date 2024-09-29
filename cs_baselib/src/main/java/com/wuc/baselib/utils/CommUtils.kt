package com.wuc.baselib.utils

import android.app.Application
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Process
import android.view.View
import androidx.core.content.ContextCompat

object CommUtils {
    private var mApplication: Application? = null
    var handler: Handler? = null
        private set
    var mainThreadId: Int = 0
        private set

    fun init(application: Application?, handler: Handler?, mainThreadId: Int) {
        mApplication = application
        CommUtils.handler = handler
        CommUtils.mainThreadId = mainThreadId
    }

    //---------------------初始化Application定义的方法-----------------------------------
    val context: Context
        get() = mApplication?.applicationContext
            ?: throw IllegalStateException("application is null. please initialize [CommUtils.init(this, Handler(Looper.getMainLooper()), android.os.Process.myTid())].")


    //------------------------获取各种资源----------------------------------------
    fun getString(id: Int): String {
        return context.resources.getString(id)
    }

    fun getStringArray(id: Int): Array<String> {
        return context.resources.getStringArray(id)
    }

    fun getIntArray(id: Int): IntArray {
        return context.resources.getIntArray(id)
    }

    fun getDrawable(id: Int): Drawable? {
        return ContextCompat.getDrawable(context, id)
    }

    fun getColor(id: Int): Int {
        return ContextCompat.getColor(context, id)
    }

    fun getColorStateList(id: Int): ColorStateList? {
        return ContextCompat.getColorStateList(context, id)
    }

    fun getDimens(id: Int): Int {
        return context.resources.getDimensionPixelSize(id)
    }

    //--------------------px和dip之间的转换-----------------------------------------------
    fun dip2px(dip: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dip * density + 0.5f).toInt()
    }

    fun px2dip(px: Int): Float {
        val density = context.resources.displayMetrics.density
        return px / density
    }

    fun dip2px(dip: Float): Float {
        val density = context.resources.displayMetrics.density
        return (dip * density + 0.5f)
    }

    fun px2dip(px: Float): Float {
        val density = context.resources.displayMetrics.density
        return px / density
    }


    //-------------------加载布局文件-------------------------------------------------
    fun inflate(id: Int): View {
        return View.inflate(context, id, null)
    }

    //-------------------是否运行在主线程 -----------------------------------------------
    val isRunOnUIThread: Boolean
        get() = Process.myTid() == mainThreadId

    //运行在主线程
    fun runOnUIThread(runnable: Runnable) {
        if (isRunOnUIThread) {
            runnable.run()
        } else {
            handler?.post(runnable)
        }
    }
}
