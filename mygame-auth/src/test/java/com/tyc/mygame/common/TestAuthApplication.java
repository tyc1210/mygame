package com.tyc.mygame.common;

import cn.hutool.crypto.digest.MD5;
import com.tyc.mygame.common.util.RSAEncrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-10 11:20:13
 */
public class TestAuthApplication {
    public static void main(String[] args) throws Exception {
        System.out.println(getEncodeStr());
    }

    // 获取加密字符串
    public static String getEncodeStr() throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "123456";
        MD5 md5 = MD5.create();
        String md5Pwd = md5.digestHex(password);
        System.out.println("MD5加密后数据："+md5Pwd);
        String encrypt = RSAEncrypt.encrypt(md5Pwd);
        System.out.println("RSA加密后数据："+encrypt);
        String encodeStr = encoder.encode(encrypt);
        String encode = encoder.encode(md5Pwd);
        System.out.println("数据库中存储的数据："+encode);
        System.out.println(encoder.matches(md5Pwd,"$2a$10$mA7zYJBfBs4nwsyxGA8KI.2AKuBfD3AXMeuM7ttnLsA9pdhxlPZvi"));
        return encodeStr;
    }
}
