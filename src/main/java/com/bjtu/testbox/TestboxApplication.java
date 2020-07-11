package com.bjtu.testbox;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.bjtu.testbox.mapper")
@SpringBootApplication
public class TestboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestboxApplication.class, args);
    }

}
