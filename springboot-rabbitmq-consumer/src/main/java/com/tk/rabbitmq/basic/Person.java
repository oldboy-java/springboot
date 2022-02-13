package com.tk.rabbitmq.basic;

public abstract class Person {

    private String name;

    private int age;

    public abstract boolean isMale();

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
