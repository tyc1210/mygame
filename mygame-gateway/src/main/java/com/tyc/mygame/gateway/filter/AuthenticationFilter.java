package com.tyc.mygame.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.tyc.mygame.gateway.exception.GatewayException;
import com.tyc.mygame.gateway.config.NotAuthUrlProperties;
import com.tyc.mygame.common.model.response.ResultCode;
import com.tyc.mygame.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.PublicKey;
import java.util.Map;

/**
 * 认证过滤器: 实现认证逻辑
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-10 14:15:23
 */
@Component
@Order(-1)
@Slf4j
public class AuthenticationFilter implements GlobalFilter, InitializingBean {
    private PublicKey publicKey;

    @Autowired
    private NotAuthUrlProperties notAuthUrlProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void afterPropertiesSet(){
        publicKey = JwtUtil.getPubKey(restTemplate);
    }

    public Claims getClaims(String token){
        return JwtUtil.validateJwtToken(token, publicKey);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String currentUrl = exchange.getRequest().getURI().getPath();
        //过滤不需要认证的url
        if(shouldSkip(currentUrl)) {
            log.info("不需要认证的URL:{}",currentUrl);
            return chain.filter(exchange);
        }
        log.info("需要认证的URL:{}",currentUrl);
        //解析出我们Authorization的请求头  value为: “bearer XXXXXXXXXXXXXX”
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        //判断Authorization的请求头是否为空
        if(StrUtil.isBlank(authHeader)) {
            log.warn("需要认证的url,请求头为空");
            throw new GatewayException(ResultCode.GATEWAY_AUTH_NULL);
        }
        // 获取token值
        Claims claims = JwtUtil.validateJwtToken(authHeader, publicKey);
        // 向后续需要使用的信息放到请求头中
        ServerWebExchange webExchange = wrapHeader(exchange,claims);
        return chain.filter(webExchange);
    }

    private boolean shouldSkip(String currentUrl) {
        PathMatcher pathMatcher = new AntPathMatcher();
        for(String skipPath:notAuthUrlProperties.getShouldSkipUrls()) {
            if(pathMatcher.match(skipPath,currentUrl)) {
                return true;
            }
        }
        return false;
    }

    private ServerWebExchange wrapHeader(ServerWebExchange serverWebExchange,Claims claims) {
        // 获取自定义的扩展信息
        Map additionalInfo = claims.get("additionalInfo", Map.class);
        String channel = additionalInfo.get("channel").toString();
        String putOnMark = additionalInfo.get("putOnMark").toString();
        String sdkUid = additionalInfo.get("sdkUid").toString();
        String accountId = additionalInfo.get("accountId").toString();
//        String nickName = additionalInfo.get("nickName").toString();
        // 防止中文乱码 在解析时使用相同的方式
//        String nickNameEncode = null;
//        try {
//            nickNameEncode = URLEncoder.encode(nickName, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        ServerHttpRequest request = serverWebExchange.getRequest().mutate()
                .header("channel",channel)
                .header("putOnMark",putOnMark)
                .header("sdkUid",sdkUid)
                .header("accountId",accountId)
                .build();
        //将现在的request 变成 change对象
        return serverWebExchange.mutate().request(request).build();
    }
}
