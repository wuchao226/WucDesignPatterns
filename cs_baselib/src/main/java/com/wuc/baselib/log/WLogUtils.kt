package com.wuc.baselib.log

/**
 * @author: wuc
 * @date: 2024/9/27
 * @desc: Log 的门面 1. 打印堆栈信息 2. File输出 3. 模拟控制台
 */
object WLogUtils {
    /**
     * 忽略的日志包名
     */
    private val LOG_IGNORE_PACKAGE: String
    private const val TOP_LEFT_CORNER = '┏'
    private const val BOTTOM_LEFT_CORNER = '┗'
    private const val MIDDLE_CORNER = '┠'
    private const val LEFT_BORDER = '┃'
    private const val DOUBLE_DIVIDER = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    private const val SINGLE_DIVIDER = "──────────────────────────────────────────────────────"


    init {
        val className = WLogUtils::class.java.name
        LOG_IGNORE_PACKAGE = className.substring(0, className.lastIndexOf(".") - 1)
    }

    @JvmStatic
    fun v(vararg contents: Any) {
        // *contents的作用是将contents数组中的每个元素作为单独的参数传递给函数
        log(LogType.V, *contents)
    }
    
    @JvmStatic
    fun vt(tag: String, vararg contents: Any) {
        log(LogType.V, tag, *contents)
    }

    @JvmStatic
    fun d(vararg contents: Any) {
        log(LogType.D, *contents)
    }

    @JvmStatic
    fun dt(tag: String, vararg contents: Any) {
        log(LogType.D, tag, *contents)
    }

    @JvmStatic
    fun i(vararg contents: Any) {
        log(LogType.I, *contents)
    }

    @JvmStatic
    fun it(tag: String, vararg contents: Any) {
        log(LogType.I, tag, *contents)
    }

    @JvmStatic
    fun w(vararg contents: Any) {
        log(LogType.W, *contents)
    }

    @JvmStatic
    fun wt(tag: String, vararg contents: Any) {
        log(LogType.W, tag, *contents)
    }

    @JvmStatic
    fun e(vararg contents: Any) {
        log(LogType.E, *contents)
    }

    @JvmStatic
    fun et(tag: String, vararg contents: Any) {
        log(LogType.E, tag, *contents)
    }

    @JvmStatic
    fun a(vararg contents: Any) {
        log(LogType.A, *contents)
    }

    @JvmStatic
    fun at(tag: String, vararg contents: Any) {
        log(LogType.A, tag, *contents)
    }

    @JvmStatic
    fun log(@LogType.TYPE type: Int, vararg contents: Any) {
        log(type, LogManager.getInstance().getLogConfig().getGlobalTag(), *contents)
    }

    @JvmStatic
    fun log(@LogType.TYPE type: Int, tag: String, vararg contents: Any) {
        log(LogManager.getInstance().getLogConfig(), type, tag, *contents)
    }
    
    /**
     * 打印日志的方法。
     *
     * @param config 日志配置对象，包含日志相关的配置信息
     * @param type 日志类型，使用 @LogType.TYPE 注解进行标注
     * @param tag 日志标签，用于标识日志的来源
     * @param contents 可变参数的日志内容，可以传入多个日志内容
     */
    @JvmStatic
    fun log(config: LogConfig, @LogType.TYPE type: Int, tag: String, vararg contents: Any) {
        // 如果日志配置未启用，则直接返回，不进行任何操作
        if (!config.enable()) {
            return
        }
        // 创建一个 StringBuilder 对象，用于拼接日志内容
        val sb = StringBuilder().apply {
            appendLine("$TOP_LEFT_CORNER$DOUBLE_DIVIDER")
            // 如果日志配置包含线程信息，则获取当前线程信息并添加到日志内容中
            if (config.includeThread()) {
                val threadInfo = LogConfig.threadFormatter.format(Thread.currentThread())
                appendLine("$LEFT_BORDER$threadInfo")
            }
            appendLine("$MIDDLE_CORNER$SINGLE_DIVIDER")
            // 如果日志配置的堆栈深度大于 0，则获取裁剪后的堆栈信息并添加到日志内容中
            if (config.stackTraceDepth() > 0) {
                val stackTrace = LogConfig.stackTraceFormatter.format(
                    StackTraceUtil.getCroppedRealStackTrack(
                        Throwable().stackTrace,
                        LOG_IGNORE_PACKAGE,
                        config.stackTraceDepth()
                    )
                )
                // 在每一行堆栈信息前面添加 LEFT_BORDER
                stackTrace.lines().forEach { line ->
                    appendLine("$LEFT_BORDER$line")
                }
//                appendLine(stackTrace)
            }
            appendLine("$MIDDLE_CORNER$SINGLE_DIVIDER")
            // 解析日志内容数组，并将解析后的内容添加到日志内容中
            parseBody(contents, config)?.let {
                // 替换转义字符 "\" 为普通字符
                appendLine("$LEFT_BORDER${it.replace("\\\"", "\"")}")
                append("$BOTTOM_LEFT_CORNER$DOUBLE_DIVIDER")
            }
        }
        // 获取日志打印器列表，如果配置中有打印器则使用配置中的打印器，否则使用 LogManager 中的打印器
        // 在 Kotlin 中，* 符号被称为“展开操作符”（spread operator）。它用于将数组传递给可变参数（vararg）函数。
        // config.printers() 返回一个打印器数组。listOf(*it) 部分将这个数组转换为一个列表。展开操作符 * 的作用是将数组元素展开，
        // 以便它们可以作为单独的参数传递给 listOf 函数。
        // 如果没有展开操作符，数组本身会作为一个单独的元素传递给 listOf 函数，结果是一个包含单个元素（数组）的列表，而不是包含数组元素的列表。
        val printers = config.printers()?.let { listOf(*it) } ?: LogManager.getInstance().getLogPrinters()
        // 遍历打印器列表，逐个打印日志内容
        printers?.forEach {
            it.print(config, type, tag, sb.toString())
        }
    }

    /**
     * 将日志内容数组解析为字符串。
     *
     * @param contents 日志内容数组
     * @param config 日志配置
     * @return 解析后的日志字符串
     */
    private fun parseBody(contents: Array<out Any>, config: LogConfig): String? {
        // 尝试获取 JSON 序列化器
        config.injectJsonParser()?.let { jsonParser ->
            // 如果日志内容数组只有一个元素且该元素是字符串，则直接返回该字符串
            if (contents.size == 1 && contents[0] is String) {
                return contents[0] as String
            }
            // 否则，将日志内容数组序列化为 JSON 字符串
            return jsonParser.toJson(contents)
        }
        // 如果没有 JSON 序列化器，则将日志内容数组转换为以 "; " 分隔的字符串
        return contents.joinToString(separator = "; ") { it.toString() }
    }
}
