package com.wuc.designpattern.factory.simple;

/**
 * @author: wuc
 * @date: 2024/7/7
 * @desc: 工厂类
 *  优点：完成了解耦
 *  后续的扩展角度来讲
 *  违反开闭原则
 */
public class IPhoneFactory {
    /**
     * 创建产品
     *
     * @param type
     * @return
     */
    public static IPhone createIphone(String type) {
        if (type == null) {
            return null;
        }
        IPhone iphone = null;
        if (type.equals("5s")) {
            iphone = new IPhone5s();
        } else if (type.equals("4s")) {
            iphone = new IPhone4s();
        }else if (type.equals("6s")) {
            iphone = new IPhone6s();
        }

        return iphone;
    }
}
