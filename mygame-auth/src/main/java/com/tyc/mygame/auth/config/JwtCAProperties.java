package com.tyc.mygame.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-10 12:00:52
 */
@Data
@Configuration
@ConfigurationProperties("mygame.jwt")
public class JwtCAProperties {
    /**
     * 证书名称
     */
    private String keyPairName;
    /**
     * 证书别名
     */
    private String keyPairAlias;
    /**
     * 证书私钥
     */
    private String keyPairSecret;
    /**
     * 证书存储密钥
     */
    private String keyPairStoreSecret;
}
