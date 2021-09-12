package com.imooc.girl.esign;

import lombok.Data;

@Data
public class CertificationDTO {
    private String accountId;
    private String name;
    private boolean pushPortalAuth;
    private Integer type;
    private Long tenantId;

}
