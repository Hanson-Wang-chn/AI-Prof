package com.example.ologyprofbackenddemo.common.enums;

import com.example.ologyprofbackenddemo.common.exception.OpResolve;
import lombok.AllArgsConstructor;
import lombok.Getter;

//import com.example.ologyprofbackenddemo.common.enums.OpResolveEnum.CHECK_ARGUMENT;
//import com.example.ologyprofbackenddemo.common.enums.OpResolveEnum.CONTACT_RD;


/**
 * 此枚举用于构造OpException
 */
@AllArgsConstructor
@Getter
public enum OpExceptionEnum {

    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", "非法参数", OpResolveEnum.CHECK_ARGUMENT),

    EXECUTE_FAIL("EXECUTE_FAIL", "语句执行失败",OpResolveEnum.CONTACT_RD),

    USER_ID_EMPTY("USER_ID_EMPTY", "用户ID为空", OpResolveEnum.CHECK_ARGUMENT),

    LLM_ERROR("LLM_ERROR", "大模型错误", OpResolveEnum.CONTACT_RD),

    JWT_ERROR("JWT_ERROR", "JWT解析错误", OpResolveEnum.CONTACT_RD),

    REC_ERROR("REC_ERROR", "推荐系统错误", OpResolveEnum.CONTACT_RD);

    final String code;
    final String message;
    final OpResolve resolve;
}