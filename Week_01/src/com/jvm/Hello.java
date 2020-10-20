package com.jvm;

public class Hello {
    public void hello(){
        System.out.println("Hello, classLoader!");
    }

    public static void main(String[] args) {
        Hello h =  new Hello();
        h.hello();
    }
}
