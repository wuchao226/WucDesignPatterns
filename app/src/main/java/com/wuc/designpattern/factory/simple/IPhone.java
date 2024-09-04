package com.wuc.designpattern.factory.simple;

/**
 * @author: wuc
 * @date: 2024/7/7
 * @desc: IPhone产品类，对应Product角色
 */
public interface IPhone {
    /**
     * 打电话
     */
    public void call();
    /**
     * 发短信
     */
    public void sendMessage();
    /**
     * 上网
     */
    public void surfTheInternet();
}
