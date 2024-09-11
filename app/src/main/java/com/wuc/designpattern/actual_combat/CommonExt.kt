package com.wuc.designpattern.actual_combat

import android.content.Context
import android.content.Intent

/**
 * @author: wuc
 * @date: 2024/9/9
 * @desc:
 */
/**
 * openActivity<TestActivity>(context) {
 *     putExtra("param1", "data")
 *     putExtra("param2", 123)
 * }
 */
inline fun <reified T> openActivity(context: Context, noinline block: (Intent.() -> Unit)? = null) {
    val intent = Intent(context, T::class.java)
    block?.invoke(intent)
    context.startActivity(intent)
}