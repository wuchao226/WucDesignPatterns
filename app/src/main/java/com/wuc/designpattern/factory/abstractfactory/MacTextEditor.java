package com.wuc.designpattern.factory.abstractfactory;

/**
 * @author: wuc
 * @date: 2024/7/7
 * @desc: MAC系统下文本编辑器的实现
 */
public class MacTextEditor extends AbsTextEditor {

    @Override
    public void edit() {
        System.out.println("文本编辑器,edit -- Mac版");
    }

    @Override
    public void save() {
        System.out.println("文本编辑器, save -- Mac版");
    }

}
