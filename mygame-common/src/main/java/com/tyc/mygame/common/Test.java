package com.tyc.mygame.common;

import cn.hutool.crypto.digest.MD5;
import com.tyc.mygame.common.util.RSAEncrypt;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-10 17:40:12
 */
public class Test {


    public static void main(String[] args) throws Exception {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = "123456";
        MD5 md5 = MD5.create();
        String md5Pwd = md5.digestHex(password);
        String encrypt = RSAEncrypt.encrypt(md5Pwd);
        System.out.println();
    }
}
