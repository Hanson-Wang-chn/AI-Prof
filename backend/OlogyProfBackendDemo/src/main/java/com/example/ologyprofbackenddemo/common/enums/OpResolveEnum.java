package com.example.ologyprofbackenddemo.common.enums;

import com.example.ologyprofbackenddemo.common.exception.OpResolve;
import lombok.AllArgsConstructor;

/**
 * 此类是一个通用的解决方案提示枚举类
 */

@AllArgsConstructor
public enum OpResolveEnum implements OpResolve {

    CONTACT_RD("请联系开发人员排查"),
    CHECK_ARGUMENT("请检查参数"),

    ;
    final String message;

    @Override
    public String getTip() {
        return message;
    }
}
