package com.tyc.mygame.ucenter.util;

import com.tyc.mygame.common.exception.CommonException;
import com.tyc.mygame.common.model.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-10 14:29:41
 */
@Slf4j
@Component
public class AuthUtil {
    @Autowired
    private RestTemplate restTemplate;

    private static final String CLIENT_ID = "client";

    private static final String CLIENT_SECRET = "raP/HVTthCzf0aqj4Zkud8gBNc7y1Jv5cwuId3Vi62jyfbA1vomQIDVLGvVlmPPCbKdAXJzrXptEjtYbArP63w==";

    private static final String PASSWORD = "raP/HVTthCzf0aqj4Zkud8gBNc7y1Jv5cwuId3Vi62jyfbA1vomQIDVLGvVlmPPCbKdAXJzrXptEjtYbArP63w==";

    private static final String GRANT_TYPE = "password";

    private static final String SCOPE = "all";

    /**
     * 获取公钥字符串地址：默认暴露 oauth/token_key
     */
    private static final String AUTH_TOKEN_KEY_URL = "http://localhost:8888/oauth/token";

    /**
     * 获取公钥字符串
     */
    public String getTokenFromAuthServer(String userName){
        //第一步:封装请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 设置访问参数
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();;
        params.add("client_id",CLIENT_ID);
        params.add("client_secret",CLIENT_SECRET);
        params.add("grant_type",GRANT_TYPE);
        params.add("username",userName);
        params.add("password",PASSWORD);
        params.add("scope",SCOPE);
        //设置访问的Entity
        HttpEntity entity = new HttpEntity<>(params, headers);
        ResponseEntity<String> result = null;
        try {
            result = restTemplate.exchange(AUTH_TOKEN_KEY_URL, HttpMethod.POST, entity, String.class);
            String cliams = result.getBody();
            log.info("调用认证服务器获取Token:{}",result);
            return cliams;
        }catch (Exception e) {
            log.error("远程调用认证服务器获取Token_Key失败:{}",e.getMessage());
            throw new CommonException(ResultCode.PARAM_CHECK_FAILED);
        }
    }


}
