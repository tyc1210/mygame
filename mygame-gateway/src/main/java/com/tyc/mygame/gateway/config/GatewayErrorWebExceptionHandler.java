package com.tyc.mygame.gateway.config;

import cn.hutool.json.JSONUtil;
import com.tyc.mygame.common.exception.CommonException;
import com.tyc.mygame.common.model.response.CommonResult;
import com.tyc.mygame.common.model.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-11 15:11:43
 */
@Component
@Order(-1)
@Slf4j
public class GatewayErrorWebExceptionHandler implements ErrorWebExceptionHandler {
    /**
     * 处理给定的异常
     * @param exchange
     * @param ex
     * @return
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        // 记录异常信息
        ServerHttpRequest request = exchange.getRequest();
        log.error("[全局异常]，请求资源：{}，异常信息：{}",request.getPath(),ex.getMessage());
        ServerHttpResponse response = exchange.getResponse();
        CommonResult result = new CommonResult();
        if (ex instanceof ResponseStatusException) {
            result.setCode(((ResponseStatusException) ex).getStatus().value());
            result.setMsg(((ResponseStatusException) ex).getReason());
        }else if(ex instanceof CommonException){ // 自定义异常
            result.setCode(((CommonException) ex).getCode());
            result.setMsg(((CommonException) ex).getMsg());
        } else {
            result.setCode(ResultCode.SYSTEM_ERROR.getCode());
            result.setMsg(ex.getMessage());
        }
        DataBuffer dataBuffer = response.bufferFactory()
                .allocateBuffer().write(JSONUtil.toJsonStr(result).getBytes());
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.writeAndFlushWith(Mono.just(ByteBufMono.just(dataBuffer)));
    }
}

