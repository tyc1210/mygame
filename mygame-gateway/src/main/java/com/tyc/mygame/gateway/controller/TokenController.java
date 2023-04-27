package com.tyc.mygame.gateway.controller;

import com.tyc.mygame.gateway.filter.AuthenticationFilter;
import com.tyc.mygame.common.model.response.CommonResult;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-14 10:28:25
 */
@RestController
public class TokenController {
    @Autowired
    private AuthenticationFilter authenticationFilter;

    @GetMapping("check_token")
    public CommonResult checkToken(String token){
        Claims claims = authenticationFilter.getClaims(token);
        return CommonResult.success(claims.get("additionalInfo"));
    }
}
