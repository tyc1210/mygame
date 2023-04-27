package com.tyc.mygame.common.model.response;

/**
 * 状态码
 *
 * @author tyc
 * @version 1.0
 * @date 2021-10-27 14:24:49
 */
public enum  ResultCode {
    /**
     * 成功
     */
    SUCCESS(0,"success"),
    /**
     * 用户未找到
     */
    USER_NOT_FOUND(10001,"user not found"),

    /**
     * 登录失败
     */
    USER_LOGIN_FAILED(10002,"username or password error"),

    /**
     * 用户名已存在
     */
    USER_NAME_EXITS(10003,"username exits"),

    /**
     * 参数校验异常
     */
    PARAM_CHECK_FAILED(20001,"params error"),

    /**
     * 解密失败
     */
    DECODE_ERROR(20002,"decrypt error"),

    /**
     * 请求头token为空
     */
    GATEWAY_AUTH_NULL(30001,"auth cannot be null"),

    /**
     * token校验错误
     */
    GATEWAY_TOKEN_VALIDATE_ERROR(30002,"invalid token"),

    /**
     * 获取公钥异常
     */
    GATEWAY_GET_PUBLIC_KEY_ERROR(30003,"get publicKey error"),

    /**
     * 接卸 publickey 异常
     */
    GATEWAY_PARSE_PUBLIC_KEY_ERROR(30004,"parse publicKey error"),


    UCENTER_GET_TOKEN(30001,"auth cannot be null"),

    /**
     * 接卸 publickey 异常
     */
    AUTH_DECODE_SECRET_ERROR(40001,"decode secret error"),

    /**
     * 接卸 publickey 异常
     */
    AUTH_USER_NOT_FOUND(40002,"user not found"),


    /**
     * 系统错误
     */
    SYSTEM_ERROR(500,"system error");

    private Integer code;
    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
