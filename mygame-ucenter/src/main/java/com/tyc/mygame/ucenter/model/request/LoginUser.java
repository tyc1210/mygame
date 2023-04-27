package com.tyc.mygame.ucenter.model.request;

import lombok.Data;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-14 09:43:00
 */
@Data
public class LoginUser {
    private String token;
    private String channel;
    private String putOnMark;
}
