package com.acoreweb.acoreapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // application.properties se local file upload directory uthana
    @Value("${file.upload-dir}")
    private String uploadDir;

    // application.properties se public URL path uthana (/uploads)
    @Value("${app.upload-url}")
    private String uploadUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 1. Web URL mapping define karna
        // Jaise: jab bhi koi /uploads/** URL request kare
        registry.addResourceHandler(uploadUrl + "/**")

                // 2. Toh use kahan dhoondhna hai (local disk pe)
                // file:///C:/uploads/acore-images/ ya D:/ACORE/.../uploads
                .addResourceLocations("file:///" + uploadDir + "/");

        // Final check: path me forward slash (/) hi use karna.
    }
}