package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
// 打开Hystrix功能 第二个更全能
// @EnableHystrix
@EnableCircuitBreaker
// 综合型注解 @SpringCloudApplication = @SpringBootApplication + @EnableDiscoveryClient + @EnableCircuitBreaker
//@SpringCloudApplication
public class AtodeliverApplication {
    public static void main(String[] args) {
        SpringApplication.run(AtodeliverApplication.class,args);
    }

    // 使用RestTemplate模板对象 远程调用
    @Bean
    // ribbon负载均衡
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
