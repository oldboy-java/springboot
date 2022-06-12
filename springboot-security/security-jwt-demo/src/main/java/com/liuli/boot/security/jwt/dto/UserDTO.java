package com.liuli.boot.security.jwt.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName(value = "t_user")
public class UserDTO {
    @TableId(value = "id")
    private String id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "fullname")
    private String fullname;

    @TableField(value = "mobile")
    private String mobile;
}
