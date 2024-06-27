package com.wuc.designpattern.flyweight.multiple;

import com.wuc.designpattern.flyweight.CustomerStringImpl;
import com.wuc.designpattern.flyweight.ICustomerString;
import com.wuc.designpattern.flyweight.MultipleCustomerStringImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: wuc
 * @date: 2024/6/26
 * @desc: 享元工厂角色类
 */
public class CustomerStringFactory {
    // 一般而言，享元工厂对象在整个系统中只有一个，因此也可以使用单例模式
    private static Map<Character, ICustomerString> map = new HashMap<>();

    // 单纯享元模式
    public ICustomerString factory(Character state) {
        ICustomerString cacheTemp = map.get(state);
        if (cacheTemp == null) {
            cacheTemp = new CustomerStringImpl(state);
            map.put(state, cacheTemp);
        }
        return cacheTemp;
    }

    // 复合享元模式
    public ICustomerString factory(List<Character> states) {
        MultipleCustomerStringImpl impl = new MultipleCustomerStringImpl();
        for (Character state : states) {
            impl.add(state, this.factory(state));
        }
        return impl;
    }
}
