package cn.edu.ecnu.fiveguy.backendofaiprof.common.enums;

import cn.edu.ecnu.fiveguy.backendofaiprof.common.exception.OpResolve;
import lombok.AllArgsConstructor;

/**
 * 此类是一个通用的解决方案提示枚举类
 * 所含枚举提示都是通用一类的话术，一般情况下不提倡使用
 * 若要提供精细的提示，请自行定义构造类实现OpResolve
 */
@AllArgsConstructor
public enum CommonResolveEnum implements OpResolve {

    CONTACT_RD("请联系开发人员排查"),

    CHECK_ARGUMENT("请检查参数"),

    ;
    final String message;

    @Override
    public String getTip() {
        return message;
    }
}
