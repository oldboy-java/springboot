package com.liul.shiro.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class Role  implements Serializable {
    private Long id;
    private String roleName;

}
