package com.wuc.designpattern.factory.method;

/**
 * @author: wuc
 * @date: 2024/7/7
 * @desc:
 */
public class IOFactory {
    public static void main(String[] args) {

    }

    /**
     * 工厂达成的目的
     * 创建实例，解耦
     *
     * @param <T>
     * @return
     */
    public static <T extends IOHandler> T getIOHandler(String clazz) {
        IOHandler ioHandler = null;
        try {
            //反射应用，使得工厂变成一组可扩展的方案
            ioHandler = (IOHandler) Class.forName(clazz).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) ioHandler;
    }
}
