package com.wuc.designpattern.flyweight;

/**
 * @author: wuc
 * @date: 2024/6/26
 * @desc: 具体享元角色类
 */
public class CustomerStringImpl implements ICustomerString{
    // 负责为内部状态提供存储空间
    private Character mInnerState = null;

    public CustomerStringImpl(Character mInnerState) {
        this.mInnerState = mInnerState;
    }

    @Override
    public void opt(String state) {
        System.out.println("Inner state = " + this.mInnerState);
        System.out.println("Out state = " + state);
    }
}
