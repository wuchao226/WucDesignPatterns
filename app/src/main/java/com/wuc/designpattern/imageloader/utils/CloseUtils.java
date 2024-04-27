
package com.wuc.designpattern.imageloader.utils;

import java.io.Closeable;
import java.io.IOException;

/**
  * @author: wuc
  * @date: 2024/4/28
  * @desc: 关闭工具类
  */
public abstract class CloseUtils {
    /**
     * 关闭Closeable对象
     * 
     * @param closeable
     */
    public static void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
