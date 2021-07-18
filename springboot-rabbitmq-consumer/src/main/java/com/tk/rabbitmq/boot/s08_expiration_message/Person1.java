package com.tk.rabbitmq.boot.s08_expiration_message;

import lombok.Data;

import java.io.Serializable;

@Data
public class Person1 implements Serializable {
    private int id;
    private String name;
}
