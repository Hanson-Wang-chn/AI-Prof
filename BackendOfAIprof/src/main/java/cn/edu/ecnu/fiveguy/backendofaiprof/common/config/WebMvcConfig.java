package cn.edu.ecnu.fiveguy.backendofaiprof.common.config;

import cn.edu.ecnu.fiveguy.backendofaiprof.common.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器路径
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AppProperties appProperties;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(appProperties))
                // 拦截的路径
                .addPathPatterns("/**")
                // 开放的路径
                .excludePathPatterns("/login/**", "/token/validate");
    }
}
