package com.imooc.girl.pojo;

import lombok.Data;

@Data
public class Db {

    private String user;

    private String pwd;

    public Db(String user, String pwd) {
        super();
        this.user = user;
        this.pwd = pwd;
    }


}
