package com.tyc.mygame.common.model.response;

import lombok.Data;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2021-10-27 14:20:52
 */
@Data
public class CommonResult<T> {
    private Integer code;
    private String msg;
    private T data;

    public CommonResult(){

    }

    public CommonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CommonResult(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    public CommonResult(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public static<T> CommonResult<T> success(T t){
        CommonResult commonResult = new CommonResult(ResultCode.SUCCESS);
        commonResult.setData(t);
        return commonResult;
    }

    public static<T> CommonResult<T> success(){
        CommonResult commonResult = new CommonResult(ResultCode.SUCCESS);
        return commonResult;
    }

    public static<T> CommonResult<T> failed(ResultCode resultCode,T t){
        CommonResult commonResult = new CommonResult(resultCode);
        commonResult.setData(t);
        return commonResult;
    }

    public static<T> CommonResult<T> failed(ResultCode resultCode){
        CommonResult commonResult = new CommonResult(resultCode);
        return commonResult;
    }

    public static CommonResult failed(){
        CommonResult commonResult = new CommonResult(ResultCode.SYSTEM_ERROR);
        return commonResult;
    }

    public static CommonResult failed(Integer code, String msg){
        CommonResult result = new CommonResult(code, msg);
        return result;
    }

}
