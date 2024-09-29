package com.wuc.baselib.log.printer

import com.wuc.baselib.log.LogConfig
import com.wuc.baselib.log.LogMo
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.BlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * @author: wuc
 * @date: 2024/9/26
 * @desc: Log 的 文件打印器
 * 1、BlockingQueue 的使用，防止频繁的创建线程；
 * 2、线程同步；
 * 3、文件操作，BufferedWriter 的应用；
 */
class FilePrinter private constructor(
    // log 保存路径，如果是外部路径需要确保已经有外部存储的读写权限
    val logPath: String,
    // log文件的有效时长, 单位毫秒，<=0 表示一直有效
    val retentionTime: Long
) : LogPrinter {

    companion object {
        @Volatile
        private var instance: LogPrinter? = null

        @JvmStatic
        fun getInstance(logPath: String, retentionTime: Long): LogPrinter {
            return instance ?: synchronized(this) {
                instance ?: FilePrinter(logPath, retentionTime).also { instance = it }
            }
        }

        private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    }

    init {
        scheduleLogCleanup()
    }

    private val printWorker = PrintWorker()

    @Volatile
    private var logWriter = LogWriter()

    override fun print(config: LogConfig, level: Int, tag: String, printStr: String) {
        val timeMills = System.currentTimeMillis()
        if (!printWorker.isRunning) {
            printWorker.start()
        }
        // 将日志信息封装成 LogMo 对象并放入 PrintWorker 的队列中
        printWorker.put(LogMo(timeMills, level, tag, printStr))
    }

    /**
     * 实际执行日志写入操作。如果当前没有准备好的文件，则生成新的文件名并准备写入
     */
    private fun doPrint(logMo: LogMo) {
        val lastFileName = logWriter.preFileName
        if (lastFileName.isNullOrEmpty()) {
            val newFileName = genFileName()
            if (logWriter.isReady) {
                logWriter.close()
            }
            if (!logWriter.ready(newFileName)) {
                return
            }
        }
        logWriter.write(logMo.flattenLog())
    }

    /**
     * 创建文件名称
     *
     * @return String
     */
    private fun genFileName(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        sdf.timeZone = TimeZone.getDefault()
        return "${sdf.format(Date(System.currentTimeMillis()))}.log"
    }


    /**
     * 安排定期清理日志文件的方法。
     * 
     * 该方法使用 ScheduledExecutorService 来安排一个定期执行的任务，
     * 该任务每小时执行一次 clearExpiredLog 方法，以确保定期清理过期的日志文件。
     * 
     * 1. 创建一个 ScheduledExecutorService 对象 scheduler，使用 Executors.newScheduledThreadPool(1) 方法创建一个具有单个线程的调度程序。
     * 2. 调用 scheduler.scheduleWithFixedDelay 方法安排定期执行的任务。
     *    - 第一个参数是一个 Runnable 对象，表示要执行的任务，这里是调用 clearExpiredLog 方法。
     *    - 第二个参数是初始延迟时间，这里设置为 0，表示任务在调度后立即执行。
     *    - 第三个参数是任务之间的延迟时间，这里设置为 1，表示任务每隔 1 小时执行一次。
     *    - 第四个参数是时间单位，这里使用 TimeUnit.HOURS 表示小时。
     */
    private fun scheduleLogCleanup() {
        // 创建一个具有单个线程的调度程序
        val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
        // 安排定期执行的任务，每小时执行一次 clearExpiredLog 方法
        scheduler.scheduleWithFixedDelay({
            clearExpiredLog()
        }, 0, 1, TimeUnit.HOURS)
    }

    /**
     * 清除过期log
     */
    /**
     * 清除过期的日志文件
     * 
     * 该方法会遍历日志目录中的所有文件，并删除那些超过设定的有效时长的日志文件。
     * 
     * 1. 首先检查 retentionTime 是否小于 0，如果是，则直接返回，不进行任何操作。
     * 2. 获取当前的系统时间 currentTimeMillis。
     * 3. 创建一个 File 对象 logDir，表示日志文件的目录。
     * 4. 调用 logDir.listFiles() 获取目录中的所有文件，如果目录为空或发生错误，则直接返回。
     * 5. 遍历目录中的每个文件，检查文件的最后修改时间与当前时间的差值是否超过了 retentionTime。
     * 6. 如果超过了 retentionTime，则删除该文件。
     */
    private fun clearExpiredLog() {
        // 如果日志文件的有效时长小于 0，则不进行任何操作
        if (retentionTime < 0) {
            return
        }
        // 获取当前的系统时间
        val currentTimeMillis = System.currentTimeMillis()
        // 创建一个 File 对象，表示日志文件的目录
        val logDir = File(logPath)
        // 获取目录中的所有文件，如果目录为空或发生错误，则直接返回
        val files = logDir.listFiles() ?: return
        // 遍历目录中的每个文件
        for (file in files) {
            // 检查文件的最后修改时间与当前时间的差值是否超过了有效时长
            if (currentTimeMillis - file.lastModified() > retentionTime) {
                // 如果超过了有效时长，则删除该文件
                file.delete()
            }
        }
    }

    /**
     * Log 打印 工作线程
     */
    private inner class PrintWorker : Runnable {
        private val logsQueue: BlockingQueue<LogMo> = LinkedBlockingQueue()

        // 判断工作线程是否还在运行中(true 在运行)
        @Volatile
        var isRunning: Boolean = false
            private set

        /**
         * 启动工作线程
         */
        fun start() {
            synchronized(this) {
                executorService.execute(this)
                isRunning = true
            }
        }

        /**
         * 将log放入打印队列
         *
         * @param log 要被打印的log
         */
        fun put(log: LogMo) {
            try {
                // logsQueue.put(log)：这是一个阻塞方法，如果队列已满，它会一直等待直到有空间可用
                logsQueue.put(log)
                // logs.offer(log)：这是一个非阻塞方法，如果队列已满，它会立即返回 false，不会等待。
//                logsQueue.offer(log)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        override fun run() {
            try {
                while (true) {
                    // logs.take() 是一个阻塞方法，用于从 BlockingQueue 中获取并移除队列的头元素。如果队列为空，它会一直等待直到有元素可用。
                    // 从队列中获取日志，如果队列为空则阻塞等待
                    val log = logsQueue.take()
                    doPrint(log)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                synchronized(this) {
                    isRunning = false
                }
            }
        }
    }

    /**
     * 基于 BufferedWriter 将 log 写入文件
     */
    private inner class LogWriter {
        var preFileName: String? = null
            private set
        private var logFile: File? = null
        private var bufferedWriter: BufferedWriter? = null

        val isReady: Boolean
            get() = bufferedWriter != null

        /**
         * log 写入前的准备操作
         * @param newFileName 要保存log的文件名
         * @return true 表示准备就绪
         */
        fun ready(newFileName: String): Boolean {
            // 上一个文件名
            preFileName = newFileName
            // 当前日志文件
            logFile = File(logPath, newFileName)
            // 当log文件不存在时创建log文件
            if (!logFile!!.exists()) {
                try {
                    // 获取日志文件的父目录
                    logFile!!.parentFile?.let { parentFile ->
                        // 如果父目录不存在，则创建父目录
                        if (!parentFile.exists()) {
                            parentFile.mkdirs()
                        }
                    }
                    // 创建文件
                    logFile!!.createNewFile()
                } catch (e: IOException) {
                    preFileName = null
                    logFile = null
                    return false
                }
            }
            return try {
                // 初始化 BufferedWriter
                bufferedWriter = BufferedWriter(FileWriter(logFile, true))
                true
            } catch (e: Exception) {
                preFileName = null
                logFile = null
                false
            }
        }

        /**
         * 关闭 bufferedWriter
         */
        fun close(): Boolean {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    return false
                } finally {
                    preFileName = null
                    logFile = null
                    bufferedWriter = null
                }
            }
            return true
        }

        /**
         * 将格式化后的 log 写入文件
         *
         * @param flattenedLog 格式化后的log
         */
        fun write(flattenedLog: String) {
            try {
                bufferedWriter!!.write(flattenedLog)
                bufferedWriter!!.newLine()
                bufferedWriter!!.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}