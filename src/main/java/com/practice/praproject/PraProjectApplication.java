package com.practice.praproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.practice.praproject.mapper")
public class PraProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PraProjectApplication.class, args);
    }

}
