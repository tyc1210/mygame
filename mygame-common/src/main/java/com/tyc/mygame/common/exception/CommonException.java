package com.tyc.mygame.common.exception;

import com.tyc.mygame.common.model.response.ResultCode;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2021-10-27 14:48:14
 */
public class CommonException extends RuntimeException{
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public CommonException (int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public CommonException(ResultCode code) {
        super("base error with code:" + code.getMsg());
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public CommonException(ResultCode code, String msg) {
        super(msg);
        this.code = code.getCode();
        this.msg = msg;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
