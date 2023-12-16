package com.liuli.boot.es.java.model.es;

import lombok.Data;

@Data
public class UserEsModel extends BaseEsModel{
    private String name;
    private Integer age;
}
