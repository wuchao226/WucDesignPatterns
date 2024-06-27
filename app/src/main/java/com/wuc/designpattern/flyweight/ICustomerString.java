package com.wuc.designpattern.flyweight;

/**
 * @author: wuc
 * @date: 2024/6/26
 * @desc: 抽象享元角色类
 */
public interface ICustomerString {
    // 外部状态以参数的形式通过此方法传入
    void opt(String state);
}
