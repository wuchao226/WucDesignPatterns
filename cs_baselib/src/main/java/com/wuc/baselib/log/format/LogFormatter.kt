package com.wuc.baselib.log.format

/**
 * @author: wuc
 * @date: 2024/9/25
 * @desc: 日志格式化接口
 */
interface LogFormatter<T> {
    fun format(data: T): String
}