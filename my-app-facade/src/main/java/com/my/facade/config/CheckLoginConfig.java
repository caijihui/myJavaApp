package com.my.facade.config;

import com.my.facade.interceptor.UserLoginInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "com.my.common")
public class CheckLoginConfig implements WebMvcConfigurer {
    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor())
                .addPathPatterns("/user/**")
                .addPathPatterns("/userInfo/**")
                .excludePathPatterns("/user/login");//开放登录路径
    }
}