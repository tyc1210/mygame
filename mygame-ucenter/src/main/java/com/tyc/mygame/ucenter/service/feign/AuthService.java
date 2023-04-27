package com.tyc.mygame.ucenter.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient("mygame-auth")
public interface AuthService {
    @GetMapping("/token")
    String getToken(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("grant_type") String grant_type,@RequestParam("client_id") String client_id,@RequestParam("client_secret") String client_secret);
}
