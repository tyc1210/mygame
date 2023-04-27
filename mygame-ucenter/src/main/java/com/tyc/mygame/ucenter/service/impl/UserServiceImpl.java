package com.tyc.mygame.ucenter.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tyc.mygame.ucenter.dao.UserMapper;
import com.tyc.mygame.ucenter.entity.Account;
import com.tyc.mygame.ucenter.entity.SysUser;
import com.tyc.mygame.common.exception.CommonException;
import com.tyc.mygame.ucenter.model.request.LoginUser;
import com.tyc.mygame.ucenter.model.request.RegisterUser;
import com.tyc.mygame.common.model.response.CommonResult;
import com.tyc.mygame.ucenter.model.response.LoginResponse;
import com.tyc.mygame.ucenter.service.IAccountService;
import com.tyc.mygame.ucenter.service.IUserService;
import com.tyc.mygame.ucenter.util.AuthUtil;
import com.tyc.mygame.common.util.RSAEncrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-11 16:44:18
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IAccountService accountService;

    @Override
    public void register(RegisterUser registerUser) {
        SysUser user = getByName(registerUser.getUserName());
        if(null != user){
            throw new CommonException(50001,"用户名被占用");
        }
        String password = registerUser.getPassword().replace(" ", "+").replaceAll("\\s*", "");
        try {
            password = RSAEncrypt.decrypt(password);
        } catch (Exception e) {
            throw new CommonException(10001,"password error");
        }
        registerUser.setPassword(bCryptPasswordEncoder.encode(password));
        userMapper.insert(new SysUser(registerUser.getUserName(),registerUser.getPassword()));
    }

    @Override
    public SysUser getByName(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("userName", name);
        List<SysUser> sysUsers = userMapper.selectByMap(map);
        return sysUsers.isEmpty() ? null : sysUsers.get(0);
    }

    @Autowired
    private AuthUtil authUtil;

    @Override
    public LoginResponse login(LoginUser user) {
        LoginResponse loginResponse = new LoginResponse();
        String sdkToken = user.getToken();
        String sdkUid = "";
        //  1.检验第三方token 获取 sdkUid
        sdkUid = getSdkUid(user.getChannel(),sdkToken);
        // 2.判断新用户/老用户
        Account account = accountService.getByChannelAndUid(user.getChannel(), sdkUid);
        if(null == account){
            Account regAccount = new Account();
            regAccount.setChannel(user.getChannel());
            regAccount.setPutOnMark(user.getPutOnMark());
            regAccount.setRegTime(LocalDateTime.now());
            regAccount.setSdkUid(sdkUid);
            accountService.save(regAccount);
            log.info("注册新用户===>",JSONUtil.toJsonStr(regAccount));
        }else {
            log.info("用户登录===> {}",JSONUtil.toJsonStr(account));
        }
        // 3.生成 gameToken
        String userName = user.getChannel()+"::"+sdkUid;
        String gameToken = authUtil.getTokenFromAuthServer(userName);
        JSONObject tokenInfo = JSONUtil.parseObj(gameToken);
        loginResponse.setAccess_token(tokenInfo.getStr("access_token"));
        loginResponse.setRefresh_token(tokenInfo.getStr("refresh_token"));
        return loginResponse;
    }

    /**
     * 根据渠道校验token 返回 sdkUid
     */
    private String getSdkUid(String channel, String sdkToken) {
        // todo 策略模式进行处理
        if(channel.equals("MY_GAME")){
            String url = "http://localhost:8888/check_token?token="+sdkToken;
            ResponseEntity<CommonResult> resp = restTemplate.getForEntity(url, CommonResult.class);
            if(resp.getStatusCode().equals(HttpStatus.OK)){
                Integer code = resp.getBody().getCode();
                if(code.equals(0)){
                    JSONObject dataJSON = JSONUtil.parseObj(resp.getBody().getData());
                    return dataJSON.getStr("userId");
                }else {
                    throw new CommonException(code,resp.getBody().getMsg());
                }
            }else {
                throw new CommonException(30008,"mygame check token");
            }
        }else {
            return "111111";
        }
    }
}
