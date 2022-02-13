package com.tk.rabbitmq.basic;

public class Man extends Person {

    public Man(String name, int age) {
        super(name, age);
    }

    @Override
    public boolean isMale() {
        return true;
    }

}


class test {

}