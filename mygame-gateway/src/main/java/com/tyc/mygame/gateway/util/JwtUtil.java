package com.tyc.mygame.gateway.util;

import cn.hutool.core.util.StrUtil;
import com.tyc.mygame.gateway.exception.GatewayException;
import com.tyc.mygame.common.model.response.ResultCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-10 14:29:41
 */
@Slf4j
public class JwtUtil {
    private static final String CLIENT_ID = "client";

    private static final String CLIENT_SECRET = "raP/HVTthCzf0aqj4Zkud8gBNc7y1Jv5cwuId3Vi62jyfbA1vomQIDVLGvVlmPPCbKdAXJzrXptEjtYbArP63w==";

    private static final String AUTH_HEADER = "bearer ";

    /**
     * 获取公钥字符串地址：默认暴露 oauth/token_key
     */
    private static final String AUTH_TOKEN_KEY_URL = "http://127.0.0.1:9999/oauth/token_key";

    /**
     * 校验token
     */
    public static Claims validateJwtToken(String authHeader, PublicKey publicKey) {
        String token =null ;
        try{
            token = StrUtil.subAfter(authHeader, AUTH_HEADER, true);
            Jwt<JwsHeader, Claims> parseClaimsJwt = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
            Claims claims = parseClaimsJwt.getBody();
            log.info("claims:{}",claims);
            return claims;
        }catch(Exception e){
            log.error("校验token异常:{},异常信息:{}",token,e.getMessage());
            throw new GatewayException(ResultCode.GATEWAY_TOKEN_VALIDATE_ERROR);
        }
    }

    /**
     * 获取公钥
     */
    public static PublicKey getPubKey(RestTemplate restTemplate){
        String tokenKey = getTokenFromAuthServer(restTemplate);
        try {
            String dealTokenKey =tokenKey.replaceAll("\\-*BEGIN PUBLIC KEY\\-*", "").replaceAll("\\-*END PUBLIC KEY\\-*", "").trim();
            java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(dealTokenKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);
            log.info("生成公钥:{}",publicKey);
            return publicKey;
        } catch (Exception e) {
            log.error("生成公钥异常：{}",e.getMessage());
            throw new GatewayException(ResultCode.GATEWAY_PARSE_PUBLIC_KEY_ERROR);
        }
    }

    /**
     * 获取公钥字符串
     */
    private static String getTokenFromAuthServer(RestTemplate restTemplate){
        //第一步:封装请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(CLIENT_ID,CLIENT_SECRET);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(null, headers);
        //第二步:远程调用获取token_key
        try {
            ResponseEntity<Map> response = restTemplate.exchange(AUTH_TOKEN_KEY_URL, HttpMethod.GET, entity, Map.class);
            String tokenKey = response.getBody().get("value").toString();
            log.info("去认证服务器获取Token_Key:{}",tokenKey);
            return tokenKey;
        }catch (Exception e) {
            log.error("远程调用认证服务器获取Token_Key失败:{}",e.getMessage());
            throw new GatewayException(ResultCode.GATEWAY_GET_PUBLIC_KEY_ERROR);
        }
    }


}
