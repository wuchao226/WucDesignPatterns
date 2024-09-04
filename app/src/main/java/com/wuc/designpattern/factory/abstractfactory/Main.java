package com.wuc.designpattern.factory.abstractfactory;

/**
 * @author: wuc
 * @date: 2024/7/7
 * @desc:
 */
public class Main {
    public static void main(String[] args) {
        MacAppFactory factory = new MacAppFactory();
        AbsTextEditor textEditor = factory.createTextEditor();
        textEditor.edit();
        textEditor.save();

        AbsImageEditor imageEditor = factory.createImageEditor();
        imageEditor.edit();
        imageEditor.save();
    }
}
