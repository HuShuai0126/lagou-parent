package com.lagou.edu;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard // 开启hystrix dashboard
public class HystrixDashboardApplication9000 {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardApplication9000.class,args);
    }

    /**
     * 在被监控的微服务中，注册一个servlet，后期我们就是通过访问这个servlet获取该服务的hystrix监控数据的
     * 前提：被监控的微服务需要引入springboot的actuator功能
     * @return
     */
    @Bean
    public ServletRegistrationBean getServlet(){
        HystrixMetricsStreamServlet streamServlet = new
                HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new
                ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);

        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }

}
