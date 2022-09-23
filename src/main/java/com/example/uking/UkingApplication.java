package com.example.uking;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.Uking.mapper")
@Slf4j
public class UkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(UkingApplication.class, args);
    }

}
