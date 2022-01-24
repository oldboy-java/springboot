package com.liuli;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomEnvironmentPostProcessor implements EnvironmentPostProcessor {
    // 定义配置文件加载器
    private PropertiesPropertySourceLoader loader = new PropertiesPropertySourceLoader();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();

        //定位自定义配置文件
        Resource resource = new ClassPathResource("/custom/custom.properties");

        try {
            PropertySource<?> propertySource = loader.load("custom", resource).get(0);
            propertySources.addFirst(propertySource);
        } catch (IOException e) {
            log.error("exception:", e);
        }
    }

}
