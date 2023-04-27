package com.tyc.mygame.ucenter.controller;

import com.tyc.mygame.ucenter.service.IUserService;
import com.tyc.mygame.ucenter.entity.SysUser;
import com.tyc.mygame.ucenter.model.request.LoginUser;
import com.tyc.mygame.ucenter.model.request.RegisterUser;
import com.tyc.mygame.common.model.response.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-10 15:04:45
 */
@RestController
@RequestMapping("user")
public class UserController {


    @Autowired
    private IUserService userService;

    @GetMapping("hello")
    public CommonResult hello(HttpServletRequest request) throws UnsupportedEncodingException {
        String nickName = request.getHeader("nickName");
        return CommonResult.success("hello:"+ URLDecoder.decode(nickName,"UTF-8"));
    }

    @GetMapping("/name")
    public CommonResult<SysUser> getByName(String name) {
        return CommonResult.success(userService.getByName(name));
    }

    /**
     * 官方渠道注册接口
     * @param user
     * @return
     */
    @PostMapping("register")
    public CommonResult registerUser(@RequestBody RegisterUser user){
        userService.register(user);
        return CommonResult.success();
    }

    /**
     * 登录/注册Account接口
     * @param user
     * @return
     */
    @PostMapping("login")
    public CommonResult login(@RequestBody LoginUser user){
        return CommonResult.success(userService.login(user));
    }
}
