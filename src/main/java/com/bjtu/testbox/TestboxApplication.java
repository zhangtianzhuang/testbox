package com.bjtu.testbox;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@MapperScan("com.bjtu.testbox.mapper")
@SpringBootApplication
@EnableTransactionManagement
// 这里不要继承WebMvcConfigurationSupport,导致swagger2一直不能用！！！！！
public class TestboxApplication /*extends WebMvcConfigurationSupport*/ {
    public static void main(String[] args) {
        SpringApplication.run(TestboxApplication.class, args);
    }
}
