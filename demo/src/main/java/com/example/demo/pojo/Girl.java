package com.example.demo.pojo;


import com.example.demo.enums.CupSize;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Girl {


    private Long id;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 胸罩杯大小
     */
    private CupSize cupSize;

    public Girl(Integer age, CupSize cupSize) {
        this.age = age;
        this.cupSize = cupSize;
    }

}