package com.wuc.designpattern.factory.simple;

/**
 * @author: wuc
 * @date: 2024/7/7
 * @desc: Iphone5s 类,对应ConcreteProduct角色
 */
public class IPhone5s implements IPhone{
    @Override
    public void call() {
        System.out.println("用Iphone5s打电话");
    }

    @Override
    public void sendMessage() {
        System.out.println("用Iphone5s发短信");
    }

    @Override
    public void surfTheInternet() {
        System.out.println("用Iphone5s上网");
    }
}
