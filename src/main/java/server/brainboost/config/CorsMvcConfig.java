package server.brainboost.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    //로그인 외 통신에 대한 cors 문제 해결
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
            .allowedOriginPatterns("*") // 모든 도메인 및 포트 허용
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 필요한 HTTP 메서드 설정
            .allowedHeaders("*") // 모든 헤더 허용
            .allowCredentials(true); // 자격 증명 허용 여부
    }

}
