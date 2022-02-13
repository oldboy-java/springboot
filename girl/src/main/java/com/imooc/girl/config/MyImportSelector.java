package com.imooc.girl.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

// 自定义逻辑返回需要导入到IOC容器中的组件，结合@Import使用来自定义注册到容器中的Bean
public class MyImportSelector implements ImportSelector {

    //返回值就是导入到容器中的组件全类名
    // AnnotationMetadata：当前标注@Import注解的类所有注解信息
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return StringUtils.addStringToArray(new String[]{}, "com.imooc.girl.bean.Green");
    }

}
