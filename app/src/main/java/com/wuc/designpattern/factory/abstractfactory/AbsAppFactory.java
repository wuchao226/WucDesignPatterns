package com.wuc.designpattern.factory.abstractfactory;

/**
  * @author: wuc
  * @date: 2024/7/7
  * @desc: 应用软件抽象工厂
  */
public abstract class AbsAppFactory {
    //切分产品
    public abstract AbsTextEditor createTextEditor();

    public abstract AbsImageEditor createImageEditor();
}
