package com.nedlee.finance.util;

public class Apple {

    public final int box_amount=20;

    public int Boxing(int x){

        return x/box_amount+1;
    }

    public static void main(String[] args) {
        Apple apple=new Apple();

        System.out.println(apple.Boxing(345));
    }
}
