package com.wuc.designpattern.flyweight.single;


import com.wuc.designpattern.flyweight.CustomerStringImpl;
import com.wuc.designpattern.flyweight.ICustomerString;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: wuc
 * @date: 2024/6/26
 * @desc: 享元工厂角色类
 */
public class CustomerStringFactory {
    // 一般而言，享元工厂对象在整个系统中只有一个，因此也可以使用单例模式
    protected Map<Character, ICustomerString> map = new HashMap<>();
    public ICustomerString factory(Character state) {
        ICustomerString cacheTemp = map.get(state);
        if (cacheTemp == null) {
            cacheTemp = new CustomerStringImpl(state);
            map.put(state, cacheTemp);
        }
        return cacheTemp;
    }
}
