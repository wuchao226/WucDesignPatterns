package com.wuc.designpattern.factory.method;

/**
 * @author: wuc
 * @date: 2024/7/7
 * @desc: 工厂方法
 */
public abstract class IOHandler {
    public abstract void add(String id,String name);
    public abstract void remove(String id);
    public abstract void update(String id,String name);
    public abstract void query(String id);
}
