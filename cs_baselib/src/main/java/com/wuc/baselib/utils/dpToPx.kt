@file:JvmName("DisplayUtil")
@file:JvmMultifileClass

package com.wuc.baselib.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.Dimension

fun dpToPx(dpValue: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dpValue,
        CommUtils.context.resources.displayMetrics
    )
}

fun dpToPx(dpValue: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dpValue.toFloat(),
        CommUtils.context.resources.displayMetrics
    ).toInt()
}

fun dpToPx(context: Context, @Dimension(unit = Dimension.DP) dp: Int): Float {
    val r = context.resources
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics)
}


fun dp2px(dp: Float, resources: Resources): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
}

/**
 * 将dp转换为与之相等的px
 */
fun dp2px(dp: Float): Int {
    val resources = AppGlobals.getApplication().resources
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
}

/**
 * 将px转换为与之相等的dp
 */
fun px2dp(pxValue: Float): Int {
    val resources = AppGlobals.getApplication().resources
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * 将sp转换为px
 */
fun sp2px(spValue: Float): Int {
    val resources = AppGlobals.getApplication().resources
    val fontScale = resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}

/**
 * 将px转换为sp
 */
fun px2sp(pxValue: Float): Int {
    val resources = AppGlobals.getApplication().resources
    val fontScale = resources.displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}

/**
 * 所有字体均使用dp
 */
fun spToPx(spValue: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        spValue,
        CommUtils.context.resources.displayMetrics
    )
}