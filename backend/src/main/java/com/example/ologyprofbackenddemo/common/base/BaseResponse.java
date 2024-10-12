package com.example.ologyprofbackenddemo.common.base;

import com.example.ologyprofbackenddemo.common.enums.OpResolveEnum;
import com.example.ologyprofbackenddemo.common.enums.OpExceptionEnum;
import com.example.ologyprofbackenddemo.common.enums.ResponseCodeEnum;
import com.example.ologyprofbackenddemo.common.exception.OpException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 通用的返回类
 * attention：此类不允许new，只允许通过提供的静态方法构造
 *
 * @param <T>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

    private T data;

    private String code;

    private String message;

    private String resolve;

    private BaseResponse(T data, ResponseCodeEnum responseCode) {
        this.data = data;
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.resolve = null;
    }

    private BaseResponse(OpException ex) {
        this.data = null;
        this.code = ex.getExCode();
        this.message = ex.getMessage();
        this.resolve = ex.getResolve().getTip();
    }

    private BaseResponse(ResponseCodeEnum responseCode, Exception ex) {
        this.data = null;
        this.code = responseCode.getCode();
        this.message = ex.getMessage();
        this.resolve = OpResolveEnum.CONTACT_RD.getTip();
    }

    public boolean success() {
        return StringUtils.equals(code, ResponseCodeEnum.SUCCESS.getCode());
    }


    public static <T> BaseResponse<T> buildSuccess(T data) {
        return new BaseResponse<>(data, ResponseCodeEnum.SUCCESS);
    }

    public static <T> BaseResponse<T> buildBizEx(OpException ex) {
        return new BaseResponse<>(ex);
    }

    public static <T> BaseResponse<T> buildBizEx(OpExceptionEnum exEnum) {
        return new BaseResponse<>(new OpException(exEnum));
    }

    public static <T> BaseResponse<T> buildSysEx(Exception e) {
        return new BaseResponse<>(ResponseCodeEnum.SYSTEM_EXCEPTION, e);
    }

}
