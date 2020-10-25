package com.liul.shiro.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class Permission  implements Serializable {
    private Long id;
    private String permission;
}
