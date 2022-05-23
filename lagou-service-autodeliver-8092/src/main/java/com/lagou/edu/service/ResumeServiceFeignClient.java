package com.lagou.edu.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

// 配置要访问的服务
@FeignClient(value = "lagou-service-resume")
@RequestMapping("/resume")
public interface ResumeServiceFeignClient {

    @GetMapping("/openstate/{id}")
    public Integer findDefaultResumeState(@PathVariable Long id);
}

