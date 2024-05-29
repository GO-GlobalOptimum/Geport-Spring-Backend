package go.glogprototype.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import go.glogprototype.domain.user.dao.UserRepository;
import go.glogprototype.global.jwt.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import go.glogprototype.global.jwt.filter.JwtAuthenticationProcessingFilter;
import go.glogprototype.global.jwt.service.JwtService;
import go.glogprototype.global.oauth2.handler.OAuth2LoginFailureHandler;
import go.glogprototype.global.oauth2.handler.OAuth2LoginSuccessHandler;
import go.glogprototype.global.oauth2.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * 인증은 CustomJsonUsernamePasswordAuthenticationFilter에서 authenticate()로 인증된 사용자로 처리
 * JwtAuthenticationProcessingFilter는 AccessToken, RefreshToken 재발급
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
//테스트
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .formLogin(formlogin -> formlogin.disable()) // disable FormLogin
            .httpBasic(httpBasic -> httpBasic.disable()) // disable httpBasic
            .csrf(csrf -> csrf.disable()) // disable csrf security
            .headers(headersConfig -> headersConfig.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
            // Since session is not used, set to STATELESS
            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            //== Permission management options per URL ==//
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/sign-up", "/spring/login", "/swagger-ui/index.html/**", "/api-docs", "/swagger-ui-custom.html",
                        "/v3/api-docs/**", "/swagger-ui/**", "/api-docs/**", "/swagger-ui.html", "/swagger-custom-ui.html", "/spring/posts/test").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                    .baseUri("/spring/oauth2/authorization/google")) // 여기가 인증 요청 경로
                .redirectionEndpoint(redirectionEndpoint -> redirectionEndpoint
                    .baseUri("/spring/login/oauth2/code/google")) // 여기가 리디렉션 경로
                .successHandler(oAuth2LoginSuccessHandler)
                .failureHandler(oAuth2LoginFailureHandler)
                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(customOAuth2UserService))
            );

        // 필터 설정
        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
            = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        return customJsonUsernamePasswordLoginFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        return new JwtAuthenticationProcessingFilter(jwtService, userRepository);
    }
}
