package com.liul.shiro.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Long id;
    private String userName;
    private String password;
    private Integer status;

}
