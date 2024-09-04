package com.wuc.designpattern.factory.simple;

/**
 * @author: wuc
 * @date: 2024/7/7
 * @desc: Iphone4s 类,对应ConcreteProduct角色
 */
public class IPhone4s implements IPhone{
    @Override
    public void call() {
        System.out.println("用Iphone4s打电话");
    }

    @Override
    public void sendMessage() {
        System.out.println("用Iphone4s发短信");
    }

    @Override
    public void surfTheInternet() {
        System.out.println("用Iphone4s上网");
    }
}
