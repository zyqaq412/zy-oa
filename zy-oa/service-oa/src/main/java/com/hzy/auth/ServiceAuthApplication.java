package com.hzy.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @title: ServiceAuthApplication
 * @Author zxwyhzy
 * @Date: 2023/6/3 21:51
 * @Version 1.0
 */

@SpringBootApplication
@ComponentScan("com.hzy") // 配置统一扫描这个包下的内容
public class ServiceAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthApplication.class);
    }
}
