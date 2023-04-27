package com.tyc.mygame.auth.config;

import com.tyc.mygame.auth.entity.Account;
import com.tyc.mygame.auth.model.GameLoginUser;
import com.tyc.mygame.auth.model.SDKLoginUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 扩展JWT中存储的内容，根据自己业务添加字段到Jwt中
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-10 13:41:47
 */
@Component
public class MyGameTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Object principal = authentication.getPrincipal();
        //
        if(principal instanceof SDKLoginUser){
            SDKLoginUser loginUser = (SDKLoginUser) authentication.getPrincipal();
            final Map<String, Object> additionalInfo = new HashMap<>();
            final Map<String, Object> retMap = new HashMap<>();
            additionalInfo.put("userId",loginUser.getSysUser().getUserId());
            additionalInfo.put("nickName",loginUser.getSysUser().getUsername());
            retMap.put("additionalInfo",additionalInfo);
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(retMap);
        }
        if(principal instanceof GameLoginUser){
            GameLoginUser unofficialLoginUser = (GameLoginUser) authentication.getPrincipal();
            final Map<String, Object> additionalInfo = new HashMap<>();
            final Map<String, Object> retMap = new HashMap<>();
            Account account = unofficialLoginUser.getAccount();
            additionalInfo.put("sdkUid",account.getSdkUid());
            additionalInfo.put("accountId",account.getAccountId());
            additionalInfo.put("channel",account.getChannel());
            additionalInfo.put("putOnMark",account.getPutOnMark());
            retMap.put("additionalInfo",additionalInfo);
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(retMap);
        }


        return accessToken;
    }
}
