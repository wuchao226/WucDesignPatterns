package com.wuc.baselib.ext

/**
 * @author: wuc
 * @date: 2024/9/13
 * @desc:
 */

inline fun <reified T> Any.saveAs(): T {
    return this as T
}

@Suppress("UNCHECKED_CAST")
fun <T> Any.saveAsUnChecked(): T {
    return this as T
}

inline fun <reified T> Any.isEqualType(): Boolean {
    return this is T
}