package com.study.Ex13FileUpload;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {
    // addResourceHandlers : templates폴더와 static폴더의 리소스를 사용 가능하게 해줌.
    // localhost:8080/image.png 경로로 접근 가능하도록 열어주는 역할.
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:src/main/resources/templates/",
                        "file:src/main/resources/static/");
    }
}