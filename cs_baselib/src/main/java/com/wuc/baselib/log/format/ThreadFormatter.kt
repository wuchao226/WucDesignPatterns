package com.wuc.baselib.log.format

/**
 * @author: wuc
 * @date: 2024/9/25
 * @desc: 线程格式化
 */
class ThreadFormatter: LogFormatter<Thread> {
    override fun format(data: Thread): String {
        return "Thread: ${data.name}"
    }
}