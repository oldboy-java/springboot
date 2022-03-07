package org.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(Bean2AutoConfiguration.class)
public class Bean1AutoConfiguration {

    public Bean1AutoConfiguration() {
        System.err.println("####Bean1AutoConfiguration 实例化#############");
    }
}
