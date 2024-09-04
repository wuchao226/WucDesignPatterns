package com.wuc.designpattern.factory.abstractfactory;

/**
  * @author: wuc
  * @date: 2024/7/7
  * @desc: 具体工厂, 创建各种应用, 这里为文本编辑器和图像编辑器.
  */
public class MacAppFactory extends AbsAppFactory {

    @Override
    public AbsTextEditor createTextEditor() {
        return new MacTextEditor();
    }

    @Override
    public AbsImageEditor createImageEditor() {
        return new MacImageEditor();
    }

}
