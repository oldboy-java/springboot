package com.liuli.boot.sharding.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class User {
    private Long  id;
    private String userName;
    private String password;
}
