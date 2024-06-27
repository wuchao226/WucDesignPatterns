package com.wuc.designpattern.flyweight.multiple;

import com.wuc.designpattern.flyweight.ICustomerString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wuc
 * @date: 2024/6/26
 * @desc: 客户端
 */
public class Main {
    public static void main(String[] args) {
        List<Character> states = new ArrayList<Character>();
        states.add('Y');
        states.add('A');
        states.add('B');
        CustomerStringFactory factory = new CustomerStringFactory();
        ICustomerString customerString = factory.factory(states);
        customerString.opt("Mutex object test");
    }
}
