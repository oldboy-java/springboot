package com.liuli.boot.sharding.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {
    private Long  id;
    private BigDecimal price;
    private Long userId;
    private String status;
}
