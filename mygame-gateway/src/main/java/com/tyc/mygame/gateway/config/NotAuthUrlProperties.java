package com.tyc.mygame.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-10 14:17:23
 */
@Data
@Component
@ConfigurationProperties("mygame.gateway")
public class NotAuthUrlProperties {
    private LinkedHashSet<String> shouldSkipUrls;
}
