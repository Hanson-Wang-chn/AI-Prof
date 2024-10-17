package cn.edu.ecnu.fiveguy.backendofaiprof.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCodeEnum {
    SUCCESS("SUCCESS", "成功"),
    BIZ_EXCEPTION("BIZ_EXCEPTION", "业务异常"),
    SYSTEM_EXCEPTION("SYSTEM_EXCEPTION", "系统异常"),

    ;

    final String code;
    final String message;
}
