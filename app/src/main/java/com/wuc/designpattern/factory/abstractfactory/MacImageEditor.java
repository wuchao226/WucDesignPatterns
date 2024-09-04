package com.wuc.designpattern.factory.abstractfactory;

/**
 * @author: wuc
 * @date: 2024/7/7
 * @desc: MAC系统下图像编辑器的实现
 */
public class MacImageEditor extends AbsImageEditor {

    @Override
    public void edit() {
        System.out.println("图片处理编辑器,edit -- Mac版");
    }

    @Override
    public void save() {
        System.out.println("图片处理编辑器, save -- Mac版");
    }

}
