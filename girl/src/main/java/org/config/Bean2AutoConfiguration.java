package org.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Bean2AutoConfiguration {

    public Bean2AutoConfiguration() {
        System.err.println("####Bean2AutoConfiguration 实例化#############");
    }
}
