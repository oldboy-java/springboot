package com.example.demo.pojo;

import javax.persistence.*;

public class Girl {
    @Id
    private String id;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 胸罩杯大小
     */
    @Column(name = "cup_size")
    private String cupSize;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取年龄
     *
     * @return age - 年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取胸罩杯大小
     *
     * @return cup_size - 胸罩杯大小
     */
    public String getCupSize() {
        return cupSize;
    }

    /**
     * 设置胸罩杯大小
     *
     * @param cupSize 胸罩杯大小
     */
    public void setCupSize(String cupSize) {
        this.cupSize = cupSize;
    }
}