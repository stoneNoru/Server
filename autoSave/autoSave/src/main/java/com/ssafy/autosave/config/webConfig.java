package com.ssafy.autosave.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan(basePackages = "com.ssafy.autosave.*.model.mapper")
public class webConfig {
}
