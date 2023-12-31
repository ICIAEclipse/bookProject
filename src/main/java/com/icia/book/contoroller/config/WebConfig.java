package com.icia.book.contoroller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private String resourcePath = "/upload/**"; // html 에서 접근할 경로
    private String savePath = "file:///D:/springboot_img/"; // 실제 파일이 저장된 경로
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePath);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1) // 해당 인터셉터의 우선순위
                .addPathPatterns("/member/mypage") // 인터셉터로 체크할 주소(모든주소)
                .addPathPatterns("/admin/**")
                .addPathPatterns("/order/**")
                .excludePathPatterns("/order/createNumber"); // 인터셉터 검증을 하지 않을 주소
        registry.addInterceptor(new AdminCheckInterceptor())
                .order(2)
                .addPathPatterns("/admin/**");
    }
}