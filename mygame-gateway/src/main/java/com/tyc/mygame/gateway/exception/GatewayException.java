package com.tyc.mygame.gateway.exception;


import com.tyc.mygame.common.exception.CommonException;
import com.tyc.mygame.common.model.response.ResultCode;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2021-10-27 14:48:14
 */
public class GatewayException extends CommonException {

    public GatewayException(ResultCode code) {
        super(code);
    }

    public GatewayException(ResultCode code, String msg) {
        super(code, msg);
    }


    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
