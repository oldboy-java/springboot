package com.tk.rabbitmq.boot.s01_helloworld;

import java.io.Serializable;


public class User implements Serializable{

	public User() {
		super();
	}

	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public User(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	private int age;
}
