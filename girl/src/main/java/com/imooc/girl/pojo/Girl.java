package com.imooc.girl.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@ToString
@NoArgsConstructor
@ApiModel("用户信息")
@Data
@TableName(value = "girl")
public class Girl implements Serializable {

    @TableId(type = IdType.ID_WORKER, value = "id")
    private Long id;

    @NotNull
    @ApiModelProperty(value = "罩杯尺寸")
    @TableField(value = "cup_size")
    private String cupSize;

    //給age属性添加验证：如果小于18,则提示错误message
    @Min(value = 18, message = "未成年少女禁止入内")
    @NotNull
    @ApiModelProperty(value = "年龄")
    @TableField(value = "age")
    private Integer age;


    //@DateTimeFormat(iso=ISO.DATE)  //支持将日期字符串参数转成Date类型 ,验证initBinder，这里先注释掉注解
    @NotNull
    @ApiModelProperty(value = "生日")
    @TableField(value = "birthday")
    private Date birthday; //生日

    @NumberFormat(pattern = "##,###.00")    //支持将金额格式参数转换成BigDecimal类型
    @NotNull
    @ApiModelProperty(value = "存款")
    @TableField(value = "money")
    private BigDecimal money;//存款


}
