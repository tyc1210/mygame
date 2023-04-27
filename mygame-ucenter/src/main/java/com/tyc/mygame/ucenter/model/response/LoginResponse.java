package com.tyc.mygame.ucenter.model.response;

import lombok.Data;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-14 09:44:59
 */
@Data
public class LoginResponse {
    private String access_token;
    private String refresh_token;
}
