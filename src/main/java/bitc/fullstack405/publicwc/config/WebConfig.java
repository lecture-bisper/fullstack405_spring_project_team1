package bitc.fullstack405.publicwc.config;

import bitc.fullstack405.publicwc.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/mypage/**")  // 마이페이지 관련 경로에 대해 인터셉터 적용
                .excludePathPatterns("/login", "/signup", "/"); // 로그인 및 회원가입 페이지는 제외
    }

}
