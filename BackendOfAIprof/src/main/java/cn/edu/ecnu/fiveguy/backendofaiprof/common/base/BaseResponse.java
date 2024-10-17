package cn.edu.ecnu.fiveguy.backendofaiprof.common.base;

import cn.edu.ecnu.fiveguy.backendofaiprof.common.enums.CommonResolveEnum;
import cn.edu.ecnu.fiveguy.backendofaiprof.common.enums.OpExceptionEnum;
import cn.edu.ecnu.fiveguy.backendofaiprof.common.enums.ResponseCodeEnum;
import cn.edu.ecnu.fiveguy.backendofaiprof.common.exception.OpException;

import org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        this.resolve = CommonResolveEnum.CONTACT_RD.getTip();
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
