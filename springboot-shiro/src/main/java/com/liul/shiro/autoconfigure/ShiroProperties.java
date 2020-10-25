package com.liul.shiro.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "shiro")
@Data
public class ShiroProperties {
    private String loginUrl;
    private String successUrl;
    private String unauthorizedUrl;

}
