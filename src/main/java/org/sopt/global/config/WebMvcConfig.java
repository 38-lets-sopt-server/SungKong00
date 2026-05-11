package org.sopt.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * CORS(Cross-Origin Resource Sharing) 설정
 * 클라이언트가 다른 도메인에서 API에 접근할 수 있도록 허용
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final List<String> allowedOrigins;
    private final boolean allowCredentials;

    public WebMvcConfig(
            @Value("${app.cors.allowed-origins:*}") List<String> allowedOrigins,
            @Value("${app.cors.allow-credentials:false}") boolean allowCredentials
    ) {
        this.allowedOrigins = allowedOrigins;
        this.allowCredentials = allowCredentials;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var cors = registry.addMapping("/**")
                // 허용할 HTTP 메서드
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                // 허용할 요청 헤더
                .allowedHeaders("*")
                // 응답에 포함될 헤더
                .exposedHeaders("Content-Disposition")
                // preflight 요청의 캐시 시간 (초)
                .maxAge(3600);

        if (allowedOrigins.contains("*")) {
            cors.allowedOriginPatterns("*")
                    .allowCredentials(false);
        } else {
            cors.allowedOrigins(allowedOrigins.toArray(new String[0]))
                    .allowCredentials(allowCredentials);
        }
    }
}
