package com.wuc.designpattern.flyweight;

/**
 * @author: wuc
 * @date: 2024/6/26
 * @desc:
 */
public class TestString {
    public static void main(String[] args) {
        testString();
    }

    public static void testString() {
        String str1 = new String("abc");
        String str2 = "abc";
        String str3 = new String("abc");
        String str4 = "ab" + "c ";
        System.out.println(str1.equals(str2));
        System.out.println(str1.equals(str3));
        System.out.println(str2.equals(str3));

        System.out.println(str1 == str2);
        System.out.println(str1 == str3);
        System.out.println(str2 == str3);
        System.out.println(str2 == str4);
        //true
        //true
        //true
        //false
        //false
        //false
        //false
    }
}
