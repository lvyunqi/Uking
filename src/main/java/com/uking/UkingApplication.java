package com.uking;

import com.gitee.starblues.loader.launcher.SpringBootstrap;
import com.gitee.starblues.loader.launcher.SpringMainBootstrap;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.Uking.dao")
@Slf4j
public class UkingApplication implements SpringBootstrap {

    public static void main(String[] args) {
        // 该处使用 SpringMainBootstrap 引导启动
        SpringMainBootstrap.launch(UkingApplication.class, args);
    }

    @Override
    public void run(String[] args) throws Exception {
        // 在该实现方法中, 和 SpringBoot 使用方式一致
        SpringApplication.run(UkingApplication.class, args);
    }

}
