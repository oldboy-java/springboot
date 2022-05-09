package com.liuli.hash.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "商品信息")
public class Product {

    /**
     * id
     */
    @ApiModelProperty(value = "商品ID")
    private  Long id;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String name;

    /**
     * 商品价格
     */
    @ApiModelProperty(value = "商品价格")
    private BigDecimal price;

    /**
     * 商品描述
     */
    @ApiModelProperty(value = "商品描述")
    private String detail;
}
