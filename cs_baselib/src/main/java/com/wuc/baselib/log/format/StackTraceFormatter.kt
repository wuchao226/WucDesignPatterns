package com.wuc.baselib.log.format


/**
 * @author: wuc
 * @date: 2024/9/25
 * @desc: 堆栈信息日志格式化
 */
class StackTraceFormatter: LogFormatter<Array<StackTraceElement>> {
    override fun format(stackTrace: Array<StackTraceElement>): String {
        // 初始化一个容量为128字符的StringBuilder
        val sb = StringBuilder(128)
        return when {
            stackTrace.isEmpty() -> ""
            stackTrace.size == 1 -> {
                // 如果堆栈信息只有一个元素，返回格式化后的单个元素字符串
                // \t─ 横向制表符 8为缩进
                "\t─ ${stackTrace[0]}"
            }
            else -> {
                // 如果堆栈信息有多个元素，逐个格式化并拼接到StringBuilder中
                for (index in stackTrace.indices) {
                    if (index == 0) {
                        sb.append("stackTrace:  \n")
                    }
                    if (index != stackTrace.size - 1) {
                        sb.append("\t├ ")
                        sb.append(stackTrace[index].toString())
                        sb.append("\n")
                    } else {
                        sb.append("\t└ ")
                        sb.append(stackTrace[index].toString())
                    }
                }
                sb.toString()
            }
        }
    }
}