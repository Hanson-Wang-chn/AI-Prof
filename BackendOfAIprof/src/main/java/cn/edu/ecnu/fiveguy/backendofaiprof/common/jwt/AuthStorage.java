package cn.edu.ecnu.fiveguy.backendofaiprof.common.jwt;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class AuthStorage {
    @ApiModelProperty("请求头token的下标")
    public static final String TOKEN_KEY = "token";

    /**
     * 模拟session
     */
    private static final ThreadLocal<JWTUser> JWT_USER = new ThreadLocal<>();

    /**
     * 全局获取用户
     */
    public static JWTUser getUser() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        JWTUser jwtUser = JWT_USER.get();
        if (jwtUser == null) throw new OpException(OpExceptionEnum.JWT_ERROR);
        return jwtUser;
    }

    /**
     * 设置用户
     */
    public static void setUser(String token, JwtUser user) {
        JWT_USER.set(user);
    }

    /**
     * 清除授权
     */
    public static void clearUser() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        JWT_USER.remove();
    }
}
