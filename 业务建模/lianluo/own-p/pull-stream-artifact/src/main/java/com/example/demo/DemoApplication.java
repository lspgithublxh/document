package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.demo.mapper")
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        //输出
        SpringApplication.run(DemoApplication.class, args);
    }

}
