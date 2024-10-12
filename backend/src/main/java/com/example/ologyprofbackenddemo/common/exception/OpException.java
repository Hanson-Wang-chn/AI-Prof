package com.example.ologyprofbackenddemo.common.exception;

import com.example.ologyprofbackenddemo.common.enums.OpExceptionEnum;
import lombok.Getter;

/**
 * OpException是本系统顶层业务异常
 */

@Getter
public class OpException extends RuntimeException {

    private static final long serialVersionUID = -71898646073843622L;

    private final String exCode;
    private final String exMessage;
    private final OpResolve resolve;

    public OpException(OpExceptionEnum ex) {
        exCode = ex.getCode();
        exMessage = ex.getMessage();
        resolve = ex.getResolve();
    }

    public OpException(OpExceptionEnum opEnum, Exception e) {
        exCode = opEnum.getCode();
        exMessage = opEnum.getMessage();
        resolve = e::getMessage;
    }

    public OpException(OpExceptionEnum ex, String message) {
        exCode = ex.getCode();
        exMessage = String.format("%s:%s", ex.getMessage(), message);
        resolve = ex.getResolve();
    }


    @Override
    public String getMessage() {
        return exMessage;
    }
}
