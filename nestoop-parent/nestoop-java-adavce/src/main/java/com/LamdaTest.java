package com;

/**
 * Created by botter
 *
 * @Date 15/12/16.
 * @description
 */
public class LamdaTest {

    interface A{
        String test(String s);
    }

    static void demo(A a) {
        System.out.println(a.test("5434"));
    }

    public static void  main(String[] args) {
        demo((String s) -> { return  s + "AAAAA";});
    }
}
