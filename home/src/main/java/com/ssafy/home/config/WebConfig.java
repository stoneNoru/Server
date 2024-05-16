package com.ssafy.home.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.home.interceptor.JWTInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	private final JWTInterceptor jwtInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// JWTInterceptor를 등록하고, 로그인과 관련된 URL 패턴을 제외합니다.
		registry.addInterceptor(jwtInterceptor)
		.addPathPatterns("applies/**");
		//.excludePathPatterns("/users/**", "/users"); // 로그인 관련 URL 패턴을 제외합니다.
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("users/**")
				.allowedOrigins("*")
				.allowedMethods("POST");
		registry.addMapping("applies/**")
				.allowedOrigins("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
	}

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/assets/img/");
		registry.addResourceHandler("/*.html**").addResourceLocations("classpath:/static/");
    }

	
}
