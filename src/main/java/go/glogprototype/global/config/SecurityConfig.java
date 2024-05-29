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

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(formLogin -> formLogin.disable()) // disable FormLogin
                .httpBasic(httpBasic -> httpBasic.disable()) // disable httpBasic
                .csrf(csrf -> csrf.disable()) // disable csrf security
                .headers(headersConfig -> headersConfig.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))

                // Set session management to stateless
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Permission management per URL
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/sign-up", "/login", "/swagger-ui/index.html/**", "/api-docs", "/swagger-ui-custom.html",
                                "/v3/api-docs/**", "/swagger-ui/**", "/api-docs/**", "/swagger-ui.html", "/swagger-custom-ui.html").permitAll()
                        .anyRequest().authenticated())

                // OAuth2 Login Configuration
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint

                                        .baseUri("/spring/oauth2/authorization/google") //여기로 인증 요청이 들어옴. OAuth2 인증 요청이 시작되는 엔드포인트, Google 로그인 버튼을 눌렀을 때 이 엔드포인트로 이동
                                //.authorizationRequestRepository(cookieAuthorizationRequestRepository)
                        )
                        .redirectionEndpoint(redirectionEndpoint -> redirectionEndpoint
                                .baseUri("/spring/oauth2/code/google") //authorization url을 통한 인증 결과에 따라 redirect 되는 url. 인증이 정상적으로 진행되는 경우에는 callback url으로 사용자 인증 코드(authorization code)를 함께 반환됨.
                        )
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2LoginFailureHandler)
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(customOAuth2UserService))
                );

        // Add custom filters
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
        CustomJsonUsernamePasswordAuthenticationFilter filter = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        return new JwtAuthenticationProcessingFilter(jwtService, userRepository);
    }
}
