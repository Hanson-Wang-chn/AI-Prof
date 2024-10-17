package cn.edu.ecnu.fiveguy.backendofaiprof.common.enums;

//import CommonResolveEnum.CHECK_ARGUMENT;
//import CommonResolveEnum.CONTACT_RD;
import cn.edu.ecnu.fiveguy.backendofaiprof.common.exception.OpResolve;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 此枚举用于构造OpException
 */
@AllArgsConstructor
@Getter
public enum OpExceptionEnum {

    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", "非法参数", CommonResolveEnum.CHECK_ARGUMENT),

    EXECUTE_FAIL("EXECUTE_FAIL", "语句执行失败", CommonResolveEnum.CONTACT_RD),

    USER_ID_EMPTY("USER_ID_EMPTY", "用户ID为空", CommonResolveEnum.CHECK_ARGUMENT),

    LLM_ERROR("LLM_ERROR", "大模型错误", CommonResolveEnum.CONTACT_RD),

    JWT_ERROR("JWT_ERROR", "JWT解析错误", CommonResolveEnum.CONTACT_RD),

    REC_ERROR("REC_ERROR", "推荐系统错误", CommonResolveEnum.CONTACT_RD);

    final String code;
    final String message;
    final OpResolve resolve;
}
