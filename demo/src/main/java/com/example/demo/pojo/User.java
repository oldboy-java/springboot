package com.example.demo.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class User {

    private String name;

    @JsonIgnore       //密码是私密信息，不需要返回
    private String password;

    private Integer age;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8") //日前格式化
    private Date birthday;

    @JsonInclude(value = Include.NON_NULL) //当内容不为空时才返回
    private String desc;

}
