package com.wuc.designpattern.flyweight.single;

import com.wuc.designpattern.flyweight.ICustomerString;

/**
 * @author: wuc
 * @date: 2024/6/26
 * @desc: 客户端
 */
public class Main {
    public static void main(String[] args) {
        CustomerStringFactory customerStringFactory = new CustomerStringFactory();
        ICustomerString customerString1 = customerStringFactory.factory('A');
        customerString1.opt("customer1");
        ICustomerString customerString2 = customerStringFactory.factory('B');
        customerString2.opt("customer2");
        ICustomerString customerString3 = customerStringFactory.factory('C');
        customerString3.opt("customer3");
    }
}
