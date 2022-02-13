package com.imooc.girl.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@ToString
@NoArgsConstructor
@ApiModel("用户信息")
public class Girl implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @ApiModelProperty(value = "罩杯尺寸")
    private String cupSize;

    //給age属性添加验证：如果小于18,则提示错误message
    @Min(value = 18, message = "未成年少女禁止入内")
    @NotNull
    @ApiModelProperty(value = "年龄")
    private Integer age;


    //@DateTimeFormat(iso=ISO.DATE)  //支持将日期字符串参数转成Date类型 ,验证initBinder，这里先注释掉注解
    @NotNull
    @ApiModelProperty(value = "生日")
    private Date birthday; //生日

    @NumberFormat(pattern = "##,###.00")    //支持将金额格式参数转换成BigDecimal类型
    @NotNull
    @ApiModelProperty(value = "存款")
    private BigDecimal money;//存款

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCupSize() {
        return cupSize;
    }

    public void setCupSize(String cupSize) {
        this.cupSize = cupSize;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
