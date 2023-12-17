package com.liuli.boot.es.java.model.es;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductEsModel extends BaseEsModel{

    /**
     * sku
     */
    private String sku;

    /**
     * 名称
     */
    private String name;

    /**
     * 简介
     */
    private String detail;

    /**
     * 当前价格
     */
    private Double nowPrice;


    /**
     * 定价
     */
    private Double prePrice;

    /**
     * 折扣 如8.7
     */
    private Double discount;

    /**
     * 价格单位
     */
    private String priceUint;

    /**
     * 图片
     */
    private String img;

    /**
     * 评论数
     */
    private long comment;

    /**
     * 出版社
     */
    private String press;

    /**
     * 作者
     */
    private String author;

    /**
     * 出版日期
     */
    private String publishDate;

}
