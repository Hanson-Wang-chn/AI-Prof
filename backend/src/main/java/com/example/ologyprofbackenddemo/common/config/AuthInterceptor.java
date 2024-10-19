package com.example.ologyprofbackenddemo.common.config;


import com.example.ologyprofbackenddemo.common.AppProperties;
import com.example.ologyprofbackenddemo.common.jwt.AuthStorage;
import com.example.ologyprofbackenddemo.common.jwt.JwtUser;
import com.example.ologyprofbackenddemo.common.jwt.TokenProvider;
import com.xhpolaris.idlgen.basic.UserMeta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 *
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AppProperties appProperties;

    public AuthInterceptor(AppProperties appProperties) {
        this.appProperties = appProperties;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(AuthStorage.TOKEN_KEY);
//        if (StringUtils.hasText(token) && token.startsWith("Berry ")) {
//            token = token.substring(6);
//        }
//        else token = null;
        if (StringUtils.hasLength(token)) {
            UserMeta tokens = TokenProvider.decodeToken(token, appProperties.getPublicKey());
            JwtUser jwtUser = new JwtUser().setUserId(tokens.getUserId()).setValid(true);
            // 是否认证通过
            if (StringUtils.hasLength(jwtUser.getUserId()) && jwtUser.isValid()) {
                // 保存授权信息
                AuthStorage.setUser(token, jwtUser);
                return true;
            }
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("请先登录！");
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求完成清除授权信息
        AuthStorage.clearUser();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

