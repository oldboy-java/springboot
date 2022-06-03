package com.liuli.publish.subscribe.model;


public class User {
    private String name;
    private Integer age;

    public User(){}
    public User(String name, Integer age) {
        this.age = age;
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static UserBuilder builder(){
       return new User.UserBuilder();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static  class UserBuilder {
        private String name;
        private Integer age;

        public User.UserBuilder name(String name){
            this.name = name;
            return  this;
        }
        public User.UserBuilder age(Integer age){
            this.age = age;
            return  this;
        }

        public User build(){
            return new User(this.name, this.age);
        }
    }
}
