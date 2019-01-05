package com.imooc.girl.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
public class Girl {

	@Id
	@GeneratedValue
	private Integer id;
	@NotNull
	private String cupSize;
	
	//給age属性添加验证：如果小于18,则提示错误message
	@Min(value=18,message="未成年少女禁止入内")
	@NotNull
	private Integer age;
	
	
	//@DateTimeFormat(iso=ISO.DATE)  //支持将日期字符串参数转成Date类型 ,验证initBinder，这里先注释掉注解
	@NotNull
	private Date birthday; //生日
	
	@NumberFormat(pattern="##,###.00")    //支持将金额格式参数转换成BigDecimal类型
	@NotNull
	private BigDecimal money;//存款
	
}
