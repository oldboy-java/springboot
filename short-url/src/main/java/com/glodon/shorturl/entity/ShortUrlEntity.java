package com.glodon.shorturl.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName t_short_url
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "t_short_url")
public class ShortUrlEntity implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 域名
     */
    @TableField(value = "f_base_url")
    private String baseUrl;

    /**
     * 链接除了域名外的后缀
     */
    @TableField(value = "f_suffix_url")
    private String suffixUrl;

    /**
     * 完整链接
     */
    @TableField(value = "f_full_url")
    private String fullUrl;

    /**
     * 当前 full_url 链接的短码
     */
    @TableField(value = "f_short_code")
    private String shortCode;

    /**
     * 失效日期
     */
    @TableField(value = "f_expire_time")
    private Date expireTime;

    /**
     * 当前链接总点击次数
     */
    @TableField(value = "f_total_click_count")
    private Long totalClickCount;

    /**
     * 失效日期
     */
    @TableField(value = "f_create_time")
    private Date createTime;

    private static final long serialVersionUID = 1L;


}