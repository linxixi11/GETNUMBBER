package com.xingye.data.content;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.xingye.data.content.dao")
public class GetNumberApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetNumberApplication.class, args);
    }
}
