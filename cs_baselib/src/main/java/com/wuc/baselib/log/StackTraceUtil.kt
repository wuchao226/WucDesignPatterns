package com.wuc.baselib.log

/**
 * @author: wuc
 * @date: 2024/9/27
 * @desc: Log 堆栈工具类
 */
object StackTraceUtil {

    /**
     * 获取真实的堆栈信息，并根据最大深度进行裁剪。
     *
     * @param stackTrace 完整的堆栈信息
     * @param maxDepth 要裁剪的最大深度，0 表示不限制
     * @return 裁剪后的真实堆栈信息
     */
    fun getCroppedRealStackTrack(
        stackTrace: Array<StackTraceElement>,
        ignorePackage: String,
        maxDepth: Int
    ): Array<StackTraceElement> {
        return cropStackTrace(getRealStackTrack(stackTrace, ignorePackage), maxDepth)
    }

    /**
     * 获取除忽略包名之外的堆栈信息
     *
     * 该方法会遍历提供的堆栈信息数组 stackTrace，并删除所有来自指定忽略包名 ignorePackage 的元素。
     * 最终返回一个新的堆栈信息数组，其中包含所有不属于忽略包名的元素。
     *
     * @param stackTrace 完整的堆栈信息数组
     * @param ignorePackage 要忽略的包名，可以为 null
     * @return 真实的堆栈信息数组，所有元素来自系统和库用户
     */
    private fun getRealStackTrack(
        stackTrace: Array<StackTraceElement>,
        ignorePackage: String?
    ): Array<StackTraceElement> {
        // 初始化忽略深度为 0
        var ignoreDepth = 0
        // 获取堆栈信息的总深度
        val allDepth = stackTrace.size
        // 从堆栈信息数组的末尾开始向前遍历
        for (i in allDepth - 1 downTo 0) {
            // 获取当前堆栈元素的类名
            val className = stackTrace[i].className
            // 如果忽略包名不为空且当前类名以忽略包名开头，则更新忽略深度并退出循环
            if (ignorePackage != null && className.startsWith(ignorePackage)) {
                ignoreDepth = i + 1
                break
            }
        }
        // 计算真实的堆栈深度
        val realDepth = allDepth - ignoreDepth
        // 返回从忽略深度到总深度之间的堆栈信息子数组
        return stackTrace.copyOfRange(ignoreDepth, allDepth)
    }

    /**
     * 根据最大深度裁剪堆栈信息。
     * 
     * 该方法会根据提供的最大深度 maxDepth 对原始堆栈信息 callStack 进行裁剪，
     * 返回裁剪后的堆栈信息数组。
     *
     * @param callStack 原始堆栈信息数组
     * @param maxDepth 要裁剪的最大深度，0 表示不限制
     * @return 裁剪后的堆栈信息数组
     */
    private fun cropStackTrace(callStack: Array<StackTraceElement>, maxDepth: Int): Array<StackTraceElement> {
        // 计算实际需要的堆栈深度。如果 maxDepth 大于 0，则取 callStack.size 和 maxDepth 中的较小值；
        // 否则，使用 callStack.size 作为实际深度。
        val realDepth = if (maxDepth > 0) minOf(callStack.size, maxDepth) else callStack.size
        // 返回从索引 0 到 realDepth 之间的堆栈信息子数组
        return callStack.copyOfRange(0, realDepth)
    }
}