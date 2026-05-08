package org.sopt.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS(Cross-Origin Resource Sharing) 설정
 * 클라이언트가 다른 도메인에서 API에 접근할 수 있도록 허용
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 모든 origin 허용 (프로덕션에서는 구체적인 도메인으로 제한)
                .allowedOriginPatterns("*")
                // 허용할 HTTP 메서드
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                // 허용할 요청 헤더
                .allowedHeaders("*")
                // 응답에 포함될 헤더
                .exposedHeaders("Content-Disposition")
                // 인증 정보 포함 여부
                .allowCredentials(true)
                // preflight 요청의 캐시 시간 (초)
                .maxAge(3600);
    }
}
