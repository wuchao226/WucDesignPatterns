package com.wuc.designpattern.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: wuc
 * @date: 2024/6/26
 * @desc: 复合享元对象
 */
public class MultipleCustomerStringImpl implements ICustomerString {
    private Map<Character, ICustomerString> map = new HashMap<>();

    public void add(Character state, ICustomerString value) {
        map.put(state, value);
    }

    @Override
    public void opt(String state) {
        ICustomerString temp;
        for (Character obj : map.keySet()) {
            temp = map.get(obj);
            temp.opt(state);
        }
    }
}
