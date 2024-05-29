package go.glogprototype.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로에 대해 모든 HTTP 메서드에 대한 CORS를 허용
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "http://localhost:4000", "https://geport.blog", "http://geport.blog")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true) // 이 부분 추가
                .allowedHeaders("*");
    }
}