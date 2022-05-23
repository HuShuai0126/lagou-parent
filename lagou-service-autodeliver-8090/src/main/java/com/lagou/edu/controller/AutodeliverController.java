package com.lagou.edu.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/autodeliver")
public class AutodeliverController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    /**@GetMapping("/checkState/{id}")
    public Integer finResumeOpenState(@PathVariable Long id){
        // 调用简历微服务  -> 远程调用
        Integer forObject = restTemplate.getForObject("http://localhost:8080/resume/openstate/" + id, Integer.class);
        return forObject;
    }*/

    /**
     * 注册到Eureka之后的改造
     * @param id
     * @return
     */
    /**@GetMapping("/checkState/{id}")
    public Integer finResumeOpenState(@PathVariable Long id){
        // TODO 从Eureka Server中获取我们关注的那个服务实例信息和接口
        // 1. 从Eureka Server获取我们关注的lagou-service-resume的实例信息（使用客户端对象
        List<ServiceInstance> instances = discoveryClient.getInstances("lagou-service-resume");
        // 2. 从多个实例中选择一个（负载均衡的过程
        ServiceInstance serviceInstance = instances.get(0);
        // 3. 获取实例的元数据信息
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        String url = "http://" + host + ":" + port + "/resume/openstate/" + id;
        System.out.println("url = " + url);
        // 调用简历微服务  -> 远程调用
        Integer forObject = restTemplate.getForObject(url, Integer.class);
        return forObject;
    }*/

    /**
     * 测试Ribbon负载均衡
     * @param id
     * @return
     */
    /**@GetMapping("/checkState/{id}")
    public Integer finResumeOpenState(@PathVariable Long id){
        // 只需要提供服务名，他自己就能找到resume服务提供者
        String serverName = "lagou-service-resume";
        // 使用ribbon不需要我们自己选择一个，然后访问
        String url = "http://" + serverName + "/resume/openstate/" + id;
        System.out.println("url = " + url);
        // 调用简历微服务  -> 远程调用
        Integer forObject = restTemplate.getForObject(url, Integer.class);
        return forObject;
    }*/

    /**
     * 测试Hystrix
     * @param id
     * @return
     */
    // 熔断控制
    /**@HystrixCommand(
            // 细节配置
            commandProperties = {
                    // 每一个属性都是HystrixProperty
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
            }
    )
    @GetMapping("/checkState/{id}")
    public Integer finResumeOpenState(@PathVariable Long id){
        // 只需要提供服务名，他自己就能找到resume服务提供者
        String serverName = "lagou-service-resume";
        // 使用ribbon不需要我们自己选择一个，然后访问
        String url = "http://" + serverName + "/resume/openstate/" + id;
        System.out.println("url = " + url);
        // 调用简历微服务  -> 远程调用
        Integer forObject = restTemplate.getForObject(url, Integer.class);
        return forObject;
    }*/

    /**
     * 测试Hystrix  并设置降级处理
     * @param id
     * @return
     */
    // 熔断控制
    @HystrixCommand(
            // 线程池标识，要保证唯一，不唯一的话就共用同一个线程池了。默认每一个线程池10个线程
            threadPoolKey = "finResumeOpenState",
            // 线程池属性配置
            threadPoolProperties = {
                @HystrixProperty(name = "coreSize",value = "1"), // 线程数
                @HystrixProperty(name = "maxQueueSize",value = "20") // 等待队列
            },
            // 细节配置
            commandProperties = {
                    // 每一个属性都是HystrixProperty
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
            },fallbackMethod = "myFallBack"  // 回退方法
    )
    @GetMapping("/checkState/{id}")
    public Integer finResumeOpenState(@PathVariable Long id, MultipartFile multipartFile){
        // 只需要提供服务名，他自己就能找到resume服务提供者
        String serverName = "lagou-service-resume";
        // 使用ribbon不需要我们自己选择一个，然后访问
        String url = "http://" + serverName + "/resume/openstate/" + id;
        System.out.println("url = " + url);
        // 调用简历微服务  -> 远程调用
        Integer forObject = restTemplate.getForObject(url, Integer.class);
        return forObject;
    }

    public Integer myFallBack(Long id){
        return -1;  // 兜底数据
    }

}
