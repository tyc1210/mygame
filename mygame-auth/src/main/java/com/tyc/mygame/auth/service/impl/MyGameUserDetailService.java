package com.tyc.mygame.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.tyc.mygame.auth.entity.Account;
import com.tyc.mygame.auth.entity.SysUser;
import com.tyc.mygame.auth.model.GameLoginUser;
import com.tyc.mygame.auth.model.SDKLoginUser;
import com.tyc.mygame.auth.service.IAccountService;
import com.tyc.mygame.auth.service.ISysUserService;
import com.tyc.mygame.common.exception.CommonException;
import com.tyc.mygame.common.model.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 配置如何认证用户信息
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-10 11:07:56
 */
@Service
@Slf4j
public class MyGameUserDetailService implements UserDetailsService {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IAccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(StrUtil.isBlank(username)){
            log.warn("用户登陆用户名错误");
            throw new CommonException(ResultCode.PARAM_CHECK_FAILED);
        }
        // GAME登录
        if(username.contains("::")){
            String[] arr = username.split("::");
            String channel = arr[0];
            String sdkUid = arr[1];
            Account account = accountService.getByChannelAndUid(channel,sdkUid);
            if(null == account){
                throw new CommonException(ResultCode.AUTH_USER_NOT_FOUND);
            }
            return new GameLoginUser(account);
        }else {
            // SDK登录
            SysUser sysUser = userService.getByName(username);
            if(null == sysUser){
                throw new CommonException(ResultCode.AUTH_USER_NOT_FOUND);
            }
            return new SDKLoginUser(sysUser);
        }
    }
}
