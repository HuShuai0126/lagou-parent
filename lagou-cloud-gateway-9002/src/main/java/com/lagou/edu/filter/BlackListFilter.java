package com.lagou.edu.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义全局过滤器，会对所有路由生效
 */
@Component  // 注册
public class BlackListFilter implements GlobalFilter, Ordered {

    // 模拟黑名单（实际从数据库或者redis）
    private static List<String> blackList = new ArrayList<>();

    static {
        blackList.add("0:0:0:0:0:0:0:1");  // 模拟本机地址
    }

    /**
     * 过滤器核心方法
     * @param exchange 封装了request和response对象的上下文
     * @param chain 网关过滤器链，包含全局过滤器链和单路由过滤器
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 思路：获取客户端ip 判断是否在黑名单中，在的话：拒绝访问  不在：放行
        // 取出request对象  response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 从request对象中获取客户端ip
        String clientIp = request.getRemoteAddress().getHostString();
        // 拿clientIp去黑名单中查询，存在则拒绝访问
        if(blackList.contains(clientIp)){
            // 拒绝访问
            response.setStatusCode(HttpStatus.UNAUTHORIZED);// 状态码
            String data = "请求拒绝";
            DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
            return response.writeWith(Mono.just(wrap));
        }
        // 合法请求，放行执行后续操作
        return chain.filter(exchange);
    }

    /**
     *
     * @return 当前过滤器优先级，数值越小优先级越高
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
