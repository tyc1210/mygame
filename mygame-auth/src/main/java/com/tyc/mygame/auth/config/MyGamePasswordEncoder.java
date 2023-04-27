package com.tyc.mygame.auth.config;

import com.tyc.mygame.common.exception.CommonException;
import com.tyc.mygame.common.model.response.ResultCode;
import com.tyc.mygame.common.util.RSAEncrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-12 13:44:21
 */
@Component
public class MyGamePasswordEncoder implements PasswordEncoder {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public String encode(CharSequence charSequence) {
        return bCryptPasswordEncoder.encode(charSequence);
    }

    /**
     * 客户端校验与用户名密码校验都走这里
     * @param charSequence 客户端传入的值（client_secret或password）
     * @param s 数据库存储的值
     * @return
     */
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        // 客户端传入的值经过MD5然后又经过RSA加密，这里要进行解密后传入进行比对
        // 数据库中存储的是密码经过MD5再经过bCryptPasswordEncoder加密的值
        String decrypt = null;
        try {
            String cipher = charSequence.toString().replace(" ", "+").replaceAll("\\s*", "");
            decrypt = RSAEncrypt.decrypt(cipher);
        } catch (Exception e) {
            throw new CommonException(ResultCode.AUTH_DECODE_SECRET_ERROR);
        }
        return bCryptPasswordEncoder.matches(decrypt, s);
    }
}
