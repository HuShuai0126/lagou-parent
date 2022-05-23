package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EntityScan("com.lagou.edu.pojo")
// 开启 Eureka客户端 （只用于 eureka client）
@EnableEurekaClient
// 开启注册中心客户端 （通用型）      从spring cloud的E版本开始，可以不写注解，但是建议加上。见名思意
@EnableDiscoveryClient
public class LagouResumteApplication8081 {
    public static void main(String[] args) {
        SpringApplication.run(LagouResumteApplication8081.class,args);
    }
}
